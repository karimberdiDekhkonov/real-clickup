package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;

public interface FolderService {
    ApiResponse addSpace(FolderDto dto);

    ApiResponse editSpace(FolderDto dto);
}
