package co.simplon.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import co.simplon.models.Categories;
import co.simplon.models.Colors;
import co.simplon.models.Images;
import co.simplon.models.Items;
import co.simplon.models.Stock;
import co.simplon.repository.CategoriesRepository;
import co.simplon.repository.ColorsRepository;
import co.simplon.repository.ItemsRepository;

@Component
public class DataBaseInitialization implements ApplicationListener<ContextRefreshedEvent> {
	private final static char CSV_SEPARATOR = ';';

	@Inject
	private CategoriesRepository categoriesRepository;
	@Inject
	private ColorsRepository colorsRepository;
	@Inject
	private ItemsRepository itemsRepository;

	/**
	 * method to load basics categories
	 * 
	 * @param fileLocation
	 * @return
	 */
	public final String loadingDBCategories(String fileLocation) {
		// Constructor of reader of file with the separator
		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {
			List<String[]> fileLineList = reader.readAll(); // including the informations of file in a List of data
															// array
			if (!fileLineList.isEmpty()) {
				int length = 2;
				boolean data = true;
				// Check the length of file (for categories it's 2
				// Check that they are no empty data
				for (String[] fileLineCheck : fileLineList) {
					if (fileLineCheck.length != 2) {
						length = fileLineCheck.length;
					}
					for (String s : fileLineCheck) {
						if (s.equals("") || s.equals("null")) {
							data = false;
						}
					}
				}
				if (length != 2) {
					return "the file of categories is not in the correct format.";
				} else if (!data) {
					return "the file of categories have some empty data.";
				} else {
					// When checks of file is good we load it
					List<Categories> listOfCategories = new ArrayList<>();
					int nbCatLoaded = 0;
					int nbCatUpdated = 0;
					for (String[] fileLine : fileLineList) {
						// Check that category doesn't exist
						Optional<Categories> searchCat = this.categoriesRepository.findByName(fileLine[0]);
						Optional<Categories> searchCat2 = this.categoriesRepository.findByImage(fileLine[1]);
						// if the category exist check the update
						if (searchCat.isPresent() || searchCat2.isPresent()) {
							if (searchCat.isPresent()) {
								Categories oldCat = searchCat.get();
								if (!oldCat.getImage().equals(fileLine[1])) {
									oldCat.setImage(fileLine[1]);
									listOfCategories.add(oldCat);
									nbCatUpdated++;
								}
							}
							if (searchCat2.isPresent()) {
								Categories oldCat = searchCat2.get();
								if (!oldCat.getName().equals(fileLine[0])) {
									oldCat.setName(fileLine[0]);
									listOfCategories.add(oldCat);
									nbCatUpdated++;
								}
							}
						} else {
							Categories cat = new Categories(fileLine[0], fileLine[1]);
							listOfCategories.add(cat);
							nbCatLoaded++;
						}
					}
					// after add the categories that should be saved we write a resume of actions
					this.categoriesRepository.saveAll(listOfCategories);
					System.out.println(nbCatLoaded + " categories loaded");
					System.out.println(nbCatUpdated + " categories updated");
					return "the file of categories has been loaded.";
				}
			} else {
				return "Actually, the file of categories is empty.";
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading categories.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to load basics compositions
	 * 
	 * @param fileLocation
	 * @return
	 */
	public final String loadingDBCompositions(String fileLocation) {
		// Constructor of reader of file with the separator
		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {
			List<String[]> fileLineList = reader.readAll(); // including the informations of file in a List of data
			// array
			if (!fileLineList.isEmpty()) {
				int length = 3;
				boolean data = true;
				// Check the length of file (for categories it's 2
				// Check that they are no empty data
				for (String[] fileLineCheck : fileLineList) {
					if (fileLineCheck.length != 3) {
						length = fileLineCheck.length;
					}
					for (String s : fileLineCheck) {
						if (s.equals("") || s.equals("null")) {
							data = false;
						}
					}
				}
				if (length != 3) {
					return "the file of compositions is not in the correct format.";
				} else if (!data) {
					return "the file of compositions have some empty data.";
				} else {
					// When checks of file is good we load it
					List<Colors> compositions = new ArrayList<>();
					int nbCompositionsLoaded = 0;
					int nbCompositionsUpdated = 0;
					for (String[] fileLine : fileLineList) {
						// Check that composition doesn't exist
						Optional<Colors> searchCompo = this.colorsRepository
								.findByName(fileLine[1].toLowerCase());
						Optional<Colors> searchCompo2 = this.colorsRepository.findBySticker(fileLine[2]);
						// if the composition exist, check the update
						if (searchCompo.isPresent() || searchCompo2.isPresent()) {
							if (searchCompo.isPresent()) {
								Colors oldCompo = searchCompo.get();
								if (!oldCompo.getSticker().equals(fileLine[2])
										&& oldCompo.getMaterial().equals(Material.valueOf(fileLine[0]))) {
									oldCompo.setSticker(fileLine[2]);
									compositions.add(oldCompo);
									nbCompositionsUpdated++;
								}
							}
							if (searchCompo2.isPresent()) {
								Colors oldCompo = searchCompo2.get();
								if (!oldCompo.getName().equals(fileLine[1].toLowerCase())
										&& oldCompo.getMaterial().equals(Material.valueOf(fileLine[0]))) {
									oldCompo.setName(fileLine[1].toLowerCase());
									compositions.add(oldCompo);
									nbCompositionsUpdated++;
								}
							}
						} else {
							Material material = Material.valueOf(fileLine[0].toUpperCase());
							Colors compo = new Colors(material, fileLine[1].toLowerCase(), fileLine[2]);
							compositions.add(compo);
							nbCompositionsLoaded++;
						}
					}
					// after add the compositions that should be saved we write a resume of actions
					this.colorsRepository.saveAll(compositions);
					System.out.println(nbCompositionsLoaded + " compositions loaded");
					System.out.println(nbCompositionsUpdated + " compositions updated");
					return "the file of compositions has been loaded.";
				}
			} else {
				return "Actually, the file of compositions is empty.";
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading compositions.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	/**
	 * method to load basics items
	 * 
	 * @return
	 */
	public final String loadingDBItems(String fileLocation) {
		// Constructor of reader of file with the separator
		CSVParser csvParser = new CSVParserBuilder().withSeparator(CSV_SEPARATOR).build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileLocation)).withCSVParser(csvParser).build()) {
			List<String[]> fileLineList = reader.readAll(); // including the informations of file in a List of data
			// array
			if (!fileLineList.isEmpty()) {
				int length = 10;
				for (String[] fileLineCheck : fileLineList) {
					if (fileLineCheck.length != 3) {
						length = fileLineCheck.length;
					}
				}
				if (length != 10) {
					return "the file of items is not in the correct format.";
				} else {
					// When checks of file is good we load it
					List<Items> itemsList = new ArrayList<>();
					int nbItemsLoaded = 0;
					int nbItemsUpdated = 0;
					int nbItemsUnchanged = 0;
					int nbItems = fileLineList.size();
					int nbLine = 0;
					String lines = "";
					for (String[] fileLine : fileLineList) {
						nbLine++;
						boolean loading = true;
						if (fileLine[0].isEmpty() || fileLine[1].isEmpty() || fileLine[2].isEmpty()
								|| fileLine[3].isEmpty() || fileLine[8].isEmpty() || fileLine[9].isEmpty()) {
							loading = false;
							lines += "- " + nbLine + " (empty basics datas error) ";
						} else {
							// correct formating of the name
							char[] chars = fileLine[0].toCharArray();
							chars[0] = Character.toUpperCase(chars[0]);
							String tempName = new String(chars);
							// correct formating of the reference
							String tempReference = fileLine[1].toUpperCase() + " / CL.";
							String[] colors = fileLine[9].split("[-./ ,]");
							Set<Colors> tempColors = new HashSet<>();
							for (String s : colors) {
								tempReference += s + ".";
								// correct formating of colors
								Optional<Colors> compo = this.colorsRepository.findById(Long.parseLong(s));
								if (!compo.isPresent()) {
									loading = false;
									lines += "- " + nbLine + " (colors error)";
								} else {
									tempColors.add(new Colors(compo.get()));
								}
							}
							// correct formating of category
							Categories tempCat = new Categories();
							Optional<Categories> cat = this.categoriesRepository.findById(Long.parseLong(fileLine[2]));
							if (!cat.isPresent()) {
								loading = false;
								lines += "- " + nbLine + " (category error)";
							} else {
								tempCat = cat.get();
							}
							// correct formating of price
							Double tempPrice = Double.valueOf(fileLine[3]);
							// correct formating of price
							Double tempDiscountPrice = Double.valueOf(fileLine[4]);
							// correct formating of stock
							Stock tempStock = new Stock(Integer.parseInt(fileLine[7]));
							// correct formating of list of images
							String[] listImageName = fileLine[8].split("[-/ ,]");
							List<Images> tempImages = new ArrayList<>();
							for (String s : listImageName) {
								Images image = new Images(s);
								tempImages.add(image);
							}
							// if data is ok we add it at final list
							if (loading) {
								Optional<Items> searchItem = this.itemsRepository.findByReference(tempReference);
								if (!searchItem.isPresent()) {
									Items i = new Items();
									i.setName(tempName);
									i.setReference(tempReference);
									i.setCategory(tempCat);
									i.setPrice(tempPrice);
									i.setDiscountPrice(tempDiscountPrice);
									i.setTechnicalInformations(fileLine[5]);
									i.setDescription(fileLine[6]);
									tempStock.setItem(i);
									i.setStockage(tempStock);
									for(Images img: tempImages) {
										img.setItem(i);
									}
									i.setListImagesOfItem(tempImages);
									i.setColors(tempColors);
									itemsList.add(i);
									nbItemsLoaded++;
								} else if (searchItem.isPresent()
										&& searchItem.get().getCategory().getId() == (tempCat.getId())) {
									Items i = new Items();
									i.setId(searchItem.get().getId());
									i.setName(tempName);
									i.setPrice(tempPrice);
									i.setDiscountPrice(tempDiscountPrice);
									i.setTechnicalInformations(fileLine[5]);
									i.setDescription(fileLine[6]);
									tempStock.setItem(i);
									i.setStockage(tempStock);
									for(Images img: tempImages) {
										img.setItem(i);
									}
									i.setListImagesOfItem(tempImages);
									i.setColors(tempColors);
									itemsList.add(i);
									nbItemsUpdated++;
								} else {
									lines += "- " + nbLine + " (error: reference ever exists in another category)";
									nbItemsUnchanged++;
								}
							}
						}
					}
					if (lines.equals("")) {
						lines = "- any -";
					}
					// after add the compositions that should be saved we write a resume of actions
					this.itemsRepository.saveAll(itemsList);
					System.out.println(nbItemsLoaded + " items loaded");
					System.out.println(nbItemsUpdated + " items updated");
					System.out.println(nbItemsUnchanged + " items unchanged");
					int nbTotal = nbItemsLoaded + nbItemsUpdated + nbItemsUnchanged;
					System.out.println("result : " + nbTotal + " items out of " + nbItems);
					int nbError = nbItems - nbTotal;
					System.out.println("==> " + nbError + " items untreated because in error and " + nbItemsUnchanged
							+ " items unchanged :");
					System.out.println("Lines " + lines);
					return "the file of items has been loaded.";
				}
			} else {
				return "Actually, the file of items is empty.";
			}
		} catch (FileNotFoundException e) {
			return "Actually, no file to loading items.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Internal error is happening";
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		System.out.println("loading Database :");
		System.out.println(loadingDBCategories("src/main/resources/data/Categories"));
		System.out.println(loadingDBCompositions("src/main/resources/data/Colors"));
		System.out.println(loadingDBItems("src/main/resources/data/Items"));
	}

}
