package ug.visituganda.visituganda.repository.serviceProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import ug.visituganda.visituganda.entity.company.CompanyRegister;
import ug.visituganda.visituganda.entity.User;

import java.util.Optional;

public interface CompanyRegisterRepository extends JpaRepository<CompanyRegister, Long> {

    Optional<CompanyRegister> findByOwner(User owner);

    Optional<CompanyRegister> findByOwnerId(Long ownerId); // ADD THIS
}