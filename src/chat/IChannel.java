package chat;

import java.util.ArrayList;

import org.bukkit.entity.Player;


/**
 * Interface to represent a channel in the chat managing system
 * @author palomox
 *
 */
public interface IChannel {
	/**
	 * Sends a raw message to the channel, without the format
	 * @param msg text to send in the message
	 */
	public void sendRawMessage(String msg);
	@Deprecated
	public void sendFormattedMsg(String formatedmsg, Player sender);
	/**
	 * Method to get the name of the channel
	 * @return the name of the channel.
	 */
	public String getName();
	/**
	 * Method to get an ArrayList of the players who are reading that channel
	 * @return ArrayList of the players reading a channel
	 */
	public ArrayList<Player> getChannelReaders();
	/**
	 * Adds a Player into the readers of a channel
	 * @param player the player to add to the channel
	 * @return if the player was succesfully added
	 */
	public boolean addChannelReader(Player player);
	/**
	 * Method to get the prefix to send a message to a channel without being in it
	 * @return the char of the prefix, or null if there is no one
	 */
	public char getPrefix();
	/**
	 * Method to get the format of the channel in a {@link String#format(String, Object...)} compatible way.
	 * @return String which represents the format of the channel in a {@link String#format(String, Object...)} compatible way
	 */
	public String getFormat();
}
