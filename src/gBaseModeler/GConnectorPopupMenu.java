package gBaseModeler;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class GConnectorPopupMenu extends JPopupMenu implements PopupMenuListener
{
	private static final long	serialVersionUID	=3L;
	
	private GConnector			el;
	
	private JMenuItem			lockMenuItem;
	private JMenuItem			pin2Source;
	private JMenuItem			pin2Target;
	private JMenu				subZOrdermenu;
	private JMenuItem			deleteMenuItem;
	private JMenuItem			propertiesMenuItem;
	
	public GConnectorPopupMenu(GConnector element, String title)
	{
		super(title);
		this.el=element;
		this.addPopupMenuListener(this);
		
		createMenu();
	}
	
	
	//
	//
	//
	
	private void setLock()
	{
		if (el.isLock())
			el.setLock(false);
		else
			el.setLock(true);
	}
	
	private void Pin2Source()
	{
		if (el.isSourcePined())
			el.setSourceElementPinPoint(new Point(-1, -1));
		else
			el.setSourceElementPinPoint(new Point(el.getCPoints()[0].x-el.getSourceElement().getX(), el.getCPoints()[0].y
					-el.getSourceElement().getY()));
	}
	
	private void Pin2Target()
	{
		if (el.isTargetPined())
			el.setTargetElementPinPoint(new Point(-1, -1));
		else
			el.setTargetElementPinPoint(new Point(el.getCPoints()[el.getcCPoints()-1].x-el.getTargetElement().getX(), el
					.getCPoints()[el.getcCPoints()-1].y-el.getTargetElement().getY()));
		
	}
	
	private void setZOrder_Forward()
	{
		if (el.getParent().getComponentZOrder(el)-1>=0
				&&el.getParent().getComponentZOrder(el)-1<el.getParent().getComponentCount())
			el.getParent().setComponentZOrder(el, el.getParent().getComponentZOrder(el)-1);
	}
	
	private void setZOrder_Back()
	{
		if (el.getParent().getComponentZOrder(el)+1>=0
				&&el.getParent().getComponentZOrder(el)+1<el.getParent().getComponentCount())
			el.getParent().setComponentZOrder(el, el.getParent().getComponentZOrder(el)+1);
	}
	
	private void setZOrder_Top()
	{
		el.getParent().setComponentZOrder(el, 0);
	}
	
	private void setZOrder_Bottom()
	{
		el.getParent().setComponentZOrder(el, el.getParent().getComponentCount()-1);
	}
	
	private void setDelete()
	{
		int answer=JOptionPane.showConfirmDialog(null, "Are you sure, \n to delete this Connector ???", "Delete Confirm",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (answer==JOptionPane.NO_OPTION)
			return;
		
		//delete the element
		el.Delete();
	}
	
	private void setProperties()
	{
		JOptionPane.showMessageDialog(null, "Sorry, \nProperties Form comes in the future...");
	}
	
	
	//
	//
	//
	//
	//
	//
	//
	//
	//
	///
	
	
	private void createMenu()
	{
		//// LOCK
		lockMenuItem=new JMenuItem();
		lockMenuItem.setText("Lock");
		lockMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setLock();
			}
		});
		this.add(lockMenuItem);
		
		//// Pin to Source
		pin2Source=new JMenuItem();
		pin2Source.setText("Pin to Source");
		pin2Source.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Pin2Source();
			}
		});
		this.add(pin2Source);
		
		//// Pin to Target
		pin2Target=new JMenuItem();
		pin2Target.setText("Pin to Target");
		pin2Target.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Pin2Target();
			}
		});
		this.add(pin2Target);
		
		
		this.addSeparator();//-------------------------------
		
		//// Z_Order
		subZOrdermenu=new JMenu("set ZOrder");
		JMenuItem menuItem1=new JMenuItem("set to Forward");
		menuItem1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setZOrder_Forward();
			}
		});
		JMenuItem menuItem2=new JMenuItem("set to Back");
		menuItem2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setZOrder_Back();
			}
		});
		JMenuItem menuItem3=new JMenuItem("set to Top");
		menuItem3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setZOrder_Top();
			}
		});
		
		JMenuItem menuItem4=new JMenuItem("set to Bottom");
		menuItem4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setZOrder_Bottom();
			}
		});
		
		subZOrdermenu.add(menuItem1);
		subZOrdermenu.add(menuItem2);
		subZOrdermenu.add(menuItem3);
		subZOrdermenu.add(menuItem4);
		this.add(subZOrdermenu);
		
		
		this.addSeparator();//-------------------------------
		
		
		//// Delete
		deleteMenuItem=new JMenuItem();
		deleteMenuItem.setText("Delete !!!");
		deleteMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setDelete();
			}
		});
		this.add(deleteMenuItem);
		
		this.addSeparator();//-------------------------------
		
		
		//// Properties
		propertiesMenuItem=new JMenuItem();
		propertiesMenuItem.setText("Properties...");
		propertiesMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setProperties();
			}
		});
		this.add(propertiesMenuItem);
		
		
	}// End of create menu
	
	
	//
	//
	
	
	private void refreshMenu()
	{
		//// LOCK
		if (el.isLock())
			lockMenuItem.setText("unLock");
		else
			lockMenuItem.setText("Lock");
		
		//// Pin to Source
		if (el.isSourcePined())
			pin2Source.setText("unPin from Source");
		else
			pin2Source.setText("Pin to Source");
		
		//// Pin to target
		if (el.isTargetPined())
			pin2Target.setText("unPin from Target");
		else
			pin2Target.setText("Pin to Target");
		
		if (el.isLock())
		{
			pin2Source.setEnabled(false);
			pin2Target.setEnabled(false);
			deleteMenuItem.setEnabled(false);
			subZOrdermenu.setEnabled(false);
			propertiesMenuItem.setEnabled(false);
		}
		else
		{
			pin2Source.setEnabled(true);
			pin2Target.setEnabled(true);
			deleteMenuItem.setEnabled(true);
			subZOrdermenu.setEnabled(true);
			propertiesMenuItem.setEnabled(true);
		}
	}// End of refresh menu
	
	
	
	//
	//
	
	
	
	public void popupMenuCanceled(PopupMenuEvent popupMenuEvent)
	{
		el.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent)
	{
		el.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent)
	{
		refreshMenu();
	}
	
	
	
}// End of Class
