package mbrs.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mbrs.project.model.Pharmacy;

@Repository
public interface PharmacyRepository  extends JpaRepository<Pharmacy, Long> {

}
