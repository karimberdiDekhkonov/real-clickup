package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.enums.StatusTypeName;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class StatusType extends AbsUUIDEntity {
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Status status;

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusTypeName defaultNames;
}
