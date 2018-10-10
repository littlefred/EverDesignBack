package co.simplon.services;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Categories;
import co.simplon.repository.CategoriesRepository;
import co.simplon.tools.ControlsData;

@Service
@Named
public class CategoriesServicesImp implements CategoriesServices {
	@Inject
	private CategoriesRepository categoriesRepository;

	/**
	 *  method to find all categories
	 */
	@Override
	public Set<Categories> findAll() {
		List<Categories> primaryList = categoriesRepository.findAll();
		// Update the list to a TreeSet
		Set<Categories> finalList = new TreeSet<Categories>(primaryList);
		return finalList;
	}

	/**
	 * method to save a category
	 */
	@Override
	public Categories save(Categories cat) {
		if (ControlsData.controlCatName(cat.getName()) && ControlsData.controlPicNameOfCat(cat.getImage())) {
			return this.categoriesRepository.save(cat);
		}
		return null;
	}

}
