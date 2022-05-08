package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Status extends AbsUUIDEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Space space;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Category category;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private StatusType statusType;
}
