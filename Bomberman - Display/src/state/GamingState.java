package state;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.Engine;
import engine.EngineListener;
import engine.Entity;
import engine.ResourceManager;
import engine.abillity.KeyboardMove;
import engine.abillity.KeyboardPopBomb;
import engine.abillity.RealRender;
import engine.entity.Bomberman;



public class GamingState extends BasicGameState implements SelectGame {
	private static String renderSuffix="-render.xml";
	private static String mapSuffix="-map.xml";
	private static String resourcePath="ressources/";
	private String currentGame;
	private Engine engine=new Engine();
	private ResourceManager ressources=new ResourceManager();

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
			setGame(getPossibleGame().get(1));
			
			engine.addListener(new ListenEngine());
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		try {
			this.ressources.load(GamingState.resourcePath+this.currentGame+GamingState.renderSuffix);
			
			this.engine.unLoad();
			this.engine.loadLevel(GamingState.resourcePath+this.currentGame+GamingState.mapSuffix);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		List<Bomberman> list=this.engine.getBombermans();
		if(list.size()>0){
			KeyboardMove move=new KeyboardMove(list.get(0), container.getInput());
			list.get(0).addAbillity(move);
			move.setKeyDown(Input.KEY_S);
			move.setKeyUp(Input.KEY_Z);
			move.setKeyLeft(Input.KEY_Q);
			move.setKeyRight(Input.KEY_D);
			move.setSpeed(3f);
			
			KeyboardPopBomb popBomb=new KeyboardPopBomb(list.get(0), container.getInput());
			list.get(0).addAbillity(popBomb);
			popBomb.setPower(5);
			popBomb.setKeyPop(Input.KEY_SPACE);
		}
		if(list.size()>1){
			KeyboardMove move=new KeyboardMove(list.get(1), container.getInput());
			list.get(1).addAbillity(move);
			move.setKeyDown(Input.KEY_DOWN);
			move.setKeyUp(Input.KEY_UP);
			move.setKeyLeft(Input.KEY_LEFT);
			move.setKeyRight(Input.KEY_RIGHT);
			move.setSpeed(3f);
			
			KeyboardPopBomb popBomb=new KeyboardPopBomb(list.get(1), container.getInput());
			list.get(1).addAbillity(popBomb);
			popBomb.setPower(5);
			popBomb.setKeyPop(Input.KEY_NUMPAD0);
		}
		/*
		for(Bomberman bomberman : this.engine.getBombermans()){
			KeyboardMove move=new KeyboardMove(bomberman, container.getInput());
			bomberman.addAbillity(move);
			KeyboardPopBomb popBomb=new KeyboardPopBomb(bomberman, container.getInput());
			bomberman.addAbillity(popBomb);
			popBomb.setPower(5);
		}
		*/

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
		arg2.setBackground(Color.gray);
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
			RealRender render=new RealRender(entity);
			ResourceManager res=GamingState.this.ressources;
			
			render.setMoveRender(-45, 45, res.getAnimation(entity.getClass().getSimpleName()+"_MOVE_RIGHT"));
			render.setMoveRender(45, 135, res.getAnimation(entity.getClass().getSimpleName()+"_MOVE_DOWN"));
			render.setMoveRender(135, 225, res.getAnimation(entity.getClass().getSimpleName()+"_MOVE_LEFT"));
			render.setMoveRender(225, 315, res.getAnimation(entity.getClass().getSimpleName()+"_MOVE_UP"));
			
			render.setStaticRender(-45, 45, res.getRenderable(entity.getClass().getSimpleName()+"_STATIC_RIGHT"));
			render.setStaticRender(45, 135, res.getRenderable(entity.getClass().getSimpleName()+"_STATIC_DOWN"));
			render.setStaticRender(135, 225, res.getRenderable(entity.getClass().getSimpleName()+"_STATIC_LEFT"));
			render.setStaticRender(225, 315, res.getRenderable(entity.getClass().getSimpleName()+"_STATIC_UP"));
			
			entity.addAbillity(render);
		}

		@Override
		public void entityRemoved(Entity entity) {
			
		}
		
	}

	@Override
	public void selectGame(String game) {
		this.currentGame=game;
		
	}

}
