package co.simplon.repository;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.models.Colors;

@Repository
@Named
public interface ColorsRepository extends JpaRepository<Colors, Long> {
	// method to find a color by this name
	public Optional<Colors> findByName(String name);

	// method to find a color by this image
	public Optional<Colors> findBySticker(String sticker);

}
