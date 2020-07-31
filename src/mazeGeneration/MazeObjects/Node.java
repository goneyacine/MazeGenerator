package mazeGeneration.MazeObjects;

import java.util.*;

public class Node {
	
	public int scale;
	public int[] worldPosition;
	 
	public Node(int scale,int[] worldPosition) {
		
		this.scale = scale;
		this.worldPosition = worldPosition;
        
	}

}
