package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.enums.ActionType;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class MemberDto {
    private UUID id;

    private UUID roleId;

    private String email;

    private String fullName;

    private String roelName;

    private Timestamp lastActive;

    private ActionType actionType;
}
