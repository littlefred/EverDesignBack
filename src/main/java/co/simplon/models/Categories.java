package co.simplon.models;

import java.util.Set;
import java.util.TreeSet;

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

@Entity
@Table(name = "categories")
public class Categories implements Comparable<Object> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category")
	@SequenceGenerator(name = "category", sequenceName = "category_seq", allocationSize = 1)
	private Long id;
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "image", nullable = false, length = 50)
	private String image;
	
	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Items> listOfItems = new TreeSet<>();
	
	/************************
	 * CONSTRUCTORS
	 ***********************/
	public Categories() {}
	
	public Categories(String name, String image) {
		this.name = name;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the listOfItems
	 */
	public Set<Items> getListOfItems() {
		return listOfItems;
	}

	/**
	 * @param listOfItems the listOfItems to set
	 */
	public void setListOfItems(Set<Items> listOfItems) {
		this.listOfItems = listOfItems;
		for (Items item : listOfItems) {
			item.setCategory(this);
		}
	}

	// method to compare categories with name, used to do a TreeSet
	@Override
	public int compareTo(Object o) {
		Categories c = (Categories) o;
		int compC = this.name.compareToIgnoreCase(c.name);
		return compC;
	}

}
