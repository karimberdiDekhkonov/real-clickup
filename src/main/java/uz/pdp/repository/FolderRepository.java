package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.Folder;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    boolean existsByNameAndSpaceId(String name, UUID space_id);

    @Transactional
    @Modifying
    public void deleteById(UUID id);
}
