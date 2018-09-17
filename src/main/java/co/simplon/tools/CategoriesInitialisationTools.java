package co.simplon.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import co.simplon.models.Categories;
import co.simplon.repository.CategoriesRepository;

@Named
public class CategoriesInitialisationTools {
	@Inject
	private CategoriesRepository categoriesRepository;

	/**
	 * method to insert a categories file
	 * 
	 * @param file
	 * @return
	 */
	public final String InsertionFileCategories(List<String[]> file) {
		List<Categories> listOfCategories = new ArrayList<>(); // open list to note categories to upload in DBB
		// initialisation of counters to follow the result of insertion file
		int nbCategoriesLoaded = 0;
		int nbCategoriesUpdated = 0;
		int nbCategoriesUnchanged = 0;
		int nbCategoriesUntreated = 0;
		int nbCatgories = file.size();
		int nbLine = 0;
		String lines = "";
		// processing for each line of file
		for (String[] line : file) {
			nbLine++;
			// Check that category doesn't exist
			Optional<Categories> catInBase = this.categoriesRepository.findByName(line[0]);
			if (!catInBase.isPresent()) {
				Optional<Categories> catInBase2 = this.categoriesRepository.findByImage(line[1]);
				if (!catInBase2.isPresent()) {
					Categories tempCat = new Categories(line[0], line[1]);
					listOfCategories.add(tempCat);
					nbCategoriesLoaded++;
				} else {
					nbCategoriesUntreated++;
					lines += "- " + nbLine + "(picture already used) ";
				}
			} else {
				Categories tempCat = catInBase.get();
				if (tempCat.getImage().equals(line[1])) {
					nbCategoriesUnchanged++;
				} else {
					tempCat.setImage(line[1]);
					nbCategoriesUpdated++;
					listOfCategories.add(tempCat);
				}
			}
		}
		// after add categories that should be saved we write a resume of actions
		this.categoriesRepository.saveAll(listOfCategories);
		System.out.println(nbCategoriesLoaded + " categories loaded");
		System.out.println(nbCategoriesUpdated + " categories updated");
		System.out.println(nbCategoriesUnchanged + " categories unchanged");
		int nbTotal = nbCategoriesLoaded + nbCategoriesUpdated + nbCategoriesUnchanged;
		System.out.println("result : " + nbTotal + " categories out of " + nbCatgories);
		if (!lines.equals("")) {
			System.out.println("==> " + nbCategoriesUntreated + " categories untreated :");
			System.out.println("Lines " + lines);
		}
		return "file loaded";
	}
}
