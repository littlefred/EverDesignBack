package co.simplon.services;

import java.util.List;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Items;

@Service
@Named
public interface ItemsServices {
	// method to find all items
	public List<Items> findAll();
	// method to get stock of an item
	public int stock(Long idItem);

}
