package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import main.UHCLebrel;
import uhc.GameManager;
import uhc.GameStatuses;
import uhc.PartChangeEvent;
import uhc.StatusChangeEvent;
import uhc.UHCPart;
import uhc.UHCTeam;
import util.UHCPlayer;

/**
 * Handler of the main command of this plugin, used to configure it
 *
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
			Bukkit.getConsoleSender().sendMessage("You have to be a player to execute this command!");
			return false;
		} else {
			Player ejecutor = (Player) sender;
			if (!(ejecutor.isOp() || ejecutor.hasPermission("uhc.command.main"))) {
				return true;
			}
			switch (args[0]) {
			case "reload":
				if (!(sender.hasPermission("uhc.command.reload"))) {
					sender.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&4You can't execute this command!"));
					break;
				}
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Config reloaded succesfully"));
				break;
			case "addteam":
				if (!(args.length <= 0)) {
					ArrayList<String> argus = new ArrayList<String>(Arrays.asList(args));
					argus.remove(0);
					StringJoiner j = new StringJoiner(" ");
					for (String tmp : argus) {
						j.add(tmp);
					}
					UHCLebrel.instance.gameManager
							.addTeam(new UHCTeam(j.toString(), UHCLebrel.instance.gameManager.getEquipos().size() + 1));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&2Team '" + j.toString() + "' created successfully!"));
				}
				break;
			case "loadteams":
				UHCLebrel.instance.loadTeamsFromConfig();
				sender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&2Teams succesfully loaded from config!"));
				break;
			case "teamlist":
				ArrayList<String> mensajesal = new ArrayList<String>();
				mensajesal.add(ChatColor.translateAlternateColorCodes('&', "&8--------&6Team list&8-----------"));
				for (UHCTeam tmp : UHCLebrel.instance.getGameManager().getEquipos().keySet()) {
					int id = tmp.getId();
					String nombre = tmp.getNombre();
					String bonito = ChatColor.translateAlternateColorCodes('&',
							"&6" + id + "&8---------------------&2" + nombre);
					mensajesal.add(bonito);
				}
				mensajesal.add(ChatColor.translateAlternateColorCodes('&', "&8-----------------------------------"));
				for (String msg : mensajesal) {
					sender.sendMessage(msg);
				}
				break;
			case "addmember":
				if (!(args.length <= 1)) {
					int teamid = Integer.valueOf(args[1]);
					String personaname = args[2];
					UHCTeam team = UHCTeam.getEquipoById(teamid);
					UHCPlayer mammert = UHCLebrel.instance.getUHCPlayerByName(personaname);
					team.addMamerto(mammert);
					UHCLebrel.instance.getGameManager().addMammert(mammert);
					Player ejec = mammert.getPlayer();
					UHCLebrel.instance.getUHCPlayerByName(ejec.getName()).setTeam(team);
					UHCLebrel.instance.getUHCPlayerByName(ejec.getName()).setWritingChannel(team.getChannel());
					UHCLebrel.instance.getUHCPlayerByName(ejec.getName()).addReadingChannel(team.getChannel());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&2'" + personaname + " added to team'" + team.getNombre() + "'!"));
				}
				break;
			case "start":
				BukkitRunnable r = new BukkitRunnable() {
					@Override
					public void run() {
						World ovw = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.overworld"));
						World net = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.nether"));
						World end = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.end"));
						ovw.setPVP(false);
						net.setPVP(false);
						end.setPVP(false);
						ovw.getWorldBorder().setSize(UHCLebrel.instance.getConfig().getInt("juego.worldborder.inicial"));
						net.getWorldBorder().setSize(UHCLebrel.instance.getConfig().getInt("juego.worldborder.inicial")/8);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
						ovw.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
						net.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
						end.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

						if (UHCLebrel.instance.isPremadeTeams()) {
							int number = UHCLebrel.instance.getGameManager().getEquipos().size();
							int side = 2 * UHCLebrel.instance.getConfig().getInt("juego.worldborder.inicial");
							Location[] spawns = getSpacedSpawn(number, side);
							int i = 0;
							for (UHCTeam tmp : UHCLebrel.instance.getGameManager().getEquipos().keySet()) {
								for (UHCPlayer mam : tmp.getMiembros().keySet()) {
									Player mamerto = mam.getPlayer();
									mamerto.setGameMode(GameMode.SURVIVAL);
									mamerto.setFlying(false);
									mamerto.setAllowFlight(false);
									mamerto.setInvulnerable(false);
									mamerto.setHealth(mamerto.getMaxHealth());
									mamerto.getInventory().clear();
									for (PotionEffect tmpp : mamerto.getActivePotionEffects()) {
										mamerto.removePotionEffect(tmpp.getType());
									}
									final Location spawn = spawns[i];
									BukkitRunnable tp = new BukkitRunnable() {
										@Override
										public void run() {
											mamerto.getPlayer().teleport(spawn);
										}
									};
									tp.runTask(UHCLebrel.instance);

								}
								i++;
							}
						} else {
							int players = UHCLebrel.instance.getGameManager().getParticipantes().size();
							int side = 2 * UHCLebrel.instance.getConfig().getInt("juego.worldborder.inicial");
							Location[] spawns = getSpacedSpawn(players, side);
							for (int i = 0; i < players; i++) {
								Location spawn = spawns[i];
								UHCPlayer player = UHCLebrel.instance.gameManager.getParticipantes().get(i);
								Player mamerto = player.getPlayer();
								mamerto.setGameMode(GameMode.SURVIVAL);
								mamerto.setFlying(false);
								mamerto.setAllowFlight(false);
								mamerto.setInvulnerable(false);
								mamerto.setHealth(mamerto.getMaxHealth());
								mamerto.getInventory().clear();
								for (PotionEffect tmpp : mamerto.getActivePotionEffects()) {
									mamerto.removePotionEffect(tmpp.getType());
								}

								BukkitRunnable tp = new BukkitRunnable() {

									@Override
									public void run() {
										player.getPlayer().teleport(spawn);
									}
								};
								tp.runTask(UHCLebrel.instance);
							}

						}

						UHCLebrel.instance.getGameManager().setEstado(GameStatuses.PLAYING);
						UHCLebrel.instance.getGameManager().setEpisodio(new UHCPart(1));
						Bukkit.getPluginManager().callEvent(new StatusChangeEvent(GameStatuses.PLAYING));
						Bukkit.getPluginManager().callEvent(new PartChangeEvent(1));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2¡Game started!"));
					}
				};
				r.runTask(UHCLebrel.instance);
				break;
			case "reset":
				UHCLebrel.instance.gameManager = new GameManager();
				Bukkit.getPluginManager().callEvent(new StatusChangeEvent(GameStatuses.WAITING));
				break;
			case "removeplayer":
				if (args.length > 1) {
					String playerName = args[1];
					UHCPlayer adescalificar = UHCLebrel.instance.getUHCPlayerByName(playerName);
					adescalificar.setDescalificado(true);
					adescalificar.getPlayer().setHealth(0);
				}
				break;
			case "stopserver":
				if (UHCLebrel.instance.gameManager.getEstado() == GameStatuses.FINISHING) {
					Bukkit.getServer().shutdown();
				}
				break;
			default:
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4That argument doesn't exist!."));
			}
			return true;
		}
	}

	private Location[] getSpacedSpawn(int spawns, int side) {
		int perimeter = 4 * side;
		int separation = perimeter / spawns;
		int x = -side / 2;
		int z = -side / 2;
		Location[] spawnsLocations = new Location[spawns];
		for (int i = 0; i < spawns; i++) {
			World overworld = Bukkit.getWorld(UHCLebrel.instance.getConfig().getString("juego.mundos.overworld"));
			int y = overworld.getHighestBlockYAt(x, z);

			Location spawn = new Location(overworld, x, y+2, z);
			spawnsLocations[i] = spawn;
			if (x + separation <= side / 2 && z == -side / 2) {
				x += separation;
				continue;
			}
			if (x + separation > side / 2 && z == -side / 2) {
				z += x + separation - side / 2;
				x = side / 2;
				continue;
			}
			if (x == side / 2 && z + separation <= side / 2) {
				z += separation;
				continue;
			}
			if (x == side / 2 && z + separation > side / 2) {
				x -= z + separation - side / 2;
				z = side / 2;
				continue;
			}
			if (x - separation >= -side / 2 && z == side / 2) {
				x -= separation;
				continue;
			}
			if (x - separation < -side / 2 && z == side / 2) {
				z -= x - separation + side;
				x = -side / 2;
				continue;
			}

		}
		return spawnsLocations;
	}
}
