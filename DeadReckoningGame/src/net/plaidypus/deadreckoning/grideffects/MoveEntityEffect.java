package net.plaidypus.deadreckoning.grideffects;

import net.plaidypus.deadreckoning.DeadReckoningGame;
import net.plaidypus.deadreckoning.board.Tile;

import org.newdawn.slick.Graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveEntityEffect.
 * 
 * a simple grideffect for doing gradual line motion between two tiles
 * 
 * unused as of now
 */
public class MoveEntityEffect extends GridEffect {

	/** The destination. */
	Tile destination;

	/** The currentdown. */
	double currentdown;

	/** The layer. */
	int layer;

	/**
	 * Instantiates a new move entity effect.
	 * 
	 * @param location
	 *            the location
	 * @param layer
	 *            the layer
	 * @param targetLocation
	 *            the target location
	 */
	public MoveEntityEffect(Tile location, int layer, Tile targetLocation) {
		super(location);
		this.layer = layer;
		this.destination = targetLocation;
		location.getEntity(layer).setVisible(false);

		if (destination.getX() > location.getX()) {
			location.getEntity(layer).setFacing(true);
		} else if (destination.getX() < location.getX()) {
			location.getEntity(layer).setFacing(false);
		}

		currentdown = 5.0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.plaidypus.deadreckoning.grideffects.GridEffect#update(int)
	 */
	public void update(int delta) {
		if (destination.canBeSeen()) {
			currentdown = 0;
		}
		currentdown = currentdown / 2;
		if (currentdown < 0.5
				|| (!destination.canBeSeen() && !location.canBeSeen())) {
			this.setComplete(true);
			location.getEntity(layer).setVisible(true);
			location.getParent().moveEntity(location.getEntity(layer),
					destination, layer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.plaidypus.deadreckoning.grideffects.GridEffect#render(org.newdawn
	 * .slick.Graphics, float, float)
	 */
	public void render(Graphics g, float xoff, float yoff) {
		if (destination.canBeSeen()) {
			location.getEntity(layer).forceRender(
					g,
					destination.getX() * DeadReckoningGame.tileSize + xoff,
					destination.getY() * DeadReckoningGame.tileSize
							- (int) currentdown + yoff);
		}
	}
	/*
	 * Tile destination; float xoff, yoff; int moveSpeed; double hypotenuse, a,
	 * b, speed = 100, distravelled;
	 * 
	 * public MoveEntityEffect(Tile location, Tile targetLocation) {
	 * super(location); this.destination = targetLocation;
	 * location.getEntity().setVisible(false); a =
	 * location.getX()-destination.getX(); b =
	 * location.getY()-destination.getY(); hypotenuse =
	 * Math.sqrt(Math.pow(a,2)+Math.pow(b,2)); distravelled=0;
	 * 
	 * Entity e = location.getEntity();
	 * 
	 * if(destination.getX()>location.getX()){ e.setFacing(true); } else
	 * if(destination.getX()<location.getX()){ e.setFacing(false); } }
	 * 
	 * 
	 * public void update(int delta) {
	 * 
	 * if( distravelled>=hypotenuse ){ //TODO fix finish trigger conditions
	 * location.getEntity().setVisible(true);
	 * location.getParent().moveEntity(location,destination);
	 * this.setComplete(true); } else{ this.xoff-=a*(speed/delta)/hypotenuse;
	 * this.yoff-=b*(speed/delta)/hypotenuse;
	 * distravelled+=(speed/delta)/DeadReckoningGame.tileSize; } }
	 * 
	 * public void render(Graphics g, float xoff, float yoff) {
	 * if((location.lightLevel>=1 || destination.lightLevel>=1) &&
	 * (location.isVisible() || location.isVisible())){
	 * location.getEntity().forceRender(g,
	 * xoff+location.getX()*DeadReckoningGame
	 * .tileSize+this.xoff,yoff+location.getY
	 * ()*DeadReckoningGame.tileSize+this.yoff); } }
	 */
}
