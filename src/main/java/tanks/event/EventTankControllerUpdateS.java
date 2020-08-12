package tanks.event;

import io.netty.buffer.ByteBuf;
import tanks.Effect;
import tanks.Game;
import tanks.tank.Tank;
import tanks.tank.TankPlayerController;
import tanks.tank.TankRemote;

public class EventTankControllerUpdateS extends EventTankUpdate
{
    public boolean forced;

    public EventTankControllerUpdateS()
    {

    }

    public EventTankControllerUpdateS(Tank t, boolean forced)
    {
        super(t);
        this.forced = forced;
    }

    @Override
    public void read(ByteBuf b)
    {
        super.read(b);
        this.forced = b.readBoolean();
    }

    @Override
    public void write(ByteBuf b)
    {
        super.write(b);
        b.writeBoolean(this.forced);
    }

    @Override
    public void execute()
    {
        Tank t = Tank.idMap.get(this.tank);

        if (this.clientID == null && (t instanceof TankRemote || (t instanceof TankPlayerController && (this.forced || !Game.clientID.equals(((TankPlayerController) t).clientID)))))
        {
            if (t instanceof TankPlayerController && Game.clientID.equals(((TankPlayerController) t).clientID))
            {
                Game.effects.add(Effect.createNewEffect(this.posX, this.posY, Effect.EffectType.laser));
                Game.effects.add(Effect.createNewEffect(t.posX, t.posY, Effect.EffectType.healing));

                TankPlayerController p = (TankPlayerController) t;
                p.interpolatedOffX = this.posX - (t.posX - p.interpolatedOffX * (TankPlayerController.interpolationTime - p.interpolatedProgress) / TankPlayerController.interpolationTime);
                p.interpolatedOffY = this.posY - (t.posY - p.interpolatedOffY * (TankPlayerController.interpolationTime - p.interpolatedProgress) / TankPlayerController.interpolationTime);
                p.interpolatedProgress = 0;
            }

            if (t instanceof TankRemote)
            {
                TankRemote r = (TankRemote) t;
                double iTime = Math.max(0.1, (time - r.lastUpdate) / 10.0);

                r.interpolatedOffX = this.posX - (t.posX - r.interpolatedOffX * (r.interpolationTime - r.interpolatedProgress) / r.interpolationTime);
                r.interpolatedOffY = this.posY - (t.posY - r.interpolatedOffY * (r.interpolationTime - r.interpolatedProgress) / r.interpolationTime);
                r.interpolatedProgress = 0;
                r.interpolationTime = iTime;
                r.lastUpdate = time;
            }

            t.posX = this.posX;
            t.posY = this.posY;
            t.vX = this.vX;
            t.vY = this.vY;

            if (t instanceof TankPlayerController && Game.clientID.equals(((TankPlayerController) t).clientID))
                t.processRecoil();

            t.angle = this.angle;
        }
    }
}
