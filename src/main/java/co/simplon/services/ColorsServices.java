package co.simplon.services;

import java.util.Set;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Colors;

@Service
@Named
public interface ColorsServices {
	/**
	 *  method to find all categories
	 * @return
	 */
	public Set<Colors> findAll();

}
