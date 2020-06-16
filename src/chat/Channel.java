package chat;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import main.Main;

public class Channel {
	private Main plugin;
	private ArrayList<Player> lectores;
	private String name;
	
	public Channel(Main plugin, String name) {
		this.name = name;
		this.plugin = plugin;
		
		lectores = new ArrayList<Player>();
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
	
	
	
}
