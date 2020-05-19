package mbrs.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mbrs.project.model.Pharmacy;
import mbrs.project.repositories.PharmacyRepository;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;

	public Pharmacy getOne(Long id) {
		Optional<Pharmacy> pharmacy = this.pharmacyRepository.findById(id);
		if(pharmacy.isPresent())
			return pharmacy.get();
		return null;
	}

	public List<Pharmacy> findAll() {
		return this.pharmacyRepository.findAll();
	}

	public Pharmacy save(Pharmacy pharmacy) {
		return this.pharmacyRepository.save(pharmacy);
	}

	public boolean delete(Long id) {
		Pharmacy pharmacy = this.getOne(id);
		if (pharmacy != null) {
			this.pharmacyRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Pharmacy update(Long id, Pharmacy pharmacy) {
		Pharmacy oldPharmacy = this.getOne(id);
		
		if(oldPharmacy == null)
			return null;
		
		oldPharmacy.setAddress(pharmacy.getAddress());
		oldPharmacy.setName(pharmacy.getName());
		
		return this.save(oldPharmacy);
	}
}
