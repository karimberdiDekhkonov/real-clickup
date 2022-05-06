package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Folder;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);
}
