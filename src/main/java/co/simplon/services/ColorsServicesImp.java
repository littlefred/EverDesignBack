package co.simplon.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Colors;
import co.simplon.repository.ColorsRepository;

@Service
@Named
public class ColorsServicesImp implements ColorsServices{
	@Inject
	private ColorsRepository colorsRepository;

	/**
	 * method to find all colors
	 */
	@Override
	public Set<Colors> findAll() {
		List<Colors> primaryList = colorsRepository.findAll();
		// Update the list to a hashSet
		Set<Colors> finalList = new HashSet<Colors>(primaryList);
		return finalList;
	}

}
