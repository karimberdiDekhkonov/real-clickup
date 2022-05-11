package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.pdp.entity.CheckListItem;

import javax.transaction.Transactional;
import java.util.UUID;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, UUID> {
    boolean existsByNameAndCheckListId(String name, UUID checkList_id);


    @Transactional
    @Modifying
    void deleteByNameAndCheckListId(String name, UUID checkList_id);
}
