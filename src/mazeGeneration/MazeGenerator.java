package mazeGeneration;

import mazeGeneration.MazeObjects.*;
import java.util.List;
import java.util.*;

public class MazeGenerator {
	
    public Board board;
    private Room defaultRoom;
    private int isStart = 1;
    private int startRandomRow,startRandomColumn;
    private List<Node> nodesToOpen = new ArrayList<>();
    public List<Node> openedNodes = new ArrayList<>();
    
    public void generateMaze(int[] boardScale,int nodeScale,int divisionOperations) throws Exception {
    	//setting up the maze board
    	board = new Board(boardScale,nodeScale);
    	//setting up nodes 
    	setUpBoardNodes(board);
    	//dividing the room
    	divideRooms(divisionOperations,board.nodes);
    	makeHoles();
    	for(int i = board.nodes.get(0).size() - 1;i > 1;i --) {
    		if(board.nodes.get(board.nodes.size() - 1).get(i).isObstical) {
    			board.nodes.get(board.nodes.size() - 1).get(i).isObstical = false;
    			if(!board.nodes.get(board.nodes.size() - 1).get(i + 1).isObstical) {
    				break;
    			}	
    	}
    }
    	findPath();
 }
    private void setUpBoardNodes(Board board) {
    	//creating the nodes array
    	List<List<Node>> nodes = new ArrayList<>();
    	//adding node to the nodes array
    	for(int x = 0; x <  board.scale[0] / board.nodeScale;x++) {
    		List<Node> row = new ArrayList<>();
    		for(int y = 0; y <  board.scale[1] / board.nodeScale; y++) {
    			int [] nodePosition = { ((board.scale[0] / board.nodeScale) + board.nodeScale) * x,(( board.scale[1] / board.nodeScale) + board.nodeScale)   * y};
    			int[] localNodePosition = {nodes.size(),row.size()};
    		    Node node = new Node(board.nodeScale,nodePosition,localNodePosition);
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
    public void findPath() {
    	if(openedNodes.size() == 0) {
    		//initial steps
    		Node startNode = board.nodes.get(0).get(0);
    		Node endNode = board.nodes.get(board.nodes.size() - 1).get(board.nodes.get(0).size() - 1);
    		startNode.gCost = distance(startNode.worldPosition[0],startNode.worldPosition[1],startNode.worldPosition[0],startNode.worldPosition[1]);
    	    startNode.hCost = distance(endNode.worldPosition[0],endNode.worldPosition[1],startNode.worldPosition[0],startNode.worldPosition[1]);
    		startNode.fCost = startNode.hCost + startNode.gCost;
    		openNode(startNode);
    	}
    	if(nodesToOpen.size() == 0)
    		return;
    	openNode(nodesToOpen.get(0));
    }
    private void openNode(Node node) {
    	if(node.localPosition[0] == board.nodes.size() - 1 && node.localPosition[1] == board.nodes.get(0).size() - 1)
    		return;
    	nodesToOpen.remove(node);
    	node.toOpen = false;
		Node endNode = board.nodes.get(board.nodes.size() - 1).get(board.nodes.get(0).size() - 1);
		node.isOpened = true;
		openedNodes.add(node);
		if(node.localPosition[0] -1  >= 0) {
			Node C = board.nodes.get(node.localPosition[0] - 1).get(node.localPosition[1]);
			C.gCost = distance(C.localPosition[0],C.localPosition[1],node.localPosition[0],node.localPosition[1]) + node.gCost;
			C.hCost =  distance(endNode.worldPosition[0],endNode.worldPosition[1],C.worldPosition[0],C.worldPosition[1]);
			C.fCost = C.hCost + C.gCost;
			C.toOpen = true;
			nodesToOpen.add(C);
		}
		if(node.localPosition[0] + 1 <= board.nodes.size() - 1) { 
				Node F = board.nodes.get(node.localPosition[0] + 1).get(node.localPosition[1]);
				F.gCost = distance(F.localPosition[0],F.localPosition[1],node.localPosition[0],node.localPosition[1]) + node.gCost;
				F.hCost =  distance(endNode.worldPosition[0],endNode.worldPosition[1],F.worldPosition[0],F.worldPosition[1]);
				F.fCost = F.hCost + F.gCost;
				F.toOpen = true;
				nodesToOpen.add(F);
		}
		   if(node.localPosition[1]  - 1 >= 0) {
         	  Node G = board.nodes.get(node.localPosition[0]).get(node.localPosition[1] - 1);
				G.gCost = distance(G.localPosition[0],G.localPosition[1],node.localPosition[0],node.localPosition[1]) + node.gCost;
				G.hCost =  distance(endNode.worldPosition[0],endNode.worldPosition[1],G.worldPosition[0],G.worldPosition[1]);
				G.fCost = G.hCost + G.gCost;
				G.toOpen =  true;
				nodesToOpen.add(G);
			  }
			  if(node.localPosition[1] + 1 <= board.nodes.get(0).size() - 1) {
				  Node H  = board.nodes.get(node.localPosition[0]).get(node.localPosition[1] + 1);
				H.gCost = distance(H.localPosition[0],H.localPosition[1],node.localPosition[0],node.localPosition[1]) + node.gCost;
				H.hCost =  distance(endNode.worldPosition[0],endNode.worldPosition[1],H.worldPosition[0],H.worldPosition[1]);
				H.fCost = H.hCost + H.gCost;
				H.toOpen = true;
				nodesToOpen.add(H);
			  } 
		if(nodesToOpen.size()  == 0)
			return;
		quickSort(0,nodesToOpen.size() - 1);
		openNode(nodesToOpen.get(0));
    }
    private void quickSort(int left,int right) {
    	  if (left >= right)
              return;
              int pivot = (left + right) / 2;
              int index = partition(left, right, pivot);
              quickSort(left, index - 1);
              quickSort(index, right);
    }
    private int partition(int left,int right,int pivot) {
    	while (left <= right) {
            while (nodesToOpen.get(left).fCost < nodesToOpen.get(pivot).fCost)
                left++;
            while (nodesToOpen.get(right).fCost > nodesToOpen.get(pivot).fCost)
                right--;
            if (left <= right) {
                Node temp = nodesToOpen.get(left);
                nodesToOpen.set(left,nodesToOpen.get(right));
                nodesToOpen.set(right,temp);
                left++;
                right--;
            }
        }
    	return left;	
    }
    private int distance(int x1,int y1,int x2,int y2) {
    	return (int) Math.sqrt(Math.pow(x1 - x2,2) + Math.pow(y1 - y2,2));
    }
}