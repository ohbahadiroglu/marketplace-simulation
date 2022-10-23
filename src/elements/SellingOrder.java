package elements;
/**
 * SellingOrder class is a child of Order Class. 
 * Implements Comparable interface to determine the priority of sellingOrders PriorityQueue.
 * @author Huzeyf
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder> {
	/**
	 * Constructor method to create a SellingOrder object.
	 * @param traderID The id of the owner of the sellingOrder.
	 * @param amount trying to sell.
	 * @param price trying to sell from.
	 */
	public SellingOrder(int traderID, double amount, double price) {
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
	public int compareTo(SellingOrder e) {
		if(this.price < e.price) {
			return -1;
		}else if(this.price > e.price){
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
