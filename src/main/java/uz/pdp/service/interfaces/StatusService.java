package uz.pdp.service.interfaces;

import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.StatusDto;

import java.util.UUID;

public interface StatusService {
    ApiResponse addStatus(StatusDto dto);

    ApiResponse editStatus(UUID id, StatusDto dto);
}
