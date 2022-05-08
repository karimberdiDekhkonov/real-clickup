package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Task_Tag extends AbsUUIDEntity {
    @ManyToOne
    private Task task;

    @ManyToOne
    private Tag tag;

}
