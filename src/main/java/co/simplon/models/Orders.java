package co.simplon.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.simplon.tools.Status;

@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order")
	@SequenceGenerator(name = "order", sequenceName = "order_seq", allocationSize = 1)
	private Long id;
	@Column(name = "orderNumber", nullable = false, length = 50, unique = true)
	private String numberOrder;
	@Column(name = "creationDate", nullable = false)
	private Date dateOfCreation;
	@Column(name = "stepDate", nullable = false)
	private Date dateOfStep;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;
	@Column(name = "address", nullable = false, length = 400)
	private String address;
	
	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_user", foreignKey = @ForeignKey(name = "fk_user"), nullable = false)
	private Users user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "linkOrderContent")
	private List<OrdersContent> listOrderItems = new ArrayList<>();
	
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
	 * @return the orderNumber
	 */
	public String getNumberOrder() {
		return numberOrder;
	}

	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setNumberOrder(String numberOrder) {
		this.numberOrder = numberOrder;
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
	 * @return the dateOfStep
	 */
	public Date getDateOfStep() {
		return dateOfStep;
	}

	/**
	 * @param dateOfStep the dateOfStep to set
	 */
	public void setDateOfStep(Date dateOfStep) {
		this.dateOfStep = dateOfStep;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the listOrders
	 */
	public List<OrdersContent> getListOrderItems() {
		return listOrderItems;
	}

	/**
	 * @param listOrders the listOrders to set
	 */
	public void setListOrderItems(List<OrdersContent> listOrderItems) {
		this.listOrderItems = listOrderItems;
	}
	
}
