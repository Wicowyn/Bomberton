package state;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import display.KeyAction;

public class MenuState extends BasicGameState implements KeyAction{
	
	//bouton du menu
	private MouseOverArea quit;
	private MouseOverArea play;
	private MouseOverArea option;
	private Input input;
	private Image menu;
	private LayoutMenu menu2;
	
	private Animation courir;
	private Animation courir2;
	private SpriteSheet perso;
	private Image perso2;
	private Image persoDroite;

	
	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
			input = container.getInput();
			// a implement dans layoutMenu
			menu = new Image("image/background-menu1.png");
			
			menu2=new LayoutMenu(container, new Image("ressources/fleche.png"));
			menu2.setLocation(480, 330);
			
			play = new MouseOverArea(container,new Image("image/element1.png"), 0, 0);
			play.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
			play.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
			menu2.addElement(play);
			
			option = new MouseOverArea(container,new Image("image/element2.png"), 0, 0);
			option.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
			option.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
			menu2.addElement(option);
			
			quit = new MouseOverArea(container,new Image("image/element3.png"), 0, 0);
			quit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
			quit.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
			menu2.addElement(quit);
			
			perso = new SpriteSheet("image/KarabounChicken.gif", 80,80); //80 = taille de l'image
			
			//Constructeur : Animation(SpriteSheet frames, int x1, int y1, int x2, int y2, boolean horizontalScan, int duration, boolean autoUpdate)
			courir = new Animation(perso, 0,0,0,3,true, 100, false);
		  
		     
		     
		     
			/*touche = new TextField(container, null, 200, 450, 200, 50);
			touche.setText("test"); test du TEXTFIELD*/
	}
	

	
	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
	
		menu.draw();
		g.drawString("Menu principale",500,280);
		menu2.render(container, g);
		courir.draw(200+200,200);
		
		
		
		
		
	}
	/*public void componentActivated(AbstractComponent source) { //methode de l'interface ComponentListener

		if (source == quit) {

			container.exit();

		}
		if (source == play) {

			game.enterState(); // Id de la page

		}
	}*/
	
	

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
			
			if(this.input.isKeyDown(Keyboard.KEY_ESCAPE)){
				container.exit();
			}
			if(this.input.isKeyDown(Keyboard.KEY_D)){
				courir.setCurrentFrame(3);
			}
			if(this.input.isKeyDown(Keyboard.KEY_Q)){
				courir.setCurrentFrame(2);
			}
		
	}

	@Override
	// ID de la page
	public int getID() {
		return PageName.Menu;
	}



	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void down() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void left() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void right() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void action1() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void action2() {
		// TODO Auto-generated method stub
		
	}
	
}

