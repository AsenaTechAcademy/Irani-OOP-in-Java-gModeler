package gUML25.UseCaseDiagram;

import gBaseModeler.GBaseSetting;
import gBaseModeler.GGElement;
import gBaseModeler.GPinableLabel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GActor extends GGElement
{
	private static final long	serialVersionUID	=3L;
	
	private GPinableLabel		label;
	private boolean				isLabelAdded		=false;
	
	public GActor()
	{
		super(20, 20, 50, 85);
		this.setName("an Actor");
		Initialize();
	}
	
	public GActor(String ActorName, int x, int y, int width, int Height)
	{
		super(x, y, width, (int) (1.7*width));
		this.setName(ActorName);
		Initialize();
	}
	
	private void Initialize()
	{
		label=new GPinableLabel(getName(), this, getX()-20, getY()+getHeight()-10);
		label.setSize(getWidth()+40, 100);
		label.setSelectable(false);
	}
	
	public void Delete()
	{
		this.getParent().remove(label);
		super.Delete();
	}
	
	
	public void setSize(int x, int y)
	{
		y=(int) (1.7*x);
		
		super.setSize(x, y);
		label.setSize(getWidth()+40, 100);
		label.setLocation(getX()-20, getY()+getHeight()-10);
	}
	
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		label.setChangeElementLocation(getX(), getY());
	}
	
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		DrawActor(g, 8, 8, getWidth()-8, getHeight()-8);
		
		if (!isLabelAdded)
		{
			this.getParent().add(label);
			this.getParent().setComponentZOrder(label, 0);
			this.getParent().setComponentZOrder(this, 0);
			isLabelAdded=true;
		}
	}
	
	private void DrawActor(Graphics g, int x1, int y1, int x2, int y2)
	{
		g.setColor(Color.black);
		Graphics2D g2=(Graphics2D) g;
		
		int p=0;
		int a=(x2-x1)/3;
		int mid=(x2-x1)/2;
		
		//  O   
		g.setColor(GBaseSetting.ElementsBackground);
		g2.setPaint(new GradientPaint(0, 0, GBaseSetting.ElementsBackground, getWidth()/2, 1, Color.WHITE));
		g.fillOval(x1+mid-a, y1+p, 2*a, 2*a);
		g.setColor(Color.black);
		g.drawOval(x1+mid-a, y1+p, 2*a, 2*a);
		
		//  |    
		int body=(y2-y1)/4;
		if (y2-y1<60)
			body=(y2-y1)/4;
		g.drawLine(x1+mid, y1+2*a, x1+mid, y1+2*a+3+body);
		
		//  ---
		int left=(x2-x1)/8;
		int p2=(x2-x1)/8;
		g.drawLine(x1+left, y1+4+2*a+p2, x2-left, y1+4+2*a+p2);
		
		//  /\
		int bt=(x2-x1)/10;
		int btleft=(x2-x1)/9;
		g.drawLine(x1+mid, y1+2*a+3+body, x1+btleft, y2-bt);
		g.drawLine(x1+mid, y1+2*a+3+body, x2-btleft, y2-bt);
	}
	
	
	
	public int getMinWidth()
	{
		return 25;
	}
	
	public int getMaxWidth()
	{
		return 250;
	}
	
	public int getMinHeight()
	{
		return 2*getMinWidth();
	}
	
	public int getMaxHeight()
	{
		return 2*getMaxWidth();
	}
	
}// End of Class Actor
