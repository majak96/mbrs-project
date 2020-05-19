package mbrs.project.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mbrs.project.model.Medicine;
import mbrs.project.services.MedicineService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/medicine", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicineController {

	@Autowired
	private MedicineService medicineService;

	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.medicineService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Medicine medicine = this.medicineService.getOne(id);
		return (medicine == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(medicine);
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody @Valid Medicine medicine) {
		Medicine medicineNew = this.medicineService.save(medicine);
		
		return (medicineNew == null) ? ResponseEntity.badRequest().build() :ResponseEntity.ok(medicineNew);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return (this.medicineService.delete(id)) ? ResponseEntity.status(200).build() : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Medicine medicine) {
		Medicine updatedMedicine = this.medicineService.update(id, medicine);
		
		return (updatedMedicine == null) ? ResponseEntity.badRequest().build() :ResponseEntity.ok(updatedMedicine);
			
	}

}
