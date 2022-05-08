package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Tag;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
