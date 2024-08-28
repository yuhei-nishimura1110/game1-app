package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public class melleter implements attacker {

	@Override
	public int attack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getMuscle();
		double y = x * transformation.ex(player.getJob_melleter());
		return (int) y;
	}

	@Override
	public int cooattack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getMuscle();
		double y = x * transformation.ex(player.getJob_melleter());
		return (int) y;
	}

	@Override
	public void wait(Player player, Transformation transformation) {
		transformation.Add_Fluctuation_Status(player, 1, 5);
	}

}
