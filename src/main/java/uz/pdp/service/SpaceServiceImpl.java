package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import uz.pdp.entity.Space;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.SpaceDto;
import uz.pdp.repository.AttachmentRepository;
import uz.pdp.repository.SpaceRepository;
import uz.pdp.repository.UserRepository;
import uz.pdp.repository.WorkspaceRepository;
import uz.pdp.service.interfaces.SpaceService;

import java.util.Optional;
import java.util.UUID;

public class SpaceServiceImpl implements SpaceService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    SpaceRepository spaceRepository;

    @Override
    public ApiResponse addSpace(SpaceDto dto) {
        if (spaceRepository.existsByNameAndWorkspaceId(dto.getName(), dto.getWorkspaceId())) {
            return new ApiResponse("This Workspace is already exist !", false);
        }
        Space space = new Space(
                dto.getName(),
                dto.getColor(),
                workspaceRepository.findById(dto.getWorkspaceId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                attachmentRepository.findById(dto.getAvatarId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                userRepository.findById(dto.getOwnerId()).orElseThrow(()->new ResourceNotFoundException("Id"))
        );
        spaceRepository.save(space);
        return new ApiResponse("Success",true);
    }

    @Override
    public ApiResponse editSpace(UUID workspaceId, SpaceDto dto) {
        Optional<Space> optional = spaceRepository.findById(dto.getId());
        if (!optional.isPresent()){
            return new ApiResponse("Id is not found",false);
        }
        Space space = optional.get();
        space.setColor(dto.getColor());
        space.setAvatarId(attachmentRepository.findById(dto.getAvatarId()).orElseThrow(()->new ResourceNotFoundException("Id")));
        space.setName(dto.getName());
        space.setWorkspace(workspaceRepository.findById(dto.getWorkspaceId()).orElseThrow(()->new ResourceNotFoundException("Id")));
        space.setOwnerId(userRepository.findById(dto.getOwnerId()).orElseThrow(()->new ResourceNotFoundException("Id")));

        spaceRepository.save(space);
        return new ApiResponse("Success",true);
    }
}
