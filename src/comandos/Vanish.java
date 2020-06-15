package comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;

public class Vanish implements CommandExecutor{
	private Main plugin;

	public Vanish(Main plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			if(ejecutor.hasPermission("hopoke.vanish")||ejecutor.isOp()) {
				//vanish
				return true;
			}else {
				ejecutor.sendMessage(ChatColor.DARK_RED+"¡No tienes permisos para ejecutar este comando!");
				return true;
			}
		}
	}
}
