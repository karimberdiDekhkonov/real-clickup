package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Space;

import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {
    boolean existsByNameAndWorkspaceId(String name,Long workspaceId);
    Optional<Space> findByNameAndWorkspaceId(String name, Long workspaceId);
}
