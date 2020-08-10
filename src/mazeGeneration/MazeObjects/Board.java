package mazeGeneration.MazeObjects;

import java.util.*;
public class Board {
	
	public int[] scale;
	public int nodeScale;
	public List<List<Node>> nodes;
	
	public Board(int[] scale,int nodesScale) {
	
		this.scale = scale;
		this.nodeScale = nodesScale;;
		
	}

}
