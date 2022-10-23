package elements;
/**
 * Order class is parent of SellingOrder and BuyingOrder classes.
 * Every order object has 3 field.These are:amount,traderID,price.
 * @author Huzeyf
 *
 */
public abstract class Order {
	double amount;
	double price;
	int traderID;
	/**
	 * Constructor method of order class.
	 * It cannot be called directly due to reason that it is an abstract class.It can only invoke via subclasses.
	 * @param traderID
	 * @param amount
	 * @param price
	 */
	public Order(int traderID,double amount, double price) {
		this.amount=amount;
		this.price=price;
		this.traderID=traderID;
	}

	public int getTraderID() {
		return this.traderID;
	}
}
