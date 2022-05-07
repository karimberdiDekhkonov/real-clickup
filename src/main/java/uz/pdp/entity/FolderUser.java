package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FolderUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User folderUser;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private WorkspaceRole workspaceRole;
}
