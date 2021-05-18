package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "tour", uniqueConstraints = @UniqueConstraint(columnNames = {"tour"}))
public class Tour extends MyEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String tour;

	@OneToMany (mappedBy="tour", cascade = CascadeType.REFRESH)
	private List<Matches> matches;

	@OneToMany (mappedBy="tour")
	private List<Next> next;

	@Override
	public String toString () {
		return "Tour{}";
	}
}