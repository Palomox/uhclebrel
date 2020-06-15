package comandos;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import main.Main;

public class ChatCmd implements CommandExecutor{
	private Main plugin;
	public ChatCmd(Main plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			String mensaje = String.join(" ", args.toString());
			Set<Player> jugatas = new HashSet<Player>();
			jugatas.addAll(Bukkit.getServer().getOnlinePlayers());
			plugin.getPm().callEvent(new AsyncPlayerChatEvent(true, ejecutor, mensaje, jugatas));
			return true;
			
		}
			
		}
			
		

}
