package mazeGeneration.MazeObjects;


public class Board {
	
	public int[] scale;
	public int nodeScale;
	public int[] position;
	public Node[][] nodes;
	
	public Board(int[] scale,int nodesScale,int[] position) {
	
		this.scale = scale;
		this.nodeScale = nodesScale;
		this.position = position;
		
	}

}
