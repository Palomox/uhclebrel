package comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import main.UHCLebrel;

public class Setspawn implements CommandExecutor{
	private UHCLebrel plugin;

	public Setspawn(UHCLebrel plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			if(ejecutor.isOp()||ejecutor.hasPermission("hopoke.setspawn")) {
				Location lspawn = ejecutor.getLocation();
				FileConfiguration config = plugin.getConfig();
				config.set("spawn.x", lspawn.getBlockX());
				config.set("spawn.y", lspawn.getBlockY());
				config.set("spawn.z", lspawn.getBlockZ());
				config.set("spawn.yaw", lspawn.getYaw());
				config.set("spawn.pitch", lspawn.getPitch());
				config.set("spawn.world", lspawn.getWorld().getName());
				plugin.saveConfig();
				ejecutor.sendMessage(ChatColor.GREEN+"¡Se ha colocado el Spawn exitosamente!");
				return true;
			}else {
				ejecutor.sendMessage(ChatColor.DARK_RED+"¡No tienes suficientes permisos para ejecutar este comando!");
				return true;
			}
		}
	}

}
