package tanks.tank;

import tanks.Drawing;
import tanks.Game;
import tanks.Model;
import tanks.Movable;

public class Turret extends Movable
{
	public double size = 8;
	public double length = Game.tile_size;
	public double colorR;
	public double colorG;
	public double colorB;

	Tank tank;

	public static Model base_model = new Model();
	public static Model turret_model = new Model();

	public Turret(Tank t) 
	{
		super(t.posX, t.posY);
		this.tank = t;
		this.colorR = calculateSecondaryColor(this.tank.colorR);
		this.colorG = calculateSecondaryColor(this.tank.colorG);
	    this.colorB = calculateSecondaryColor(this.tank.colorB);
	}

	public void draw(double angle, boolean forInterface, boolean in3d, boolean transparent)
	{
		this.posX = tank.posX;
		this.posY = tank.posY;

		if (Game.framework != Game.Framework.swing)
		{
			double s = (this.tank.size * (Game.tile_size - this.tank.destroyTimer) / Game.tile_size) * Math.min(this.tank.drawAge / Game.tile_size, 1);

			double l = length * (Game.tile_size - this.tank.destroyTimer) / Game.tile_size - Math.max(Game.tile_size - tank.drawAge, 0) / Game.tile_size * length;

			if (forInterface)
				l = Math.min(length, Game.tile_size * 1.5);

			if (transparent)
				Drawing.drawing.setColor(this.colorR, this.colorG, this.colorB, 127);
			else
				Drawing.drawing.setColor(this.colorR, this.colorG, this.colorB);

			if (forInterface)
				Drawing.drawing.drawInterfaceModel(turret_model, this.posX, this.posY, l, l * size / 8, angle);
			else if (!in3d)
				Drawing.drawing.drawModel(turret_model, this.posX, this.posY, l, l * size / 8, angle);
			else
				Drawing.drawing.drawModel(turret_model, this.posX, this.posY, (s * 1.3) / 2 , l, l * size / 8, l * size / 8, angle);

			if (transparent)
				Drawing.drawing.setColor((this.colorR + this.tank.colorR) / 2, (this.colorG + this.tank.colorG) / 2, (this.colorB + this.tank.colorB) / 2, 127);
			else
				Drawing.drawing.setColor((this.colorR + this.tank.colorR) / 2, (this.colorG + this.tank.colorG) / 2, (this.colorB + this.tank.colorB) / 2);

			if (forInterface)
				Drawing.drawing.drawInterfaceModel(base_model, this.posX, this.posY, l, l, angle);
			else if (!in3d)
				Drawing.drawing.drawModel(base_model, this.posX, this.posY, l, l, angle);
			else
				Drawing.drawing.drawModel(base_model, this.posX, this.posY, s / 2, l, l, l, angle);
		}
		else
		{
			double amount = 1;
			if (Game.fancyGraphics)
				amount = 0.25;

			this.setPolarMotion(angle, 1);

			double l = length * (Game.tile_size - this.tank.destroyTimer) / Game.tile_size - Math.max(Game.tile_size - tank.drawAge, 0);
			if (forInterface)
				l = Math.min(length, Game.tile_size * 1.5);

			for (double i = 0; i < l; i += amount)
			{
				//p.setColor(new Color(75, 40, 0));
				Drawing.drawing.setColor(this.colorR, this.colorG, this.colorB);
				int s = (int) (size * (Game.tile_size - this.tank.destroyTimer - Math.max(Game.tile_size - tank.drawAge, 0)) / Game.tile_size * l / Game.tile_size);

				if (forInterface)
				{
					s = Math.min((int) size, 12);
					Drawing.drawing.fillInterfaceOval(this.posX, this.posY, s, s);
				}
				else
				{
					Drawing.drawing.fillOval(this.posX, this.posY, s, s);
				}

				this.posX += this.vX * amount;
				this.posY += this.vY * amount;
			}
		}
	}
	
	@Override
	public void update() {}

	@Override
	public void draw() {}

	public static double calculateSecondaryColor(double input)
	{
		return (input + 64) / 2;
	}

}
