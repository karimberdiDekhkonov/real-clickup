package uz.pdp.entity.template;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class AbsUUIDEntity extends AbsMainEntity{
    @Id
    @GeneratedValue//(generator = "uuid2")
//    @Type(type = "org.hibernate.type.PostgresUUIDType")
//    @GenericGenerator(name = "uuid2",strategy = "or.hibernate.id.UUIDGenerator")
    private UUID id;
}
