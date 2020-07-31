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
		int nodeScale = 40;
		MazeGenerator  mazeGenerator = new MazeGenerator();
	    try {
			mazeGenerator.generateMaze(boardScale, nodeScale);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    board = mazeGenerator.board;
	      //creating the maze image to display it on the web page
	    BufferedImage mazeImage = new BufferedImage(boardScale[0] + 120,boardScale[1] + 120,BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = mazeImage.createGraphics();
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, mazeImage.getWidth(), mazeImage.getHeight());
	    g2d.setColor(Color.BLACK);
	    for(Node[] nodes : board.nodes) {
	    	for(Node node : nodes) {
	    		g2d.drawRect(node.worldPosition[0] + 15,node.worldPosition[1] + 15,board.nodeScale,board.nodeScale);
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

