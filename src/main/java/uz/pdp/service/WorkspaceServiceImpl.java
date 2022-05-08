package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.entity.*;
import uz.pdp.entity.enums.ActionType;
import uz.pdp.entity.enums.WorkspacePermissionName;
import uz.pdp.entity.enums.WorkspaceRoleName;
import uz.pdp.payload.*;
import uz.pdp.repository.*;
import uz.pdp.security.JwtProvider;
import uz.pdp.service.interfaces.WorkspaceService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    WorkspaceUserRepository workspaceUserRepository;
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    WorkspaceRepository workspaceRepository;


    @Override
    public ApiResponse addWorkspace(WorkspaceDto dto, User user) {
        if (workspaceRepository.existsByNameAndOwnerId(dto.getName(), user.getId())) {
            return new ApiResponse("You have already such Workspace", false);
        }
        //ADD WORKSPACE
        Workspace workspace = new Workspace(
                dto.getName(),
                dto.getColor(),
                user,
                dto.getAttachmentId() == null ? null : attachmentRepository.findById(dto.getAttachmentId()).orElseThrow(() -> new ResourceNotFoundException("attachment"))
        );
        workspaceRepository.save(workspace);
        //ADD WORKSPACE ROLE
        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                workspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));

        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));


        //ADD WORKSPACE ROLE PERMISSIONS
        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        WorkspacePermissionName[] permissionNames = WorkspacePermissionName.values();
        for (WorkspacePermissionName permissionName : permissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    ownerRole,
                    permissionName);
            workspacePermissions.add(workspacePermission);

            if (permissionName.getWorkSpaceRoleName().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissions.add(new WorkspacePermission(
                        adminRole,
                        permissionName));
            } else if (permissionName.getWorkSpaceRoleName().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissions.add(new WorkspacePermission(
                        memberRole,
                        permissionName));
            } else if (permissionName.getWorkSpaceRoleName().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissions.add(new WorkspacePermission(
                        guestRole,
                        permissionName));
            }
        }
        workspacePermissionRepository.saveAll(workspacePermissions);

        //ADD WORKSPACE USER
        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));


        return new ApiResponse("Workspace is successfully created", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDto dto) {
        Optional<Workspace> optional = workspaceRepository.findById(id);
        if (!optional.isPresent()) {
            return new ApiResponse("Workspace is not found", false);
        }
        Workspace workspace = optional.get();
        if (dto.getName() != null) {
            workspace.setName(dto.getName());
        }
        workspace.setColor(dto.getColor());
        if (dto.getColor() != null) {
            workspace.setColor(dto.getColor());
        }
        workspace.setName(dto.getName());
        if (dto.getAttachmentId() != null) {
            Optional<Attachment> attachmentOptional = attachmentRepository.findById(dto.getAttachmentId());
            attachmentOptional.ifPresent(workspace::setAvatar);
        }

        workspaceRepository.save(workspace);
        return new ApiResponse("Successfully changed", true);
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace is not found", false);
        }
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByUserId(ownerId);
        if (!optionalWorkspaceUser.isPresent()) {
            return new ApiResponse("Workspace User is not found", false);
        }
        WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
        Workspace workspace = optionalWorkspace.get();

        workspace.setOwner(userRepository.findById(ownerId).orElseThrow(() -> new ResourceNotFoundException("User")));
        workspaceRepository.save(workspace);

        workspaceRoleRepository.findByName(WorkspaceRoleName.ROLE_OWNER.name());
        workspaceUser.setWorkspaceRole(workspaceRoleRepository.findByName(WorkspaceRoleName.ROLE_OWNER.name()).orElseThrow(() -> new ResourceNotFoundException("WorkspaceRole")));
        workspaceUser.setWorkspace(workspace);
        workspaceUserRepository.save(workspaceUser);

        return new ApiResponse("Successfully changed", true);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Id is not found", false);
        }
    }

    @Override
    public List<Workspace> getWorkspaces(User user) {
        return workspaceRepository.findByOwnerId(user.getId());
    }

    @Override
    public ApiResponse adOrEditOrRemoveWorkspaceUser(Long id, MemberDto dto) {
        if (dto.getActionType().equals(ActionType.ADD)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.save(new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id")),
                    userRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("id")),
                    workspaceRoleRepository.getById(dto.getRoleId()),
                    new Timestamp(System.currentTimeMillis()),
                    null));


            String token = jwtProvider.generateToken(workspaceUser.getUser().getEmail());
            String code = "http://localhost:8080/api/workspace/join?" + workspaceUser.getWorkspace().getId() + "\nAuthorization: Bearer " + token;
            sendMail(workspaceUser.getUser().getEmail(), code);

        } else if (dto.getActionType().equals(ActionType.EDIT)) {
            Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByUserIdAndWorkspaceId(dto.getId(), id);
            if (!optionalWorkspaceUser.isPresent()) {
                return new ApiResponse("Id is not found", false);
            }
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Id")));
            workspaceUserRepository.save(workspaceUser);

        } else if (dto.getActionType().equals(ActionType.REMOVE)) {
            workspaceUserRepository.deleteByUserIdAndWorkspaceId(dto.getId(), id);
        }
        return new ApiResponse("Success", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, MemberDto dto) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByUserIdAndWorkspaceId(dto.getId(), id);
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Not found", false);
    }

    @Override
    public List<MemberDto> getWorkspaceRoles(Long id, MemberDto dto) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId(id);
        List<MemberDto> members = new ArrayList<>();
        for (WorkspaceUser workspaceUser : workspaceUsers) {
            MemberDto memberDto = new MemberDto();
            memberDto.setId(workspaceUser.getUser().getId());
            memberDto.setFullName(workspaceUser.getUser().getFullName());
            memberDto.setEmail(workspaceUser.getUser().getEmail());
            memberDto.setRoelName(workspaceUser.getWorkspaceRole().getName());
            memberDto.setLastActive(workspaceUser.getUser().getLastActive());
            members.add(memberDto);
        }
        return members;
    }

    @Override
    public ApiResponse addWorkspaceRole(RoleDto dto) {
        try {
            workspaceRoleRepository.save(new WorkspaceRole(
                    workspaceRepository.findById(dto.getWorkspaceId()).orElseThrow(() -> new ResourceNotFoundException("Id")),
                    dto.getName(),
                    null
            ));
            return new ApiResponse("Successfully added", true);
        } catch (ResourceNotFoundException e) {
            return new ApiResponse("Error", false);
        }
    }

    @Override
    public ApiResponse addOrRemovePermissions(PermissionDto dto) {
        if (dto.getPermissionActionType().name().equals("ADD")) {
            Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getWorkspaceRoleId());
            if (!optionalWorkspaceRole.isPresent()) {
                return new ApiResponse("WorkspaceRole is not found", false);
            }
            WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
            List<WorkspacePermission> workspacePermissionList = new ArrayList<>();

            WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();
            for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
                String[] permissions = dto.getPermissions();
                for (String permission : permissions) {
                    if (permission.equals(workspacePermissionName.name())) {
                        workspacePermissionList.add(new WorkspacePermission(
                                workspaceRole,
                                workspacePermissionName
                        ));
                    }
                }
            }
            workspacePermissionRepository.saveAll(workspacePermissionList);
        } else if (dto.getPermissionActionType().name().equals("REMOVE")) {
            Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getWorkspaceRoleId());
            if (!optionalWorkspaceRole.isPresent()) {
                return new ApiResponse("WorkspaceRole is not found", false);
            }
            String[] permissions = dto.getPermissions();
            for (String permission : permissions) {
                workspacePermissionRepository.deleteByWorkspacePermissionNameAndAndWorkspaceRoleId(permission, dto.getWorkspaceRoleId());
            }

        } else {
            return new ApiResponse("Error", false);
        }
        return new ApiResponse("Success", true);
    }

    public Boolean sendMail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Invitation message");
            mailMessage.setFrom("PDP.UZ");
            mailMessage.setTo(sendingEmail);
            mailMessage.setText(emailCode);
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
