package co.simplon.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import co.simplon.models.Orders;
import co.simplon.repository.OrdersRepository;
import co.simplon.tools.Status;

@Service
@Named
public class OrdersServicesImp implements OrdersServices {
	@Inject
	private OrdersRepository ordersRepository;

	// method to save an order in DB
	@Override
	public Orders save(Orders order) {
		return this.ordersRepository.save(order);
	}

	// method to get an order in progress before paid
	@Override
	public Optional<Orders> getOrderPendingPayment(Long userId) {
		List<Status> listStatus = new ArrayList<>();
		listStatus.add(Status.NOVALIDATED);
		return this.ordersRepository.findByUserIdAndStatusIn(userId, listStatus);
	}

}
