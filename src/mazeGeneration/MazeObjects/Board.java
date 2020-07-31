package mazeGeneration.MazeObjects;


public class Board {
	
	public int[] scale;
	public int nodeScale;
	public Node[][] nodes;
	
	public Board(int[] scale,int nodesScale) {
	
		this.scale = scale;
		this.nodeScale = nodesScale;;
		
	}

}
