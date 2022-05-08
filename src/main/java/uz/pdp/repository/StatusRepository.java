package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Status;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);
}
