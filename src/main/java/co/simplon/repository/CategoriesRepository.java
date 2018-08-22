package co.simplon.repository;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.models.Categories;

@Repository
@Named
public interface CategoriesRepository extends JpaRepository<Categories, Long>{
	// method to find a category by this name
	public Optional<Categories> findByName(String name);
	
	// method to find a category by this image
	public Optional<Categories> findByImage(String image);

}
