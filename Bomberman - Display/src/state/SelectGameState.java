package state;

import java.util.List;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import components.LimitedMenu;
import components.Text;

public class SelectGameState extends BasicGameState {
	private Image background=null;
	private LimitedMenu menu=null;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.background=new Image("image/background-menu1.png");
		
		this.menu=new LimitedMenu(container, new Image("ressources/fleche.png"));
		this.menu.setLocation(480, 330);
		List<String> list=GamingState.getPossibleGame();
		
		AngelCodeFont font=new AngelCodeFont("ressources/Latin.fnt", new Image("ressources/Latin.tga"));

		for(String str : list){
			Text text=new Text(container,font);
			text.setText(str);
			text.setColor(Color.blue);
			this.menu.addElement(text);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		this.background.draw();
		this.menu.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return PageName.SelectGame;
	}

}
