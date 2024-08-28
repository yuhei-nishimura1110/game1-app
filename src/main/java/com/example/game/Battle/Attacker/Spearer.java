package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public class Spearer implements attacker {

	@Override
	public int attack(Player player, Transformation transformation) {
		int a = new java.util.Random().nextInt(10);
		double b = 0;
		switch (a) {
		case 0, 1, 2 -> {
			b = 1;
		}
		case 3, 4, 5, 6 -> {
			b = 0.5;
		}
		case 7, 8, 9 -> {
			b = 1.5;
		}
		}
		double x = Math.sqrt(player.getMuscle() * player.getTechnique() * player.getQuick() * player.getQuick());
		double y = x * transformation.ex(player.getJob_spearer()) * b;
		return (int) y;
	}

	@Override
	public int cooattack(Player player, Transformation transformation) {
		int a = new java.util.Random().nextInt(10);
		double b = 0;
		switch (a) {
		case 0, 1, 2 -> {
			b = 1;
		}
		case 3, 4, 5, 6 -> {
			b = 0.5;
		}
		case 7, 8, 9 -> {
			b = 1.5;
		}
		}
		double x = Math.sqrt(player.getMuscle() * player.getTechnique() * player.getQuick() * player.getQuick());
		double y = x * transformation.ex(player.getJob_spearer()) * b * 0.7;
		return (int) y;
	}

	@Override
	public void wait(Player player, Transformation transformation) {
		transformation.Add_Fluctuation_Status(player, 1, 5);

	}

}
