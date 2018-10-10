package co.simplon.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.simplon.tools.Material;

@Entity
@Table(name = "colors")
public class Colors {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compo")
	@SequenceGenerator(name = "compo", sequenceName = "compo_seq", allocationSize = 1)
	private Long id;
	@Column(name = "material", nullable = false)
	private Material material;
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "sticker", nullable = false, length = 50, unique = true)
	private String sticker;

	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	@ManyToMany(mappedBy="colors")
	@JsonIgnore
	private Set<Items> itemRef = new HashSet<Items>();

	/************************
	 * CONSTRUCTORS
	 ***********************/
	public Colors() {
	}

	public Colors(Material material, String name, String sticker) {
		this.material = material;
		this.name = name;
		this.sticker = sticker;
	}
	
	public Colors(Colors c) {
		this.id = c.getId();
		this.material = c.getMaterial();
		this.name = c.getName();
		this.sticker = c.getSticker();
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
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 *            the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
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
	 * @return the sticker
	 */
	public String getSticker() {
		return sticker;
	}

	/**
	 * @param sticker
	 *            the sticker to set
	 */
	public void setSticker(String sticker) {
		this.sticker = sticker;
	}

}
