package comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import main.UHCLebrel;

public class Spawn implements CommandExecutor{
	private UHCLebrel plugin;

	public Spawn(UHCLebrel plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			if(ejecutor.hasPermission("hopoke.spawn")||ejecutor.isOp()) {
				FileConfiguration config = plugin.getConfig();
				int x = config.getInt("spawn.x");
				int y = config.getInt("spawn.y");
				int z = config.getInt("spawn.z");
				float yaw = config.getInt("spawn.yaw");
				float pitch = config.getInt("spawn.pitch");
				World world = plugin.getServer().getWorld(config.getString("spawn.world"));
				Location lspawn = new Location(world, x, y, z, yaw, pitch);
				ejecutor.teleport(lspawn);
				ejecutor.sendMessage(ChatColor.DARK_GREEN+"¡Se te ha teletransportado al spawn exitosamente!");
				return true;
			}else {
				ejecutor.sendMessage(ChatColor.DARK_RED+"¡No tienes permisos para ejecutar este comando!");
				return true;
			}
		}
	}
}
