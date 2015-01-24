# Disarm
Disarm plugin for AbsoluteCraft

## Documentation

Disarm is a minigame with two teams which start in two separate buildings. Inside each building is three bombs, which will appear as TNT. To disarm the bomb you must break it with shears and bring it back to your spawn zone where you put it in a chest. A timer counts down in this mode and so it is a possibility that both teams can lose. If only one team wins, the game ends and tokens are added to the winning players. If both teams win, they are teleported to a final zone where there is only one bomb. The objective of this mode is to sabotage the opposing teams spawn zone and set it off. PvP is enabled. Each time a person is killed , bomb gets reset.

1. The player types /disarm join
2. The player is added to the queue and teleported to the lobby
3. When more than four players are in the queue, the game countdown starts for 30 seconds
  - The countdown is cancelled if the queue goes below 4 players
  - If there are four or five players, only two out of the three bombs are active in the game
  - If there are six or more players, all three bombs are active
  - If the queue has an odd number of players, team one will always have one extra player
4. Players are split into two teams and teleported to their respective spawn zone
5. A five second timer counts down until players are allowed out of the spawn zone
6. A five minute timer starts which counts down until the bombs go off, unless they have been disarmed
  - If both teams disarm all of the bombs, the second game mode is started
  - If only one team disarm all of the bombs, that team wins and the spawn zone of the opposing team will explode
  - If both teams fail to disarm all of the bombs, the second game mode is started
7. If the second game mode is started, players are teleported to their respective spawn zone in the second area
8. A five second timer counts down until players are allowed out of the spawn zone
9. The game waits until a team has taken the bomb from the middle of the area, sneaked it into the opposing teams spawn zone and set it to explode.
10. Each player on the winning team gets tokens.
