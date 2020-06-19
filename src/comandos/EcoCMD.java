package comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EcoCMD implements CommandExecutor {
	private Main plugin;

	public EcoCMD(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command label, String ni, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		} else {
			Economy eco = plugin.getEcon();
			Player ejecutor = (Player) sender;
			if (args.length >= 0) {
				switch (args[0]) {
				case "set":
					if(ejecutor.hasPermission("hopoke.economy.set")||ejecutor.isOp()) {
					 EconomyResponse r = eco.depositPlayer(ejecutor.getPlayer(), new Double(args[1]));
					 if(r.transactionSuccess()) {
						 ejecutor.sendMessage(ChatColor.translateAlternateColorCodes('&', "La transacción se ha realizado con éxito."));
					 }else {
						 ejecutor.sendMessage(ChatColor.translateAlternateColorCodes('&', "Algo ha salido mal!"));
					 }
					}
				case "add":
					if(ejecutor.hasPermission("hopoke.economy.add")||ejecutor.isOp()) {
						
					}
				case "take":
					if(ejecutor.hasPermission("hopoke.economy.take")||ejecutor.isOp()) {
						
					}
				case "pay":
					if(ejecutor.hasPermission("hopoke.economy.pay")||ejecutor.isOp()) {
						
					}
				}
			}else {
				ejecutor.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4El primer argumento ha de ser: take, add, pay, o set"));
			}
			return true;
		}
	}
}
