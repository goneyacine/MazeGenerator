package mazeGeneration;

import mazeGeneration.MazeObjects.*;

public class Hole {
   	

	public static void makeHole(Node node,Wall targetWall) {		
		
		node.nodeWalls.get(node.nodeWalls.indexOf(targetWall)).enabled = false;
		
	}
	
	
}
