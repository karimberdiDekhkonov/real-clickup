package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Project;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
