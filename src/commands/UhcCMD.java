package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import main.UHCLebrel;
import uhc.UHCPart;
import uhc.PartChangeEvent;
import uhc.UHCTeam;
import uhc.StatusChangeEvent;
import uhc.GameStatuses;
import uhc.GameManager;
import util.UHCPlayer;

/**
 * Handler of the main command of this plugin, used to configure it
 * @author palomox
 *
 */
public class UhcCMD implements CommandExecutor {
	private UHCLebrel plugin;
	public UhcCMD(UHCLebrel plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command label, String ni, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("No se puede ejecutar este comando desde la consola!");
			return false;
		} else {
			Player ejecutor = (Player) sender;
			if(!(ejecutor.isOp() || ejecutor.getName() == "Blazekh")) {
				return true;
			}
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
				UHCLebrel.instance.juego.addTeam(new UHCTeam(j.toString(), UHCLebrel.instance.juego.getEquipos().size()+1));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha creado el equipo '"+j.toString()+"'!"));
				}
				break;
			case "setteamspawn":
				if(!(args.length <=1)) {
					int teamid = Integer.valueOf(args[1]);
					UHCTeam team = UHCTeam.getEquipoById(teamid);
					team.setSpawn(ejecutor.getLocation());
					UHCLebrel.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.world", team.getSpawn().getWorld().getName());
					UHCLebrel.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.x", team.getSpawn().getX());
					UHCLebrel.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.y", team.getSpawn().getY());
					UHCLebrel.instance.getConfig().set("juego.equipos."+team.getNombre()+".spawn.z", team.getSpawn().getZ());
					UHCLebrel.instance.saveConfig();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha colocado el spawn del equipo '"+team.getNombre()+"'!"));
				}
				break;
			case "loadteams":
				UHCLebrel.instance.loadTeamsFromConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se han cargado los equipos desde la configuraci�n correctamente!"));
				break;
			case "teamlist":
				ArrayList<String> mensajesal = new ArrayList<String>();
				mensajesal.add(ChatColor.translateAlternateColorCodes('&', "&8--------&6Lista de equipos&8-----------"));
				for(UHCTeam tmp : UHCLebrel.instance.getJuego().getEquipos().keySet()) {
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
					UHCTeam team = UHCTeam.getEquipoById(teamid);
					UHCPlayer mammert = UHCLebrel.instance.getHPByName(personaname);
					team.addMamerto(mammert);
					Player ejec = mammert.getPlayer();
					UHCLebrel.instance.getHPByName(ejec.getName()).setTeam(team);
					UHCLebrel.instance.getHPByName(ejec.getName()).setWritingChannel(team.getChannel());
					UHCLebrel.instance.getHPByName(ejec.getName()).addReadingChannel(team.getChannel());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2�Se ha a�adido a "+personaname+" al equipo '"+team.getNombre()+"'!"));
				}
				break;
			case "start":
				for(UHCTeam tmp : UHCLebrel.instance.juego.getEquipos().keySet()) {
					for(UHCPlayer mam : tmp.getMiembros().keySet()) {
						UHCLebrel.instance.juego.addMammert(mam);
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
						mam.getPlayer().teleport(tmp.getSpawn());
					}
				}

				UHCLebrel.instance.getJuego().setEstado(GameStatuses.PLAYING);
				UHCLebrel.instance.getJuego().setEpisodio(new UHCPart(1));
				Bukkit.getPluginManager().callEvent(new StatusChangeEvent(GameStatuses.PLAYING));
				Bukkit.getPluginManager().callEvent(new PartChangeEvent(1));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Se ha iniciado la partida!"));
				break;
			case "reset":
				UHCLebrel.instance.juego = new GameManager();
				Bukkit.getPluginManager().callEvent(new StatusChangeEvent(GameStatuses.WAITING));
				break;
			case "descalificar":
				if(args.length >1) {
					String playerName = args[1];
					UHCPlayer adescalificar = UHCLebrel.instance.getHPByName(playerName);
					adescalificar.setDescalificado(true);
					adescalificar.getPlayer().setHealth(0);
				}
				break;
			case "pararserver":
				if(UHCLebrel.instance.juego.getEstado() == GameStatuses.FINISHING) {
					Bukkit.getServer().shutdown();
				}
				break;
			default:
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Ese argumento no existe."));
			}
			return true;
		}
	}
}
