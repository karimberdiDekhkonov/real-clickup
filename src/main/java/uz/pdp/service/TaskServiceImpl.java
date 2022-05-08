package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Attachment;
import uz.pdp.entity.Comment;
import uz.pdp.entity.Task;
import uz.pdp.entity.Task_Tag;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.CommentDto;
import uz.pdp.payload.TaskDto;
import uz.pdp.repository.*;
import uz.pdp.service.interfaces.TaskService;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PriorityRepository priorityRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    Task_TagRepository task_tagRepository;
    @Autowired
    TaskRepository taskRepository;

    @Override
    public ApiResponse addTask(TaskDto dto) {
        if (taskRepository.existsByNameAndProjectId(dto.getName(), dto.getProjectId())){
            return new ApiResponse("This task is already exist",false);
        }
        Task task = new Task(
                dto.getName(),
                dto.getDescription(),
                statusRepository.findById(dto.getStatusId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                priorityRepository.findById(dto.getPriorityId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                projectRepository.findById(dto.getProjectId()).orElseThrow(()-> new ResourceNotFoundException("Id")),
                dto.getParentTaskId()==null?null:taskRepository.findById(dto.getParentTaskId()).orElseThrow(()-> new ResourceNotFoundException("Id"))
        );
        if (dto.getStartedDate()!=null){
            task.setStartedDate(dto.getStartedDate());
        }
        if (dto.getDueTime()!=null){
            task.setDueTime(dto.getDueTime());
        }
        taskRepository.save(task);
        return new ApiResponse("Success",true);
    }

    @Override
    public ApiResponse addFile(UUID taskId, UUID fileId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()){
            return new ApiResponse("Task is not found",false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(fileId);
        if (!optionalAttachment.isPresent()){
            return new ApiResponse("File is not found",false);
        }
        Task task = optionalTask.get();
        task.setFile(optionalAttachment.get());
        taskRepository.save(task);
        return new ApiResponse("Successfully added",true);
    }

    @Override
    public ApiResponse deleteFile(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()){
            return new ApiResponse("Task is not found",false);
        }
        Task task = optionalTask.get();
        task.setFile(null);
        return new ApiResponse("Successfully deleted",true);
    }

    @Override
    public ApiResponse addTagToTask(UUID taskId, UUID tagId) {
        if (task_tagRepository.existsByTaskIdAndTagId(taskId,tagId)){
            return new ApiResponse("This task has already this tag",false);
        }
        task_tagRepository.save(new Task_Tag(
           taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Id")),
           tagRepository.findById(tagId).orElseThrow(()-> new ResourceNotFoundException("Id"))
        ));
        return null;
    }

    @Override
    public ApiResponse addComment(CommentDto dto) {
        Optional<Task> optional = taskRepository.findById(dto.getTaskId());
        if (!optional.isPresent()){
            return  new ApiResponse("Task is not found",false);
        }
        commentRepository.save(new Comment(
                dto.getName(),
                optional.get()
        ));
        return new ApiResponse("Successfully added",true);
    }
}
