package co.simplon.services;

import java.util.List;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Items;

@Service
@Named
public interface ItemsServices {
	/**
	 * method to find all items
	 * 
	 * @return
	 */
	public List<Items> findAll();

	/**
	 * method to get stock of an item
	 * 
	 * @param idItem
	 * @return
	 */
	public int stock(Long idItem);

	/**
	 * method to save a list of new items
	 * 
	 * @param items
	 * @return
	 */
	public List<Items> save(Items[] items);

}
