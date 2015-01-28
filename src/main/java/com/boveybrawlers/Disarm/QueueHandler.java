package com.boveybrawlers.Disarm;

import org.bukkit.scheduler.BukkitRunnable;

public class QueueHandler extends BukkitRunnable {
	private int time;
	
	public QueueHandler(int time) {
		this.time = time;
	}
	
	public void run() {
		if(time == 0) {
			this.cancel();
			Game.queueCountdown = false;
			Game.start();
		} else {
			for(Disarmer disarmer : Game.disarmers) {
				disarmer.getPlayer().setLevel(time);
			}
		}
		this.time--;
	}
}
