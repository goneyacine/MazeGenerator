package mazeGeneration.MazeObjects;

import java.util.*;

public class Node {
	
	public int scale;
	public int[] worldPosition;
	public int[] localPosition;
	public boolean isObstical = false;
	public boolean isOpened = false;
	//the distance from the start point
	public int gCost;
	//the distance from the end point 
	public int hCost;
	//the sum of hCost and gCost
	public int fCost;
	public boolean toOpen = false;
	public Node(int scale,int[] worldPosition,int[] localPosition) {
		this.scale = scale;
		this.worldPosition = worldPosition;
        this.localPosition = localPosition;
	}

}
