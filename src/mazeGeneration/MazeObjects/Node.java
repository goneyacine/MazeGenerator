package mazeGeneration.MazeObjects;

import java.util.*;

public class Node {
	
	public int scale;
	public int[] worldPosition;
	public boolean isObstical = false;
	public Node(int scale,int[] worldPosition) {
		
		this.scale = scale;
		this.worldPosition = worldPosition;
        
	}

}
