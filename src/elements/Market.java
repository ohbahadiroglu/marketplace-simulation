package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Market class is an object type where all traders can do buy/sell operations on.
 * Every market ( this program has only one market ) has 5 fields:
 * sellingOrders,buyingOrders,transactions,fee,numberOfSuccesTransactions which is a static.
 * @author Huzeyf
 *
 */
public class Market {
	private PriorityQueue<SellingOrder> sellingOrders;
	private PriorityQueue<BuyingOrder> buyingOrders;
	private ArrayList<Transaction> transactions;
	private int fee;
	public static int numberOfSuccessTransactions=0;
	/**
	 * Constructor method to create a market object.
	 * @param fee commission the market recieve from the transaction per thousand.
	 */
	public Market(int fee){
		this.fee=fee;
		this.buyingOrders=new PriorityQueue<BuyingOrder>();
		this.sellingOrders=new PriorityQueue<SellingOrder>();
		this.transactions=new ArrayList<Transaction>();
	}
	/**
	 * Adds order to the sellingOrders PriorityQueue.
	 * @param order
	 */
	public void giveSellOrder(SellingOrder order){
		
		sellingOrders.add(order);

	}
	/**
	 * Adds order to the buyingOrders PriorityQueue. 
	 * @param order
	 */
	public void giveBuyOrder(BuyingOrder order){
		
		buyingOrders.add(order);
		
	}
	/**
	 * Gives buying or selling orders for setting the current price of PQoin to the given price.
	 * Makes transactions if it is possible.
	 * @param price
	 * @param traders
	 */
	public void makeOpenMarketOperation(double price,ArrayList<Trader> traders){
		while( (buyingOrders.peek() != null )&& (buyingOrders.peek().price >= price)) {
			double SystemOrderAmount=buyingOrders.peek().amount;
			double systemOrderPrice = buyingOrders.peek().price;
			SellingOrder o = new SellingOrder( 0, SystemOrderAmount, systemOrderPrice);
			this.sellingOrders.add(o);
			checkTransactions(traders);	
//			if (this.buyingOrders.peek() == null){
//				break;
//			}
		}
		while( (sellingOrders.peek() != null ) && (sellingOrders.peek().price <= price)) {
			double SystemOrderAmount=sellingOrders.peek().amount;
			double systemOrderPrice =sellingOrders.peek().price;
			BuyingOrder o = new BuyingOrder( 0, SystemOrderAmount, systemOrderPrice);
			this.buyingOrders.add(o);
			checkTransactions(traders);
//			if(this.sellingOrders.peek()==null) {
//				break;
//			}
		}
		
	}
	/**
	 * Makes transactions between the traders if PriorityQueues are overlapping.
	 * @param traders transaction will occur between.
	 */
	public void checkTransactions(ArrayList<Trader>traders){
		if  (  this.buyingOrders.peek() != null  &&  this.sellingOrders.peek() != null  )   {
			while(this.buyingOrders.peek().price >= this.sellingOrders.peek().price) {
				BuyingOrder tempBuyerOrder=this.buyingOrders.poll();
				SellingOrder tempSellerOrder=this.sellingOrders.poll();
				double currentPrice=tempSellerOrder.price;
				double transactionAmount=tempSellerOrder.amount;
				Wallet buyerTraderWallet = traders.get(tempBuyerOrder.traderID).getWallet();
				Wallet sellerTraderWallet = traders.get(tempSellerOrder.traderID).getWallet();
				
				if(tempBuyerOrder.amount>tempSellerOrder.amount) {
					double newAmount=(tempBuyerOrder.amount-tempSellerOrder.amount);
					tempBuyerOrder.setAmount(newAmount);
					this.buyingOrders.add(tempBuyerOrder);
					transactionAmount=tempSellerOrder.amount;
				}
				
				if(tempBuyerOrder.amount<tempSellerOrder.amount) {
					double newAmount = (tempSellerOrder.amount-tempBuyerOrder.amount);
					tempSellerOrder.setAmount(newAmount);
					this.sellingOrders.add(tempSellerOrder);
					transactionAmount=tempBuyerOrder.amount;
				}
				
				double newBlockedDollarsOfBuyer = ( buyerTraderWallet.getBlockedDollars() - (currentPrice*transactionAmount) ); 
				buyerTraderWallet.setBlockedDollars(newBlockedDollarsOfBuyer);
				double newBuyerWalletCoins=buyerTraderWallet.getCoins()+transactionAmount;
				buyerTraderWallet.setCoins(newBuyerWalletCoins);
				
				double newBlockedCoinsOfSeller=sellerTraderWallet.getBlockedCoins()-transactionAmount;
				sellerTraderWallet.setBlockedCoins(newBlockedCoinsOfSeller);
				double newSellerWalletDollars = (sellerTraderWallet.getDollars() + (currentPrice*transactionAmount*(1-(double)this.fee/1000)) );
				sellerTraderWallet.setDollars(newSellerWalletDollars);
				numberOfSuccessTransactions++;
				if  (  this.buyingOrders.peek() == null  ||  this.sellingOrders.peek() == null  ) {
					break;
				}
				
			}
		}
	}
	
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}
	public void setSellingOrders(PriorityQueue<SellingOrder> sellingOrders) {
		this.sellingOrders = sellingOrders;
	}
	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}
	public void setBuyingOrders(PriorityQueue<BuyingOrder> buyingOrders) {
		this.buyingOrders = buyingOrders;
	}
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	public int getFee() {
		return this.fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}

}
