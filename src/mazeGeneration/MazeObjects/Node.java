package mazeGeneration.MazeObjects;

import java.util.*;

public class Node {
	
	public int scale;
	public int[] worldPosition;
	
	public Wall topWall = new Wall(this,true,"Top");
	public Wall buttonWall = new Wall(this,true,"Button");
	public Wall rightWall = new Wall(this,true,"Right");
	public Wall leftWall = new Wall(this,true,"Left");
	
	public List<Wall> nodeWalls = new ArrayList<>();
	 
	public Node(int scale,int[] worldPosition) {
		
		this.scale = scale;
		this.worldPosition = worldPosition;
        
		nodeWalls.add(topWall);
		nodeWalls.add(buttonWall);
		nodeWalls.add(rightWall);
		nodeWalls.add(leftWall);
	}

}
