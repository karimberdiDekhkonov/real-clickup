package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.FolderDto;
import uz.pdp.payload.FolderUserDto;
import uz.pdp.service.interfaces.FolderService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    @Autowired
    FolderService folderService;

    @PostMapping("/addFolder")
    public HttpEntity<?> addFolder(@RequestBody FolderDto dto) {
        ApiResponse apiResponse = folderService.addSpace(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/editFolder/{id}")
    public HttpEntity<?> editFolder(@PathVariable UUID id, @RequestBody FolderDto dto) {
        ApiResponse apiResponse = folderService.editSpace(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/deleteFolderUser/{id}")
    HttpEntity<?>deleteFolderUser(@PathVariable UUID id){
        ApiResponse apiResponse = folderService.deleteFolderUser(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addFolderUser")
    public HttpEntity<?>addFolderUser(@Valid @RequestBody FolderUserDto dto){
        ApiResponse apiResponse = folderService.addFolderUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
