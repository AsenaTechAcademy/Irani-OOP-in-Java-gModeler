package gBaseModeler;

import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class GPinableLabel extends GGElement
{
	private static final long		serialVersionUID	=3L;
	
	private GBaseModelerInterface	parent;
	private int						pX, pY;					// previous x, y of parent (in the real location)
			
	private int						pinX, pinY;				// the pinned x, y of parent (in the real location) 
	private JLabel					label;						// text of label
																
																
	public GPinableLabel(String text, GBaseModelerInterface parent, int PinX, int PinY)
	{
		super(0, 0, 10, 10); // in the following this settings will change
		
		
		this.parent=parent;
		pX=pXY().x;
		pY=pXY().y;
		this.pinX=PinX;
		this.pinY=PinY;
		
		//this.setLocation(pX, pY-20);
		this.setLocation(pinX, pinY);
		
		label=new JLabel("<html><p align=center>"+text+"</p></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.TOP);
		
		this.setResizerectswidth(5);
		this.setComponentPopupMenu(null);
		
		this.setLayout(null);
		this.setSize(label.getPreferredSize().width+5, label.getPreferredSize().height+5);
		label.setBounds(2, 2, getWidth()-3, getHeight()-3);
		this.add(label);
		
		InitialResizeRectangles();
		this.setTSelectable(false);
		
		this.setFatherable(false);
		this.setChildable(false);
	}
	
	
	
	//returns a point of parent that can be an Element or a Connection
	private Point pXY()
	{
		if (parent instanceof GElement)
		{
			GElement temp=(GElement) parent;
			return new Point((int) temp.getX(), (int) temp.getY());
		}
		if (parent instanceof GConnector)
		{
			GConnector temp=(GConnector) parent;
			return new Point((int) temp.getX(), (int) temp.getY());
		}
		return new Point(-1, -1);
	}
	
	
	// the inputs are the new x, y not dx, dy
	// px, py have been used here to calculate dx, dx
	public void setChangeElementLocation(int newX, int newY)
	{
		this.setLocation(getX()+(newX-pX), getY()+(newY-pY));
		pinX=pinX+(newX-pX);
		pinY=pinY+(newY-pY);
		pX=newX;
		pY=newY;
	}
	
	
	// new pined points are inputs, not dx, dy
	// so pinX and pinY have been used here
	public void setChangePins(int newPinx, int newPiny)
	{
		this.setLocation(getX()+newPinx-pinX, getY()+newPiny-pinY);
		
		pinX=newPinx;
		pinY=newPiny;
	}
	
	
	// to set location of Label
	public void setSize(int x, int y)
	{
		super.setSize(x, y);
		label.setBounds(2, 2, getWidth()-3, getHeight()-3);
		InitialResizeRectangles();
	}
	
	
	
	public void focusGained(FocusEvent fe)
	{
		super.focusGained(fe);
		
		// we draw the pin line by the help of GDiagram
		((GDiagram) this.getParent()).drawHelpPoint(new Point(pinX, pinY), this.getConnectionPoint(new Point(pinX, pinY)));
	}
	
	
	
	public void mouseDragged(MouseEvent e)
	{
		super.mouseDragged(e);
		
		// we draw the pin line by the help of GDiagram
		((GDiagram) this.getParent()).drawHelpPoint(new Point(pinX, pinY), this.getConnectionPoint(new Point(pinX, pinY)));
	}
	
	
	
	public void focusLost(FocusEvent fe)
	{
		super.focusLost(fe);
		
		// Erase all lines
		((GDiagram) this.getParent()).ignoreHelpPoints();
	}
	
	
	//
	//
	//
	//
	//
	
	
	public int getMinWidth()
	{
		return 10;
		//return label.getPreferredSize().width;
	}
	
	public int getMaxWidth()
	{
		return 250;
	}
	
	public int getMinHeight()
	{
		return 10;
		//return label.getPreferredSize().height;
	}
	
	public int getMaxHeight()
	{
		return 250;
	}
	
	public void setText(String text)
	{
		label.setText(text);
		this.setSize(label.getPreferredSize().width+5, label.getPreferredSize().height+5);
	}
	
	public String getText()
	{
		return label.getText();
	}
}
