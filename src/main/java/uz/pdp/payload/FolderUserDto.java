package uz.pdp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class FolderUserDto {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID folderId;

    @NotNull
    UUID workspaceRoleId;
}
