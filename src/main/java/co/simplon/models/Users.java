package co.simplon.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.simplon.tools.Countries;
import co.simplon.tools.Positions;

@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
	@SequenceGenerator(name = "user", sequenceName = "user_seq", allocationSize = 1)
	private Long id;
	@Column(name = "firstName", nullable = false, length = 25)
	private String firstName;
	@Column(name = "lastName", nullable = false, length = 25)
	private String lastName;
	@Column(name = "birthDate", nullable = false)
	private Date dateOfBirth;
	@Column(name = "mail", nullable = false, length = 100)
	private String mail;
	@Column(name = "password", nullable = false, length = 25)
	@JsonIgnore
	private int password;
	@Column(name = "phone", nullable = false, length = 15)
	private int phone;
	@Column(name = "street", nullable = false, length = 100)
	private String street;
	@Column(name = "city", nullable = false, length = 165)
	private String city;
	@Column(name = "zipCode", nullable = false, length = 10)
	private String zipCode;
	@Column(name = "country", nullable = false)
	private Countries country;
	@Column(name = "position", nullable = false)
	private Positions position;
	@Column(name = "creationDate", nullable = false)
	private Date dateOfCreation;
	
	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "user")
	private List<Orders> listOrders = new ArrayList<>();
	
	/************************
	 * GETTERS AND SETTERS
	 ***********************/

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the password
	 */
	public int getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(int password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public int getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(int phone) {
		this.phone = phone;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the country
	 */
	public Countries getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Countries country) {
		this.country = country;
	}

	/**
	 * @return the position
	 */
	public Positions getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Positions position) {
		this.position = position;
	}

	/**
	 * @return the dateOfCreation
	 */
	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	/**
	 * @param dateOfCreation the dateOfCreation to set
	 */
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	/**
	 * @return the listOrders
	 */
	public List<Orders> getListOrders() {
		return listOrders;
	}

	/**
	 * @param listOrders the listOrders to set
	 */
	public void setListOrders(List<Orders> listOrders) {
		this.listOrders = listOrders;
	}
	
}
