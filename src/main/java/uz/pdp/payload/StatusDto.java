package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.StatusType;
import uz.pdp.entity.template.AbsUUIDEntity;


import java.util.UUID;

@Data
public class StatusDto extends AbsUUIDEntity {

    private String name;

    private UUID spaceId;

    private UUID projectId;

    private UUID categoryId;

    private String color;

    private StatusType statusType;
}
