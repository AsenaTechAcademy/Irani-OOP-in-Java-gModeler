package gBaseModeler;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class GElementPopupMenu extends JPopupMenu implements PopupMenuListener
{
	private static final long	serialVersionUID	=3L;
	
	private GElement			el;
	
	private JMenuItem			lockMenuItem;
	private JMenuItem			moveableMenuItem;
	private JMenuItem			resizeableMenuItem;
	private JMenu				subZOrdermenu;
	private JMenuItem			deleteMenuItem;
	private JMenuItem			propertiesMenuItem;
	
	public GElementPopupMenu(GElement element, String title)
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
	
	private void setMoveable()
	{
		if (el.isMovable())
			el.setMovable(false);
		else
			el.setMovable(true);
	}
	
	private void setResizeable()
	{
		if (el.isResizable())
			el.setResizable(false);
		else
			el.setResizable(true);
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
		int answer=JOptionPane.showConfirmDialog(null, "Are you sure, \n to delete this Element ???", "Delete Confirm",
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
		
		//// Moveable
		moveableMenuItem=new JMenuItem();
		moveableMenuItem.setText("set unMoveable");
		moveableMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setMoveable();
			}
		});
		this.add(moveableMenuItem);
		
		//// Resizeable
		resizeableMenuItem=new JMenuItem();
		resizeableMenuItem.setText("set unResizeable");
		resizeableMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setResizeable();
			}
		});
		this.add(resizeableMenuItem);
		
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
		
		//// Moveable
		if (el.isMovable())
			moveableMenuItem.setText("set unMoveable");
		else
			moveableMenuItem.setText("set Moveable");
		
		//// Resizeable
		if (el.isResizable())
			resizeableMenuItem.setText("set unResizeable");
		else
			resizeableMenuItem.setText("set Resizeable");
		
		if (el.isLock())
		{
			moveableMenuItem.setEnabled(false);
			resizeableMenuItem.setEnabled(false);
			deleteMenuItem.setEnabled(false);
			subZOrdermenu.setEnabled(false);
			propertiesMenuItem.setEnabled(false);
		}
		else
		{
			moveableMenuItem.setEnabled(true);
			resizeableMenuItem.setEnabled(true);
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
