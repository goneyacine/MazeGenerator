package mazeGeneration;

import mazeGeneration.MazeObjects.*;
import java.awt.*; 
import javax.swing.*;
public class MazeGenerator {
	
    public Board board;
    public void generateMaze(int[] boardScale,int nodeScale) throws Exception {
    	
    	//setting up the maze board
    	board = new Board(boardScale,nodeScale);
    	//setting up nodes 
    	setUpBoardNodes(board);
   
    }
    
    private static void setUpBoardNodes(Board board) {
    	//creating the nodes array
    	Node[][] nodes = new Node[board.scale[0] / board.nodeScale][ board.scale[1] / board.nodeScale];
    	//adding node to the nodes array
    	for(int x = 0; x <  board.scale[0] / board.nodeScale;x++) {
    		
    		for(int y = 0; y <  board.scale[1] / board.nodeScale; y++) {
    			int [] nodePosition = { ((board.scale[0] / board.nodeScale) + board.nodeScale) * x,(( board.scale[1] / board.nodeScale) + board.nodeScale)   * y};
    		    Node node = new Node(board.nodeScale,nodePosition);
    			nodes[x][y] = node;
    		}
    	}
    	//set the board nodes
    	board.nodes = nodes;
    }  

}
