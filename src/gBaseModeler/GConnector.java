package gBaseModeler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GConnector extends GBaseConnectorAdapter
{
	private static final long	serialVersionUID	=3L;
	
	private GElement			_sourceElement;						// a variable to save Source Element
	private GElement			_targetElement;						// a variable to save Target Element
																		
	// Connectors can be Pinned to a Point in Source Element or Target Element
	// Pin or Unpin and the Point if Pin are saved by 4 following Elements
	private boolean				isSourcePined		=false;
	private boolean				isTargetPined		=false;
	private Point				_sourceElementPP	=new Point(-1, -1);
	private Point				_targetElementPP	=new Point(-1, -1);
	
	// little complex
	// to save user chosen appropriate point into Element, this point used for something like Pin
	// but it is dynamically changes
	// default origin is center of Element, so by changing the Source or Target Connection Point, origins change
	private Point				_SourcePoint_origin;
	private Point				_TargetPoint_origin;
	
	private Point				CPoints[];								// array of all points of Connection
	private int					cCPoints;
	
	private int					DashedLevel			=0;
	private int					ThicknessLevel		=1;
	private int					sourceArrowType		=0;
	private int					targetArrowType		=0;
	
	private static final int	resizeRectsWidth	=8;				// the size of blue Rectangles of Connection
																		
	// maximum point of Connection, this can be set by sub class
	// for example if it is set to 2, users can not create middle point by CTRL+Click	
	private int					MaxPoints			=20;
	
	// the length of width and height that application should automatically set points to align with other points
	private int					autoSetSize			=8;
	
	// to save blue rectangles, the Length of this array is controlled by CPoints.cCPoints
	private Rectangle			resizeRectangles[];
	
	// the number of point in the Connection that is moving, -1 means that it is not points moving time
	// this is Rect Moving means that user move one point of Connection, not moving a Line of Connection
	private int					iisRectsMoving		=-1;
	
	// to save pressed or typed key by the user if it is -1, means that no key pressed	
	private int					key					=-1;
	
	// the number of Line that is moving. in this time a Line (2 Points) are moving
	private int					iisConnectorMoving	=-1;
	
	private int					px					=0, py=0;			// to save previous mouse pressed point
			
	public GConnector(GElement Source, GElement Target, int DashedLevel, int ThicknessLevel, int sourceArrowType,
			int targetArrowType)
	{
		super();
		_sourceElement=Source;
		Source.addConnector(this);
		
		_targetElement=Target;
		Target.addConnector(this);
		
		this.DashedLevel=DashedLevel;
		this.ThicknessLevel=ThicknessLevel;
		this.sourceArrowType=sourceArrowType;
		this.targetArrowType=targetArrowType;
		this.setForeground(Color.black);
		
		Initialize();
		
		this.setComponentPopupMenu(new GConnectorPopupMenu(this, "Menu"));
	}
	
	
	
	private void Initialize()
	{
		CPoints=new Point[MaxPoints];
		cCPoints=0;
		
		// if source or target is pinned, Pined Points used for source and target
		// else each Connector should use the getConnectionPoint method from source and target to get Connection Point		
		if (!isSourcePined)
			CPoints[cCPoints++]=_sourceElement.getConnectionPoint(_targetElement.getCenterPoint(),
					_sourceElement.getCenterPoint());
		else
			CPoints[cCPoints++]=_sourceElementPP;
		
		
		if (!isTargetPined)
			CPoints[cCPoints++]=_targetElement.getConnectionPoint(CPoints[cCPoints-2], _targetElement.getCenterPoint());
		else
			CPoints[cCPoints++]=_targetElementPP;
		
		
		// each Connector is the same size as GDiagram, 
		// but contains method override and just around the lines of Connector is active
		this.setBounds(0, 0, GBaseSetting.DiagramWidth-1, GBaseSetting.DiagramHeight-1);
		
		_SourcePoint_origin=_sourceElement.getCenterPoint();
		_TargetPoint_origin=_targetElement.getCenterPoint();
		
		InitialResizeRectangles();
	}
	
	
	
	// when an Element Delete, elements use this method to delete their connectors
	// it is important that when a source is going to delete, all Connectors should be deleted
	// so, for each Connector, the Connector should delete from Target element too, 
	// and vise versa
	public void removeElement(GElement e)
	{
		if (e==_sourceElement)
			_targetElement.removeConnector(this);
		if (e==_targetElement)
			_sourceElement.removeConnector(this);
		this.getParent().remove(this);
		//this=null;
	}
	
	
	// this method used for deleting a Connector directly,
	public void Delete()
	{
		this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		_sourceElement.removeConnector(this);
		_targetElement.removeConnector(this);
		this.getParent().remove(this);
		//this=null;
	}
	
	
	// this method used for apply the Source pined point and disable it by -1,-1
	public void setSourceElementPinPoint(Point sourcepp)
	{
		if (sourcepp.x==-1&&sourcepp.y==-1)
		{
			isSourcePined=false;
			_sourceElementPP=new Point(-1, -1);
		}
		else
		{
			isSourcePined=true;
			_sourceElementPP=new Point(sourcepp.x+_sourceElement.getX(), sourcepp.y+_sourceElement.getY());
		}
	}
	
	
	
	// this method used for apply the Target pined point and disable it by -1,-1
	public void setTargetElementPinPoint(Point targetpp)
	{
		if (targetpp.x==-1&&targetpp.y==-1)
		{
			isTargetPined=false;
			_targetElementPP=new Point(-1, -1);
		}
		else
		{
			isTargetPined=true;
			_targetElementPP=new Point(targetpp.x+_targetElement.getX(), targetpp.y+_targetElement.getY());
		}
	}
	
	
	
	// after create a Connector, the Source or Target of Connector can be changed runtime
	public void setSourceElement(GElement source)
	{
		this._sourceElement.removeConnector(this);
		this._sourceElement=source;
		source.addConnector(this);
		Initialize();
		repaint();
	}
	
	
	// after create a Connector, the Source or Target of Connector can be changed runtime
	public void setTargetElement(GElement target)
	{
		this._targetElement.removeConnector(this);
		this._targetElement=target;
		target.addConnector(this);
		Initialize();
		repaint();
	}
	
	
	
	// CPoint change property EVENT
	protected void CPointsChanged()
	{
		// may be used in child classes
	}
	
	
	
	// all set in CPoint array should be done by this method
	// in this method all conditions checked
	protected void setCPoint(int i, Point pvalue)
	{
		if (i<0||i>=cCPoints) // incorrect i
			return;
		
		pvalue=autoSet2Point(i, pvalue); //Auto Set to align other points		
		
		// set to Source Point, getConnectionPoint should be called based on CPoint[1]
		// CPoint[1] may be next point or target point (if there is not any middle points)
		if (i==0)
			if (!isSourcePined) // if not Pinned
				pvalue=_sourceElement.getConnectionPoint(CPoints[1], pvalue);
			else
				pvalue=_sourceElementPP;
		
		// set to Target Point 
		if (i==cCPoints-1)
			if (!isTargetPined)
				pvalue=_targetElement.getConnectionPoint(CPoints[cCPoints-2], pvalue);
			else
				pvalue=_targetElementPP;
		
		
		// set pvalue to CPoint
		CPoints[i]=pvalue;
		reInitialResizeRectangles(i);
		
		// if set to a point after Source Point, Source Point should be changed based of CPoint[1]
		if (i==1)
		{
			if (!isSourcePined)
				CPoints[0]=_sourceElement.getConnectionPoint(CPoints[1], _SourcePoint_origin);
			else
				CPoints[0]=_sourceElementPP;
			reInitialResizeRectangles(0);
		}
		
		//if set to a point before Target Point, Target Point should be changed based of CPoint[cCpoints-2]
		// CPoint[cCpoints-1] is Target 
		if (i==cCPoints-2)
		{
			if (!isTargetPined)
				CPoints[cCPoints-1]=_targetElement.getConnectionPoint(CPoints[cCPoints-2], _TargetPoint_origin);
			else
				CPoints[cCPoints-1]=_targetElementPP;
			reInitialResizeRectangles(cCPoints-1);
		}
		
		CPointsChanged();
	}
	
	
	private void InitialResizeRectangles()
	{
		resizeRectangles=new Rectangle[cCPoints];
		
		for (int i=0; i<cCPoints; i++)
			resizeRectangles[i]=new Rectangle(CPoints[i].x-resizeRectsWidth/2, CPoints[i].y-resizeRectsWidth/2, resizeRectsWidth,
					resizeRectsWidth);
	}
	
	
	private void reInitialResizeRectangles(int i)
	{
		resizeRectangles[i]=new Rectangle(CPoints[i].x-resizeRectsWidth/2, CPoints[i].y-resizeRectsWidth/2, resizeRectsWidth,
				resizeRectsWidth);
	}
	
	
	// draw Connector based on given configurations in Constructor or other setter methods
	// this method can be override, but drawing a Connector is unique and it is better to use this method
	protected void drawElement(Graphics g)
	{
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setStroke(new BasicStroke(1));
		
		// set Lines configs
		if (DashedLevel>0&&DashedLevel<=10&&ThicknessLevel>=1&&ThicknessLevel<=10)
		{
			Stroke drawingStroke=new BasicStroke(ThicknessLevel, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { DashedLevel }, 0);
			g2.setStroke(drawingStroke);
		}
		
		// draw Lines
		g.setColor(this.getForeground());
		for (int i=0; i<cCPoints-1; i++)
			g.drawLine(CPoints[i].x, CPoints[i].y, CPoints[i+1].x, CPoints[i+1].y);
		
		// Draw Arrows
		g2.setStroke(new BasicStroke(1));
		GConnectorArrows.DrawConnectorArrow(sourceArrowType, g, CPoints[0].x, CPoints[0].y,
				GMath.getAngle(CPoints[1], CPoints[0]));
		GConnectorArrows.DrawConnectorArrow(targetArrowType, g, CPoints[cCPoints-1].x, CPoints[cCPoints-1].y,
				GMath.getAngle(CPoints[cCPoints-2], CPoints[cCPoints-1]));
		
	}
	
	
	// in draw selected just draw blue rectangle on CPoints of Connector
	// if it is locked, draw black rectangles
	protected void drawSelected(Graphics g)
	{
		if (hasFocus()) // no need, it will check on paintComponent of parent
		{
			super.drawSelected(g);
			if (isLock())
				g.setColor(Color.black);
			else
				g.setColor(Color.blue);
			
			// draw rectangles on CPoints
			for (int i=0; i<cCPoints; i++)
				g.fillRect(resizeRectangles[i].x, resizeRectangles[i].y, resizeRectangles[i].width, resizeRectangles[i].height);
		}
	}
	
	
	// because the size of Connectors is the same size as GDiagram, contains method should be override
	// in the near of Lines of Connector (6 pixels) the connector is active, else inactive
	@Override
	public boolean contains(int x, int y)
	{
		int d=0;
		for (int i=0; i<cCPoints-1; i++)
		{
			d=GMath.getPointLineDistance(x, y, CPoints[i].x, CPoints[i].y, CPoints[i+1].x, CPoints[i+1].y);
			
			if (d<=15)
				if (((x>=CPoints[i].x-4&&x<=CPoints[i+1].x+4)||(x>=CPoints[i+1].x-4&&x<=CPoints[i].x+4))
						&&((y>=CPoints[i].y-4&&y<=CPoints[i+1].y+4)||(y>=CPoints[i+1].y-4&&y<=CPoints[i].y+4)))
					return true;
			
			// the second check used for contains around blue rectangles
			// more area is active around blue rectangles
		}
		return false;
	}
	
	
	
	//Auto set in near of other points align
	private Point autoSet2Point(int iCPoint, Point p)
	{
		int x=p.x;
		int y=p.y;
		if (iCPoint>=1&&Math.abs(x-CPoints[iCPoint-1].x)<autoSetSize)
			x=CPoints[iCPoint-1].x;
		
		if (iCPoint>=1&&Math.abs(y-CPoints[iCPoint-1].y)<autoSetSize)
			y=CPoints[iCPoint-1].y;
		
		if (iCPoint<cCPoints-1&&Math.abs(x-CPoints[iCPoint+1].x)<autoSetSize)
			x=CPoints[iCPoint+1].x;
		
		if (iCPoint<cCPoints-1&&Math.abs(y-CPoints[iCPoint+1].y)<autoSetSize)
			y=CPoints[iCPoint+1].y;
		
		return new Point(x, y);
	}
	
	
	public void mouseDragged(MouseEvent e)
	{
		if (isLock())
			return;
		
		// Rects are moving
		if (iisRectsMoving!=-1)
		{
			setCPoint(iisRectsMoving, new Point(e.getX(), e.getY()));
			
			// in Source and Target points moving, origins change
			if (iisRectsMoving==0)
				_SourcePoint_origin=CPoints[0];
			else if (iisRectsMoving==cCPoints-1)
				_TargetPoint_origin=CPoints[cCPoints-1];
			
			repaint();
			return;
		}
		
		
		////// Moving a line of Connector
		if (iisConnectorMoving!=-1)
		{
			// in moving a Line, auto size points stop move simply
			// so before it, auto size disables and after in enabled again
			autoSetSize=1;
			moveConnector(e);
			autoSetSize=8;
			px=e.getX();
			py=e.getY();
			return;
		}
		
	}// End of Mouse Drag
	
	
	
	// little complex and more conditions
	// this method is used for moving Lines of Connectors and check conditions
	// for example priority of setPoints is important and in source and target, origins should change
	// may be optimize later...
	protected void moveConnector(MouseEvent e)
	{
		if (isLock())
			return;
		
		int i=iisConnectorMoving; // the number of Line that is moving
		
		if (i>0&&i<cCPoints-2) // just in middle points (line do not include source or target points)
		{
			setCPoint(i, new Point(CPoints[i].x+(e.getX()-px), CPoints[i].y+(e.getY()-py)));
			setCPoint(i+1, new Point(CPoints[i+1].x+(e.getX()-px), CPoints[i+1].y+(e.getY()-py)));
		}
		else if (i==0&&i==cCPoints-2) // we have just source and target points (cCpoints=2 and there is just one line)
		{
			setCPoint(i+1, new Point(CPoints[i+1].x+(e.getX()-px), CPoints[i+1].y+(e.getY()-py)));
			_TargetPoint_origin=CPoints[cCPoints-1];
			
			setCPoint(i, new Point(CPoints[i].x+(e.getX()-px), CPoints[i].y+(e.getY()-py)));
			_SourcePoint_origin=CPoints[0];
		}
		else if (i==0&&i!=cCPoints-2) // in source point and cCpoint>2 (line just include source point)
		{
			setCPoint(i+1, new Point(CPoints[i+1].x+(e.getX()-px), CPoints[i+1].y+(e.getY()-py)));
			setCPoint(i, new Point(CPoints[i].x+(e.getX()-px), CPoints[i].y+(e.getY()-py)));
			_SourcePoint_origin=CPoints[0];
		}
		else if (i!=0&&i==cCPoints-2) // in target point and cCpoint>2 (line just include target point)
		{
			setCPoint(i, new Point(CPoints[i].x+(e.getX()-px), CPoints[i].y+(e.getY()-py)));
			setCPoint(i+1, new Point(CPoints[i+1].x+(e.getX()-px), CPoints[i+1].y+(e.getY()-py)));
			_TargetPoint_origin=CPoints[cCPoints-1];
		}
		repaint();
	}
	
	
	public void mouseReleased(MouseEvent e)
	{
		// set any thing to default state
		
		super.mouseReleased(e);
		moveConnector(e); // because auto set disabled and enabled, in mouse release this method correct aligns and apply auto set
		
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		iisRectsMoving=-1;
		iisConnectorMoving=-1;
		key=-1;
	}
	
	
	// when Source and Target Element are moving together, GDiagram recognize this action
	// and then by this method, moves all middle points too
	public void ChangeAllMidPointinTSelMove(int dx, int dy)
	{
		for (int i=1; i<=cCPoints-2; i++)
			setCPoint(i, new Point(CPoints[i].x-dx, CPoints[i].y-dy));
	}
	
	
	// this method is used by Elements of Connector to inform Element changes in Location and Size
	// so Connector should follow the Element and change it's CPoints
	public void ChangeElementsSizeLocation(GElement e, int dx, int dy)
	{
		if (e==_sourceElement)
		{
			_SourcePoint_origin=new Point(_SourcePoint_origin.x-dx, _SourcePoint_origin.y-dy);
			_sourceElementPP=new Point(_sourceElementPP.x-dx, _sourceElementPP.y-dy);
			setCPoint(0, _SourcePoint_origin);
			repaint();
		}
		
		if (e==_targetElement)
		{
			_TargetPoint_origin=new Point(_TargetPoint_origin.x-dx, _TargetPoint_origin.y-dy);
			_targetElementPP=new Point(_targetElementPP.x-dx, _targetElementPP.y-dy);
			setCPoint(cCPoints-1, _TargetPoint_origin);
			repaint();
		}
	}
	
	
	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		
		if (e.isPopupTrigger()) // for menu
			return;
		
		
		// Delete a middle point by CTRL+Click on the middle point
		if (!isLock()&&cCPoints>2&&key==KeyEvent.VK_CONTROL)
		{
			for (int i=1; i<cCPoints-1; i++)
				if (resizeRectangles[i].contains(e.getX(), e.getY()))
				{
					for (int j=i; j<cCPoints-1; j++)
						CPoints[j]=CPoints[j+1];
					cCPoints--;
					InitialResizeRectangles();
					
					CPointsChanged();
					repaint();
				}
		}
		
		
		// Create a new middle point by CTRL+Click on any line of Connector
		if (!isLock()&&cCPoints<MaxPoints&&key==KeyEvent.VK_CONTROL)
		{
			// do not create on start point and end point
			if (resizeRectangles[0].contains(e.getX(), e.getY())||resizeRectangles[cCPoints-1].contains(e.getX(), e.getY()))
				return;
			
			
			// insert new middle point
			// Find a good place to insert (nearest line)
			int d=0;
			for (int i=0; i<cCPoints-1; i++)
			{
				d=GMath.getPointLineDistance(e.getX(), e.getY(), CPoints[i].x, CPoints[i].y, CPoints[i+1].x, CPoints[i+1].y);
				if (d<=4)
				{
					for (int j=cCPoints-1; j>i; j--)
						CPoints[j+1]=CPoints[j];
					CPoints[i+1]=new Point(e.getX(), e.getY());
					cCPoints++;
					InitialResizeRectangles();
					CPointsChanged();
					repaint();
					break;
				}
			}
		}
		
		
		// Identify dragging blue Rects, 
		if (iisRectsMoving==-1&&iisConnectorMoving==-1)
			for (int i=0; i<=cCPoints-1; i++)
				if (resizeRectangles[i].contains(e.getX(), e.getY()))
					iisRectsMoving=i;
		if (iisRectsMoving!=-1)
		{
			this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
		
		
		
		///  identifying the Line to Move
		/// if it is not RectMoving time 
		if (iisRectsMoving==-1&&iisConnectorMoving==-1)
		{
			int d;
			for (int i=0; i<cCPoints-1; i++)
			{
				d=GMath.getPointLineDistance(e.getX(), e.getY(), CPoints[i].x, CPoints[i].y, CPoints[i+1].x, CPoints[i+1].y);
				if (d<=4)
				{
					iisConnectorMoving=i;
					break;
				}
			}
		}
		if (iisRectsMoving==-1&&iisConnectorMoving!=-1)
		{
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			this.getParent().setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
		
		
		px=e.getX();
		py=e.getY();
	}// End of Mouse Pressed
	
	
	public Point getCenterPoint()
	{
		int x1=CPoints[cCPoints/2-1].x;
		int y1=CPoints[cCPoints/2-1].y;
		int x2=CPoints[cCPoints/2].x;;
		int y2=CPoints[cCPoints/2].y;;
		
		return new Point((x1+x2)/2, (y1+y2)/2);
		
	}
	
	
	public GElement getSourceElement()
	{
		return _sourceElement;
	}
	
	
	public GElement getTargetElement()
	{
		return _targetElement;
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
	
	
	
	//
	//
	//
	//
	
	
	
	public int getDashedLevel()
	{
		return DashedLevel;
	}
	
	public int getThicknessLevel()
	{
		return ThicknessLevel;
	}
	
	public int getSourceArrowType()
	{
		return sourceArrowType;
	}
	
	public int getTargetArrowType()
	{
		return targetArrowType;
	}
	
	public static int getResizerectswidth()
	{
		return resizeRectsWidth;
	}
	
	public int getMaxpoints()
	{
		return MaxPoints;
	}
	
	protected void setMaxpoints(int PointsNo)
	{
		if (PointsNo<2)
			this.MaxPoints=2; // minimum number of Points
		else
			this.MaxPoints=PointsNo;
	}
	
	public int getAutoSetSize()
	{
		return autoSetSize;
	}
	
	protected void setDashedLevel(int dashedLevel)
	{
		DashedLevel=dashedLevel;
	}
	
	protected void setThicknessLevel(int thicknessLevel)
	{
		ThicknessLevel=thicknessLevel;
	}
	
	protected void setSourceArrowType(int sourceArrowType)
	{
		this.sourceArrowType=sourceArrowType;
	}
	
	protected void setTargetArrowType(int targetArrowType)
	{
		this.targetArrowType=targetArrowType;
	}
	
	protected void setAutoSetSize(int autoSetSize)
	{
		this.autoSetSize=autoSetSize;
	}
	
	public int getiisRectsMoving()
	{
		return iisRectsMoving;
	}
	
	public int getiisConnectorMoving()
	{
		return iisConnectorMoving;
	}
	
	public int getKey()
	{
		return key;
	}
	
	protected Point[] getCPoints()
	{
		return CPoints;
	}
	
	public int getcCPoints()
	{
		return cCPoints;
	}
	
	protected Rectangle[] getResizeRectangles()
	{
		return resizeRectangles;
	}
	
	public boolean isSourcePined()
	{
		return isSourcePined;
	}
	
	public boolean isTargetPined()
	{
		return isTargetPined;
	}
	
	
}// End of Class
