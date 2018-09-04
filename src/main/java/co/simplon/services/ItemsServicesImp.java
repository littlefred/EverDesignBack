package co.simplon.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import co.simplon.models.Items;
import co.simplon.repository.ItemsRepository;

@Service
public class ItemsServicesImp implements ItemsServices {
	@Inject
	private ItemsRepository itemsRepository;

	// method to find all categories
	@Override
	public List<Items> findAll() {
		return this.itemsRepository.findAll();
	}

	// method to get stock about an item
	@Override
	public int stock(Long idItem) {
		// TODO Auto-generated method stub
		return this.itemsRepository.getOne(idItem).getStockage().getQuantity();
	}

}
