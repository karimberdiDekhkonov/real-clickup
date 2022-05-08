package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Category;
import uz.pdp.entity.Status;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.StatusDto;
import uz.pdp.repository.CategoryRepository;
import uz.pdp.repository.ProjectRepository;
import uz.pdp.repository.SpaceRepository;
import uz.pdp.repository.StatusRepository;
import uz.pdp.service.interfaces.StatusService;

import java.util.Optional;
import java.util.UUID;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    StatusRepository statusRepository;

    @Override
    public ApiResponse addStatus(StatusDto dto) {
        if (statusRepository.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId())){
            return new ApiResponse("This status is already exist",false);
        }
        Status status = new Status(
                dto.getName(),
                spaceRepository.findById(dto.getSpaceId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                projectRepository.findById(dto.getProjectId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                dto.getColor(),
                null
                );
        statusRepository.save(status);
        return new ApiResponse("Successfully added",true);
    }

    @Override
    public ApiResponse editStatus(UUID id, StatusDto dto) {
        Optional<Status> optional = statusRepository.findById(id);
        if (!optional.isPresent()){
            return new ApiResponse("Status is not found",false);
        }
        Status status = optional.get();
        new Status(
                dto.getName(),
                spaceRepository.findById(dto.getSpaceId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                projectRepository.findById(dto.getProjectId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Id")),
                dto.getColor(),
                null
                );
        status.setColor(dto.getColor());
        status.setName(dto.getName());
        status.setCategory(categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Id")));
        status.setProject(projectRepository.findById(dto.getProjectId()).orElseThrow(()->new ResourceNotFoundException("Id")));
        status.setSpace(spaceRepository.findById(dto.getSpaceId()).orElseThrow(()->new ResourceNotFoundException("Id")));
        statusRepository.save(status);
        return new ApiResponse("Successfully edited",true);
    }
}
