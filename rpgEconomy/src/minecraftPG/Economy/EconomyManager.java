package minecraftPG.Economy;

import java.util.HashMap;

/**
 * Class managing the Economy.
 * @author HalfLegend
 *
 */
public class EconomyManager {
    
    public static HashMap<String, Double> balances = new HashMap<String, Double>();
    private static RpgEconomy plugin;
    
    public EconomyManager(RpgEconomy instance) {
        plugin = instance;
    }
    
    /**
     * Set the account of a player the an amount.
     * @param player String
     * @param amount double
     */
    public static void setBalance(String player, double amount) {
        balances.put(player, amount);
    }
    
    /**
     * Get the account of a player.
     * @param player String
     * @return double
     */
    public static double getBalances(String player) {
        return balances.get(player);
    }
    
    public static boolean hasAccount(String player) {
        return balances.containsKey(player);
    }

    public static void add(String player, double amount) {
        if (getBalances(player) + amount < 0) {
            amount = 0;
        } else {
            balances.put(player, getBalances(player) + amount);
        }
        
    }
    
    /**
     * Get the HashMap.
     */
    public static HashMap<String, Double> getBalanceMap() {
        return balances;
    }
    
    public static RpgEconomy getPlugin() {
        return plugin;
    }
}
