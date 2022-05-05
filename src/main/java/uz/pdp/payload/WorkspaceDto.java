package uz.pdp.payload;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class WorkspaceDto {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    private UUID attachmentId;
}
