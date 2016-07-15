package gUML25.ActivityDiagram;

import gBaseModeler.GGElement;
import gBaseModeler.GPinableLabel;

import java.awt.Color;
import java.awt.Graphics;

public class GStartPoint extends GGElement
{
	private static final long	serialVersionUID		=3L;
	
	private GPinableLabel		label;
	private boolean				isLabelAddedtoParent	=false;
	
	
	public GStartPoint()
	{
		super(20, 50, 20, 20);
		setName("Start");
		Initialize();
	}
	
	public GStartPoint(String ActivityStartName, int x, int y, int width, int height)
	{
		super(x, y, width, width);
		setName(ActivityStartName);
		Initialize();
	}
	
	private void Initialize()
	{
		label=new GPinableLabel(getName(), this, getX()+getWidth()/2, getY());
		label.setLocation(getX()+getWidth()/2-15, getY()-25);
	}
	
	public void setSize(int x, int y)
	{
		super.setSize(x, x);
		label.setChangePins(getX()+getWidth()/2, getY());
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		label.setChangeElementLocation(getX(), getY());
	}
	
	
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		if (!isLabelAddedtoParent)
		{
			isLabelAddedtoParent=true;
			this.getParent().add(label);
		}
		
		g.setColor(Color.black);
		g.fillOval(1, 1, getWidth()-2, getHeight()-2);
	}
	
	
	
	public int getMinWidth()
	{
		return 15;
	}
	
	public int getMaxWidth()
	{
		return 100;
	}
	
	public int getMinHeight()
	{
		return 15;
	}
	
	public int getMaxHeight()
	{
		return 100;
	}
	
}// End of Class
