package co.simplon.repository;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.models.Items;

@Repository
@Named
public interface ItemsRepository extends JpaRepository<Items, Long> {
	// method to find a item by this name
	public Optional<Items> findByName(String name);

	// method to find a item by this reference
	public Optional<Items> findByReference(String reference);

}
