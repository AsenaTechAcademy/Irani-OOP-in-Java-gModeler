package gBaseModeler;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public abstract class GBaseConnectorAdapter extends JComponent implements GBaseConnectorInterface
{
	
	private static final long	serialVersionUID	=3L;
	
	private float				zoomFactor			=1;
	private boolean				isLock				=false;
	private boolean				isSelectable		=true;
	private boolean				isTSelectable		=false;
	
	
	public GBaseConnectorAdapter()
	{
		// super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addFocusListener(this);
		
		this.setLayout(null);
		this.setEnabled(true);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawElement(g);
		if (hasFocus())
			drawSelected(g);
	}
	
	
	
	protected void drawElement(Graphics g)
	{
		
	}
	
	
	protected void drawSelected(Graphics g)
	{
		
	}
	
	@Override
	public void ChangeElementsSizeLocation(GElement e, int dx, int dy)
	
	{
		
	}
	
	@Override
	public void setSourceElement(GElement source)
	{
		
	}
	
	@Override
	public void setTargetElement(GElement target)
	{
		
	}
	
	@Override
	public void Delete()
	{
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		requestFocus();
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void focusGained(FocusEvent e)
	{
		repaint();
	}
	
	@Override
	public void focusLost(FocusEvent e)
	{
		repaint();
	}
	
	@Override
	public void setZoomFactor(float zoomfactor)
	{
		this.zoomFactor=zoomfactor;
	}
	
	@Override
	public float getZoomFactor()
	{
		return this.zoomFactor;
	}
	
	@Override
	public void setLock(boolean lock)
	{
		this.isLock=lock;
	}
	
	@Override
	public boolean isLock()
	{
		return this.isLock;
	}
	
	
	@Override
	public boolean isSelectable()
	{
		return isSelectable;
	}
	
	protected void setSelectable(boolean selectablestatus)
	{
		// if selectable set to false Focus and Mouse should be disable on that Connector
		if (isSelectable()==true&&selectablestatus==false)
		{
			this.isSelectable=false;
			this.setFocusable(false);
			this.removeMouseListener(this);
			this.removeMouseMotionListener(this);
		}
		
		//else should be turn them back
		if (isSelectable()==false&&selectablestatus==true)
		{
			this.isSelectable=true;
			this.setFocusable(true);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
	}
	
	@Override
	public boolean isTSelectable()
	{
		return isTSelectable;
	}
	
	
	protected void setTSelectable(boolean isTSelectable)
	{
		//disabled TSelected for Connector
		//this.isTSelectable=isTSelectable;
	}
	
	@Override
	public Point getCenterPoint()
	{
		return new Point(-1, -1);
	}
	
	
}// End of Class
