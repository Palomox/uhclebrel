package comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;
import util.HoPokePlayer;

public class HoCoreCMD implements CommandExecutor {
	private Main plugin;
	public HoCoreCMD(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command label, String ni, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("No se puede ejecutar este comando desde la consola!");
			return false;
		} else {
			switch(args[0]) {
			case "flogin":
				if(args.length>0) {
					HoPokePlayer pl = plugin.getHPByName(args[1]);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2La primera vez que se unió ese jugador fue "+pl.getFJ().toString()));
				}
				break;
			case "reload":
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Se ha recargado la configuracion exitosamente [La configuracion de la base de datos &lNO&r&2]"));
				break;
			default:
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Ese argumento no existe."));
			}
			return true;
		}

	}
}
