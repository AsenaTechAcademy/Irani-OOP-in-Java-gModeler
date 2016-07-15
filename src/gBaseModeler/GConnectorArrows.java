package gBaseModeler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

public abstract class GConnectorArrows
{
	public static final int	AT_Empty		=0;
	public static final int	AT_Arrow		=1;
	public static final int	AT_Inherit		=2;
	public static final int	AT_Aggregation	=3;
	public static final int	AT_Composition	=4;
	public static final int	AT_Nested		=5;
	
	
	private static int		lenght			=12;
	
	public static void DrawConnectorArrow(int AT_ArrowType, Graphics g, int x, int y, int degree)
	{
		if (AT_ArrowType==GConnectorArrows.AT_Arrow)
			drawArrow(g, x, y, degree);
		else if (AT_ArrowType==GConnectorArrows.AT_Inherit)
			drawInherit(g, x, y, degree);
		else if (AT_ArrowType==GConnectorArrows.AT_Aggregation)
			drawAggregation(g, x, y, degree);
		else if (AT_ArrowType==GConnectorArrows.AT_Composition)
			drawComposition(g, x, y, degree);
		else if (AT_ArrowType==GConnectorArrows.AT_Nested)
			drawNested(g, x, y, degree);
	}
	
	public static void drawArrow(Graphics g, int x, int y, int degree)
	{
		Graphics2D g2=(Graphics2D) g;
		g2Init(g2, x, y, degree);
		
		// draw your shape here
		g2.setColor(Color.black);
		g2.drawLine(x, y, x-lenght, y-lenght/2);
		g2.drawLine(x, y, x-lenght, y+lenght/2);
		// End of draw
		
		g2deInit(g2, x, y, degree);
	}
	
	
	
	public static void drawInherit(Graphics g, int x, int y, int degree)
	{
		Graphics2D g2=(Graphics2D) g;
		g2Init(g2, x, y, degree);
		
		// draw your shape here
		Polygon gpol=new Polygon();
		gpol.addPoint(x, y);
		gpol.addPoint(x-lenght, y-lenght/2);
		gpol.addPoint(x-lenght, y+lenght/2);
		
		g2.setColor(GBaseSetting.ArrowsBackground);
		g2.fillPolygon(gpol);
		
		g2.setColor(Color.black);
		g2.drawPolygon(gpol);
		// End of draw
		
		g2deInit(g2, x, y, degree);
	}
	
	
	public static void drawAggregation(Graphics g, int x, int y, int degree)
	{
		Graphics2D g2=(Graphics2D) g;
		g2Init(g2, x, y, degree);
		
		// draw your shape here
		Polygon gpol=new Polygon();
		gpol.addPoint(x, y);
		gpol.addPoint(x-lenght, y-lenght/2);
		gpol.addPoint(x-2*lenght, y);
		gpol.addPoint(x-lenght, y+lenght/2);
		
		g2.setColor(GBaseSetting.ArrowsBackground);
		g2.fillPolygon(gpol);
		
		g2.setColor(Color.black);
		g2.drawPolygon(gpol);
		// End of draw
		
		g2deInit(g2, x, y, degree);
	}
	
	
	
	public static void drawComposition(Graphics g, int x, int y, int degree)
	{
		Graphics2D g2=(Graphics2D) g;
		g2Init(g2, x, y, degree);
		
		// draw your shape here
		Polygon gpol=new Polygon();
		gpol.addPoint(x, y);
		gpol.addPoint(x-lenght, y-lenght/2);
		gpol.addPoint(x-2*lenght, y);
		gpol.addPoint(x-lenght, y+lenght/2);
		
		g2.setColor(Color.black);
		g2.fillPolygon(gpol);
		// End of draw
		
		g2deInit(g2, x, y, degree);
	}
	
	
	
	public static void drawNested(Graphics g, int x, int y, int degree)
	{
		Graphics2D g2=(Graphics2D) g;
		g2Init(g2, x, y, degree);
		
		// draw your shape here
		int lenght1=lenght+2;
		g2.setColor(GBaseSetting.ArrowsBackground);
		g2.fillOval(x-lenght1, y-lenght1/2, lenght1, lenght1);
		g2.setColor(Color.black);
		g2.drawOval(x-lenght1, y-lenght1/2, lenght1, lenght1);
		g2.drawLine(x-lenght1/2, y-lenght1/2, x-lenght1/2, y+lenght1/2);
		g2.drawLine(x-lenght1, y, x, y);
		// End of draw
		
		g2deInit(g2, x, y, degree);
	}
	
	
	//
	//
	//
	//
	//
	//
	//
	private static void g2Init(Graphics2D g2, int x, int y, int degree)
	{
		g2.translate(x, y);
		g2.rotate(Math.toRadians(degree));
		g2.translate(-x, -y);
		
	}
	
	private static void g2deInit(Graphics2D g2, int x, int y, int degree)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(0.9f));
		
		g2.translate(x, y);
		g2.rotate(-Math.toRadians(degree));
		g2.translate(-x, -y);
		
	}
}
