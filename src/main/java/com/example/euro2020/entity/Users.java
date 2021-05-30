package com.example.euro2020.entity;

import com.example.euro2020.security.model.enums.Roles;
import com.example.euro2020.security.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usrs", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
public class Users extends MyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "login")
	private String login;
	@Column(name = "password")
	private String password;
	@Column(name = "date_reg")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateRegistration;
	@Column(name = "date_authorization_last")
	private Timestamp dateAuthorizationLast;
	@Column(name = "date_authorization")
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar dateAuthorization;
	@Column(name = "ip")
	private String ip;
	@Column(name = "browser")
	private String browser;
	@Column(name = "OS")
	private String os;
	@Column(name = "display")
	private boolean display;

	@Column(name = "roles")
	@Enumerated(EnumType.STRING)
	private Roles roles;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;


	@OneToMany(mappedBy = "usr", fetch = FetchType.LAZY)
	private List<Next> next;

	@OneToMany(mappedBy = "usr", fetch = FetchType.LAZY)
	private List<Prognosis> prognosis;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "champion",
		joinColumns =
			{@JoinColumn(name = "id_users", referencedColumnName = "id")},
		inverseJoinColumns =
			{@JoinColumn(name = "id_teams", referencedColumnName = "id")})
	private Teams champion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "bombardier",
		joinColumns =
			{@JoinColumn(name = "id_users", referencedColumnName = "id")},
		inverseJoinColumns =
			{@JoinColumn(name = "id_player", referencedColumnName = "id")})
	private Player bombardier;

	@OneToMany(mappedBy = "usr", fetch = FetchType.LAZY)
	private List<PlacingTeam> placings;

	@OneToMany(mappedBy = "usr", fetch = FetchType.LAZY)
	private List<Journal> journal;

	@OneToOne(mappedBy = "usr")
	private Rating rating;

	@Override
	public String toString () {
		return "Users{" +
			"id=" + id +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", login='" + login + '\'' +
			'}';
	}
}