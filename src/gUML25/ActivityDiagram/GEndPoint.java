package gUML25.ActivityDiagram;

import gBaseModeler.GBaseSetting;
import gBaseModeler.GGElement;
import gBaseModeler.GPinableLabel;

import java.awt.Color;
import java.awt.Graphics;

public class GEndPoint extends GGElement
{
	private static final long	serialVersionUID		=3L;
	
	private GPinableLabel		label;
	private boolean				isLabelAddedtoParent	=false;
	
	
	public GEndPoint()
	{
		super(10, 250, 20, 20);
		setName("پایان");
		Initialize();
	}
	
	public GEndPoint(String ActivityEndName, int x, int y, int width, int height)
	{
		super(x, y, width, width);
		setName(ActivityEndName);
		Initialize();
	}
	
	private void Initialize()
	{
		label=new GPinableLabel(getName(), this, getX()+getWidth()/2, getY()+getWidth());
		label.setLocation(getX()+getWidth()/2-15, getY()+getHeight()+10);
	}
	
	
	public void setSize(int x, int y)
	{
		super.setSize(x, x);
		label.setChangePins(getX()+getWidth()/2, getY()+getHeight());
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
		
		g.setColor(GBaseSetting.ElementsBackground);
		g.fillOval(1, 1, getWidth()-2, getHeight()-2);
		
		g.setColor(Color.black);
		g.drawOval(1, 1, getWidth()-2, getHeight()-2);
		
		g.setColor(Color.black);
		g.fillOval(6, 6, getWidth()-11, getHeight()-11);
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
