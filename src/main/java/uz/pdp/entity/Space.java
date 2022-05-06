package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Space extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private Workspace workspace;

    private String initialLetter;

    public Space(String name, String color, Workspace workspace, Attachment avatarId, User ownerId) {
        this.name = name;
        this.color = color;
        this.workspace = workspace;
        this.avatarId = avatarId;
        this.ownerId = ownerId;
    }

    @OneToOne
    private Attachment avatarId;

    @OneToOne
    private User ownerId;

    @PreUpdate
    @PrePersist
    public void myMethod(){
        this.initialLetter=this.name.toUpperCase(Locale.ROOT).substring(0,1);
    }


}
