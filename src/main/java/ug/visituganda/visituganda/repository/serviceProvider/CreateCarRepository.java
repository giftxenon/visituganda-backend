package ug.visituganda.visituganda.repository.serviceProvider;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ug.visituganda.visituganda.modal.CreateCar;

import java.util.List;

@Repository
public interface CreateCarRepository extends JpaRepository<CreateCar, Long> {
    List<CreateCar> findByCompanyId(Long companyId);

}