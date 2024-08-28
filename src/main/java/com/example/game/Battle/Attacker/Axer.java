package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public class Axer implements attacker {

	@Override
	public int attack(Player player, Transformation transformation) {
		int a = new java.util.Random().nextInt(10);
		int b = 1;
		switch (a) {
		case 0, 1 -> {
			b = 2;
		}
		}
		Integer x = player.getMuscle() * player.getMuscle();
		double y = x * transformation.ex(player.getJob_axer()) * b;
		return (int) y;
	}

	@Override
	public int cooattack(Player player, Transformation transformation) {
		int a = new java.util.Random().nextInt(10);
		int b = 1;
		switch (a) {
		case 0, 1 -> {
			b = 2;
		}
		}
		Integer x = player.getMuscle() * player.getMuscle();
		double y = x * transformation.ex(player.getJob_axer()) * b;
		return (int) y;
	}

	@Override
	public void wait(Player player, Transformation transformation) {
		transformation.Add_Fluctuation_Status(player, 1, 5);
	}

}
