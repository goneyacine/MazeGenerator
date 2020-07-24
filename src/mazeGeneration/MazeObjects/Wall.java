package mazeGeneration.MazeObjects;

public class Wall {

	public Node parent;
	public boolean enabled = true;
	public String name;

	public Wall(Node parent,boolean enabled,String name) {
		
		this.parent = parent;
		this.enabled = enabled;
		this.name = name;
		
	}
	
}
