package ${project_package}.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ${package}.${class_name?cap_first};
import ${project_package}.services.${class_name?cap_first}Service;

/**
 * [${.now}]
 * This file was generated based on the template "${.current_template_name}".
 * Changes to this file may cause incorrect behavior and will be lost if the code is regenerated.
 */
public class ${class_name?cap_first}BaseController {

	@Autowired
	protected ${class_name?cap_first}Service ${class_name?lower_case}Service;

	@GetMapping("")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(this.${class_name?lower_case}Service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		${class_name?cap_first} ${class_name?lower_case} = this.${class_name?lower_case}Service.getOne(id);
		return (${class_name?lower_case} == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(${class_name?lower_case});
	}

	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody @Valid ${class_name?cap_first} ${class_name?lower_case}) {
		${class_name?cap_first} ${class_name?lower_case}New = this.${class_name?lower_case}Service.save(${class_name?lower_case});
		
		return (${class_name?lower_case}New == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(${class_name?lower_case}New);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		return (this.${class_name?lower_case}Service.delete(id)) ? ResponseEntity.status(200).build() : ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ${class_name?cap_first} ${class_name?lower_case}) {
		${class_name?cap_first} updated${class_name?cap_first} = this.${class_name?lower_case}Service.update(id, ${class_name?lower_case});
		
		return (updated${class_name?cap_first} == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(updated${class_name?cap_first});
	}

}
