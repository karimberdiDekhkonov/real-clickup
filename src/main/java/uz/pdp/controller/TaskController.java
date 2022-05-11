package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.*;
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

    @PostMapping("/assignTaskUser")
    public HttpEntity<?> assignTaskUser(@RequestBody taskUserDto dto){
        ApiResponse apiResponse = taskService.assignTaskUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/deleteTaskUser")
    public HttpEntity<?> deleteTaskUser(@RequestBody taskUserDto dto){
        ApiResponse apiResponse = taskService.deleteTaskUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addChecklist")
    public HttpEntity<?>addChecklist(@RequestBody ChecklistDto dto){
        ApiResponse apiResponse = taskService.addCheckList(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/editCheckList/{id}/{name}")
    public HttpEntity<?>editCheckList(@PathVariable UUID id,@PathVariable String name){
        ApiResponse apiResponse = taskService.editCheckList(id,name);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/deleteCheckList/{id}")
    public HttpEntity<?>deleteCheckList(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.deleteCheckList(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addCheckListItem")
    public HttpEntity<?> addCheckListItem(@RequestBody CheckListItemDto dto){
        ApiResponse apiResponse = taskService.addCheckListItem(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/deleteCheckListItem/{name}/{checklistId}")
    public HttpEntity<?>deleteCheckListItem(@PathVariable String name,@PathVariable UUID checklistId){
        ApiResponse apiResponse = taskService.deleteCheckListItem(name,checklistId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
