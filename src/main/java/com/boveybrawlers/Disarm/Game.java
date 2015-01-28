package com.boveybrawlers.Disarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

public class Game implements Listener {
	public static boolean playing = false;
	public static ArrayList<Disarmer> disarmers = new ArrayList<Disarmer>();
	
	public static QueueHandler queueTask = null;
	public static boolean queueCountdown = false;
	
	public static PlacementHandler placementTask = null;
	public static boolean placementCountdown = false;
	
	public static SeekHandler seekTask = null;
	public static boolean seekCountdown = false;
	
	public static int getDisarmerByName(String username) {
		for(Disarmer disarmer : disarmers) {
			if(disarmer.getName().equals(username)) {
				return disarmers.indexOf(disarmer);
			}
		}
		return -1;
	}
	
	public static void addPlayer(String username) {
		disarmers.add(new Disarmer(username));
		
		if(disarmers.size() == 16) {
			start();
			queueTask.cancel();
		} else if(disarmers.size() == 4) {
			if(queueCountdown == false) {
				queueCountdown();
			}
		} else if(disarmers.size() == 1) {
			if(Bukkit.getWorld("games") != null) {
				Disarm.plugin.world = Bukkit.getWorld("Games");
			}
			
			Disarm.plugin.lobby = new Location(Disarm.plugin.world, 0, 0, 0, 0, 0);
			Disarm.plugin.teamOneSpawn = new Location(Disarm.plugin.world, 0, 0, 0, 0, 0);
			Disarm.plugin.teamTwoSpawn = new Location(Disarm.plugin.world, 0, 0, 0, 0, 0);
		}
		
		Disarmer disarmer = disarmers.get(getDisarmerByName(username));
		
		disarmer.setGameMode(0);
		
		for(Disarmer d : disarmers) {
			d.sendMessage(Disarm.prefix + ChatColor.GREEN + username + " has joined");
		}
	}
	
	public static void removePlayer(int index) {
		String username = disarmers.get(index).getName();
		
		Player player = disarmers.get(index).getPlayer();
		
		if(playing == true) {
			Team dead = player.getScoreboard().getPlayerTeam(player);
			dead.unregister();
		}
		
		player.setScoreboard(Disarm.plugin.manager.getNewScoreboard());
		
		disarmers.get(index).removeInventory();
		disarmers.get(index).heal();
		disarmers.get(index).teleport(Disarm.plugin.lobby);
		disarmers.get(index).getPlayer().setLevel(0);
		
		disarmers.remove(index);
		
		for(Disarmer disarmer : disarmers) {
			disarmer.sendMessage(Disarm.prefix + ChatColor.RED +  username + " has left");
		}
		
		if(playing == false && disarmers.size() == 1) {
			if(queueCountdown == true) {
				queueTask.cancel();
			}
		}
		
		if(playing == true && disarmers.size() == 3) {
			if(placementCountdown == true) {
				placementTask.cancel();
				placementCountdown = false;
			}
			if(seekCountdown == true) {
				seekTask.cancel();
				seekCountdown = false;
			}
			
			for(Disarmer disarmer : disarmers) {
				disarmer.sendMessage(Disarm.prefix + ChatColor.RED + "Not enough players to continue");
				
				disarmer.removeInventory();
				disarmer.heal();
				disarmer.teleport(Disarm.plugin.lobby);
			}
			
			// Reset game
			disarmers.clear();
			playing = false;
		}
	}
	
	private static void queueCountdown() {
		queueCountdown = true;
		queueTask = new QueueHandler(30);
		queueTask.runTaskTimer(Disarm.plugin, 0, 20);
	}
	
	public static void start() {
		int i = 1;
		int middle = Math.round(disarmers.size() / 2);
		
		long seed = System.nanoTime();
		Collections.shuffle(disarmers, new Random(seed));
		
		for(Disarmer disarmer : disarmers) {
			if(i > middle) {
				Disarm.plugin.teamTwo.addPlayer(disarmer.getPlayer());
				disarmer.teleport(Disarm.plugin.teamOneSpawn);
				disarmer.setTeam(1);
				
				disarmer.teleport(Disarm.plugin.teamOneSpawn);
			} else {
				Disarm.plugin.teamOne.addPlayer(disarmer.getPlayer());
				disarmer.teleport(Disarm.plugin.teamTwoSpawn);
				disarmer.setTeam(0);
				
				disarmer.teleport(Disarm.plugin.teamTwoSpawn);
			}
			
			disarmer.getPlayer().setScoreboard(Disarm.plugin.board);
			Disarm.plugin.objective.getScore(disarmer.getName()).setScore(0);
			Disarm.plugin.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			disarmer.setInventory("blocks");
			
			i++;
			
			playing = true;
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Disarm.plugin, new Runnable() {
				public void run() {
					Disarm.plugin.teamOneDoorOne.getBlock().setType(Material.REDSTONE_BLOCK);
					Disarm.plugin.teamOneDoorTwo.getBlock().setType(Material.REDSTONE_BLOCK);
					Disarm.plugin.teamTwoDoorOne.getBlock().setType(Material.REDSTONE_BLOCK);
					Disarm.plugin.teamTwoDoorTwo.getBlock().setType(Material.REDSTONE_BLOCK);
					
					placementCountdown();
				}
			}, 100); // 5 second timer
		}
	}
	
	public static void seek() {
		Disarm.plugin.teamOneDoorOne.getBlock().setType(Material.AIR);
		Disarm.plugin.teamOneDoorTwo.getBlock().setType(Material.AIR);
		Disarm.plugin.teamTwoDoorOne.getBlock().setType(Material.AIR);
		Disarm.plugin.teamTwoDoorTwo.getBlock().setType(Material.AIR);
		
		for(Disarmer disarmer : disarmers) {
			disarmer.removeInventory();
			if(disarmer.getTeam() == 0) {
				disarmer.teleport(Disarm.plugin.teamOneSpawn);
			} else {
				disarmer.teleport(Disarm.plugin.teamTwoSpawn);
			}
			
			disarmer.sendMessage(Disarm.prefix + ChatColor.BOLD + ChatColor.GOLD + "Bomb Placement Finished!");
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Disarm.plugin, new Runnable() {
			public void run() {
				Disarm.plugin.teamOneDoorOne.getBlock().setType(Material.REDSTONE_BLOCK);
				Disarm.plugin.teamOneDoorTwo.getBlock().setType(Material.REDSTONE_BLOCK);
				Disarm.plugin.teamTwoDoorOne.getBlock().setType(Material.REDSTONE_BLOCK);
				Disarm.plugin.teamTwoDoorTwo.getBlock().setType(Material.REDSTONE_BLOCK);
				
				seekCountdown();
			}
		}, 100); // 5 second timer
	}
	
	public static void placementCountdown() {
		placementCountdown = true;
		placementTask = new PlacementHandler(90);
		placementTask.runTaskTimer(Disarm.plugin, 0, 20);
	}
	
	public static void seekCountdown() {
		seekCountdown = true;
		seekTask = new SeekHandler(300);
		seekTask.runTaskTimer(Disarm.plugin, 0, 20);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		int index = getDisarmerByName(player.getName());
		if(index != -1) {
			removePlayer(index);
		}
	}
}
