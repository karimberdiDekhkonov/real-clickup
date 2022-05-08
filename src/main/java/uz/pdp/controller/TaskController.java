package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CommentDto;
import uz.pdp.payload.TaskDto;
import uz.pdp.service.interfaces.TaskService;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping("/addTask")
    public HttpEntity<?>addTask(@RequestBody TaskDto dto){
        ApiResponse apiResponse = taskService.addTask(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/addFile/{taskId}/{fileId}")
    public HttpEntity<?>addFile(@PathVariable UUID taskId,@PathVariable UUID fileId){
        ApiResponse apiResponse = taskService.addFile(taskId,fileId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/deleteFile/{taskId}")
    public HttpEntity<?>deleteFile(@PathVariable UUID taskId){
        ApiResponse apiResponse = taskService.deleteFile(taskId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addTagToTask/{taskId}/{tagId}")
    public HttpEntity<?> addTagToTask(@PathVariable UUID taskId,@PathVariable UUID tagId){
        ApiResponse apiResponse = taskService.addTagToTask(taskId,tagId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addComment")
    public HttpEntity<?> addComment(@RequestBody CommentDto dto){
        ApiResponse apiResponse = taskService.addComment(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
