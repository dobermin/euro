package com.example.euro2020.entity;

import com.example.euro2020.security.model.Role;
import com.example.euro2020.security.model.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usrs", uniqueConstraints = {@UniqueConstraint(columnNames = "login")})
public class Users extends MyEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
//	@Column(name = "date_reg")
//	private String dateRegistration;
	@Column(name = "date_reg")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateRegistration;
//	@Column(name = "date_auth_last")
//	private String dateAuthorizationLast;
	@Column(name = "date_authorization_last")
	private Timestamp dateAuthorizationLast;
//	@Column(name = "date_authorization")
//	private String dateAuthorization;
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

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(name = "users_roles",
		joinColumns = @JoinColumn(name = "users_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany (mappedBy="user", fetch=FetchType.LAZY)
	private List<Next> next;

	@OneToMany (mappedBy="user", fetch=FetchType.LAZY)
	private List<Prognosis> prognosis;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "champion",
		joinColumns =
			{ @JoinColumn(name = "id_users", referencedColumnName = "id") },
		inverseJoinColumns =
			{ @JoinColumn(name = "id_teams", referencedColumnName = "id") })
	private Teams champion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "bombardier",
		joinColumns =
			{ @JoinColumn(name = "id_users", referencedColumnName = "id") },
		inverseJoinColumns =
			{ @JoinColumn(name = "id_player", referencedColumnName = "id") })
	private Player bombardier;

	@OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
	private List<Placing> placings;

	@OneToOne(mappedBy = "user", fetch=FetchType.LAZY)
	private Rating rating;

	public List<Next> getNext () {
		return next;
	}

	public void setNext (List<Next> next) {
		this.next = next;
	}

	public List<Prognosis> getPrognosis () {
		return prognosis;
	}

	public void setPrognosis (List<Prognosis> prognosis) {
		this.prognosis = prognosis;
	}

	public Teams getChampion () {
		return champion;
	}

	public void setChampion (Teams champion) {
		this.champion = champion;
	}

	public Player getBombardier () {
		return bombardier;
	}

	public void setBombardier (Player bombardier) {
		this.bombardier = bombardier;
	}

	public List<Placing> getPlacings () {
		return placings;
	}

	public void setPlacings (List<Placing> placings) {
		this.placings = placings;
	}

	public Rating getRating () {
		return rating;
	}

	public void setRating (Rating rating) {
		this.rating = rating;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getFirstName () {
		return firstName;
	}

	public void setFirstName (String firstName) {
		this.firstName = firstName;
	}

	public String getLastName () {
		return lastName;
	}

	public void setLastName (String lastName) {
		this.lastName = lastName;
	}

	public String getLogin () {
		return login;
	}

	public void setLogin (String login) {
		this.login = login;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public Calendar getDateRegistration () {
		return dateRegistration;
	}

	public void setDateRegistration (Calendar dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public Timestamp getDateAuthorizationLast () {
		return dateAuthorizationLast;
	}

	public void setDateAuthorizationLast (Timestamp dateAuthorizationLast) {
		this.dateAuthorizationLast = dateAuthorizationLast;
	}

	public Calendar getDateAuthorization () {
		return dateAuthorization;
	}

	public void setDateAuthorization (Calendar dateAuthorization) {
		this.dateAuthorization = dateAuthorization;
	}

	public String getIp () {
		return ip;
	}

	public void setIp (String ip) {
		this.ip = ip;
	}

	public String getBrowser () {
		return browser;
	}

	public void setBrowser (String browser) {
		this.browser = browser;
	}

	public String getOs () {
		return os;
	}

	public void setOs (String os) {
		this.os = os;
	}

	public boolean isDisplay () {
		return display;
	}

	public void setDisplay (boolean display) {
		this.display = display;
	}

	public Status getStatus () {
		return status;
	}

	public void setStatus (Status status) {
		this.status = status;
	}

	public Set<Role> getRole () {
		return roles;
	}

	public void setRole (Set<Role> role) {
		this.roles = role;
	}
}