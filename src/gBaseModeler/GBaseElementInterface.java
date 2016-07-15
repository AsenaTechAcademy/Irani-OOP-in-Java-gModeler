package gBaseModeler;

import java.awt.Point;

public interface GBaseElementInterface extends GBaseModelerInterface
{
	public Point getConnectionPoint(Point p);
	
	public void addConnector(GConnector newCon);
	
	public void removeConnector(GConnector oldConn);
	
	public boolean ResizeTo(int newWidth, int newHeight);
	
	public boolean isTSelected();
	
	public boolean isMovable();
	
	public boolean isResizable();
	
}
