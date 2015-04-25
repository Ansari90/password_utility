package data_io;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Block implements Serializable
{

	private String name;
	private String information;
	
	public Block(String name)
	{
		this.name = name;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getInformation() { return information; }
	public void setInformation(String information) { this.information = information; } 
	
}
