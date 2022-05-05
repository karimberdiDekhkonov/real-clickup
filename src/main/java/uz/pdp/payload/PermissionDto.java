package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.enums.PermissionActionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;
@Data
public class PermissionDto {
    private UUID workspaceRoleId;

    private String[] permissions;

    @Enumerated(EnumType.STRING)
    private PermissionActionType permissionActionType;
}
