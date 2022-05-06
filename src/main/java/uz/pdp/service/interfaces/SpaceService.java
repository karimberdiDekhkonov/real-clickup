package uz.pdp.service.interfaces;

import org.springframework.stereotype.Service;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.SpaceDto;

import java.util.UUID;

@Service
public interface SpaceService {

    ApiResponse addSpace(SpaceDto dto);

    ApiResponse editSpace(UUID workspaceId, SpaceDto dto);
}
