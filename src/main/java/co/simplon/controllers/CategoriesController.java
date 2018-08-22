package co.simplon.controllers;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.models.Categories;
import co.simplon.services.CategoriesServices;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
	@Inject
	private CategoriesServices categoriesServices;
	
	// method to find all categories
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Set<Categories>> findAll() {
		Set<Categories> finalList = categoriesServices.findAll();
		if (finalList.isEmpty()) {
			return new ResponseEntity<Set<Categories>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Set<Categories>>(finalList, HttpStatus.OK);
		}
	}

}
