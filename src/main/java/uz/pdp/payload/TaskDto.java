package uz.pdp.payload;

import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TaskDto {

    private String name;

    private String description;

    private UUID statusId;

    private UUID categoryId;

    private UUID priorityId;

    private UUID fileId;

    private UUID projectId;

    private UUID parentTaskId;

    private Timestamp startedDate;

    private Timestamp dueTime;
}
