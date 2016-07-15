package gBaseModeler;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public abstract class GBaseDiagramAdapter extends JComponent implements GBaseModelerInterface
{
	private static final long	serialVersionUID	=3L;
	private float				zoomFactor			=1;
	private boolean				isLock				=false;
	private boolean				isSelectable		=true;
	private boolean				isTSelectable		=false;
	
	
	public GBaseDiagramAdapter()
	{
		// super();
		this.setPreferredSize(new Dimension(GBaseSetting.DiagramWidth, GBaseSetting.DiagramHeight));
		//this.setBounds(0, 0, GBaseSetting.DiagramWidth, GBaseSetting.DiagramHeight);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addFocusListener(this);
		
		this.setEnabled(true);
		this.setLayout(null);
	}
	
	@Override
	public void Delete()
	{
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
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
	public void focusGained(FocusEvent e)
	{
		
	}
	
	@Override
	public void focusLost(FocusEvent e)
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
	
	protected void drawElement(Graphics g)
	{
		
	}
	
	protected void drawSelected(Graphics g)
	{
		
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
		this.isSelectable=selectablestatus;
	}
	
	@Override
	public boolean isTSelectable()
	{
		return isTSelectable;
	}
	
	protected void setTSelectable(boolean isTSelectable)
	{
		//this.isTSelectable=isTSelectable;
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
	
	
}// End of Class
