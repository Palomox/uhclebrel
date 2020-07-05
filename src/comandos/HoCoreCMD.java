package comandos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;
import uhc.Equipo;
import uhc.Juego;
import util.Mamerto;

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
			case "reload":
				if(!(sender.hasPermission("hopoke.command.reload"))) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4¡No tienes permiso para ejecutar este comando!"));
					break;
				}
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Se ha recargado la configuracion exitosamente [La configuracion de la base de datos &lNO&r&2]"));
				break;
			case "addteam":
				if(!(args.length <=0)) {
					ArrayList<String> argus = new ArrayList<String>(Arrays.asList(args));
					argus.remove(0);
					StringJoiner j = new StringJoiner(" ");
					for(String tmp : argus) {
						j.add(tmp);
					}
				Main.instance.juego.addTeam(new Equipo(j.toString(), Main.instance.juego.getEquipos().size()+1));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha creado el equipo '"+j.toString()+"'!"));
				}
				break;
			case "addmember":
				if(!(args.length <=1)) {
					int teamid = Integer.valueOf(args[1]);
					String personaname = args[2];
					Equipo team = Equipo.getEquipoById(teamid);
					team.addMamerto(Main.instance.getHPByName(personaname));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha añadido a "+personaname+" al equipo '"+team.getNombre()+"'!"));
				}
				break;
			case "start":
				for(Equipo tmp : Main.instance.juego.getEquipos().keySet()) {
					for(Mamerto mam : tmp.getMiembros().keySet()) {
						Main.instance.juego.addMammert(mam);
					}
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha iniciado la partida!"));
				break;
			case "reset":
				Main.instance.juego = new Juego();
				break;
			case "finish":
				break;
			default:
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Ese argumento no existe."));
			}
			return true;
		} 
	}
}
