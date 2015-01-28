package com.boveybrawlers.Disarm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Disarm extends JavaPlugin {
	public static Disarm plugin = null;
	public static String prefix = ChatColor.YELLOW + "" + ChatColor.BOLD + "Disarm" + ChatColor.RESET + ChatColor.DARK_GRAY + " | " + ChatColor.RESET;
	
	public World world;
	
	public Location lobby;
	
	public Location teamOneDoorOne;
	public Location teamOneDoorTwo;
	public Location teamTwoDoorOne;
	public Location teamTwoDoorTwo;
	
	public Location teamOneSpawn = null;
	public Location teamTwoSpawn;
	
	public ScoreboardManager manager;
	public Scoreboard board;
	public Objective objective;
	
	public Team teamOne;
	public Team teamTwo;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		this.getCommand("disarm").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Game(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
