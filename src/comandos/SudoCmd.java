package comandos;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;

public class SudoCmd implements CommandExecutor{
	private Main plugin;
	public SudoCmd(Main plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			Player vistima = Bukkit.getPlayer(args[0]);
			List<String> cmdl = Arrays.asList(args);
			cmdl.remove(0);
			String cmd = String.join(" ", cmdl.toString());
			Bukkit.dispatchCommand(vistima, cmd);
			plugin.getALogger().logCmd("Ha forzado a "+vistima.getName()+" a ejecutar '"+cmd+"'.", ejecutor);
			return true;
		}
			
		}

}
