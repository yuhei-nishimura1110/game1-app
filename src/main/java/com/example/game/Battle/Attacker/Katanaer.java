package com.example.game.Battle.Attacker;

import com.example.game.Service.Transformation;
import com.example.game.entity.Player;

public class Katanaer implements attacker {

	@Override
	public int attack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getTechnique() * player.getMagic() * player.getSpirit();
		double y = x * transformation.ex(player.getJob_katanaer());
		return (int) y;
	}

	@Override
	public int cooattack(Player player, Transformation transformation) {
		Integer x = player.getMuscle() * player.getTechnique() * player.getMuscle();
		double y = x * transformation.ex(player.getJob_katanaer()) * 1.5;
		return (int) y;
	}

	@Override
	public void wait(Player player, Transformation transformation) {
		// TODO 自動生成されたメソッド・スタブ
		transformation.Add_Fluctuation_Status(player, 1, 5);
	}
}
