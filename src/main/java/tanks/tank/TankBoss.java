package tanks.tank;

import tanks.Game;
import tanks.Panel;
import tanks.bullet.Bullet;
import tanks.event.EventCreateTank;
import tanks.gui.screen.ScreenGame;
import tanks.obstacle.Obstacle;
import tanks.registry.RegistryTank;

import java.util.ArrayList;

/**
 * A big boss tank which spawns other tanks and takes 5 regular bullets to destroy
 */
public class TankBoss extends TankAIControlled
{
	public TankBoss(String name, double x, double y, double angle)
	{
		super(name, x, y, Game.tile_size * 3, 255, 0, 0, angle, ShootAI.alternate);

		this.enableMovement = false;
		this.enableMineLaying = false;
		this.bullet.maxLiveBullets = 4;
		this.cooldownRandom = 200;
		this.cooldownBase = 100;
		this.aimAccuracyOffset = 0;
		this.bullet.bounces = 3;
		this.bullet.effect = Bullet.BulletEffect.trail;
		this.bullet.speed = 25.0 / 8;
		this.bullet.size = 25;
		this.bullet.heavy = true;
		this.bullet.name = "Mega bullet";
		this.health = 5;
		this.baseHealth = 5;
		this.coinValue = 25;
		this.spawnsTanks = true;

		this.description = "A big boss tank which spawns---other tanks and takes 5 regular---bullets to destroy";
	}
}
