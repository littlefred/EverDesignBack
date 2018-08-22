package co.simplon.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "items")
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item")
	@SequenceGenerator(name = "item", sequenceName = "item_seq", allocationSize = 1)
	private Long id;
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "reference", nullable = false, length = 30)
	private String reference;
	@Column(name = "price", nullable = false, precision = 8, scale = 2)
	private Double price;
	@Column(name = "discountPrice", precision = 8, scale = 2)
	private Double discountPrice;
	@Column(name = "informations", nullable = false, length = 255)
	private String technicalInformations;
	@Column(name = "description", length = 255)
	private String description;

	/*********************************
	 * LINK(S) with other(s) entities
	 ********************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_category", foreignKey = @ForeignKey(name = "fk_category"), nullable = false)
	private Categories category;

	@OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private Stock stockage;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Images> listImagesOfItem = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "ITEM_COLORS", joinColumns = {
			@JoinColumn(name = "FK_ITEM", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "FK_COLORS", referencedColumnName = "ID") })
	private Set<Colors> colors = new HashSet<Colors>();

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
	 * @return the productCode
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setReference(String productCode) {
		this.reference = productCode;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the discountPrice
	 */
	public Double getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice
	 *            the discountPrice to set
	 */
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * @return the technicalInformations
	 */
	public String getTechnicalInformations() {
		return technicalInformations;
	}

	/**
	 * @param technicalInformations
	 *            the technicalInformations to set
	 */
	public void setTechnicalInformations(String technicalInformations) {
		this.technicalInformations = technicalInformations;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public Categories getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Categories category) {
		this.category = category;
	}

	/**
	 * @return the listImagesOfItem
	 */
	public List<Images> getListImagesOfItem() {
		return listImagesOfItem;
	}

	/**
	 * @param listImagesOfItem
	 *            the listImagesOfItem to set
	 */
	public void setListImagesOfItem(List<Images> listImagesOfItem) {
		this.listImagesOfItem = listImagesOfItem;
	}

	/**
	 * @return the colors
	 */
	public Set<Colors> getColors() {
		return colors;
	}

	/**
	 * @param colors the colors to set
	 */
	public void setColors(Set<Colors> colors) {
		this.colors = colors;
	}

	/**
	 * @return the stockage
	 */
	public Stock getStockage() {
		return stockage;
	}

	/**
	 * @param stockage the stockage to set
	 */
	public void setStockage(Stock stockage) {
		this.stockage = stockage;
	}

}
