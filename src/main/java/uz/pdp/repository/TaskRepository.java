package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    boolean existsByNameAndProjectId(String name, UUID project_id);
}
