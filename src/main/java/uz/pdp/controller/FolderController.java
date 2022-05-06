package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;
import uz.pdp.payload.SpaceDto;
import uz.pdp.service.FolderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    @Autowired
    FolderService folderService;

    @PostMapping
    public HttpEntity<?> addFolder(@RequestBody FolderDto dto) {
        ApiResponse apiResponse = folderService.addSpace(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping
    public HttpEntity<?> editFolder(@PathVariable UUID folderId, @RequestBody FolderDto dto) {
        ApiResponse apiResponse = folderService.editSpace(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
