package gBaseModeler;

import gModelerUI.GRelationHelper;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class GGElement extends GElement
{
	private static final long	serialVersionUID	=3L;
	
	
	private String				Name;
	
	
	private boolean				isChildable			=true;
	private boolean				isFatherable		=true;
	
	
	private List<GGElement>		childs				=new ArrayList<GGElement>();
	private GGElement			father;
	
	
	public GGElement(String Name, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.Name=Name;
		father=null;
		childs.clear();
	}
	
	public GGElement(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		Name="";
		father=null;
		childs.clear();
	}
	
	public void mousePressed(MouseEvent e)
	{
		// this section is used in creating a new Relationship between source and target
		// so, in this time no need to focus an Element
		if (GRelationHelper.isSourceTargetSelectionTime())
		{
			GRelationHelper.setElementSelection(this);
			return;
		}
		
		// else super.mousePressed
		super.mousePressed(e);
	}
	
	// override the setLocation of parent, then before setLocation we can check any condition on x,y
	public void setLocation(int x, int y)
	{
		int px=getX();
		int py=getY();
		super.setLocation(x, y);
		int dx=getX()-px;
		int dy=getY()-py;
		
		// say to childs
		for (GGElement el:childs)
			el.FatherMoved(dx, dy);
		
		
		// to Inform parent (GDiagram) about Element Change Location to set (if it was TSelected)
		if (!isAbsoluteChange()&&isTSelected())
			((GDiagram) (this.getParent())).TChangeLocation(this, px-x, py-y);
		
	}
	
	
	
	public void Delete()
	{
		ReleaseAllChilds2Father();
		super.Delete();
	}
	
	
	
	public void setSize(int width, int height)
	{
		int x=width;
		int y=height;
		if (x>=getMinWidth()&&x<=getMaxWidth()&&y>=getMinHeight()&&y<=getMaxHeight())
		{
			super.setSize(width, height);
			ReleaseChilds2Father();
		}
		
		
		super.setSize(width, height);
		
		ReleaseChilds2Father();
	}
	
	
	protected void FatherMoved(int dx, int dy)
	{
		this.setLocation(getX()+dx, getY()+dy);
	}
	
	
	
	public void mouseReleased(MouseEvent e)
	{
		super.mouseReleased(e);
		
		FindaFather2this_mouseRelease();
		FindChilds2this_mouseRelease();
		
	}// End of Mouse Release
	
	
	
	//
	//
	//
	//
	// this method is used by Delete
	// in this method, all childs release to father,
	// if father==null, release to Digram
	private void ReleaseAllChilds2Father()
	{
		for (GGElement el:childs)
			if (this.father==null)
				el.father=null;
			else
			//if (this.father!=null)
			{
				this.father.childs.add(el);
				el.father=this.father;
			}
		
		this.childs.clear();
		
	}// End of ReleaseAllChilds2Father
	
	
	//
	//
	//
	//
	// this method is used by setSize
	// in this method, all childs that are out of boundary release to father,
	// if father==null, release to Digram
	private void ReleaseChilds2Father()
	{
		
		boolean isfind=true;
		while (isfind)
		{
			isfind=false;
			
			for (GGElement el:childs)
			{
				if (!this.getRectangle().contains(el.getRectangle()))
				{
					this.childs.remove(el); // because the el removes in For, so For should start again
					
					if (this.father==null)
						el.father=null;
					else
					//if (this.father!=null)
					{
						this.father.childs.add(el);
						el.father=this.father;
					}
					isfind=true;
					break;// because the el removes in For, so For should start again
				}//if
			}//for
		}//while
		
		
	}// End of ReleaseChilds2Father
	
	
	
	//
	//
	//
	//
	// try to find some Childs to THIS
	private void FindChilds2this_mouseRelease()
	{
		
		if (!this.isFatherable)
			return; // this GGElement is not fatherable
			
			
		// try to find childs (bipedar)
		for (int i=0; i<this.getParent().getComponentCount(); i++)
		{
			// just can be father for GGElements 
			if (!(this.getParent().getComponent(i) instanceof GGElement))
				continue;
			
			
			GGElement fa=(GGElement) this.getParent().getComponent(i);
			
			if (!fa.isChildable)
				continue; // the finded GGElement can not have any father
				
			if (fa==this)
				continue; // a GGElement can not be father to itself 
				
				
			if (fa.father!=null) // this can be father for null father GGElements (bipedars) or same fathers
			{
				if (this.father!=null)
					if (fa.father!=this.father)
						continue;
				
				if (this.father==null)
					continue;
			}
			
			
			// a new child found
			if (this.getRectangle().contains(fa.getRectangle()))
			{
				if (fa.father!=null)
					fa.father.childs.remove(fa);
				
				fa.father=this;
				this.AddnewChild(fa);
				//break;
			}
		}// for i
		
		
	} // End of FindChilds2this_mouseRelease
	
	
	
	//
	//
	//
	// try to find a father to THIS
	private void FindaFather2this_mouseRelease()
	{
		// GGElement is moving on it's father domain
		// Wrong
		//if (father!=null&&father.getRectangle().contains(this.getRectangle()))
		//	return;
		
		boolean isChilded=false;
		
		if (!this.isChildable)
			return; // this GGElement is not childable
			
			
		// try to find a father
		for (int i=0; i<this.getParent().getComponentCount(); i++)
		{
			// just can be child for GGElement 
			if (!(this.getParent().getComponent(i) instanceof GGElement))
				continue;
			
			
			GGElement fa=(GGElement) this.getParent().getComponent(i);
			
			if (!fa.isFatherable)
				continue; // the finded GGElement can not have any childs
				
			if (fa==this)
				continue; // a GGElement can not be child to itself 
				
				
			// a GGElement can not have a GGElement as Child twice
			// no need, it was checked in first line of method, but good to be here too
			if (fa.getChilds().contains(this)&&fa.getRectangle().contains(this.getRectangle()))
			{
				isChilded=true;
				break;
			}
			
			
			// a new father found
			if (fa.getRectangle().contains(this.getRectangle()))
			{
				if (this.father!=null)
					this.father.childs.remove(this);
				
				this.setFather(fa);
				fa.AddnewChild(this);
				isChilded=true;
				break;
			}
		}// for i
		
		if (!isChilded) // move to empty space in GDiagram
		{
			if (this.father!=null)
			{
				this.father.childs.remove(this);
				this.father=null;
			}
		}
		
	} // End of FindaFather2this
	
	
	
	//
	//
	//
	//
	//
	//
	//
	//
	
	protected void AddnewChild(GGElement newE)
	{
		childs.add(newE);
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	protected List<GGElement> getChilds()
	{
		return childs;
	}
	
	protected GGElement getFather()
	{
		return father;
	}
	
	protected void setFather(GGElement father)
	{
		this.father=father;
	}
	
	public boolean isChildable()
	{
		return isChildable;
	}
	
	public boolean isFatherable()
	{
		return isFatherable;
	}
	
	protected void setChildable(boolean isChildable)
	{
		this.isChildable=isChildable;
	}
	
	protected void setFatherable(boolean isFatherable)
	{
		this.isFatherable=isFatherable;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public void setName(String name)
	{
		Name=name;
	}
	
}// End of Class
