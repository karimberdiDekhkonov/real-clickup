package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.enums.ActionType;

import java.util.UUID;

@Data
public class MemberDto {
    private UUID id;

    private UUID roleId;

    private ActionType actionType;
}
