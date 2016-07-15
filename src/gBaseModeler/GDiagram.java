package gBaseModeler;

import gModelerUI.GRelationHelper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class GDiagram extends GBaseDiagramAdapter
{
	
	private static final long		serialVersionUID	=3L;
	
	private String					DiagramName			="";
	
	private int						px					=-1, py=-1;					// to save mouse pressed x, y 
			
	// list of Total Selected Elements by the mouse on diagram
	private List<GGElement>			Tsels				=new ArrayList<GGElement>();
	
	// the rectangle that draw on mouse pressed and dragging time on the Diagram
	private Rectangle				TselsRect			=new Rectangle();
	
	// object of align helper that draws align helpers
	private GElementsAlignHelper	alignHelper;
	
	private Point					helpPoint1			=new Point(-1, -1);
	private Point					helpPoint2			=new Point(-1, -1);
	
	
	public GDiagram(String DiagramName)
	{
		super();
		this.DiagramName=DiagramName;
		
		alignHelper=new GElementsAlignHelper();
		this.add(alignHelper);
	}
	
	public void AlignHelper(GElement e)
	{
		alignHelper.getAlignHelp(this, e); // call from align helper
	}
	
	public void setAlignHelperNull()
	{
		alignHelper.setNull(); // call from align helper
	}
	
	
	public void mouseMoved(MouseEvent e)
	{
		
		// this section helps in creating new Relationship and draw help line between source and mouse e
		if (GRelationHelper.isSourceTargetSelectionTime())
		{
			if (GRelationHelper.getSource()!=null&&GRelationHelper.getTarget()==null)
			{
				helpPoint1=GRelationHelper.getSource().getConnectionPoint(new Point(e.getX(), e.getY()),
						GRelationHelper.getSource().getCenterPoint());
				helpPoint2=new Point(e.getX(), e.getY());
				repaint();
			}
		}
		else
		{
			if (helpPoint1.x!=-1)
			{
				ignoreHelpPoints();
				repaint();
			}
		}
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw Background
		g.setColor(GBaseSetting.DiagramsBackground);
		g2.setPaint(new GradientPaint(0, 0, GBaseSetting.DiagramsBackground, 1000, 800, Color.WHITE));
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		
		if (GBaseSetting.showGrid)
			DrawGrid(g);
		
		// Draw Border
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		
		g.drawString(DiagramName, 3, 12);
		
		
		// draw Total selection rectangle
		Stroke drawingStroke=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
		g2.setStroke(drawingStroke);
		g.setColor(new Color(50, 50, 255));
		if (px!=-1&&py!=-1)
			g.drawRect(TselsRect.x, TselsRect.y, TselsRect.width, TselsRect.height);
		
		
		// draw Help Points Line
		g.setColor(Color.black);
		drawingStroke=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2 }, 0);
		g2.setStroke(drawingStroke);
		if (helpPoint1.x!=-1)
			g.drawLine(helpPoint1.x, helpPoint1.y, helpPoint2.x, helpPoint2.y);
		
	}
	
	protected void DrawGrid(Graphics g)
	{
		g.setColor(GBaseSetting.gridColor);
		for (int i=0; i<getWidth(); i+=GBaseSetting.gridStep)
			g.drawLine(i, 0, i, getHeight());
		for (int i=0; i<getHeight(); i+=GBaseSetting.gridStep)
			g.drawLine(0, i, getWidth(), i);
	}
	
	
	// total selected elements move
	// in this method all elements of total selected move with together
	// each element should call this method on it's dragging if it was Total selected
	public void TChangeLocation(GElement callerel, int dx, int dy)
	{
		for (GGElement el:Tsels)
			if (el!=callerel&&el.isTSelectable())
			{
				el.setAbsoluteChange(true);
				el.setLocation(el.getX()-dx, el.getY()-dy);
				el.setAbsoluteChange(false);
			}
		
		
		// When Source and Target Element of any connector are moving together 
		// this section recognize that, and then wants from each connector to move middle points too
		for (int i=0; i<this.getComponentCount(); i++)
			if (this.getComponent(i) instanceof GConnector)
			{
				GConnector c=(GConnector) this.getComponent(i);
				if (c.getSourceElement().isTSelected()&&c.getTargetElement().isTSelected())
				{
					c.ChangeAllMidPointinTSelMove(dx, dy);
				}
			}
		
	}
	
	
	
	public void mousePressed(MouseEvent e)
	{
		requestFocus();
		
		GRelationHelper.setSourceTargetSelectionTime(false);
		
		
		// total selection state of total selected elements clear
		TselsRect=new Rectangle();
		for (GElement el:Tsels)
		{
			el.setTSelected(false);
			el.repaint();
		}
		// list of total selected elements clear too
		Tsels.clear();
		
		px=e.getX();
		py=e.getY();
	}
	
	public void mouseDragged(MouseEvent e)
	{
		int x=Math.min(px, e.getX());
		int y=Math.min(py, e.getY());
		
		//set total selection rectangle and then draw it by repaint
		TselsRect.setBounds(x, y, Math.abs(px-e.getX()), Math.abs(py-e.getY()));
		repaint();
	}
	
	public void mouseReleased(MouseEvent e)
	{
		px=-1;
		py=-1;
		
		//identify the elements that are in the TSelection Rectangle
		// than save them in Tsel, and in each element 
		for (int i=0; i<this.getComponentCount(); i++)
			if (this.getComponent(i) instanceof GGElement)
			{
				GGElement el=(GGElement) this.getComponent(i);
				if (TselsRect.contains(el.getCenterPoint())&&el.isTSelectable()&&el.getFather()==null)
				{
					Tsels.add(el);
					el.setTSelected(true);
				}
			}
		
		// by repaint all elements in TSelection Rectangle being TSelected, with black rectangles
		// and the TSelection Rectangle removes
		repaint();
	}
	
	public void drawHelpPoint(Point p1, Point p2)
	{
		helpPoint1=p1;
		helpPoint2=p2;
	}
	
	public void ignoreHelpPoints()
	{
		helpPoint1=new Point(-1, -1);
		helpPoint2=new Point(-1, -1);
	}
	
	
} // End of Class
