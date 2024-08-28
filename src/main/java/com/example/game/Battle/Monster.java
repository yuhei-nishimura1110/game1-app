package com.example.game.Battle;

import com.example.game.entity.Player;

public class Monster {
	public int m(Player player) {
		int x = (player.getKnowledge() / 2) + player.getMagic() + player.getMuscle() + (player.getQuick() / 2)
				+ player.getMax_technique() + (player.getSpirit() / 2) + (player.getStamina() / 2);
		return x;
	}
}
