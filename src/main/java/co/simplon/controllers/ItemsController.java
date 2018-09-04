package co.simplon.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.models.Items;
import co.simplon.services.ItemsServices;

@RestController
@RequestMapping("/items")
public class ItemsController {
	@Inject
	private ItemsServices itemsServices;
	
	// method to find all categories
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Items>> findAll() {
		List<Items> finalList = itemsServices.findAll();
		if (finalList.isEmpty()) {
			return new ResponseEntity<List<Items>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Items>>(finalList, HttpStatus.OK);
		}
	}
	
	// method to get the stock of an item
	@RequestMapping(value = "/stock/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Integer> getStock(@PathVariable("id") Long id) {
		int stockItem = itemsServices.stock(id);
		return new ResponseEntity<Integer>(stockItem, HttpStatus.OK);
		/*if (stockItem <= 0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Integer>(stockItem, HttpStatus.OK);
		}*/
	}

}