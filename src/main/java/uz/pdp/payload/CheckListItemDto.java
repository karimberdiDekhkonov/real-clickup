package uz.pdp.payload;

import lombok.Data;
import java.util.UUID;

@Data
public class CheckListItemDto {
    private String name;

    private UUID checkListId;

    private boolean resolved;

    private UUID assignedUserId;
}
