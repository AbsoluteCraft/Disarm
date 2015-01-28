package com.boveybrawlers.Disarm;

import org.bukkit.scheduler.BukkitRunnable;

public class PlacementHandler extends BukkitRunnable {
	private int time;
	
	public PlacementHandler(int time) {
		this.time = time;
	}
	
	public void run() {
		if(time == 0) {
			this.cancel();
			Game.placementCountdown = false;
			Game.seek();
		}
		else {
			for(Disarmer disarmer : Game.disarmers) {
				disarmer.getPlayer().setLevel(time);
			}
		}
		
		this.time--;
	}
}