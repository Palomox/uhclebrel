package commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import chat.IChannel;
import main.UHCLebrel;
import util.UHCPlayer;

/**
 * Class which handles the /channel command
 * @author palomox
 *
 */
public class ChannelCmd implements CommandExecutor {
	private UHCLebrel plugin;

	public ChannelCmd(UHCLebrel plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED+"¡No puedes ejecutar este comando desde la consola!");
			return false;
		}else {
			Player ejecutor = (Player) sender;
			if(ejecutor.hasPermission("hopoke.command.channel")||ejecutor.isOp()) {
				UHCPlayer jugador = UHCPlayer.getHPPlayer(ejecutor, plugin);
				if(args.length <1) {
					ejecutor.sendMessage(ChatColor.DARK_RED+"¡No has especificado el canal al que unirte");

				}else {
					IChannel deseado = null;
					if(!(args.length <1)) {
						String canal = args[1];
						deseado =plugin.getChannelByName(canal);
					}
					String operacion = args[0];
					if(deseado !=null) {
						if(jugador.getPlayer().hasPermission(plugin.getConfig().getString("chat.channels."+deseado.getName()+".perm"))) {
						switch(operacion) {
						case "write":
						String dejado = jugador.getWritingChannel().getName();
						jugador.setWritingChannel(deseado);
						jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Has dejado de escribir en "+dejado+" para escribir en "+jugador.getWritingChannel().getName()+".");
						break;
						case "read":
						jugador.addReadingChannel(deseado);
						if(deseado.addChannelReader(ejecutor)) {
							jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Ahora también lees "+deseado.getName());
						}else {
							jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"No puedes leer "+deseado.getName()+" porque ya lo estas leyendo!");
						}
						break;
						case "leave":
						if(jugador.estaLeyendo(deseado)) {
							jugador.removeReadingChannel(deseado);
							deseado.getChannelReaders().remove(jugador.getPlayer());
							jugador.getPlayer().sendMessage(ChatColor.DARK_GREEN+"Has dejado de leer "+deseado.getName());
						}else {
							jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"No puedes dejar ese canal porque no estas en el.");
						}
						break;
						case "debug":
							Bukkit.getConsoleSender().sendMessage(jugador.getWritingChannel().getChannelReaders().toString());
							break;
						default:
							jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"El primer argumento ha de ser leave, read, o write");
					}
					}
					}else {
						jugador.getPlayer().sendMessage(ChatColor.DARK_RED+"Ese canal no existe");
					}
				}
			}
			return true;
		}
}}
