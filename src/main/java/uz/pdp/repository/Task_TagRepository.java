package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Task_Tag;

import java.util.UUID;

public interface Task_TagRepository extends JpaRepository<Task_Tag, UUID> {
    boolean existsByTaskIdAndTagId(UUID task_id, UUID tag_id);
}
