package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.SpaceDto;
import uz.pdp.service.interfaces.SpaceService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/space")
public class SpaceController {
    @Autowired
    SpaceService service;

    @PostMapping("/addSpace")
    public HttpEntity<?>addSpace(@Valid @RequestBody SpaceDto dto){
        ApiResponse apiResponse = service.addSpace(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/addSpace")
    public HttpEntity<?>editSpace(@Valid @PathVariable UUID workspaceId, @RequestBody SpaceDto dto){
        ApiResponse apiResponse = service.editSpace(workspaceId,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
