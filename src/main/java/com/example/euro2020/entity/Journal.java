package com.example.euro2020.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Calendar;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table
public class Journal extends MyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "ip")
	private String ip;
	@Column(name = "browser")
	private String browser;
	@Column(name = "page")
	private String page;
	@Column(name = "referer")
	private String referer;
	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	@Column(name = "OS")
	private String os;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "usr", referencedColumnName = "id")
	private Users usr;

}