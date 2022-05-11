package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.CheckList;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface ChecklistRepository extends JpaRepository<CheckList, UUID> {
    boolean existsByNameAndTaskId(String name, UUID task_id);

    @Transactional
    @Modifying
    void deleteById(UUID id);
}
