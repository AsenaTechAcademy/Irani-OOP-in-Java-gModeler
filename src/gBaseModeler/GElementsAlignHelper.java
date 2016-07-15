package gBaseModeler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class GElementsAlignHelper extends GBaseConnectorAdapter
{
	private static final long	serialVersionUID	=3L;
	
	private static Point		xstartPoint;				//x align helpers
	private static Point		xendPoint;
	
	private static Point		ystartPoint;				//y align helpers
	private static Point		yendPoint;
	
	private static Point		wstartPoint;				//width align helpers
	private static Point		wendPoint;
	
	private static Point		hstartPoint;				//height align helpers
	private static Point		hendPoint;
	
	private static Point		exyPoint;					// rulers that show every dragging time  
															
	public GElementsAlignHelper()
	{
		super();
		setNull();
		this.setEnabled(false);
		this.setBounds(0, 0, GBaseSetting.DiagramWidth-1, GBaseSetting.DiagramHeight-1);
	}
	
	public void drawElement(Graphics g)
	{
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Stroke drawingStroke=new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4 }, 0);
		g2.setStroke(drawingStroke);
		
		//draw the ruler
		g.setColor(new Color(150, 150, 150));
		g.drawLine(exyPoint.x, 0, exyPoint.x, exyPoint.y);
		g.drawLine(0, exyPoint.y, exyPoint.x, exyPoint.y);
		
		//draw align helpers
		drawingStroke=new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1 }, 0);
		g2.setStroke(drawingStroke);
		g.setColor(new Color(50, 50, 255));
		g.drawLine(xstartPoint.x, xstartPoint.y, xendPoint.x, xendPoint.y);
		g.drawLine(ystartPoint.x, ystartPoint.y, yendPoint.x, yendPoint.y);
		g.drawLine(wstartPoint.x, wstartPoint.y, wendPoint.x, wendPoint.y);
		g.drawLine(hstartPoint.x, hstartPoint.y, hendPoint.x, hendPoint.y);
	}
	
	@Override
	public boolean contains(int x, int y)
	{
		return false;
	}
	
	public void setNull()
	{
		xstartPoint=new Point(-1, -1);
		xendPoint=new Point(-1, -1);
		ystartPoint=new Point(-1, -1);
		yendPoint=new Point(-1, -1);
		wstartPoint=new Point(-1, -1);
		wendPoint=new Point(-1, -1);
		hstartPoint=new Point(-1, -1);
		hendPoint=new Point(-1, -1);
		exyPoint=new Point(-1, -1);
		repaint();
	}
	
	public void getAlignHelp(GDiagram g, GElement e)
	{
		setNull();
		exyPoint=new Point(e.getX(), e.getY());
		
		// check any other elements in diagram to find nearest Element to e
		int ix=-1, iy=-1, iw=-1, ih=-1;
		for (int i=0; i<g.getComponentCount(); i++)
		{
			if (g.getComponent(i)==e)
				continue;
			
			if (Math.abs(g.getComponent(i).getX()-e.getX())<10)
				ix=i;
			if (Math.abs(g.getComponent(i).getY()-e.getY())<10)
				iy=i;
			if (Math.abs((g.getComponent(i).getX()+g.getComponent(i).getWidth())-(e.getX()+e.getWidth()))<10)
				iw=i;
			if (Math.abs((g.getComponent(i).getY()+g.getComponent(i).getHeight())-(e.getY()+e.getHeight()))<10)
				ih=i;
		}// for i
		
		
		// set result of align searches in their attributes
		// then drawElement method draw them in repaint
		if (ix!=-1)
		{
			xstartPoint=new Point(g.getComponent(ix).getX(), g.getComponent(ix).getY());
			xendPoint=new Point(g.getComponent(ix).getX(), e.getY()+e.getHeight());
		}
		
		if (iy!=-1)
		{
			ystartPoint=new Point(g.getComponent(iy).getX(), g.getComponent(iy).getY());
			yendPoint=new Point(e.getX()+e.getWidth(), g.getComponent(iy).getY());
		}
		if (iw!=-1)
		{
			wstartPoint=new Point(g.getComponent(iw).getX()+g.getComponent(iw).getWidth(), g.getComponent(iw).getY());
			wendPoint=new Point(g.getComponent(iw).getX()+g.getComponent(iw).getWidth(), e.getY()+e.getHeight());
		}
		
		if (ih!=-1)
		{
			hstartPoint=new Point(g.getComponent(ih).getX(), g.getComponent(ih).getY()+g.getComponent(ih).getHeight());
			hendPoint=new Point(e.getX()+e.getWidth(), g.getComponent(ih).getY()+g.getComponent(ih).getHeight());
		}
		repaint();
	}
	
	//
	//
	//
	//
	
	@Override
	public GElement getSourceElement()
	{
		return null;
	}
	
	@Override
	public GElement getTargetElement()
	{
		return null;
	}
	
	
}// End of Class
