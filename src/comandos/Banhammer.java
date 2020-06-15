package comandos;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.Main;

public class Banhammer implements CommandExecutor{
	private Main plugin;
	public Banhammer(Main plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return false;
		}else {
			Player ejecutor = (Player) sender;
			if(ejecutor.hasPermission("hopoke.banhammer") || ejecutor.isOp()) {
				ejecutor.getInventory().addItem(new ItemStack(Material.DIAMOND_SHOVEL));
				ejecutor.sendMessage(ChatColor.GREEN+"Usa este poder con sabiduría y mesura.");
			}
			return true;
		}
	}

}
