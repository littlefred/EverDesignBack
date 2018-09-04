package co.simplon.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stockage")
public class Stock implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "qty", length = 6)
	private int quantity = 0;
	
	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	@Id
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_item", foreignKey = @ForeignKey(name = "fk_item"), nullable = false)
	@JsonIgnore
	private Items item;
	
	/************************
	 * CONSTRUCTORS
	 ***********************/
	public Stock() {}
	
	public Stock(int quantity) {
		this.quantity = quantity;
	}
	
	/************************
	 * GETTERS AND SETTERS
	 ***********************/

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
	
}
