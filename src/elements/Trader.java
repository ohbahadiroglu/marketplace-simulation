package elements;
/**
 * An object type which stores information about traders in the market.
 * Has 3 fields.These are id , wallet and numberOfUsers which is static.
 * @author Huzeyf
 *
 */
public class Trader {
	private int id;
	private Wallet wallet;
	public static int numberOfUsers = 0 ;
	/**
	 * Consturctor method to create a trader object which has dollars and coins in its wallet.
	 * @param dollars Initial dollars in trader's wallet.
	 * @param coins Initial coins in trader's wallet.
	 */
	public Trader(double dollars, double coins) {
		this.wallet=new Wallet(dollars,coins);
		this.id=numberOfUsers;
		numberOfUsers++;		
	}
	/**
	 * Checks sellingOrders whether is valid or not.
	 * If it is valid adds the sellingOrder to the sellingOrders' PriorityQueue 
	 * @param amount trying to sell. 
	 * @param price trying to sell from.
	 * @param market
	 * @return	0 if it is valid;1 otherwise.
	 */
	public int sell(double amount, double price, Market market) {
		if (this.wallet.getCoins()>= amount) {
			int traderID=this.getId();
			SellingOrder o = new SellingOrder(traderID, amount , price);
			market.giveSellOrder(o);
			double walletCoins=this.wallet.getCoins();
			walletCoins-=amount;
			this.wallet.setCoins(walletCoins);
			double newBlockedCoins = this.wallet.getBlockedCoins()+amount;
			this.wallet.setBlockedCoins(newBlockedCoins);
			return 0;
		}
		return 1;
	}
	/**
	 * Checks buyingOrders whether is valid or not.
	 * If it is valid adds the buyingOrder to the buyingOrders' PriorityQueue.
	 * @param amount trying to buy. 
	 * @param price trying to buy from.
	 * @param market
	 * @return	0 if it is valid;1 otherwise.
	 */
	public int buy(double amount, double price, Market market) {
		double totalCost=amount*price;
		if(this.wallet.getDollars()>=totalCost) {
			int traderID=this.getId();
			BuyingOrder o = new BuyingOrder(traderID, amount, price);
			market.giveBuyOrder(o);
			double walletDollars=this.wallet.getDollars();
			walletDollars-=totalCost;
			this.wallet.setDollars(walletDollars);
			double newBlockedDollars=this.wallet.getBlockedDollars()+totalCost;
			this.wallet.setBlockedDollars(newBlockedDollars);
			return 0;
		}
		return 1;
	}
	public int getId() {
		return id;
	}
	public Wallet getWallet() {
		return this.wallet;
	}
}
