package state;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.BasicRender;
import engine.Bomberman;
import engine.Engine;
import engine.EngineListener;
import engine.Entity;
import engine.KeyboardMove;
import engine.KeyboardPopBomb;
import engine.ResourceManager;



public class GamingState extends BasicGameState {
	private static String renderSuffix="-render.xml";
	private static String mapSuffix="-map.xml";
	private static String resourcePath="ressources/";
	private String currentGame;
	private Engine engine=new Engine();
	private ResourceManager res=new ResourceManager();

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			setGame(getPossibleGame().get(0));
			
			engine.addListener(new ListenEngine());
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		try {
			this.engine.unLoad();
			this.engine.loadLevel(GamingState.resourcePath+this.currentGame+GamingState.mapSuffix);
			
			this.res.load(GamingState.resourcePath+this.currentGame+GamingState.renderSuffix);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		for(Bomberman bomberman : this.engine.getBombermans()){
			KeyboardMove move=new KeyboardMove(bomberman, container.getInput());
			bomberman.addAbillity(move);
			KeyboardPopBomb popBomb=new KeyboardPopBomb(bomberman, container.getInput());
			bomberman.addAbillity(popBomb);
			popBomb.setPower(5);
		}

	}
	
	public void setGame(String game){
		this.currentGame=game;
	}
	
	public static List<String> getPossibleGame(){
		List<String> listMap=new ArrayList<String>();
		List<String> listRender=new ArrayList<String>();
		
		File[] files=(new File(GamingState.resourcePath)).listFiles();
		
		for(File file : files){
			String name=file.getName();
			if(name.endsWith(GamingState.renderSuffix)) listRender.add(name.substring(0, name.indexOf(GamingState.renderSuffix)));
			if(name.endsWith(GamingState.mapSuffix)) listMap.add(name.substring(0, name.indexOf(GamingState.mapSuffix)));
		}
		
		listMap.retainAll(listRender);
		
		return listMap;
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
	
	private class ListenEngine implements EngineListener{

		@Override
		public void entityAdded(Entity entity) {
			entity.addAbillity(new BasicRender(entity));
		}

		@Override
		public void entityRemoved(Entity entity) {
			
			
		}
		
	}

}
