package control;

import mapping.Bomberman;

public abstract class ControlBomberman extends Control {
	private Bomberman player;
	
	public ControlBomberman(Bomberman player){
		this.player=player;
	}
}
