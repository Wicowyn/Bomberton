package state;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Bomberman;
import engine.Engine;
import engine.Entity;
import engine.KeyboardMove;
import engine.KeyboardPopBomb;



public class GamingState extends BasicGameState {
	private Engine engine;
	List<Image> list=new ArrayList<Image>();

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		this.engine=new Engine();
		try {
			this.engine.loadLevel("Classic");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Bomberman bomberman : this.engine.getBombermans()){
			KeyboardMove move=new KeyboardMove(bomberman, arg0.getInput());
			bomberman.addAbillity(move);
			KeyboardPopBomb popBomb=new KeyboardPopBomb(bomberman, arg0.getInput());
			bomberman.addAbillity(popBomb);
			popBomb.setPower(5);
		}
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		for(Entity entity : this.engine.getEntities()) entity.render(arg0, arg1, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		this.engine.update(arg2);
		
	}

	@Override
	public int getID() {
		return PageName.Gaming;
	}

}
