package co.simplon.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "ordersContent")
public class OrdersContent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordersContent")
	@SequenceGenerator(name = "ordersContent", sequenceName = "ordersContent_seq", allocationSize = 1)
	private Long id;
	@Column(name = "quantity", nullable = false)
	private int quantity;

	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_item", foreignKey = @ForeignKey(name = "fk_item"), nullable = false)
	@JsonManagedReference(value = "listOrders")
	private Items item;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_order", foreignKey = @ForeignKey(name = "fk_order"), nullable = false)
	@JsonBackReference(value="")
	private Orders order;
	
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the item
	 */
	public Items getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Items item) {
		this.item = item;
	}

	/**
	 * @return the order
	 */
	public Orders getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Orders order) {
		this.order = order;
	}

}
