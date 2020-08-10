package mazeGeneration;

import mazeGeneration.MazeObjects.*;
import java.util.List;
import java.util.*;
public class MazeGenerator {
	
    public Board board;
    private Room defaultRoom;
    private int isStart = 1;
    public void generateMaze(int[] boardScale,int nodeScale,int divisionOperations) throws Exception {
    	//setting up the maze board
    	board = new Board(boardScale,nodeScale);
    	//setting up nodes 
    	setUpBoardNodes(board);
    	//dividing the room
    	divideRooms(divisionOperations,board.nodes);
   
    }
    
    private void setUpBoardNodes(Board board) {
    	//creating the nodes array
    	List<List<Node>> nodes = new ArrayList<>();
    	//adding node to the nodes array
    	for(int x = 0; x <  board.scale[0] / board.nodeScale;x++) {
    		List<Node> row = new ArrayList<>();
    		for(int y = 0; y <  board.scale[1] / board.nodeScale; y++) {
    			int [] nodePosition = { ((board.scale[0] / board.nodeScale) + board.nodeScale) * x,(( board.scale[1] / board.nodeScale) + board.nodeScale)   * y};
    		    Node node = new Node(board.nodeScale,nodePosition);
    			row.add(node);
    		}
    		nodes.add(row);
    	}
    	//set the board nodes
    	board.nodes = nodes;
    }  
    private void divideRooms(int divisionOperations,List<List<Node>> nodes) {
    	if(nodes == null || nodes.size() <= 3 ||nodes.get(0).size() <= 3 || divisionOperations < 1)
    	  return;
    	//generate random walls
    	int _IsStart_ = (isStart == 1) ? 10 : 4;
    	int randomRow = (int)(Math.random() * (nodes.size() - _IsStart_));
    	if(randomRow < 10 && isStart == 1)
    	   randomRow = 10;
    	else if (randomRow < 2 && isStart == 0)
    		randomRow = 2;
    	int randomColumn = (int)(Math.random() * (nodes.get(0).size() - _IsStart_));
    	if(randomColumn < 10 && isStart == 1)
    	   randomColumn = 10;   
    	else if (randomColumn < 2 && isStart == 0)
    		randomColumn = 2;
    	isStart = 0;
        for(Node node : nodes.get(randomRow))
        	node.isObstical = true;  
        for(List<Node> row : nodes)
        	row.get(randomColumn).isObstical = true;
       divisionOperations --;
       updateRooms(nodes,divisionOperations);      
    }
    private void updateRooms(List<List<Node>> nodes,int divisionOperations) {
      List<List<Node>> leftRows = new ArrayList<>();
      List<List<Node>> rightRows = new ArrayList<>();
      boolean horizontalObsticalFaced = false;
      
      for(int i = 0; i < nodes.size();i++) {
    	  if(!nodes.get(i).get(0).isObstical) {
    		  if(!horizontalObsticalFaced) {
    			 leftRows.add(nodes.get(i));
    		  }else {
    			  rightRows.add(nodes.get(i));
    		  }
    	  }else {
    		  horizontalObsticalFaced = true;
    	  }
      }
      List<List<Node>> roomA = new ArrayList<>(),roomB = new ArrayList<>(),roomC = new ArrayList<>(),roomD = new ArrayList<>();
      for(List<Node> row : leftRows) {
    	  List<Node> topRow = new ArrayList<>(),buttonRow = new ArrayList<>();
    	  boolean verticalObsticalFaced = false;
    	  for(int i = 0;i < row.size();i++) {
    		  if(!row.get(i).isObstical) {
    			  if(!verticalObsticalFaced) {
    				  topRow.add(row.get(i));
    			  }else {
    				  buttonRow.add(row.get(i));
    			  }
    		  }else {
    			  verticalObsticalFaced = true;
    		  }
    	  }
    	  roomA.add(topRow);
    	  roomB.add(buttonRow);
      }
      for(List<Node> row : rightRows) {
    	  List<Node> topRow = new ArrayList<>(),buttonRow = new ArrayList<>();
    	  boolean verticalObsticalFaced = false;
    	  for(int i = 0;i < row.size();i++) {
    		  if(!row.get(i).isObstical) {
    			  if(!verticalObsticalFaced) {
    				  topRow.add(row.get(i));
    			  }else {
    				  buttonRow.add(row.get(i));
    			  }
    		  }else {
    			  verticalObsticalFaced = true;
    		  }
    	  }
    	  roomC.add(topRow);
    	  roomD.add(buttonRow);
      }
      divideRooms(divisionOperations,roomA);
      divideRooms(divisionOperations,roomB);
      divideRooms(divisionOperations,roomC);
      divideRooms(divisionOperations,roomD);
      }
}