package state;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.BasicComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class LayoutMenu extends AbstractComponent {
	private ArrayList lesElementsMenu = new ArrayList();
	private Image cursor;
	private int Y = 330;
	private int X = 430;
	private int positionCursor = 0;
	
	public LayoutMenu(GUIContext container) {
		super(container);
		// TODO Auto-generated constructor stub
	}
	
	public void addElement(MouseOverArea bouton){
		
		lesElementsMenu.add(bouton);
	/*	if(lesElementsMenu.get(1) == null){
			
		}*/
	}
	
	// gestion du curseur (graphique)
	public void setCursor(){

		if(this.input.isKeyPressed(Keyboard.KEY_Z)){
			if(positionCursor < 0){
			this.Y-=35;	
			positionCursor++;
			}
		}
		if(this.input.isKeyPressed(Keyboard.KEY_S)){
			if(positionCursor > -lesElementsMenu.size()+1){
			this.Y+=35;
			positionCursor--;
			}
		}
		this.cursor.draw(this.X, this.Y);
		
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getY() {// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(GUIContext arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		cursor = new Image("ressources/fleche.png");
		cursor.draw(this.X, this.Y);
		
		int i;
		for(i = 0; i < lesElementsMenu.size() ;i++){
				
			MouseOverArea boutonMenu = (MouseOverArea) lesElementsMenu.get(i);
			boutonMenu.render(arg0, g);
		}
		
	}

	@Override
	public void setLocation(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}



}
