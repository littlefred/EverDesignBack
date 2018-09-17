package co.simplon.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import co.simplon.models.Categories;
import co.simplon.models.Colors;
import co.simplon.models.Images;
import co.simplon.models.Items;
import co.simplon.repository.CategoriesRepository;
import co.simplon.repository.ColorsRepository;
import co.simplon.repository.ItemsRepository;

@Named
public class ItemsInitialisationTools {
	@Inject
	private ItemsRepository itemsRepository;
	@Inject
	private ColorsRepository colorsRepository;
	@Inject
	private CategoriesRepository categoriesRepository;

	/**
	 * method to insert an items file
	 * 
	 * @param file
	 * @return
	 */
	public final String insertionFileItems(List<String[]> file) {
		List<Items> listOfItems = new ArrayList<>();// open list to note colors to upload in DBB
		// initialisation of counters to follow the result of insertion file
		int nbItemsLoaded = 0;
		int nbItemsUpdated = 0;
		int nbItemsUntreated = 0;
		int nbItems = file.size();
		int nbLine = 0;
		String lines = "";
		// processing for each line of file
		for (String[] line : file) {
			nbLine++;
			if (checkColors(line[9]) && checkCategories(line[2])) {
				Items tempItem = formatingNewItem(line);
				Optional<Items> itemInBase = this.itemsRepository.findByReference(tempItem.getReference());
				if (itemInBase.isPresent()) {
					if (itemInBase.get().getCategory().getId() == tempItem.getCategory().getId()) {
						tempItem.setId(itemInBase.get().getId());
						listOfItems.add(tempItem);
						nbItemsUpdated++;
					} else {
						nbItemsUntreated++;
						lines += "- " + nbLine + "(reference exists in another category) ";
					}
				} else {
					listOfItems.add(tempItem);
					nbItemsLoaded++;
				}
			} else {
				nbItemsUntreated++;
				lines += "- " + nbLine + "(category or colors error) ";
			}
		}
		// after add items that should be saved we write a resume of actions
		this.itemsRepository.saveAll(listOfItems);
		System.out.println(nbItemsLoaded + " items loaded");
		System.out.println(nbItemsUpdated + " items updated");
		int nbTotal = nbItemsLoaded + nbItemsUpdated;
		System.out.println("result : " + nbTotal + " items out of " + nbItems);
		if (!lines.equals("")) {
			System.out.println("==> " + nbItemsUntreated + " items untreated :");
			System.out.println("Lines " + lines);
		}
		return "file loaded";
	}

	/**
	 * method to check that colors exists
	 * 
	 * @param s
	 * @return
	 */
	private boolean checkColors(String s) {
		boolean result = true;
		String[] colors = s.split("[-./ ,]");
		for (String c : colors) {
			Optional<Colors> colorInBase = this.colorsRepository.findById(Long.parseLong(c));
			if (!colorInBase.isPresent()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * method to check that category exists
	 * 
	 * @param s
	 * @return
	 */
	private boolean checkCategories(String s) {
		boolean result = true;
		Optional<Categories> cat = this.categoriesRepository.findById(Long.parseLong(s));
		if (!cat.isPresent()) {
			result = false;
		}
		return result;
	}

	/**
	 * method to format informations in a new Item
	 * 
	 * @param infos
	 * @return
	 */
	private Items formatingNewItem(String[] infos) {
		Items i = new Items();
		// correct formating of the name
		char[] chars = infos[0].toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		String tempName = new String(chars);
		i.setName(tempName);
		// correct formating of the reference and the list of colors
		String tempReference = infos[1].toUpperCase() + " / CL.";
		String[] colors = infos[9].split("[-./ ,]");
		Set<Colors> tempColors = new HashSet<>();
		for (String s : colors) {
			tempReference += s + ".";
			Optional<Colors> colorInBase = this.colorsRepository.findById(Long.parseLong(s));
			if (colorInBase.isPresent()) {
				tempColors.add(new Colors(colorInBase.get()));
			}
		}
		i.setReference(tempReference);
		i.setColors(tempColors);
		// correct formating of category
		Categories tempCat = new Categories();
		Optional<Categories> catInBase = this.categoriesRepository.findById(Long.parseLong(infos[2]));
		if (catInBase.isPresent()) {
			tempCat = catInBase.get();
		}
		i.setCategory(tempCat);
		// correct formating of price
		int tempPrice = Integer.valueOf(infos[3]);
		i.setPrice(tempPrice);
		// correct formating of discount price
		int tempDiscountPrice = Integer.valueOf(infos[4]);
		i.setDiscountPrice(tempDiscountPrice);
		// correct formating of stock
		int tempStock = Integer.parseInt(infos[7]);
		i.setQuantity(tempStock);
		// correct formating of list of images
		String[] listImageName = infos[8].split("[-/ ,]");
		List<Images> tempImages = new ArrayList<>();
		for (String s : listImageName) {
			Images image = new Images(s);
			tempImages.add(image);
		}
		i.setListImagesOfItem(tempImages);
		i.setInformations(infos[5]); // insertion of technical informations
		i.setDescription(infos[6]); // insertion of description
		return i;
	}

}
