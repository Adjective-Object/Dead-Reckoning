package net.plaidypus.deadreckoning.skills;

import net.plaidypus.deadreckoning.board.Tile;
import net.plaidypus.deadreckoning.entities.LivingEntity;

public abstract class OffensiveSkill extends Skill{

	
	public OffensiveSkill(){
		super();
	}
	
	public OffensiveSkill(int sourceID) {
		super(sourceID);
	}

	@Override
	public boolean canTargetTile(Tile t) {
		if (!t.isOpen(Tile.LAYER_ACTIVE)
				&& !(t.getX() == getSource().getX() && t.getY() == getSource().getY())) {
			return LivingEntity.class.isAssignableFrom(t.getEntity(Tile.LAYER_ACTIVE).getClass());
		}
		return false;
	}

}
