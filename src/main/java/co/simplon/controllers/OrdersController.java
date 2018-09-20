package co.simplon.controllers;


import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.models.Orders;
import co.simplon.services.OrdersServices;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.194:4200"})
public class OrdersController {
	@Inject
	private OrdersServices ordersServices;
	
	// method to save an order in DB
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
		Orders orderSaved = this.ordersServices.save(order);
		if (orderSaved == null) {
			return new ResponseEntity<Orders>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<Orders>(orderSaved, HttpStatus.CREATED);
		}
	}
	
	// method to get an order in progress before paid
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Orders> getOrderPendingPayment(@RequestParam Long userId) {
		Optional<Orders> orderFound = this.ordersServices.getOrderPendingPayment(userId);
		if (!orderFound.isPresent()) {
			return new ResponseEntity<Orders>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Orders>(orderFound.get(), HttpStatus.CREATED);
		}
	}

}
