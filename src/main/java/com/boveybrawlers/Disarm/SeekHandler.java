package com.boveybrawlers.Disarm;

import org.bukkit.scheduler.BukkitRunnable;

public class SeekHandler extends BukkitRunnable {
	private int time;
	
	public SeekHandler(int time) {
		this.time = time;
	}
	
	public void run() {
		if(time == 0) {
			this.cancel();
			Game.seekCountdown = false;
			// end seeking
		}
		else {
			for(Disarmer disarmer : Game.disarmers) {
				disarmer.getPlayer().setLevel(time);
			}
		}
		
		this.time--;
	}
}