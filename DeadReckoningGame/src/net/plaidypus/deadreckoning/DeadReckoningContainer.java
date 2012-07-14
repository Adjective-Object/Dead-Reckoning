package net.plaidypus.deadreckoning;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.plaidypus.deadreckoning.state.HudLayersState;
import net.plaidypus.deadreckoning.utilities.FileSaveLogSystem;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

public class DeadReckoningContainer extends AppGameContainer{

	public DeadReckoningContainer(Game game) throws SlickException {
		super(game);
	}

	public DeadReckoningContainer(Game game, int width, int height,
			boolean fullscreen) throws SlickException {
		super(game, width, height, fullscreen);
	}
	
	//slightly modified version of the org.newdawn.slick.AppGameContainer's gameLoop() method
	//it allows for somewhat more "graceful" error handling.
	protected void gameLoop() throws SlickException {
		int delta = getDelta();
		if (!Display.isVisible() && updateOnlyOnVisible) {
			try { Thread.sleep(100); } catch (Exception e) {}
		} else {
			try {
				updateAndRender(delta);
			} catch (SlickException e) {
				handleError(e);
				return;
			}
		}

		updateFPS();

		Display.update();
		
		if (Display.isCloseRequested()) {
			if (game.closeRequested()) {
				running = false;
			}
		}
	}
	
	protected void handleError(SlickException e) {
		Log.error(e);
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		HudLayersState s = (HudLayersState)(DeadReckoningGame.instance.getState(DeadReckoningGame.ERRORSTATE));
		s.getElement(1).makeFrom(e.getMessage()+"\r\n" + errors.toString().replaceAll("	","") + "\r\n"+
				"A logfile has been dumped at "+FileSaveLogSystem.fileName+".\r\nPlease report this error");
		DeadReckoningGame.instance.enterState(DeadReckoningGame.ERRORSTATE);
		
		FileSaveLogSystem.closeWriter();
		
	}

	protected void updateAndRender(int delta) throws SlickException {
		if (smoothDeltas) {
			if (getFPS() != 0) {
				delta = 1000 / getFPS();
			}
		}
		
		input.poll(width, height);
		
		Music.poll(delta);
		if (!paused) {
			storedDelta += delta;
			
			if (storedDelta >= minimumLogicInterval) {
				try {
					if (maximumLogicInterval != 0) {
						long cycles = storedDelta / maximumLogicInterval;
						for (int i=0;i<cycles;i++) {
							game.update(this, (int) maximumLogicInterval);
						}
						
						int remainder = (int) (delta % maximumLogicInterval);
						if (remainder > minimumLogicInterval) {
							game.update(this, (int) (delta % maximumLogicInterval));
							storedDelta = 0;
						} else {
							storedDelta = remainder;
						}
					} else {
						game.update(this, (int) storedDelta);
						storedDelta = 0;
					}
					
				} catch (Throwable e) {
					throw new SlickException("Game.update() failure - check the game code.",e);
				}
			}
		} else {
			game.update(this, 0);
		}
		
		if (hasFocus() || getAlwaysRender()) {
			if (clearEachFrame) {
				GL.glClear(SGL.GL_COLOR_BUFFER_BIT | SGL.GL_DEPTH_BUFFER_BIT);
			} 
			
			GL.glLoadIdentity();
			
			getGraphics().resetFont();
			getGraphics().resetLineWidth();
			getGraphics().setAntiAlias(false);
			try {
				game.render(this, getGraphics());
			} catch (Throwable e) {
				throw new SlickException("Game.render() failure - check the game code.",e);
			}
			getGraphics().resetTransform();
			
			if (isShowingFPS()) {
				getDefaultFont().drawString(10, 10, "FPS: "+recordedFPS);
			}
			
			GL.flush();
		}
		
		if (targetFPS != -1) {
			Display.sync(targetFPS);
		}
	}
	

}
