package gBaseModeler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GElement extends GBaseElementAdapter
{
	
	private static final long	serialVersionUID	=3L;
	
	private int					maxConnectors		=200;
	
	// to save pressed or typed key by the user if it is -1, means that no key pressed	
	private int					key					=-1;
	
	private int					px					=0, py=0;						// to save previous mouse pressed point
	private int					drx					=0, dry=0;						// it is same as px, py, but it is used with Shift+resizing
			
	// the number of Resizing Rectangle around an Element that is used for resizing the Element
	// -1 means that it is not resizing time, this value of iisResizing is can be -1,0,1,2,3,4,5,6,7
	private int					iisResizing			=-1;
	private boolean				isMoving			=false;						// to save the moving of any Element
	private Rectangle			resizeRectangles[];								// the Rectangles around an Element in selected time
																					
	// the array of connectors related to this Element
	private GConnector			Connectors[]		=new GConnector[maxConnectors];
	private int					cConnectors			=0;							// lenght of Connectors Array
	private int					resizeRectsWidth	=6;							// the width and height of blue rectangle around an Element in selected time
	private boolean				isAbsoluteChange	=false;						// to do not call TSelection
																					
	public GElement(int x, int y, int width, int height)
	{
		super();
		resizeRectangles=new Rectangle[8];
		if (x<0)
			x=0;
		if (y<0)
			y=0;
		setBounds(x, y, width, height);
		Initialize();
		
		this.setComponentPopupMenu(new GElementPopupMenu(this, "Menu"));
	}
	
	private void Initialize()
	{
		InitialResizeRectangles();
	}
	
	
	
	// is used for Delete an Element, this time all Connectors related to this element should be removed
	// notice that Connector.Delete is little different from Connector.removeElement 
	public void Delete()
	{
		for (int i=0; i<cConnectors; i++)
			Connectors[i].removeElement(this);
		
		this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.getParent().remove(this);
		
		// this=null;
		// if all variables that linked to an element set to null, the Element remove from memory by gc
	}
	
	
	
	public void addConnector(GConnector newConn)
	{
		Connectors[cConnectors++]=newConn;
	}
	
	
	// to remove any connector from an Element, if exists
	public void removeConnector(GConnector oldConn)
	{
		for (int i=0; i<cConnectors; i++)
		{
			if (Connectors[i]==oldConn)
			{
				Connectors[i]=Connectors[cConnectors-1]; // last Element is moved to deleted Element in array
				cConnectors--;// the length of array --
			}
		}
	}
	
	
	
	protected void InitialResizeRectangles()
	{
		// resize rectangles
		int ppx=0;
		int ppy=0;
		int w=this.getWidth();
		int h=this.getHeight();
		
		int ww=resizeRectsWidth;
		
		//  0------1------2
		//  |             |
		//  |             |
		//  7             3
		//  |             |
		//  |             |
		//  6------5------4
		
		resizeRectangles[0]=new Rectangle(ppx, ppy, ww, ww);
		resizeRectangles[1]=new Rectangle(ppx+w/2-ww/2, ppy, ww, ww);
		resizeRectangles[2]=new Rectangle(ppx+w-ww, ppy, ww, ww);
		resizeRectangles[3]=new Rectangle(ppx+w-ww, ppy+h/2-ww/2, ww, ww);
		resizeRectangles[4]=new Rectangle(ppx+w-ww, ppy+h-ww, ww, ww);
		resizeRectangles[5]=new Rectangle(ppx+w/2-ww/2, ppy+h-ww, ww, ww);
		resizeRectangles[6]=new Rectangle(ppx, ppy+h-ww, ww, ww);
		resizeRectangles[7]=new Rectangle(ppx, ppy+h/2-ww/2, ww, ww);
	}
	
	
	// override the setLocation of parent, then before setLocation we can check any condition on x,y
	public void setLocation(int x, int y)
	{
		if (isLock()||!isMovable())
			return;
		
		if (x<0)
			x=0;
		if (y<0)
			y=0;
		
		int xx=getX();
		int yy=getY();
		super.setLocation(x, y);
		
		// to inform all connectors of this Element about Element Change Location to follow this element in Moving
		ConnectorsChange(xx-x, yy-y);
		
		// to Inform parent (GDiagram) about Element Change Location to set (if it was TSelected)
		//if (!isAbsoluteChange&&isTSelected())
		//	((GDiagram) (this.getParent())).TChangeLocation(this, xx-x, yy-y);
	}
	
	
	// override the setSize of parent, then before setSize we can check any condition on width, height
	public void setSize(int width, int height)
	{
		if (isLock()||!isResizable())
			return;
		
		int w=getWidth();
		int h=getHeight();
		super.setSize(width, height);
		
		// to inform all connectors of this Element about Element Change Size to follow this element in Resizing
		ConnectorsChange(w-width, h-height);
	}
	
	
	
	// to inform all Connectors of this Element about the Change of Location and Size of this Element
	private void ConnectorsChange(int dx, int dy)
	{
		for (int i=0; i<cConnectors; i++)
			Connectors[i].ChangeElementsSizeLocation(this, dx, dy);
	}
	
	
	// All resizing of this Element should be handle by this Method
	// it is for doing some controls before change Size
	// we can set the controls in setSize, but this is a new layer of control
	public boolean ResizeTo(int newWidth, int newHeight)
	{
		if (super.ResizeTo(newWidth, newHeight))
		{
			InitialResizeRectangles();
			//repaint();
			return true;
		}
		return false;
	}
	
	
	// i is the number of blue Rectangle that is use for resizing 
	// and the Cursor is set based on i
	//
	//  0------1------2
	//  |             |
	//  |             |
	//  7             3
	//  |             |
	//  |             |
	//  6------5------4
	//
	
	private void setCursor(int i)
	{
		if (i==0||i==4)
		{
			this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		}
		if (i==1||i==5)
		{
			this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		}
		if (i==2||i==6)
		{
			this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
		}
		if (i==3||i==7)
		{
			this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}
	}
	
	
	// basic configuration on draw each Element
	// sub classes should override this method, they can use this configuration by super.drawElement(); 
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BasicStroke stroke;
		if (getWidth()>100||getHeight()>100)
			stroke=new BasicStroke(1f); // the thickness of drawing
		else
			stroke=new BasicStroke(.9f);
		g2.setStroke(stroke);
	}
	
	
	// draw Selected of each Element, sub classes can be override this method
	// so new Elements can have new type of draw Selected
	protected void drawSelected(Graphics g)
	{
		super.drawSelected(g);
		g.setColor(Color.blue);
		if (hasFocus()&&!isLock())
			for (int i=0; i<8; i++)
				g.fillRect(resizeRectangles[i].x, resizeRectangles[i].y, resizeRectangles[i].width, resizeRectangles[i].height);
	}
	
	
	// this draw, performs in Total Selected time when user want to move more than one Element
	protected void drawTSelected(Graphics g)
	{
		super.drawTSelected(g);
		g.setColor(Color.black);
		for (int i=0; i<8; i++)
			g.fillRect(resizeRectangles[i].x, resizeRectangles[i].y, resizeRectangles[i].width, resizeRectangles[i].height);
	}
	
	
	public void mousePressed(MouseEvent e)
	{
		// for each selected element, ZOrder set to 0
		//Wrong
		//this.getParent().setComponentZOrder(this, 0);
		
		super.mousePressed(e);
		if (e.isPopupTrigger())
			return;
		
		// to save place of mouse
		px=e.getX();
		py=e.getY();
		drx=e.getX();
		dry=e.getY();
		
		// identifying the resizing of blue rectangles
		// if the element is not in moving time
		if (!isMoving&&iisResizing==-1)
			for (int i=0; i<=7; i++)
				if (resizeRectangles[i].contains(e.getX(), e.getY()))
					iisResizing=i;
		
		
		// setting the cursor
		if (iisResizing!=-1)
			setCursor(iisResizing);
		else
		{
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		// to inform GDiagram to draw align helpers
		((GDiagram) this.getParent()).AlignHelper(this);
		
		/// Resizing 8 point around the Element (blue Rectangles)
		if (iisResizing!=-1)
		{
			// if user pressed the ALT key, AutoSet to grids is disabled
			int ddx=(key==KeyEvent.VK_ALT ? 0 : e.getX()%GBaseSetting.gridStep);
			int ddy=(key==KeyEvent.VK_ALT ? 0 : e.getY()%GBaseSetting.gridStep);
			
			//  0------1------2
			//  |             |
			//  |             |
			//  7             3
			//  |             |
			//  |             |
			//  6------5------4
			
			
			if (iisResizing==3)
			{
				this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				this.getParent().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				ResizeTo(e.getX()+ddx, this.getHeight());
			}
			else if (iisResizing==4)
			{
				this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				this.getParent().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				ResizeTo(e.getX()+ddx, e.getY()+ddy);
			}
			else if (iisResizing==5)
			{
				this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				this.getParent().setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				ResizeTo(this.getWidth(), e.getY()+ddy);
			}
			
			// other ifs (0,1,2,6,7) delegated to students 
			
			return;
		}
		
		
		// if it is not resizing time by blue rectangles
		// check the resizing by SHIFT Drag
		this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		this.getParent().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		// Zoom in X
		if (key==KeyEvent.VK_SHIFT&&drx>e.getX())
			ResizeTo(this.getWidth()-1, this.getHeight());
		// Zoom in Y
		if (key==KeyEvent.VK_SHIFT&&dry>e.getY())
			ResizeTo(this.getWidth(), this.getHeight()-1);
		// Zoom out X
		if (key==KeyEvent.VK_SHIFT&&drx<e.getX())
			ResizeTo(this.getWidth()+1, this.getHeight());
		// Zoom out Y
		if (key==KeyEvent.VK_SHIFT&&dry<e.getY())
			ResizeTo(this.getWidth(), this.getHeight()+1);
		
		drx=e.getX();
		dry=e.getY();
		
		
		
		// else Moving the Element occurs just by mouse
		isMoving=true;
		this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		int W=this.getX()+e.getX()-px;
		int H=this.getY()+e.getY()-py;
		if (key==-1)
			this.setLocation(W-(W%GBaseSetting.gridStep), H-(H%GBaseSetting.gridStep));
		else if (key==KeyEvent.VK_ALT)
			this.setLocation(W, H);
		
	}
	
	
	
	public void mouseReleased(MouseEvent e)
	{
		// set any thing to default state
		
		super.mouseReleased(e);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		iisResizing=-1;
		isMoving=false;
		
		// inform to GDiagram to remove all align helper
		((GDiagram) this.getParent()).setAlignHelperNull();
		
	}
	
	
	// this method is little complex
	// this method is used by Connectors of this Element to find better ConnectionPoint
	// all sub classes like Condition in Activity Diagram can override this method to apply their condition on Connection Points
	public Point getConnectionPoint(Point externalPoint, Point BB)
	{
		int x=externalPoint.x;
		int y=externalPoint.y;
		
		int x1=this.getX()+1;
		int y1=this.getY()+1;
		int x2=x1+this.getWidth()-2;
		int y2=y1+this.getHeight()-2;
		
		int x3=BB.x;
		int y3=BB.y;
		
		Point p1, p2, p3, p4;
		p1=GMath.getLineLineIntersection(x1, y1, x2, y1, x, y, x3, y3);
		p2=GMath.getLineLineIntersection(x2, y1, x2, y2, x, y, x3, y3);
		p3=GMath.getLineLineIntersection(x2, y2, x1, y2, x, y, x3, y3);
		p4=GMath.getLineLineIntersection(x1, y1, x1, y2, x, y, x3, y3);
		
		
		//   0 |    1    |  2
		//  -----------------
		//     |         |
		//  7  |         |  3
		//     |         |   
		//  -----------------
		//  6  |    5    |  4
		//      
		
		if (x<=x1&&y<=y1) // 0
		{
			if (this.Gcontains(p1))
				return p1;
			if (this.Gcontains(p4))
				return p4;
		}
		else if (y<=y1&&x>=x1&&x<=x2)// 1
		{
			if (this.Gcontains(p1))
				return p1;
		}
		else if (x>=x2&&y<=y1)// 2
		{
			if (this.Gcontains(p1))
				return p1;
			if (this.Gcontains(p2))
				return p2;
		}
		else if (x>=x2&&y>=y1&&y<=y2)// 3
		{
			if (this.Gcontains(p2))
				return p2;
		}
		else if (x>=x2&&y>=y2)// 4
		{
			if (this.Gcontains(p2))
				return p2;
			if (this.Gcontains(p3))
				return p3;
		}
		else if (x>=x1&&x<=x2&&y>=y2)// 5
		{
			if (this.Gcontains(p3))
				return p3;
		}
		else if (x<=x1&&y>=y2)// 6
		{
			if (this.Gcontains(p3))
				return p3;
			if (this.Gcontains(p4))
				return p4;
		}
		else if (x<=x1&&y>=y1&&y<=y2)// 7
		{
			if (this.Gcontains(p4))
				return p4;
		}
		
		return super.getConnectionPoint(externalPoint);
		// super.getConnectionPoint is another type of getConnectionPoint, but it is not beautiful
	}
	
	
	// Gcontains used in getConnectionPoint
	private boolean Gcontains(int x, int y)
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(x, y);
	}
	
	private boolean Gcontains(Point p)
	{
		return Gcontains(p.x, p.y);
	}
	
	
	
	public void keyPressed(KeyEvent e)
	{
		key=e.getKeyCode();
	}
	
	public void keyReleased(KeyEvent e)
	{
		key=-1;
	}
	
	public void keyTyped(KeyEvent e)
	{
		key=e.getKeyCode();
	}
	
	public void focusLost(FocusEvent e)
	{
		key=-1;
		super.focusLost(e);
	}
	
	
	
	//
	//
	//
	//
	//
	//
	
	
	public boolean contains(int x, int y)
	{
		if (!isSelectable())
			return false;
		return super.contains(x, y);
	}
	
	public boolean contains(Point p)
	{
		return contains(p.x, p.y);
	}
	
	
	public int getiisResizing()
	{
		return iisResizing;
	}
	
	public int getKey()
	{
		return key;
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
	protected GConnector[] getConnectors()
	{
		return Connectors;
	}
	
	public int getcConnectors()
	{
		return cConnectors;
	}
	
	public int getResizerectswidth()
	{
		return resizeRectsWidth;
	}
	
	protected void setIisResizing(int iisResizing)
	{
		this.iisResizing=iisResizing;
	}
	
	protected void setMoving(boolean isMoving)
	{
		this.isMoving=isMoving;
	}
	
	protected void setResizerectswidth(int resizerectswidth)
	{
		resizeRectsWidth=resizerectswidth;
	}
	
	public boolean isAbsoluteChange()
	{
		return isAbsoluteChange;
	}
	
	protected void setAbsoluteChange(boolean isAbsoluteChane)
	{
		this.isAbsoluteChange=isAbsoluteChane;
	}
	
	
	
} // End of Class

