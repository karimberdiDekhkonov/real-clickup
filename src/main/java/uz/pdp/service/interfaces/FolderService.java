package uz.pdp.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;
import uz.pdp.payload.FolderUserDto;

import java.util.UUID;

public interface FolderService {
    ApiResponse addSpace(FolderDto dto);

    ApiResponse editSpace(FolderDto dto);

    ApiResponse deleteFolderUser(UUID id);

    ApiResponse addFolderUser(FolderUserDto dto);
}
