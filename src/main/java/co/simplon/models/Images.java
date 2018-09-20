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
@Table(name = "images")
public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image")
	@SequenceGenerator(name = "image", sequenceName = "image_seq", allocationSize = 1)
	private Long id;
	@Column(name = "name", nullable = false, length = 50)
	private String image;
	
	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_item", foreignKey = @ForeignKey(name = "fk_item"), nullable = false)
	@JsonBackReference(value = "linkImages")
	private Items item;
	
	/************************
	 * CONSTRUCTORS
	 ***********************/
	public Images() {}
	
	public Images(String image) {
		this.image = image;
	}
	
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
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
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
