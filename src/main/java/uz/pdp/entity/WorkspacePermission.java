package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.enums.WorkspacePermissionName;
import uz.pdp.entity.enums.WorkspaceRoleName;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class WorkspacePermission extends AbsUUIDEntity {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private WorkspaceRole workspaceRole;

    @Enumerated(EnumType.STRING)
    private WorkspacePermissionName workspacePermissionName;



}
