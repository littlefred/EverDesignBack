package co.simplon.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.simplon.models.Orders;
import co.simplon.tools.Status;

@Repository
@Named
public interface OrdersRepository extends JpaRepository<Orders, Long>{
	// method to get an order in progress before paid
	// @Query("SELECT o FROM orders o WHERE o.user.id=:userId AND o.status IN ('NOVALIDATED','VALIDATED','REFUSEDPAYMENT','REFUSEDCREDITPAYMENT')")
	public Optional<Orders> findByUserIdAndStatusIn(Long userId, List<Status> listStatus);

}
