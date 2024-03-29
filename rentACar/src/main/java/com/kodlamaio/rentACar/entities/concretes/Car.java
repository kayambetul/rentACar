package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	
	@Column(name = "description")
	
	@Size(min = 3, max = 50)
	private String description;

	
	@Column(name = "dailyPrice")
	private double dailyPrice;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@Column(name = "state")
	private int state;

	@Column(name = "plate")
	private String plate;
	
	@Column(name = "min_findex")
	private int minFindex;

	
	@Column(name = "kilometer")
	private int kilometer;

	@OneToMany(mappedBy = "car")
	List<Maintenance> maintenances;

	@OneToMany(mappedBy = "car")
	List<Rental> rentals;

}
