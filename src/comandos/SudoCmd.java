package comandos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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
			ArrayList<String> argsl = new ArrayList<String>(Arrays.asList(args));
			argsl.remove(0);
			StringJoiner sj = new StringJoiner(" ");
			for(int i=0; i<argsl.size(); i++) {
				sj.add(argsl.get(i));
			}
			Bukkit.dispatchCommand(vistima, sj.toString());
			plugin.getALogger().logCmd("Ha forzado a "+vistima.getName()+" a ejecutar '"+sj.toString()+"'.", ejecutor);
			return true;
		}
			
		}

}
