package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.StatusDto;
import uz.pdp.service.interfaces.StatusService;

import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @PostMapping("/addStatus")
    public HttpEntity<?>addStatus(@RequestBody StatusDto dto){
        ApiResponse apiResponse = statusService.addStatus(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/editStatus/{id}")
    public HttpEntity<?>editStatus(@PathVariable UUID id, @RequestBody StatusDto dto){
        ApiResponse apiResponse = statusService.editStatus(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
