package mbrs.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mbrs.project.model.Medicine;
import mbrs.project.repositories.MedicineRepository;

@Service
public class MedicineService {

	@Autowired
	private MedicineRepository medicineRepository;

	public Medicine getOne(Long id) {
		Optional<Medicine> medicine = this.medicineRepository.findById(id);
		if(medicine.isPresent())
			return medicine.get();
		return null;
	}

	public List<Medicine> findAll() {
		return this.medicineRepository.findAll();
	}

	public Medicine save(Medicine medicine) {
		return this.medicineRepository.save(medicine);
	}

	public boolean delete(Long id) {
		Medicine medicine = this.getOne(id);
		if (medicine != null) {
			this.medicineRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Medicine update(Long id, Medicine medicine) {
		Medicine oldMedicine = this.getOne(id);
		
		if(oldMedicine == null)
			return null;
		
		oldMedicine.setName(medicine.getName());
		oldMedicine.setPrice(medicine.getPrice());
		oldMedicine.setAvailableAmount(medicine.getAvailableAmount());
	
		return this.save(oldMedicine);
	}

}
