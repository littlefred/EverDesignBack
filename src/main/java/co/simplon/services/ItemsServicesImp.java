package co.simplon.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Items;
import co.simplon.repository.ItemsRepository;

@Service
@Named
public class ItemsServicesImp implements ItemsServices {
	@Inject
	private ItemsRepository itemsRepository;

	/**
	 *  method to find all items
	 */
	@Override
	public List<Items> findAll() {
		return this.itemsRepository.findAll();
	}

	/**
	 *  method to get stock about an item
	 */
	@Override
	public int stock(Long idItem) {
		return this.itemsRepository.findById(idItem).get().getQuantity();
	}

	/**
	 * method to save a list of new items
	 */
	@Override
	public List<Items> save(Items[] items) {
		List<Items> list = new ArrayList<>();
		for(Items item : items) {
			// TODO controls data
			list.add(this.itemsRepository.save(item));
		}
		return list;
	}

}
