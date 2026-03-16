package ug.visituganda.visituganda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.visituganda.visituganda.entity.company.CompanyPost;
import ug.visituganda.visituganda.modal.enums.BusinessCategory;

import java.util.List;

public interface CompanyRepository
        extends JpaRepository<CompanyPost, Long> {

    List<CompanyPost> findByCategory(BusinessCategory category);
}