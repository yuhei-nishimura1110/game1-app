package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public class Archer implements attacker {

	@Override
	public int attack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getTechnique() * player.getSpirit();
		double y = x * transformation.ex(player.getJob_archer());
		return (int) y;
	}

	@Override
	public int cooattack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getTechnique() * player.getSpirit();
		double y = x * transformation.ex(player.getJob_archer()) * 1.2;
		return (int) y;
	}

	@Override
	public void wait(Player player, Transformation transformation) {
		transformation.Add_Fluctuation_Status(player, 1, 5);

	}

}
