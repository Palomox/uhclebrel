package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import main.UHCLebrel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import uhc.UHCTeam;
import util.UHCPlayer;

public class CreateTeamCMD implements CommandExecutor{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if(UHCLebrel.instance.isPremadeTeams()) {
			sender.sendMessage(Component.text(UHCLebrel.instance.messages.teamsMade, NamedTextColor.DARK_RED));
			return true;
		}
		UHCPlayer executor = UHCLebrel.instance.getUHCPlayerByName(sender.getName());
		UHCPlayer mate = UHCLebrel.instance.getUHCPlayerByName(args[0]);

		if(mate.getTeam() != null) {
			sender.sendMessage(Component.text(String.format(UHCLebrel.instance.messages.playerTaken, mate.getPlayer().getName()), NamedTextColor.DARK_RED));
			return true;
		}

		if(executor.getTeam() != null) {
			mate.setTeam(executor.getTeam());
			executor.getTeam().addMamerto(mate);
			sender.sendMessage(Component.text(UHCLebrel.instance.messages.addedMember, NamedTextColor.GREEN));
			return true;

		} else {
			if(executor.getPlayer().getLocation().distance(mate.getPlayer().getLocation()) > 10) {
				sender.sendMessage(Component.text(UHCLebrel.instance.messages.tooFar, NamedTextColor.RED));
				return true;
			}
			String teamName = args[1];
			UHCTeam team = new UHCTeam(teamName, UHCLebrel.instance.getGameManager().getEquipos().size()+1);
			team.addMamerto(executor);
			team.addMamerto(mate);
			mate.setTeam(team);
			executor.setTeam(team);
			sender.sendMessage(Component.text(UHCLebrel.instance.messages.teamCreated, NamedTextColor.GREEN));
			return true;

		}
	}

}
