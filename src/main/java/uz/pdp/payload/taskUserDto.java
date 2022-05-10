package uz.pdp.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class taskUserDto {
    private UUID userId;
    private UUID taskId;
}
