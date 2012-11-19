package mapping;

public abstract class Block extends Entity {

	public Block(Chart chart) {
		super(chart);
		
		boolean[][] shape=new boolean[chart.getResolution()][chart.getResolution()];
		for(boolean[] tab : shape){
			for (int i=0; i<tab.length; i++) tab[i]=true;
		}
		setShape(shape);
		this.level=Level.OVERGROUND;
	}

}
