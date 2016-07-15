package gUML25.ActivityDiagram;

import gBaseModeler.GBaseSetting;
import gBaseModeler.GGElement;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class GCondition extends GGElement
{
	private static final long	serialVersionUID	=3L;
	
	
	private Point				p1, p2, p3, p4;
	
	public GCondition()
	{
		super(20, 150, 50, 50);
		setName("condition");
		setPs();
	}
	
	public GCondition(String Name, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setName(Name);
		setPs();
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		setPs();
	}
	
	private void setPs()
	{
		//     1
		//    / \
		//   /   \
		// 4        2		
		//   \   /
		//    \ /
		//     3
		p1=new Point(getWidth()/2, 0);
		p2=new Point(getWidth(), getHeight()/2);
		p3=new Point(getWidth()/2, getHeight());
		p4=new Point(0, getHeight()/2);
	}
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		Graphics2D g2=(Graphics2D) g;
		
		Polygon gpol=new Polygon();
		gpol.addPoint(p1.x, p1.y);
		gpol.addPoint(p2.x, p2.y);
		gpol.addPoint(p3.x, p3.y);
		gpol.addPoint(p4.x, p4.y);
		
		g2.setPaint(new GradientPaint(0, 0, GBaseSetting.ElementsBackground, getWidth()/2, 1, Color.WHITE));
		//g2.setColor(GBaseSetting.ElementsBackground);
		g2.fillPolygon(gpol);
		
		g2.setColor(Color.black);
		g2.drawPolygon(gpol);
	}
	
	
	
	public Point getConnectionPoint(Point AA)
	{
		int x=AA.x;
		int y=AA.y;
		
		int x1=this.getX();
		int y1=this.getY();
		int x2=x1+this.getWidth();
		int y2=y1+this.getHeight();
		
		
		//   0 |    1    |  2
		//  -----------------
		//     |         |
		//  7  |         |  3
		//     |         |   
		//  -----------------
		//  6  |    5    |  4
		//      
		
		Point R=new Point(0, 0);
		
		if (x<=x1&&y<=y1)//0
			R=p1;
		else if (y<=y1&&x>=x1&&x<=x2)//1
			R=p1;
		else if (x>=x2&&y<=y1)//2
			R=p1;
		else if (x>=x2&&y>=y1&&y<=y2)//3
			R=p2;
		else if (x>=x2&&y>=y2)//4
			R=p2;
		else if (x>=x1&&x<=x2&&y>=y2)//5
			R=p3;
		else if (x<=x1&&y>=y2)//6
			R=p4;
		else if (x<=x1&&y>=y1&&y<=y2)//7
			R=p4;
		
		if (R.x==0&&R.y==0)
			R=p1;
		
		
		return new Point(getX()+R.x, getY()+R.y);
	}
	
	public Point getConnectionPoint(Point externalPoint, Point BB)
	{
		return getConnectionPoint(externalPoint);
	}
	
	//
	//
	//
	//
	//
	
	public int getMinWidth()
	{
		return 40;
	}
	
	public int getMaxWidth()
	{
		return 200;
	}
	
	public int getMinHeight()
	{
		return 40;
	}
	
	public int getMaxHeight()
	{
		return 200;
	}
	
	
}// End of Class
