package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class TaskUser extends AbsUUIDEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Task task;
}
