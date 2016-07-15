package gBaseModeler;

import java.awt.Point;

public class GMath
{
	
	public static int getAngle(Point a, Point b)
	{
		return (int) Math.toDegrees(Math.atan2((b.y-a.y), (b.x-a.x)));
	}
	
	
	
	public static Point getLineLineIntersection(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
	{
		int x=0, y=0;
		
		double m=(x1-x2)*(y3-y4)-(y1-y2)*(x3-x4);
		if (m==0.0)
			m=0.00000000001;
		
		int a=x1*y2-x2*y1;
		int b=x3*y4-x4*y3;
		
		x=(int) ((a*(x3-x4)-b*(x1-x2))/m);
		y=(int) ((a*(y3-y4)-b*(y1-y2))/m);
		
		return new Point(x, y);
	}
	
	
	
	public static int getPointLineDistance(int x, int y, int x1, int y1, int x2, int y2)
	{
		int d=(int) (Math.abs(((x2-x1)*(y1-y))-((x1-x)*(y2-y1)))/Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
		return d;
	}
	
	
	
	public static int getPointPointDistance(int x1, int y1, int x2, int y2)
	{
		return (int) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	}
	
}
