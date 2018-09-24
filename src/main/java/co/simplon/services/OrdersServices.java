package co.simplon.services;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Orders;

@Service
@Named
public interface OrdersServices {
	// method to save an order in DB
	public Orders save(Orders order);
	
	// method to get an order in progress before paid
	public Optional<Orders> getOrderPendingPayment(Long userId);
	
	// method to update status of an order
	public Orders updateStatusOrder(Orders order);
	
	// method to delete order before to be paid
	public boolean deleteOrderBeforePaid(Long orderId, Long userId);

}
