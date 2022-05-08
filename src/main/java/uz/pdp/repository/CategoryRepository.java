package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
