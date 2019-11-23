package tanks.bullet;

import tanks.Drawing;
import tanks.Effect;
import tanks.Game;
import tanks.event.EventBulletDestroyed;
import tanks.event.EventBulletInstantWaypoint;
import tanks.gui.screen.ScreenGame;
import tanks.hotbar.ItemBullet;
import tanks.tank.Ray;
import tanks.tank.Tank;

public class BulletLaser extends BulletInstant
{
	public BulletLaser(double x, double y, int bounces, Tank t, boolean affectsMaxLiveBullets, ItemBullet ib)
	{
		super(x, y, bounces, t, affectsMaxLiveBullets, ib);
		this.playPopSound = false;
		this.baseColorR = 255;
		this.baseColorG = 0;
		this.baseColorB = 0;
		this.name = "laser";
		this.effect = BulletEffect.none;
	}

	public BulletLaser(double x, double y, int bounces, Tank t)
	{
		this(x, y, bounces, t, false, null);
	}


	/** Do not use, instead use the constructor with primitive data types. */
	@Deprecated
	public BulletLaser(Double x, Double y, Integer bounces, Tank t, ItemBullet ib) 
	{
		this(x, y, bounces, t, false, ib);
	}

	@Override
	public void update()
	{
		this.shoot();
		Drawing.drawing.playSound("/laser.wav");
		Game.removeMovables.add(this);
	}

	@Override
	public void addEffect()
	{
		Game.effects.add(Effect.createNewEffect(this.posX, this.posY, this.posZ, Effect.EffectType.laser));
	}

	@Override
	public void addDestroyEffect()
	{
		if (Game.fancyGraphics)
		{
			for (int i = 0; i < this.size * 4; i++)
			{
				Effect e = Effect.createNewEffect(this.posX, this.posY, this.posZ, Effect.EffectType.piece);
				double var = 50;
				e.maxAge /= 2;
				e.colR = Math.min(255, Math.max(0, this.baseColorR + Math.random() * var - var / 2));
				e.colG = Math.min(255, Math.max(0, this.baseColorG + Math.random() * var - var / 2));
				e.colB = Math.min(255, Math.max(0, this.baseColorB + Math.random() * var - var / 2));

				if (Game.enable3d)
					e.set3dPolarMotion(Math.random() * 2 * Math.PI, Math.random() * Math.PI, Math.random() * this.size / 50.0 * 4);
				else
					e.setPolarMotion(Math.random() * 2 * Math.PI, Math.random() * this.size / 50.0 * 4);

				Game.effects.add(e);
			}
		}
	}
	
	@Override
	public void draw()
	{

	}
}
