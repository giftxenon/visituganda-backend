package ug.visituganda.visituganda.repository;

import ug.visituganda.visituganda.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}