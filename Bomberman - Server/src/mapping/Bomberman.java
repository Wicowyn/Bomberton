package mapping;

import java.util.HashSet;
import java.util.Set;

public class Bomberman extends Entity {
//TODO rajouter stuff
	
	public Bomberman(Chart chart) {
		super(chart);
		boolean[][] shape=new boolean[chart.getResolution()][chart.getResolution()];
		for(boolean[] tab : shape){
			for (int i=0; i<tab.length; i++) tab[i]=true;
		}
		setShape(shape);
		this.level=Level.OVERGROUND;
	}
	
	public boolean canMoveTo(int x, int y){
		if(!super.canMoveTo(x, y)) return false;
		
		Set<Entity> set=new HashSet<Entity>();
		
		for(int i=0; i<this.chart.getResolution(); i++){
			switch (getDirection()) {
			case UP:
				set.addAll(this.chart.getListEntityAt(x+i, y));
				break;
			case DOWN:
				set.addAll(this.chart.getListEntityAt(x+i, y+this.chart.getResolution()-1));
				break;
			case LEFT:
				set.addAll(this.chart.getListEntityAt(x, y+i));
				break;
			case RIGHT:
				set.addAll(this.chart.getListEntityAt(x+this.chart.getResolution()-1, y+i));
				break;
			default:
				System.err.println("Error: Bomberman: canMoveTo(int, int): direction non gérée");
				System.exit(1);
				break;

			}
			
			
		}
		
		for(Entity entity : set){
			if(entity instanceof Block) return false; //TODO rajouter l'item Bombe dans la condition
		}
		
		return true;
	}

	@Override
	protected void checkState() {
		// TODO A implémenter lorsque Item seras implémenter

	}

}
