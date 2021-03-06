package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.DeadReckoningComponent;
import net.plaidypus.deadreckoning.actions.Action;
import net.plaidypus.deadreckoning.board.GameBoard;
import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// TODO: Auto-generated Javadoc
/**
 * The Class Skill.
 * 
 * used as a simple way of generating action objects based off of a source
 * entity
 * 
 * all skills must have an argument free () constructor, to allow for
 * newInstance() calls, so that the init() method unique to each class can be
 * called because java does not have instance-independent abstract methods.
 */
public abstract class Skill extends DeadReckoningComponent {

	/** The levelcap. */
	protected int level, levelcap, levelReq;

	/** The cooldown. */
	protected int cooldown;

	/** The description. */
	String name = "NO_NAME", description = "NO_DESC";

	/** The image icon. */
	Image imageIcon;

	/** The source. */
	protected int sourceID;

	protected boolean instant=false;
	
	/**
	 * Skills are a method of having arbitrary abilities on entites (able to be
	 * stored in an arrayList) while keeping the Action class unique for each
	 * execution of an action.
	 * 
	 * @param source
	 *            the source
	 */
	public Skill(int sourceID) {
		this.sourceID = sourceID;
	}

	/**
	 * created to have unlinked instances of a skill that can be later bound.
	 * planned to be used only in character creation
	 */
	public Skill() {
		try {
			this.imageIcon = new Image("res/noSkill.png");
		} catch (SlickException e) {
		}
	}

	/**
	 * binds the skill to a certain livingentity such that it is not necessary
	 * to constantly pass the correct livingentity to parse based off of.
	 * 
	 * @param source
	 *            the source
	 */
	public void bindTo(LivingEntity source) {
		this.sourceID = source.getID();
	}

	/**
	 * generates an action targeted at a tile. Usually to be assigned to the
	 * source entity
	 * 
	 * @param target
	 *            the target
	 * @return the action
	 */
	public abstract Action makeAction(Tile target);

	/**
	 * method for checking if a certain tile is highlight-able. used in
	 * highlightRadial && other highlighting methods.
	 * 
	 * @param t
	 *            the tile that must be checked if it can be targeted
	 * @return true, if successful
	 */
	public abstract boolean canTargetTile(Tile t);

	/**
	 * method for highlighting all the tiles that a skill can target. made
	 * abstract so not all skills need to have attack ranges of certain shapes,
	 * etc.
	 * 
	 * @param board
	 *            the board
	 */
	public abstract void highlightRange(GameBoard board);

	/**
	 * highlights all the tiles within a certain range of the skill's source.
	 * (radial). made to be called by highlightRange(GameBoard board);
	 * 
	 * @param board
	 *            the board
	 * @param range
	 *            the range
	 */
	public void highlightRadial(GameBoard board, int range) {
		board.clearHighlightedSquares();
		for (int vy = 0; vy < board.getHeight(); vy++) {
			for (int vx = 0; vx < board.getWidth(); vx++) {
				if (Math.sqrt(Math.pow(GameBoard.getEntity(sourceID).getX() - vx, 2)
						+ Math.pow(GameBoard.getEntity(sourceID).getY() - vy, 2)) <= range) {
					if (canTargetTile(board.getTileAt(vx, vy))
							&& board.getTileAt(vx, vy).lightLevel >= 1) {
						board.getTileAt(vx, vy).setHighlighted(
								Tile.HIGHLIGHT_CONFIRM);
					} else {
						board.getTileAt(vx, vy).setHighlighted(
								Tile.HIGHLIGHT_DENY);
					}
				}
			}
		}
	}

	/**
	 * Checks if is instant.
	 * 
	 * @return true, if is instant
	 */
	public boolean isInstant() {
		return instant;
	}

	/**
	 * Gets the level.
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * 
	 * @param level
	 *            the new level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	public Image getIcon() {
		if(imageIcon==null){
			System.err.println(this.getClass().getCanonicalName());
		}
		return imageIcon;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the descriptor.
	 * 
	 * @param description
	 *            the new descriptor
	 */
	public void setDescriptor(String description) {
		this.description = description;
	}

	/**
	 * Level up.
	 */
	public void levelUp() {
		this.level++;
	}

	/**
	 * Can be cast.
	 * 
	 * @return true, if successful
	 */
	public boolean canBeCast() {
		return getCooldown() <= 0 && this.level>0;
	}

	/**
	 * Update skill.
	 */
	public void updateSkill() {// called on turn advance
		this.setCooldown(this.getCooldown() - 1);
	}

	/**
	 * Gets the level cap.
	 * 
	 * @return the level cap
	 */
	public int getLevelCap() {
		return this.levelcap;
	}

	/**
	 * Gets the level req.
	 * 
	 * @return the level req
	 */
	public int getLevelReq() {
		return this.levelReq;
	}

	/**
	 * Sets the cooldown.
	 * 
	 * @param cooldown
	 *            the new cooldown
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	/**
	 * Gets the cooldown.
	 * 
	 * @return the cooldown
	 */
	public int getCooldown() {
		return cooldown;
	}
	
	public LivingEntity getSource(){
		return (LivingEntity)GameBoard.getEntity(sourceID);
	}

	/**
	 * loads the resources needed for this class
	 * 
	 * called automagically by modloader
	 * 
	 * @throws SlickException
	 */
	@Override
	public abstract void init() throws SlickException;

	public void setSource(int entityID) {
		this.sourceID = entityID;
	}
	
	public void setIcon(Image icon){
		this.imageIcon=icon;
	}
}
