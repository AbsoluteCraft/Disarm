package com.boveybrawlers.Disarm;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Disarmer {
	String username = null;
	Integer score = 0;
	Integer team;
	
	public Disarmer(String username) {
		this.username = username;
	}
	
	public String getName() {
		return this.username;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	@SuppressWarnings("deprecation")
	public void addScore(int score) {
		this.score += score;
		Disarm.plugin.objective.getScore(Bukkit.getServer().getPlayer(this.username)).setScore(this.score);
	}
	
	public Integer getTeam() {
		return this.team;
	}
	
	public void setTeam(Integer team) {
		this.team = team;
	}
	
	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(this.username);
	}
	
	public void heal() {
		Player player = Bukkit.getServer().getPlayer(this.username);
		player.setHealth((double) 20);
		player.setFoodLevel(20);
	}
	
	public void setGameMode(int mode) {
		if(mode == 0) {
			Bukkit.getServer().getPlayer(this.username).setGameMode(GameMode.SURVIVAL);
		} else if(mode == 1) {
			Bukkit.getServer().getPlayer(this.username).setGameMode(GameMode.CREATIVE);
		}
	}
	
	public void sendMessage(String message) {
		Bukkit.getServer().getPlayer(this.username).sendMessage(message);
	}
	
	public void teleport(Location location) {
		Player player = Bukkit.getServer().getPlayer(this.username);
		player.teleport(location);
	}
	
	public void removeInventory() {
		Player player = Bukkit.getServer().getPlayer(this.username);
		player.getInventory().clear();
	}
	
	public void setInventory(String type) {
		Player player = Bukkit.getServer().getPlayer(this.username);
		player.getInventory().clear();
		
		if(type == "blocks") {
			player.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 1));
			player.getInventory().addItem(new ItemStack(Material.ANVIL, 1));
			player.getInventory().addItem(new ItemStack(Material.COBBLE_WALL, 1));
			player.getInventory().addItem(new ItemStack(Material.SEA_LANTERN, 1));
			player.getInventory().addItem(new ItemStack(Material.FLOWER_POT, 1));
			player.getInventory().addItem(new ItemStack(Material.FURNACE, 1));
			player.getInventory().addItem(new ItemStack(Material.BREWING_STAND, 1));
			player.getInventory().addItem(new ItemStack(Material.NETHER_BRICK_STAIRS, 1));
			player.getInventory().addItem(new ItemStack(Material.HOPPER, 1));
		} else if(type == "shears") {
			player.getInventory().addItem(new ItemStack(Material.SHEARS, 1));
		}
	}
}
