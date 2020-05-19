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

import mbrs.project.model.Pharmacy;
import mbrs.project.services.PharmacyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/pharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.pharmacyService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Pharmacy pharmacy = this.pharmacyService.getOne(id);
		
		return (pharmacy == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(pharmacy);
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody @Valid Pharmacy pharmacy) {
		Pharmacy pharmacyNew = this.pharmacyService.save(pharmacy);
		
		return (pharmacyNew == null) ? ResponseEntity.badRequest().build() :ResponseEntity.ok(pharmacyNew);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return (this.pharmacyService.delete(id)) ? ResponseEntity.status(200).build() : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Pharmacy pharmacy) {
		Pharmacy updatedPharmacy = this.pharmacyService.update(id, pharmacy);

		return (updatedPharmacy == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updatedPharmacy);

	}

}
