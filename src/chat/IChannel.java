package chat;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public interface IChannel {
	public void sendRawMessage(String msg);
	public void sendFormattedMsg(String formatedmsg, Player sender);
	public String getName();
	public ArrayList<Player> getLectores();
	public boolean addLector(Player player);
	public char getPrefix();
}
