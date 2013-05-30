package core.entities;

import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.entities.Entity;
import net.plaidypus.deadreckoning.modloader.ModLoader;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TempleTorch extends Torch{

	static SpriteSheet secondimg;
	
	public TempleTorch(){}
	
	public TempleTorch(int radius){
		super(radius,secondimg);
	}
	
	public void init() throws SlickException{
		secondimg = new SpriteSheet(ModLoader.loadImage("core/res/bossroom/torch.png"), 32, 64);
	}
	
	public void forceRender(Graphics g, float x, float y) {
		g.drawImage(ani.getCurrentFrame(),
				x+16-ani.getCurrentFrame().getWidth()/2,
				y+32-ani.getCurrentFrame().getHeight());
	}
	
	@Override
	public Entity makeFromString(GameBoard g, String[] toload) {
		Torch t = new TempleTorch(Integer.parseInt(toload[4]));
		t.setLocation(
				g.getTileAt(
						Integer.parseInt(toload[1]),
						Integer.parseInt(toload[2])),
				Integer.parseInt(toload[3]));
		return t;
	}
	
}
