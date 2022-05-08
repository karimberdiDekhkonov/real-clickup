package uz.pdp.service.interfaces;

import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CommentDto;
import uz.pdp.payload.TaskDto;

import java.util.UUID;

public interface TaskService {

    ApiResponse addTask(TaskDto dto);

    ApiResponse addFile(UUID taskId, UUID fileId);

    ApiResponse deleteFile(UUID taskId);

    ApiResponse addTagToTask(UUID taskId, UUID tagId);

    ApiResponse addComment(CommentDto dto);
}