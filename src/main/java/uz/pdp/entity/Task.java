package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Task extends AbsUUIDEntity {

    private String name;

    private String description;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Priority priority;

    @ManyToOne
    private Project project;

    public Task(String name, String description, Status status, Category category, Priority priority, Project project, Task parentTask) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.priority = priority;
        this.project = project;
        this.parentTask = parentTask;
    }

    @ManyToOne
    private Task parentTask;

    @ManyToOne
    private Attachment file;

    private Timestamp startedDate;

    private Timestamp dueTime;

    private long estimate_time;

    private Timestamp activatedDate;

    @PreUpdate
    @PrePersist
    public void setEstimateTime(){
        this.estimate_time=dueTime.getTime()-startedDate.getTime();
    }
}

