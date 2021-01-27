package util;

import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;

public class TaskManager {
	private TaskChainFactory factory;

	public TaskManager(JavaPlugin plugin) {
		this.factory = BukkitTaskChainFactory.create(plugin);
	}

	public <T> TaskChain<T> newChain() {
		return this.factory.newChain();
	}

	public <T> TaskChain<T> newSharedChain(String name){
		return this.factory.newSharedChain(name);
	}
}
