package gBaseModeler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public abstract class GBaseElementAdapter extends JComponent implements GBaseElementInterface
{
	private static final long	serialVersionUID	=3L;
	
	private float				zoomFactor			=1;
	private boolean				isLock				=false;
	private boolean				isSelectable		=true;
	private boolean				isMoveable			=true;
	private boolean				isResizeable		=true;
	
	private boolean				isTSelected			=false;
	private boolean				isTSelectable		=true;
	
	public GBaseElementAdapter()
	{
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addFocusListener(this);
		
		this.setLayout(null);
		this.setEnabled(true);
	}
	
	public void Delete()
	{
		
	}
	
	public int getMinWidth()
	{
		return 10;
	}
	
	public int getMaxWidth()
	{
		return 300;
	}
	
	public int getMinHeight()
	{
		return 10;
	}
	
	public int getMaxHeight()
	{
		return 300;
	}
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawElement(g);
		
		if (hasFocus())
			drawSelected(g);
		
		if (isTSelected())
			drawTSelected(g);
	}
	
	protected void drawTSelected(Graphics g)
	{
		
	}
	
	protected void drawElement(Graphics g)
	{
		
	}
	
	protected void drawSelected(Graphics g)
	{
		// the red border of any elements when in selected time
		g.setColor(Color.red);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final float dash1[]= { 5.0f };
		final BasicStroke dashed=new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
		g2.setStroke(dashed);
		g2.draw(new Rectangle2D.Double(1, 1, this.getWidth()-2, this.getHeight()-2));
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
	public void mouseClicked(MouseEvent arg0)
	{
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0)
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
	public void focusGained(FocusEvent fe)
	{
		repaint();
	}
	
	@Override
	public void focusLost(FocusEvent fe)
	{
		repaint();
	}
	
	@Override
	public Point getConnectionPoint(Point AA)
	{
		// it is hard to write 
		// I will explain them in the class
		
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
		
		
		if (x<=x1&&y<=y1)//0
			return new Point(x1, y1);
		else if (y<=y1&&x>=x1&&x<=x2)//1
			return new Point(x1+(x2-x1)/2, y1);
		else if (x>=x2&&y<=y1)//2
			return new Point(x2, y1);
		else if (x>=x2&&y>=y1&&y<=y2)//3
			return new Point(x2, y1+(y2-y1)/2);
		else if (x>=x2&&y>=y2)//4
			return new Point(x2, y2);
		else if (x>=x1&&x<=x2&&y>=y2)//5
			return new Point(x1+(x2-x1)/2, y2);
		else if (x<=x1&&y>=y2)//6
			return new Point(x1, y2);
		else if (x<=x1&&y>=y1&&y<=y2)//7
			return new Point(x1, y1+(y2-y1)/2);
		
		
		int d1=GMath.getPointLineDistance(x, y, x1, y1, x2, y1);
		int d2=GMath.getPointLineDistance(x, y, x2, y1, x2, y2);
		int d3=GMath.getPointLineDistance(x, y, x1, y2, x2, y2);
		int d4=GMath.getPointLineDistance(x, y, x1, y1, x1, y2);
		
		int mind=Math.min(d1, Math.min(d2, Math.min(d3, d4)));
		if (mind==d1)
			return new Point(x, y1);
		if (mind==d2)
			return new Point(x2, y);
		if (mind==d3)
			return new Point(x, y2);
		if (mind==d4)
			return new Point(x1, y);
		
		return new Point(x1, y1);
	}
	
	@Override
	public Point getCenterPoint()
	{
		int x1=this.getX();
		int y1=this.getY();
		int x2=x1+getWidth();
		int y2=y1+getHeight();
		
		return new Point(x1+(x2-x1)/2, y1+(y2-y1)/2);
	}
	
	@Override
	public void addConnector(GConnector newConn)
	{
		
	}
	
	@Override
	public void removeConnector(GConnector oldConn)
	{
		
	}
	
	@Override
	public boolean ResizeTo(int newWidth, int newHeight)
	{
		boolean flag=false;
		int w=this.getWidth();
		int h=this.getHeight();
		
		// firstly the Min and Max of inputs should be checked
		if (newWidth>=getMinWidth()&&newWidth<=getMaxWidth())
		{
			w=newWidth;
			flag=true;
		}
		if (newHeight>=getMinHeight()&&newHeight<=getMaxHeight())
		{
			h=newHeight;
			flag=true;
		}
		
		if (flag)
		{
			this.setSize(w, h);
			return true;
		}
		return false;
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
	
	
	
	// to be public is WRONG !!! or not ???
	public void setSelectable(boolean selectablestatus)
	{
		// if selectable set to false Focus and Mouse should be disable on that Element
		if (isSelectable()==true&&selectablestatus==false)
		{
			this.isSelectable=false;
			//this.setFocusable(false);
			//this.removeMouseListener(this);
			//this.removeMouseMotionListener(this);
			//this.setComponentPopupMenu(null);
		}
		
		//else should be turn them back
		if (isSelectable()==false&&selectablestatus==true)
		{
			this.isSelectable=true;
			//this.setFocusable(true);
			//this.addMouseListener(this);
			//this.addMouseMotionListener(this);
		}
	}
	
	@Override
	public boolean isMovable()
	{
		return isMoveable;
	}
	
	
	protected void setMovable(boolean Moveablestatus)
	{
		this.isMoveable=Moveablestatus;
	}
	
	@Override
	public boolean isResizable()
	{
		return isResizeable;
	}
	
	
	protected void setResizable(boolean Resizablestatus)
	{
		this.isResizeable=Resizablestatus;
	}
	
	@Override
	public boolean isTSelected()
	{
		return isTSelected;
	}
	
	
	protected void setTSelected(boolean isTSelected)
	{
		this.isTSelected=isTSelected;
	}
	
	@Override
	public boolean isTSelectable()
	{
		return isTSelectable;
	}
	
	
	protected void setTSelectable(boolean isTSelectable)
	{
		this.isTSelectable=isTSelectable;
	}
	
}// End of class GBaseElementAdapter
