package ug.visituganda.visituganda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.visituganda.visituganda.entity.Business.BusinessPost;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.util.List;

public interface BusinessRepository
        extends JpaRepository<BusinessPost, Long> {

    List<BusinessPost> findByCategory(BusinessCategory category);
}