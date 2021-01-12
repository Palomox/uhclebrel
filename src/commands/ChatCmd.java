package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import main.UHCLebrel;

/**
 * Class which uses the /c command to fire an AsyncChatEvent.
 * @author palomox
 *
 */
public class ChatCmd implements CommandExecutor{
	private UHCLebrel plugin;
	public ChatCmd(UHCLebrel plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			StringJoiner sj = new StringJoiner(" ");
			ArrayList<String> aforzar = new ArrayList<String>(Arrays.asList(args));
			for(int i=0; i<aforzar.size(); i++) {
				sj.add(aforzar.get(i));
			}
			String mensaje = sj.toString();
			Set<Player> jugatas = new HashSet<Player>();
			jugatas.addAll(Bukkit.getServer().getOnlinePlayers());
			plugin.getPm().callEvent(new AsyncPlayerChatEvent(false, ejecutor, mensaje, jugatas));
			return true;

		}

		}



}
