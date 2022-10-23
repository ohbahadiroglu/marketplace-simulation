package elements;
/**
 * BuyingOrder class is a child of Order Class.
 * Implements Comparable interface to determine the priority of buyingOrders PriorityQueue.
 * @author Huzeyf
 * 
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder>{
	/**
	 * Constructor method to create a BuyingOrder object.
	 * @param traderID The id of the owner of the buyingOrder.
	 * @param amount trying to buy.
	 * @param price trying to buy from.
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}

	public double getAmount() {
		return this.amount;
	}
	public void setAmount(double amount) {
		this.amount=amount;
	}
	public double getPrice() {
		return this.price;
	}
	/**
	 * Overrides compareTo method from Comparable interface in order to sort the priorityQueues.
	 */
	@Override
	public int compareTo(BuyingOrder e) {
		if(this.price > e.price) {
			return -1;
		}else if(this.price < e.price){
			return 1;
		}else {
			if(this.amount>e.amount) {
				return -1;
			}else if(this.amount<e.amount) {
				return 1;
			}else {
				if (this.traderID < e.traderID) {
					return -1;
				}else if(this.traderID > e.traderID) {
					return 1;
				}else {
					return 0;
				}
			}
		}
	}
}
