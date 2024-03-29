package chat;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import main.UHCLebrel;
import me.clip.placeholderapi.PlaceholderAPI;

public class NChannel implements IChannel{
	private UHCLebrel plugin;
	private ArrayList<Player> lectores;
	private String name;
	private char prefix;
	
	public NChannel(UHCLebrel plugin, String name, char prefix) {
		this.name = name;
		this.plugin = plugin;
		this.prefix = prefix;
		
		lectores = new ArrayList<Player>();
	}

	public char getPrefix() {
		return prefix;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Player> getChannelReaders() {
		return lectores;
	}
	public void sendRawMessage(String rawmsg) {
		for(Player tmp : lectores) {
			tmp.sendMessage(rawmsg);
		}
	}
	public void sendFormattedMsg(String rawmsg, Player sender) {
		String formato = plugin.getConfig().getString("chat.channels."+this.name+".format");
		for(Player tmp : this.lectores) {
			if(UHCLebrel.instance.getUHCPlayerByName(tmp.getName()).isDesconectado()) {
				continue;
			}
			formato = PlaceholderAPI.setPlaceholders(sender, formato);
			String mensaje = formato+rawmsg;
			tmp.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje));
		}
	}
	public boolean addChannelReader(Player lector) {
		for(Player lectort : lectores) {
			if(lectort.equals(lector)) {
				return false;
			}
		}
		this.lectores.add(lector);
		return true;
	}
	public String getFormat() {
		return UHCLebrel.instance.getConfig().getString("chat.channels."+this.name+".format").replace("%player_name%", "%s").concat("%s");
	}
	
	
	
}
