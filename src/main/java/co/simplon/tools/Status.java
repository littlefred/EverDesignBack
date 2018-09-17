package co.simplon.tools;

/**
 * enumeration list for possible status of an order
 * 
 * @author Frederick
 *
 */
public enum Status {
	CANCELED, NOVALIDATED, VALIDATED, PAYMENT_IN_PROGRESS, PAID, REFUSED_PAYMENT, REFUSED_CREDIT_PAYMENT, IN_PREPARATION, IN_DELIVERY, DELIVERED;
}
