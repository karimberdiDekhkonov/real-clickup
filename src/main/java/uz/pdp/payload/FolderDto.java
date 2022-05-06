package uz.pdp.payload;

import lombok.Data;
import java.util.UUID;

@Data
public class FolderDto {
    private UUID id;

    private String name;

    private UUID spaceId;

    private boolean archived;

    private String color;
}
