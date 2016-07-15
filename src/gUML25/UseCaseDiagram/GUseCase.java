package gUML25.UseCaseDiagram;

import gBaseModeler.GBaseSetting;
import gBaseModeler.GGElement;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class GUseCase extends GGElement
{
	private static final long	serialVersionUID	=3L;
	
	private JLabel				UseCaseLabel;
	
	public GUseCase()
	{
		super(100, 10, 150, 75);
		this.setName("Use Case Name should be here");
		Initialize();
	}
	
	public GUseCase(String UseCaseName, int x, int y, int width, int height)
	{
		super(x, y, width, (int) (0.5*width));
		this.setName(UseCaseName);
		Initialize();
	}
	
	private void Initialize()
	{
		UseCaseLabel=new JLabel("<html><p align=center>"+getName()+"</p></html>");
		UseCaseLabel.setBounds(6, 6, getWidth()-6, getHeight()-6);
		UseCaseLabel.setVerticalAlignment(JLabel.CENTER);
		UseCaseLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//this.setInheritsPopupMenu(true);
		this.add(UseCaseLabel);
	}
	
	
	public void setSize(int x, int y)
	{
		if (getiisResizing()==4)
			y=x/2;
		else if (x==this.getWidth()&&(y!=this.getHeight()))
			x=2*y;
		else if (y==this.getHeight()&&(x!=this.getWidth()))
			y=x/2;
		
		super.setSize(x, y);
		UseCaseLabel.setBounds(6, 6, getWidth()-6, getHeight()-6);
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		UseCaseLabel.setBounds(6, 6, getWidth()-6, getHeight()-6);
	}
	
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		Graphics2D g2=(Graphics2D) g;
		
		g2.setPaint(new GradientPaint(0, 0, GBaseSetting.ElementsBackground, getWidth()/2, 1, Color.WHITE));
		g.fillOval(3, 3, getWidth()-4, getHeight()-4);
		
		g.setColor(Color.black);
		g.drawOval(3, 3, getWidth()-4, getHeight()-4);
		
		UseCaseLabel.setForeground(this.getForeground());
		
	}
	
	
	
	public int getMinWidth()
	{
		return 40;
	}
	
	public int getMaxWidth()
	{
		return 500;
	}
	
	public int getMinHeight()
	{
		return (int) (0.6*getMinWidth());
	}
	
	public int getMaxHeight()
	{
		return (int) (0.6*getMaxWidth());
	}
	
	
}// End of class GUseCase
