package uz.pdp.service.interfaces;


import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.SpaceDto;

import java.util.UUID;

public interface SpaceService {

    ApiResponse addSpace(SpaceDto dto);

    ApiResponse editSpace(UUID workspaceId, SpaceDto dto);
}
