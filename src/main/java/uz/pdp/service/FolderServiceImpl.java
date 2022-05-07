package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Folder;
import uz.pdp.entity.FolderUser;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;
import uz.pdp.payload.FolderUserDto;
import uz.pdp.repository.*;
import uz.pdp.service.interfaces.FolderService;

import java.util.Optional;
import java.util.UUID;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;
    @Autowired
    FolderUserRepository folderUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    FolderRepository folderRepository;

    @Override
    public ApiResponse addSpace(FolderDto dto) {
        if (folderRepository.existsByNameAndSpaceId(dto.getName(),dto.getId())){
            return  new ApiResponse("This Folder is already exist",false);
        }
        Folder folder = new Folder(
                dto.getName(),
                spaceRepository.findById(dto.getSpaceId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                dto.isArchived(),
                dto.getColor()
        );
        folderRepository.save(folder);
        return new ApiResponse("Success",true);
    }

    @Override
    public ApiResponse editSpace(FolderDto dto) {
        Optional<Folder> optional = folderRepository.findById(dto.getId());
        if (!optional.isPresent()){
            return  new ApiResponse("This Folder is not exist",false);
        }
        Folder folder = optional.get();
        folder.setSpace(spaceRepository.findById(dto.getSpaceId()).orElseThrow(()->new ResourceNotFoundException("ID")));
        folder.setArchived(dto.isArchived());
        folder.setColor(dto.getColor());
        folder.setName(dto.getName());
        folderRepository.save(folder);
        return new ApiResponse("Success",true);
    }

    @Override
    public ApiResponse deleteFolderUser(UUID id) {
        try {
            folderRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error",false);
        }
    }

    @Override
    public ApiResponse addFolderUser(FolderUserDto dto) {
        if(folderUserRepository.existsByFolderUserIdAndFolderId(dto.getUserId(),dto.getFolderId())){
            return new ApiResponse("This user is already added to this folder",false);
        }
        FolderUser folderUser = new FolderUser(
                folderRepository.findById(dto.getFolderId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                userRepository.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                workspaceRoleRepository.findById(dto.getWorkspaceRoleId()).orElseThrow(()->new ResourceNotFoundException("Id"))
        );
        folderUserRepository.save(folderUser);
        return new ApiResponse("Success",true);
    }
}
