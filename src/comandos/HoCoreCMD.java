package comandos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import main.Main;
import uhc.Episodio;
import uhc.EpisodioChangeEvent;
import uhc.Equipo;
import uhc.EstadoChangeEvent;
import uhc.EstadosJuego;
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
			case "setteamspawn":
				if(!(args.length <=1)) {
					int teamid = Integer.valueOf(args[1]);
					Equipo team = Equipo.getEquipoById(teamid);
					Player ejecutor = (Player) sender;
					team.setSpawn(ejecutor.getLocation());
					Main.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.world", team.getSpawn().getWorld().getName());
					Main.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.x", team.getSpawn().getX());
					Main.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.y", team.getSpawn().getY());
					Main.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.x", team.getSpawn().getZ());
					Main.instance.saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha colocado el spawn del equipo '"+team.getNombre()+"'!"));
				}
				break;
			case "loadteams":
				Main.instance.loadTeamsFromConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se han cargado los equipos desde la configuración correctamente!"));
				break;
			case "teamlist":
				ArrayList<String> mensajesal = new ArrayList<String>();
				mensajesal.add(ChatColor.translateAlternateColorCodes('&', "&8--------&6Lista de equipos&8-----------"));
				for(Equipo tmp : Main.instance.getJuego().getEquipos().keySet()) {
					int id = tmp.getId();
					String nombre = tmp.getNombre();
					String bonito = ChatColor.translateAlternateColorCodes('&', "&6"+id+"&8---------------------&2"+nombre);
					mensajesal.add(bonito);
				}
				mensajesal.add(ChatColor.translateAlternateColorCodes('&', "&8-----------------------------------"));
				for(String msg : mensajesal) {
					sender.sendMessage(msg);
				}
				break;
			case "addmember":
				if(!(args.length <=1)) {
					int teamid = Integer.valueOf(args[1]);
					String personaname = args[2];
					Equipo team = Equipo.getEquipoById(teamid);
					team.addMamerto(Main.instance.getHPByName(personaname));
					Player ejec = (Player) sender;
					Main.instance.getHPByName(ejec.getName()).setTeam(team);
					Main.instance.getHPByName(ejec.getName()).setWritingChannel(team.getChannel());
					Main.instance.getHPByName(ejec.getName()).addReadingChannel(team.getChannel());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha añadido a "+personaname+" al equipo '"+team.getNombre()+"'!"));
				}
				break;
			case "start":
				for(Equipo tmp : Main.instance.juego.getEquipos().keySet()) {
					for(Mamerto mam : tmp.getMiembros().keySet()) {
						Main.instance.juego.addMammert(mam);
						Player mamerto = mam.getPlayer();
						mamerto.setGameMode(GameMode.SURVIVAL);
						mamerto.setFlying(false);
						mamerto.setAllowFlight(false);
						mamerto.setInvulnerable(false);
						mamerto.setHealth(mamerto.getMaxHealth());
						mamerto.getInventory().clear();
						for(PotionEffect tmpp : mamerto.getActivePotionEffects()) {
							mamerto.removePotionEffect(tmpp.getType());
						}
						mamerto.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
						mam.getPlayer().teleport(tmp.getSpawn());
					}
				}
				Main.instance.getJuego().setEstado(EstadosJuego.JUGANDO);
				Main.instance.getJuego().setEpisodio(new Episodio(1));
				Bukkit.getPluginManager().callEvent(new EstadoChangeEvent(EstadosJuego.JUGANDO));
				Bukkit.getPluginManager().callEvent(new EpisodioChangeEvent(1));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha iniciado la partida!"));
				break;
			case "reset":
				Main.instance.juego = new Juego(); 
				Bukkit.getPluginManager().callEvent(new EstadoChangeEvent(EstadosJuego.ESPERANDO));
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
