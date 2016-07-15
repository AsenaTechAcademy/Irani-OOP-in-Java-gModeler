package gBaseModeler;

import java.awt.Point;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface GBaseModelerInterface extends MouseListener, MouseMotionListener, KeyListener, FocusListener
{
	
	public Point getCenterPoint();
	
	public void Delete();
	
	public void setZoomFactor(float zoomfactor);
	
	public float getZoomFactor();
	
	public boolean isLock();
	
	public void setLock(boolean lockstatus);
	
	public boolean isSelectable();
	
	public boolean isTSelectable();
	
}
