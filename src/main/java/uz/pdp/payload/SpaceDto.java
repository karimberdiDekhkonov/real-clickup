package uz.pdp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;
@Data
public class SpaceDto {
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String color;

    private Long workspaceId;

    private String initialLetter;

    private UUID avatarId;

    private UUID ownerId;
}
