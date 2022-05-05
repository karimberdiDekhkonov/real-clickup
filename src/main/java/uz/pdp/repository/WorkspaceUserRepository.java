package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.WorkspaceUser;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {
    Optional<WorkspaceUser>findByUserId(UUID userId);
    Optional<WorkspaceUser>findByUserIdAndWorkspaceId(UUID userId,Long workspaceId);

    @Transactional
    @Modifying
    void  deleteByUserIdAndWorkspaceId(UUID userId,Long workspaceId);
}
