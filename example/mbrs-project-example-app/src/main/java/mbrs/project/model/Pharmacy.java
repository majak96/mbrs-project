package mbrs.project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pharmacy")
public class Pharmacy {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", length = 100 ,nullable = false)
	private String name;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@OneToMany
	private Set<Pharmacist> employees;
	
	@ManyToMany
	private Set<Medicine> medicines;
	
	public Pharmacy() {
		super();
	}

	public Pharmacy(Long id, String name, String address, Set<Medicine> medicines, Set<Pharmacist> employees) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.medicines = medicines;
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}

	public Set<Pharmacist> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Pharmacist> employees) {
		this.employees = employees;
	}
	
	

}
