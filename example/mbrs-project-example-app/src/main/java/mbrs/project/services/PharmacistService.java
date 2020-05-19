package mbrs.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mbrs.project.model.Pharmacist;
import mbrs.project.repositories.PharmacistRepository;

@Service
public class PharmacistService {
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	public Pharmacist getOne(Long id) {
		Optional<Pharmacist> pharmacist = this.pharmacistRepository.findById(id);
		if(pharmacist.isPresent())
			return pharmacist.get();
		return null;
	}
	
	public List<Pharmacist> findAll() {
		return this.pharmacistRepository.findAll();
	}
	
	public Pharmacist save(Pharmacist pharmacist) {
		return this.pharmacistRepository.save(pharmacist);
	}
	
	public boolean delete(Long id) {
		Pharmacist pharmacist = this.getOne(id);
		if (pharmacist != null) {
			this.pharmacistRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Pharmacist update(Long id, Pharmacist pharmacist) {
		Pharmacist oldPharmacist = this.getOne(id);
		
		if(oldPharmacist == null)
			return null;
		
		oldPharmacist.setAddress(pharmacist.getAddress());
		oldPharmacist.setDateOfBirth(pharmacist.getDateOfBirth());
		oldPharmacist.setFirstName(pharmacist.getFirstName());
		oldPharmacist.setLastName(pharmacist.getLastName());
		oldPharmacist.setPhoneNumber(pharmacist.getPhoneNumber());
		oldPharmacist.setEmail(pharmacist.getEmail());

		return this.save(oldPharmacist);
	}


}
