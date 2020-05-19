package mbrs.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="medicine")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100 ,nullable = false)
	private String name;

	@Column(name = "price",  precision = 8, scale = 2, nullable = false)
	private Double price;

	@Column(name = "availableAmount", nullable = false)
	private Integer availableAmount;

	public Medicine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Medicine(Long id, String name, Double price, Integer availableAmount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.availableAmount = availableAmount;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Integer availableAmount) {
		this.availableAmount = availableAmount;
	}

}
