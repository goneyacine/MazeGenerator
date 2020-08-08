package servlets;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import mazeGeneration.*;
import mazeGeneration.MazeObjects.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.io.*;
import java.net.URL;

public class MazeGenerationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
	
		  response.setContentType("text/html");
		//setting up the maze board and generating the maze
		Board board = null;
		int[] boardScale = {880,880};
		int nodeScale = 60;
		MazeGenerator  mazeGenerator = new MazeGenerator();
	    try {
			mazeGenerator.generateMaze(boardScale, nodeScale,4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    board = mazeGenerator.board;
	      //creating the maze image to display it on the web page
	    BufferedImage mazeImage = new BufferedImage(boardScale[0] + 195,boardScale[1] + 195,BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = mazeImage.createGraphics();
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, mazeImage.getWidth(), mazeImage.getHeight());
	    g2d.setColor(Color.BLACK);
	    for(Node[] nodes : board.nodes) {
	    	for(Node node : nodes) {
	    		
	    		if(node.isObstical)
	    			g2d.fillRect(node.worldPosition[0] + 20,node.worldPosition[1] + 20,board.nodeScale,board.nodeScale);
	    		else
	    			g2d.drawRect(node.worldPosition[0] + 20,node.worldPosition[1] + 20,board.nodeScale,board.nodeScale);
	    	}
	   } 	
	    File file = new File(getServletContext().getRealPath("mazeImage"));
	    if(file.exists())
	    	System.out.println("");
	    else 
	    	System.out.println("");
	    	
	    ImageIO.write(mazeImage,"jpeg", file);
	    PrintWriter out  = response.getWriter();
	    out.print("<img src = " + file.getAbsolutePath() + ">");
	    }
}

