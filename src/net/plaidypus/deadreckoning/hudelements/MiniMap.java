package net.plaidypus.deadreckoning.hudelements;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.state.HudLayersState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MiniMap extends HudElement{

	int hookState, scale = 2;
	GameBoard target;
	
	Image mapRender;
	
	public MiniMap(int x, int y, int bindMethod, int hookState) {
		super(x, y, bindMethod, false);
		this.hookState=hookState;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
	}

	@Override
	public void makeFrom(Object o) {
		System.out.println(DeadReckoningGame.instance.getState(hookState));
		target = GameplayElement.class.cast(HudLayersState.class.cast(DeadReckoningGame.instance.getState(hookState)).getElement(0)).getBoard();
		try {
			mapRender=new Image(target.getWidth()*scale,target.getHeight()*scale);
			Graphics g= mapRender.getGraphics();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {}

	@Override
	public int getWidth() {
		return target.getWidth();
	}

	@Override
	public int getHeight() {
		return target.getHeight();
	}
	
	private void renderTo(Graphics g, int xo, int yo){
		g.setColor(Color.white);
		g.fillRect(xo,yo,target.getWidth()*scale+1,target.getHeight()*scale+1);
		g.setColor(Color.black);
		g.fillRect(xo+1,yo+1,target.getWidth()*scale-1,target.getHeight()*scale-1);
		
		for(int x=0; x<target.getWidth(); x++){
			for(int y=0; y<target.getHeight(); y++){
				if(target.getTileAt(x, y).explored){
					
					if(!target.getTileAt(x, y).isOpen(Tile.LAYER_ACTIVE) && target.getTileAt(x, y).canBeSeen()){
						g.setColor(Color.green);
					}
					else if(!target.getTileAt(x, y).isOpen(Tile.LAYER_PASSIVE_MAP) && target.getTileAt(x, y).canBeSeen()){
						g.setColor(Color.gray);
					}
					else if(target.getTileAt(x, y).getTileFace()!=Tile.TILE_NULL){
						g.setColor(Color.lightGray);
					}
					else{
						g.setColor(Color.white);
					}
					g.fillRect((xo+x*scale)+1, (yo+y*scale)+1, scale,scale);
				}
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(mapRender,getX()-mapRender.getWidth()/2,getY()-mapRender.getHeight()/2);
		renderTo(g,getX(),getY());
		
	}

}
