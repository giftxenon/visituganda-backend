package ug.visituganda.visituganda.repository.serviceProvider;


import org.springframework.data.jpa.repository.JpaRepository;
import ug.visituganda.visituganda.modal.CarImage;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findByCarId(Long carId);
}
