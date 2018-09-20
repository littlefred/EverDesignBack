package co.simplon.tools;

/**
 * enumeration list for possible status of an order
 * 
 * @author Frederick
 *
 */
public enum Status {
	CANCELED, NOVALIDATED, VALIDATED, PAYMENTINPROGRESS, PAID, REFUSEDPAYMENT, REFUSEDCREDITPAYMENT, INPREPARATION, INDELIVERY, DELIVERED;
}
