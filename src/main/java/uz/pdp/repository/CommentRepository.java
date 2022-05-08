package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
