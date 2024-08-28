package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public interface attacker {
	public int attack(Player player, Transformation transformation);

	public int cooattack(Player player, Transformation transformation);

	public void wait(Player player, Transformation transformation);

}
