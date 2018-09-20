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

@Entity
@Table(name = "ordersContent")
public class OrdersContent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordersContent")
	@SequenceGenerator(name = "ordersContent", sequenceName = "ordersContent_seq", allocationSize = 1)
	private Long id;
	@Column(name = "quantity", nullable = false)
	private int qty;

	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_item", foreignKey = @ForeignKey(name = "fk_item"), nullable = false)
	private Items items;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_order", foreignKey = @ForeignKey(name = "fk_order"), nullable = false)
	@JsonBackReference(value="linkOrderContent")
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
	public int getQty() {
		return qty;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQty(int quantity) {
		this.qty = quantity;
	}

	/**
	 * @return the item
	 */
	public Items getItem() {
		return items;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Items item) {
		this.items = item;
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
