package net.plaidypus.deadreckoning.utilities;

import net.plaidypus.deadreckoning.DeadReckoningGame;

public class DeadReckoningLogSystem extends RichTextLogSystem{

	public DeadReckoningLogSystem(String outDest) {
		super(outDest);
	}
	
	public void info(String message){
		super.info(message);
		if(DeadReckoningGame.instance.getMessageElement()!=null){
			DeadReckoningGame.instance.getMessageElement().addMessage(message);
		}
	}

}
