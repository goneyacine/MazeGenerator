package mazeGeneration;

import mazeGeneration.MazeObjects.*;
import java.awt.*; 
import javax.swing.*;
public class MazeGenerator {
	
    
    public static void generateMaze(Board board, int[] boardScale,int nodeScale,int[] boardPosition) {
    	
    	//setting up the maze board
    	board = new Board(boardScale,nodeScale,boardPosition);
    	//setting up nodes 
    	setUpBoardNodes(board);
    	//deleting the extra walls
    	deleteExtraWalls(board);
    	
    }
    
    
    
    //use this method only when setting up board
   private static void deleteExtraWalls(Board board) {
    	
       for(int x = 0; x < board.scale[0] / board.nodeScale;x++) {
    	   
    	   for(int y = 0; y < board.scale[1] / board.nodeScale;y++ ) {
    		   //find witch walls we should delete
    		   if (x <  board.scale[0] / board.nodeScale - 1)
    			   Hole.makeHole(board.nodes[x][y], board.nodes[x][y].rightWall);
    		   else if (x > 0)
    			   Hole.makeHole(board.nodes[x][y], board.nodes[x][y].leftWall);
    		   else if (y > 0)
    			   Hole.makeHole(board.nodes[x][y], board.nodes[x][y].topWall);
    		   else if (y < board.scale[1] / board.nodeScale - 1)
    			   Hole.makeHole(board.nodes[x][y], board.nodes[x][y].buttonWall);
    		   
    	   }
    	   
       }
       
    }
    
    private static void setUpBoardNodes(Board board) {
    	//creating the nodes array
    	Node[][] nodes = new Node[board.scale[0] / board.nodeScale][ board.scale[1] / board.nodeScale];
    	//adding node to the nodes array
    	for(int x = 0; x <  board.scale[0] / board.nodeScale;x++) {
    		
    		for(int y = 0; y <  board.scale[1] / board.nodeScale; y++) {
    			int [] nodePosition = { board.scale[0] / board.nodeScale * x + board.position[0], board.scale[1] / board.nodeScale * y + board.position[1]};
    		    Node node = new Node(board.nodeScale,nodePosition);
    			nodes[x][y] = node;
    		}
    	}
    	//set the board nodes
    	board.nodes = nodes;
    }  

}
