package elements;
/**
 * An object type which stores orders' informations in transactions.
 * Has 2 fields which are sellingOrder and buyingOrder.
 * @author Huzeyf
 *
 */
public class Transaction {
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder;
	/**
	 * Constructor method to create a transaction object which has fields:sellingOrder and buyingOrder.
	 * @param sellingOrder
	 * @param buyingOrder
	 */
	public Transaction(SellingOrder sellingOrder,BuyingOrder buyingOrder) {
		this.sellingOrder=sellingOrder;
		this.buyingOrder=buyingOrder;
	}
	
	public BuyingOrder getBuyinOrder() {
		return buyingOrder;
	}
	public void setBuyingOrder(BuyingOrder buyinOrder) {
		this.buyingOrder = buyinOrder;
	}
	public SellingOrder getSellingOrder() {
		return sellingOrder;
	}
	public void setSellingOrder(SellingOrder sellingOrder) {
		this.sellingOrder = sellingOrder;
	}
}
