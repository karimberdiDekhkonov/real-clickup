package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.TaskUser;

import javax.transaction.Transactional;
import java.util.UUID;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {
    boolean existsByUserIdAndTaskId(UUID user_id, UUID task_id);

    @Transactional
    @Modifying
    void deleteByUserIdAndTaskId(UUID user_id, UUID task_id);
}
