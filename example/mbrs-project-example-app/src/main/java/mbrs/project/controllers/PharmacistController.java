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

import mbrs.project.model.Pharmacist;
import mbrs.project.services.PharmacistService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistController {

	@Autowired
	private PharmacistService pharmacistService;

	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.pharmacistService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		Pharmacist pharmacist = this.pharmacistService.getOne(id);

		return (pharmacist == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(pharmacist);
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody @Valid Pharmacist pharmacist) {
		Pharmacist pharmacistNew = this.pharmacistService.save(pharmacist);

		return (pharmacistNew == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(pharmacistNew);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return (this.pharmacistService.delete(id)) ? ResponseEntity.status(200).build()
				: ResponseEntity.badRequest().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Pharmacist pharmacist) {
		Pharmacist updatedPharmacist = this.pharmacistService.update(id, pharmacist);

		return (updatedPharmacist == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updatedPharmacist);

	}
}
