package elements;
/**
 * An object type which stores traders dollars and coins assets.
 * Has 4 fields.These are dollars ,coins ,blockedDollars and blockedCoins.
 * @author Huzeyf
 *
 */
public class Wallet {
	private double dollars;
	private double coins;
	private double blockedDollars = 0 ;
	private double blockedCoins = 0 ; 
	/**
	 * Constructor method to create a Wallet object.
	 * @param dollars Initial dollars of wallet.
	 * @param coins Initial coins of wallet.
	 * 
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
	}
	public double getDollars() {
		return dollars;
	}
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}
	public double getCoins() {
		return coins;
	}
	public void setCoins(double coins) {
		this.coins = coins;
	}
	public double getBlockedDollars() {
		return blockedDollars;
	}
	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}
	public double getBlockedCoins() {
		return blockedCoins;
	}
	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
}
