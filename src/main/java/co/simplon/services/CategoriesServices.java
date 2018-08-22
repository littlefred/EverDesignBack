package co.simplon.services;

import java.util.Set;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Categories;

@Service
@Named
public interface CategoriesServices {
	// method to find all categories
	public Set<Categories> findAll();

}
