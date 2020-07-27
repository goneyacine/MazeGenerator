package servlets;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import mazeGeneration.*;
import mazeGeneration.MazeObjects.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MazeDisplayingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setContentType("image/jpeg");
		//setting up the maze board and generating the maze
		Board board = null;
		int[] boardScale = {800,800};
		int nodeScale = 20;
		int[] boardPosition = {50,50};
		MazeGenerator  mazeGenerator = new MazeGenerator();
	    mazeGenerator.generateMaze(boardScale, nodeScale, boardPosition);
	    board = mazeGenerator.board;
	    //finding the lines that we should draw
	    List<MazeLine> lines = new ArrayList<>();
	    
	    for(Node[] nodeArray : board.nodes) {
	    	
	    	for(Node node : nodeArray) {
	    		//finding the node corners positions(a,b,c,d) 
	    		int[] aPoint = {node.worldPosition[0] - (nodeScale / 2),node.worldPosition[1] - (nodeScale /2)};
	    		int[] bPoint = {node.worldPosition[0] + (nodeScale / 2),node.worldPosition[1] - (nodeScale /2)};
	    		int[] cPoint = {node.worldPosition[0] + (nodeScale / 2),node.worldPosition[1] + (nodeScale /2)};
	    		int[] dPoint = {node.worldPosition[0] - (nodeScale / 2),node.worldPosition[1] + (nodeScale /2)};
	    		//check if we should draw any wall of the node walls
	    		if(node.topWall.enabled)
	    			lines.add(new MazeLine(aPoint[0],aPoint[1],bPoint[0],bPoint[1]));
	    		else if (node.buttonWall.enabled)
	    			lines.add(new MazeLine(dPoint[0],dPoint[1],cPoint[0],cPoint[1]));
	    		else if (node.leftWall.enabled)
	    			lines.add(new MazeLine(aPoint[0],aPoint[1],dPoint[0],dPoint[1]));
	    		else if (node.rightWall.enabled)
	    			lines.add(new MazeLine(bPoint[0],bPoint[1],cPoint[0],cPoint[1]));
	    	}
	    	
	    }
	    //creating the maze image to display it on the web page
	    BufferedImage mazeImage = new BufferedImage(boardScale[0],boardScale[1],BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = mazeImage.createGraphics();
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, mazeImage.getWidth(), mazeImage.getHeight());
	    g2d.setColor(Color.BLACK);
	    for(MazeLine line : lines)
	    	g2d.drawLine(line.x1,line.y1,line.x2,line.y2);
	    ServletOutputStream os = response.getOutputStream();
	    ImageIO.write(mazeImage,"jpeg", os);	
}
}

