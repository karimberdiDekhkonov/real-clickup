package uz.pdp.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class RoleDto {

    @NotNull
    private Long workspaceId;

    @NotNull
    private String name;

    private UUID extendsRoleId;
}
