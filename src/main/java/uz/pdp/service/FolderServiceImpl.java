package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Folder;
import uz.pdp.entity.Space;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;
import uz.pdp.repository.FolderRepository;
import uz.pdp.repository.SpaceRepository;

import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService{
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
}
