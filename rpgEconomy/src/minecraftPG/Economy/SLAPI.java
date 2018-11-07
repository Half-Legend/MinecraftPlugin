package minecraftPG.Economy;

/**
 * Class loading & saving the config file.
 * @author geoff
 *
 */
public class SLAPI {
    private static RpgEconomy plugin = EconomyManager.getPlugin();
    
    public static void saveBalances() {
        for (String player : EconomyManager.getBalanceMap().keySet()) {
            plugin.getConfig().set("balance." + player, EconomyManager.getBalanceMap().get(player));
        }
        plugin.saveConfig();
    }
    
    /**
     * 
     */
    public static void loadBalances() {
        if(plugin.getConfig().contains("balance")) {
            for (String player : plugin.getConfig().getConfigurationSection("balance").getKeys(false)) {
                EconomyManager.setBalance(player, plugin.getConfig().getDouble("balance."+ player));
            }
        }
    }
}
