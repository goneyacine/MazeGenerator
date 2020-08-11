package mazeGeneration;

import mazeGeneration.MazeObjects.*;
import java.util.List;
import java.util.*;
public class MazeGenerator {
	
    public Board board;
    private Room defaultRoom;
    private int isStart = 1;
    private int startRandomRow,startRandomColumn;
    public void generateMaze(int[] boardScale,int nodeScale,int divisionOperations) throws Exception {
    	//setting up the maze board
    	board = new Board(boardScale,nodeScale);
    	//setting up nodes 
    	setUpBoardNodes(board);
    	//dividing the room
    	divideRooms(divisionOperations,board.nodes);
    	makeHoles();
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
    	
    	if(nodes == null || nodes.size() <= 2 ||nodes.get(0).size() <= 2 || divisionOperations < 1)
    	  return;
    	//generate random walls
    	int _IsStart_ = (isStart == 1) ? 18 : 4;
    	int randomRow = (int)(Math.random() * (nodes.size() - _IsStart_));
    	if(randomRow < 18 && isStart == 1)
    	   randomRow = 18;
    	else if (randomRow < 2 && isStart == 0)
    		randomRow = 2;
    	int randomColumn = (int)(Math.random() * (nodes.get(0).size() - _IsStart_));
    	if(randomColumn < 18 && isStart == 1)
    	   randomColumn = 18;   
    	else if (randomColumn < 2 && isStart == 0)
    		randomColumn = 2;
    	if(isStart == 1) {
    	  startRandomColumn = randomColumn;
    	  startRandomRow = randomRow;
    	  isStart = 0;
    	}
        for(Node node : nodes.get(randomRow)) {
        	node.isObstical = true;  
        }
        for(List<Node> row : nodes) {
        	row.get(randomColumn).isObstical = true;
        }
      
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
    private void makeHoles() {
    	//first checking and finding the needed walls parts
    	List<List<Node>> parts = new ArrayList<>();
    	  //vertical checking
    	for(int x = 0; x < board.nodes.size();x++) {
    		List<Node> part = new ArrayList<>();
    		for(int y = 0; y < board.nodes.get(0).size() - 1;y++) {
    			if(y == 0) {
    				if(part.size() > 0)
    					parts.add(part);
    				part = new ArrayList<>();
    			}
    			if(board.nodes.get(x).get(y).isObstical) {
    				if(x == startRandomRow && y <= startRandomColumn)
    					continue;
    				else {
    					if(x == 0 && x == board.nodes.size() - 1) {
    						part.add(board.nodes.get(x).get(y));
    					}else if(x > 0 && !board.nodes.get(x - 1).get(y).isObstical && x == board.nodes.size() - 1){
    						part.add(board.nodes.get(x).get(y));
    					}else if(x == 0 && x < board.nodes.size() - 1 && !board.nodes.get(x + 1).get(y).isObstical){
    						part.add(board.nodes.get(x).get(y));
    					}else if (x > 0&& !board.nodes.get(x - 1).get(y).isObstical && x < board.nodes.size() - 1 && !board.nodes.get(x + 1).get(y).isObstical){
    						part.add(board.nodes.get(x).get(y));
    					}else {
    					
    						if(part.size() > 0) {
    							parts.add(part);
    							part = new ArrayList<>();
    						}
    						continue;
    					}
    				}
    					
    			}else {
    				if(part.size() <= 0)
    					continue;
    				else {
    					parts.add(part);
    					part = new ArrayList<>();
    					continue;
    				}
    			}
    		}
    	}
    	//vertical checking 
    	for(int y = 0; y < board.nodes.get(0).size();y++) {
    		List<Node> part = new ArrayList<>();
    		for(int x = 0; x < board.nodes.size() - 1;x++) {
    			if(y == 0) {
    				if(part.size() > 0)
    					parts.add(part);
    				part = new ArrayList<>();
    			}
    			if(board.nodes.get(x).get(y).isObstical) {
    					if(y == 0 && y == board.nodes.size() - 1) {
    						part.add(board.nodes.get(x).get(y));
    					}else if(y > 0 && !board.nodes.get(x).get(y - 1).isObstical && y == board.nodes.get(0).size() - 1){
    						part.add(board.nodes.get(x).get(y));
    					}else if(y == 0 && y< board.nodes.get(0).size() - 1 && !board.nodes.get(x).get(y + 1).isObstical){
    						part.add(board.nodes.get(x).get(y));
    					}else if (y >  0 && !board.nodes.get(x).get(y - 1).isObstical && y < board.nodes.get(0).size() - 1 && !board.nodes.get(x).get(y + 1).isObstical){
    						part.add(board.nodes.get(x).get(y));
    					}else {
    					
    						if(part.size() > 0) {
    							parts.add(part);
    							part = new ArrayList<>();
    						}
    						continue;
    					}
    
    			}else {
    				if(part.size() <= 0)
    					continue;
    				else {
    					parts.add(part);
    					part = new ArrayList<>();
    					continue;
    				}
    			}
    		}
    	}
    	//second : making hole 
    	for(List<Node> part : parts) {
            part.get((int)(Math.random() * (part.size() - 1))).isObstical = false;    		
    	}
    }
}