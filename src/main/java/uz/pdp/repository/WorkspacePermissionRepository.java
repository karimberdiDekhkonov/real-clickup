package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.WorkspacePermission;

import javax.transaction.Transactional;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {
    @Transactional
    @Modifying
    public void deleteByWorkspacePermissionNameAndAndWorkspaceRoleId(String permissionName,UUID workspaceRoleId);
}
