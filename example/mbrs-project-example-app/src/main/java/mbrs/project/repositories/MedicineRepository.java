package mbrs.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mbrs.project.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
