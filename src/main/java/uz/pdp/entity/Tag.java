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
public class Tag extends AbsUUIDEntity {
    private String name;

    private String color;

    @ManyToOne
    private Workspace workspace;
}
