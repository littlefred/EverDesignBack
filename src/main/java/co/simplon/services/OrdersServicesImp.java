package co.simplon.services;

import java.util.ArrayList;
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
		listStatus.add(Status.VALIDATED);
		listStatus.add(Status.REFUSEDCREDITPAYMENT);
		listStatus.add(Status.REFUSEDPAYMENT);
		return this.ordersRepository.findByUserIdAndStatusIn(userId, listStatus);
	}

	// method to update status of an order
	@Override
	public Orders updateStatusOrder(Orders order) {
		Optional<Orders> orderInBase = this.ordersRepository.findByNumberOrder(order.getNumberOrder());
		if (orderInBase.isPresent() && orderInBase.get().getId() == order.getId()
				&& order.getDateOfCreation().equals(orderInBase.get().getDateOfCreation())) {
			orderInBase.get().setStatus(order.getStatus());
			orderInBase.get().setDateOfStep(order.getDateOfStep());
			orderInBase.get().setAddress(order.getAddress());
			this.ordersRepository.save(orderInBase.get());
			return orderInBase.get();
		} else {
			order.setStatus(Status.NOVALIDATED);
			return order;
		}
	}

	// method to delete an order if his status is NOVALIDATED, VALIDATED, REFUSEDPAYMENT or REFUSEDCREDITPAYMENT
	@Override
	public boolean deleteOrderBeforePaid(Long orderId, Long userId) {
		boolean result = false;
		Optional<Orders> orderInBase = this.ordersRepository.findByIdAndUserId(orderId, userId);
		if (orderInBase.isPresent()) {
			if (orderInBase.get().getStatus() == Status.REFUSEDPAYMENT
					|| orderInBase.get().getStatus() == Status.REFUSEDCREDITPAYMENT
					|| orderInBase.get().getStatus() == Status.VALIDATED
					|| orderInBase.get().getStatus() == Status.NOVALIDATED) {
				this.ordersRepository.delete(orderInBase.get());
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

}
