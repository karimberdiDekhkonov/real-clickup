package uz.pdp.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class ChecklistDto {
   private String name;

   private UUID taskId;
}
