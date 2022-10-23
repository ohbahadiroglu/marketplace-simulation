package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;
import elements.Wallet;
/**
 * Main class includes main method to run the program.
 * @author Huzeyf
 *
 */
public class Main {
	
	public static Random myRandom;
	/**
	 * Runs the program.
	 * Reads from input file,makes neccesary operations (method calling , simple count operations .etc) and prints to the output file.
	 * @param args args[0] >> input | args[1] >> output
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		int numberOfInvalidQueries=0;
		ArrayList<Trader> traders = new ArrayList<Trader>();
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		int randomSeed=in.nextInt();
		myRandom=new Random(randomSeed);
		
		int marketFee = in.nextInt();
		int numberOfUsers = in.nextInt();
		int numberOfQueries = in.nextInt();
		
		Market market = new Market(marketFee);
		
		for (int i = 0 ; i < numberOfUsers; i++) {
			
			double dollarAmount=in.nextDouble();
			double coinAmount=in.nextDouble();
			Trader newTrader = new Trader( dollarAmount, coinAmount );
			traders.add(newTrader);
		}
		traders.get(0).getWallet().setCoins(Double.POSITIVE_INFINITY);
		traders.get(0).getWallet().setDollars(Double.POSITIVE_INFINITY);
		
		for (int i = 0 ; i <= numberOfQueries ; i++) {
			String line = in.nextLine();
			String[] linetokens = line.split(" ");
			
			if(linetokens[0].equals("10")) {
				
				int traderID=Integer.parseInt(linetokens[1]);
				double price= Double.parseDouble(linetokens[2]);
				double amount=Double.parseDouble(linetokens[3]);
				int validationCheck=traders.get(traderID).buy(amount, price, market);
				numberOfInvalidQueries+=validationCheck;
				market.checkTransactions(traders);
			}
			if(linetokens[0].equals("11")) {
				if(market.getSellingOrders().isEmpty()==true) {
					numberOfInvalidQueries++;
				}else {
				int traderID=Integer.parseInt(linetokens[1]);
				double amount=Double.parseDouble(linetokens[2]);
				double price = market.getSellingOrders().peek().getPrice();
				Trader tempTrader=traders.get(traderID);
				int validationCheck=tempTrader.buy(amount, price, market);
				numberOfInvalidQueries+=validationCheck;
				market.checkTransactions(traders);
				}
			}
			if(linetokens[0].equals("20")) {
				int traderID=Integer.parseInt(linetokens[1]);
				double price= Double.parseDouble(linetokens[2]);
				double amount=Double.parseDouble(linetokens[3]);
				int validationCheck=traders.get(traderID).sell(amount, price, market);
				numberOfInvalidQueries+=validationCheck;
				market.checkTransactions(traders);
			}
			if(linetokens[0].equals("21")) {
				
				if(market.getBuyingOrders().isEmpty()==true) {
					numberOfInvalidQueries++;
				}else {
					int traderID=Integer.parseInt(linetokens[1]);
					double amount=Double.parseDouble(linetokens[2]);
					double price = market.getBuyingOrders().peek().getPrice();
					Trader tempTrader=traders.get(traderID);
					int validationCheck=tempTrader.sell(amount, price, market);
					numberOfInvalidQueries+=validationCheck;
					market.checkTransactions(traders);
					
				}
			}
			if(linetokens[0].equals("3")) {
				int traderID=Integer.parseInt(linetokens[1]);
				double amount=Double.parseDouble(linetokens[2]);
				double newWalletDollars = traders.get(traderID).getWallet().getDollars()+amount;
				traders.get(traderID).getWallet().setDollars(newWalletDollars);
			}
			if(linetokens[0].equals("4")) {
				int traderID=Integer.parseInt(linetokens[1]);
				double amount=Double.parseDouble(linetokens[2]);
				Wallet traderWallet =traders.get(traderID).getWallet();
				if (amount <= traderWallet.getDollars() ) {
					double newWalletDollars = traderWallet.getDollars()-amount;
					traders.get(traderID).getWallet().setDollars(newWalletDollars);
				}else {
					numberOfInvalidQueries++;
				}
			}
			if(linetokens[0].equals("5")) {
				int traderID=Integer.parseInt(linetokens[1]);
				Trader tempTrader=traders.get(traderID);
				double dollars = tempTrader.getWallet().getDollars();
				double coins=tempTrader.getWallet().getCoins();
				String traderAssets=String.format("Trader %d: %.5f$ %.5fPQ", traderID, dollars,coins);
				out.println(traderAssets);
			}
			if(linetokens[0].equals("777")) {
				for (Trader trader : traders) {
					double newCoins=trader.getWallet().getCoins()+myRandom.nextDouble()*10;
					trader.getWallet().setCoins(newCoins);
				}
			}
			if(linetokens[0].equals("666")) {
				double price = Double.parseDouble(linetokens[1]);
				market.makeOpenMarketOperation(price, traders);
			}
			if(linetokens[0].equals("500")) {
				double currentDollarsMarketSize=0;
				double currentCoinsMarketSize=0;
				for(BuyingOrder order : market.getBuyingOrders()) {
					double orderDollars = ( order.getAmount() * order.getPrice() );
					currentDollarsMarketSize+=orderDollars;
				}
				for (SellingOrder order : market.getSellingOrders() ) {
					double orderCoins =  order.getAmount() ;
					currentCoinsMarketSize+=orderCoins;
				}
				String marketSize = String.format("Current market size: %.5f %.5f", currentDollarsMarketSize,currentCoinsMarketSize);
				out.println(marketSize);
			}
			if(linetokens[0].equals("501")) {
				String outline=String.format("Number of successful transactions: %d" , Market.numberOfSuccessTransactions);
				out.println(outline);
			}
			if(linetokens[0].equals("502")) {
				out.println("Number of invalid queries: "+numberOfInvalidQueries);
			}
			if(linetokens[0].equals("505")) {
				double averageCurrentPrice=0;
				if(market.getBuyingOrders().isEmpty() == true && ( market.getSellingOrders().isEmpty() == false ) ) {
					averageCurrentPrice=market.getSellingOrders().peek().getPrice();
					String outline=String.format("Current prices: %.5f %.5f %.5f", 0.0,averageCurrentPrice,averageCurrentPrice);
					out.println(outline);
				}
				if(market.getBuyingOrders().isEmpty() == false && ( market.getSellingOrders().isEmpty() == true ) ) {
					averageCurrentPrice=market.getBuyingOrders().peek().getPrice();
					String outline=String.format("Current prices: %.5f %.5f %.5f", averageCurrentPrice,0.0 ,averageCurrentPrice);
					out.println(outline);
				}
				if(market.getBuyingOrders().isEmpty() == true && ( market.getSellingOrders().isEmpty() == true ) ) {
					String outline=String.format("Current prices: %.5f %.5f %.5f", 0.0 , 0.0, 0.0);
					out.println(outline);
				}
				if(market.getBuyingOrders().isEmpty() == false && ( market.getSellingOrders().isEmpty() == false ) ) {
					double currentSellPrice=market.getSellingOrders().peek().getPrice();
					double currentBuyPrice=market.getBuyingOrders().peek().getPrice();
					averageCurrentPrice=(double)(currentBuyPrice+currentSellPrice)/2;
					String outline=String.format("Current prices: %.5f %.5f %.5f", currentBuyPrice,currentSellPrice,averageCurrentPrice);
					out.println(outline);
				}
				
			}
			if(linetokens[0].equals("555")) {
				for(int k = 0; k < traders.size() ; k++ ) {
					Wallet traderWallet = traders.get(k).getWallet();
					double traderDollars = traderWallet.getDollars();
					double traderBlockedDollars = traderWallet.getBlockedDollars();
					double traderCoins = traderWallet.getCoins();
					double traderBlockedCoins = traderWallet.getBlockedCoins();
					double totalDollars = traderDollars + traderBlockedDollars;
					double totalCoins = traderCoins+ traderBlockedCoins;
					String outline=String.format("Trader %d: %.5f$ %.5fPQ", k, totalDollars,totalCoins);
					out.println(outline);
				}
			}		
		}
	}
}
