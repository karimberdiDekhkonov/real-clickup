package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Priority;

import java.util.UUID;

public interface PriorityRepository extends JpaRepository<Priority, UUID> {
}
