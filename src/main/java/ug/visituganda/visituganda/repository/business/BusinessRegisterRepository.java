package ug.visituganda.visituganda.repository.business;



import org.springframework.data.jpa.repository.JpaRepository;
import ug.visituganda.visituganda.entity.Business.BusinessRegister;

public interface BusinessRegisterRepository extends JpaRepository<BusinessRegister, Long> {
}