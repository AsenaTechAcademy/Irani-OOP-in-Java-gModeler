package gUML25.UseCaseDiagram;

import gBaseModeler.GGElement;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class GSystemBoundary extends GGElement
{
	private static final long	serialVersionUID	=3L;
	
	private JLabel				label;
	
	public GSystemBoundary()
	{
		super(250, 10, 150, 100);
		this.setName("test System Boundary");
		Initialize();
	}
	
	public GSystemBoundary(String BoundaryName, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.setName(BoundaryName);
		Initialize();
	}
	
	private void Initialize()
	{
		label=new JLabel("<html><p align=center>"+getName()+"</p></html>");
		label.setBounds(3, 3, getWidth()-6, 15);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		
		//this.setInheritsPopupMenu(true);
		this.add(label);
	}
	
	public void setSize(int x, int y)
	{
		super.setSize(x, y);
		label.setBounds(3, 3, getWidth()-6, 15);
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		label.setBounds(3, 3, getWidth()-6, 15);
	}
	
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		g.setColor(Color.black);
		g.drawRect(2, 2, getWidth()-4, getHeight()-4);
		
		label.setForeground(this.getForeground());
		
	}
	
	
	
	public int getMinWidth()
	{
		return 40;
	}
	
	public int getMaxWidth()
	{
		return 1000;
	}
	
	public int getMinHeight()
	{
		return 40;
	}
	
	public int getMaxHeight()
	{
		return 1000;
	}
	
	
}// End of class GUseCase
