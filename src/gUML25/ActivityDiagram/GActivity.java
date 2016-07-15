package gUML25.ActivityDiagram;

import gBaseModeler.GBaseSetting;
import gBaseModeler.GGElement;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;

public class GActivity extends GGElement
{
	private static final long	serialVersionUID	=3L;
	
	private JLabel				Label;
	
	
	public GActivity()
	{
		super(20, 70, 150, 70);
		setName("شرح فعالیت در این قسمت نوشته خواهد شد");
		Initialize();
	}
	
	public GActivity(String ActivityName, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setName(ActivityName);
		Initialize();
	}
	
	private void Initialize()
	{
		Label=new JLabel("<html><p align=center>"+getName()+"</p></html>");
		Label.setBounds(6, 6, getWidth()-6, getHeight()-6);
		Label.setVerticalAlignment(JLabel.TOP);
		Label.setHorizontalAlignment(JLabel.CENTER);
		this.add(Label);
	}
	
	
	public void setSize(int x, int y)
	{
		super.setSize(x, y);
		Label.setBounds(6, 6, getWidth()-6, getHeight()-6);
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		Label.setBounds(6, 6, getWidth()-6, getHeight()-6);
	}
	
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		Graphics2D g2=(Graphics2D) g;
		g2.setPaint(new GradientPaint(0, 0, GBaseSetting.ElementsBackground, getWidth()/2, 1, Color.WHITE));
		
		g2.fill(new RoundRectangle2D.Double(2, 2, getWidth()-3, getHeight()-3, 30, 30));
		
		g.setColor(Color.black);
		g2.draw(new RoundRectangle2D.Double(2, 2, getWidth()-3, getHeight()-3, 30, 30));
		
		Label.setForeground(this.getForeground());
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
