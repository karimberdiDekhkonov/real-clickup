package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.FolderUser;

import java.util.UUID;

public interface FolderUserRepository extends JpaRepository<FolderUser, UUID> {
    boolean existsByFolderUserIdAndFolderId(UUID userId,UUID folderId);
}
