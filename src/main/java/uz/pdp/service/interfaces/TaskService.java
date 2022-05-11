package uz.pdp.service.interfaces;

import uz.pdp.payload.*;

import java.util.UUID;

public interface TaskService {

    ApiResponse addTask(TaskDto dto);

    ApiResponse addFile(UUID taskId, UUID fileId);

    ApiResponse deleteFile(UUID taskId);

    ApiResponse addTagToTask(UUID taskId, UUID tagId);

    ApiResponse addComment(CommentDto dto);

    ApiResponse assignTaskUser(taskUserDto dto);

    ApiResponse deleteTaskUser(taskUserDto dto);

    ApiResponse addCheckList(ChecklistDto dto);

    ApiResponse editCheckList(UUID id, String name);

    ApiResponse deleteCheckList(UUID id);

    ApiResponse addCheckListItem(CheckListItemDto dto);

    ApiResponse deleteCheckListItem(String name, UUID checklistId);
}
