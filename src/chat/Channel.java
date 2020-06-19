package chat;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import main.Main;

public class Channel {
	private Main plugin;
	private ArrayList<Player> lectores;
	private String name;
	private char prefix;
	
	public Channel(Main plugin, String name, char prefix) {
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

	public ArrayList<Player> getLectores() {
		return lectores;
	}
	public void sendRawMessage(String rawmsg) {
		for(Player tmp : lectores) {
			tmp.sendMessage(rawmsg);
		}
	}
	public boolean addLector(Player lector) {
		for(Player lectort : lectores) {
			if(lectort.equals(lector)) {
				return false;
			}
		}
		this.lectores.add(lector);
		return true;
	}
	
	
	
}
