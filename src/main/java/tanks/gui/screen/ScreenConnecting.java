package tanks.gui.screen;

import tanks.Drawing;
import tanks.Game;
import tanks.gui.Button;

public class ScreenConnecting extends Screen
{
	public String text = "Connecting...";
	public String exception = "";
	public boolean showBack = false;
	public Thread thread;

	public ScreenConnecting(Thread t)
	{
		this.thread = t;
	}

	Button back = new Button(Drawing.drawing.interfaceSizeX / 2, Drawing.drawing.interfaceSizeY / 2 + 60, 350, 40, "Back", new Runnable()
	{
		@Override
		public void run() 
		{
			Game.screen = new ScreenJoinParty();
			thread.stop();
		}
	}
	);

	@Override
	public void update() 
	{
		//if (this.showBack)
			back.update();
	}

	@Override
	public void draw() 
	{
		this.drawDefaultBackground();
		Drawing.drawing.setColor(0, 0, 0);
		Drawing.drawing.setFontSize(24);
		Drawing.drawing.drawInterfaceText(Drawing.drawing.interfaceSizeX / 2, Drawing.drawing.interfaceSizeY / 2 - 60, this.text);
		Drawing.drawing.drawInterfaceText(Drawing.drawing.interfaceSizeX / 2, Drawing.drawing.interfaceSizeY / 2, this.exception);
	
		//if (this.showBack)
			back.draw();
	}

}
