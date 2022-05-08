package uz.pdp.service.interfaces;

import uz.pdp.entity.User;
import uz.pdp.entity.Workspace;
import uz.pdp.payload.*;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {
    ApiResponse addWorkspace(WorkspaceDto dto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDto dto);

    ApiResponse changeOwnerWorkspace(Long id, UUID ownerId);

    ApiResponse deleteWorkspace(Long id);

    List<Workspace> getWorkspaces(User user);

    ApiResponse adOrEditOrRemoveWorkspaceUser(Long id, MemberDto dto);

    ApiResponse joinToWorkspace(Long id, MemberDto dto);

    List<MemberDto> getWorkspaceRoles(Long id, MemberDto dto);

    ApiResponse addWorkspaceRole(RoleDto dto);

    ApiResponse addOrRemovePermissions(PermissionDto dto);
}
