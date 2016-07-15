package gUML25.CommonRelationships;

import gBaseModeler.GConnector;
import gBaseModeler.GGElement;
import gBaseModeler.GPinableLabel;

import java.awt.Graphics;

public abstract class GUMLBaseConnector extends GConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	private GPinableLabel		sourceLabel;
	private GPinableLabel		middleLabel;
	private GPinableLabel		targetLabel;
	private boolean				isLabelsAdded		=false;
	
	
	public GUMLBaseConnector(String ConnectionName, GGElement Source, GGElement Target, int DashedLevel, int sourceArrowType,
			int targetArrowType)
	{
		super(Source, Target, DashedLevel, 1, sourceArrowType, targetArrowType);
		
		sourceLabel=new GPinableLabel("", this, this.getCPoints()[0].x, getCPoints()[0].y);
		middleLabel=new GPinableLabel("", this, this.getCenterPoint().x, this.getCenterPoint().y);
		targetLabel=new GPinableLabel("", this, this.getCPoints()[getcCPoints()-1].x, getCPoints()[getcCPoints()-1].y);
	}
	
	protected void drawElement(Graphics g)
	{
		super.drawElement(g);
		
		if (!isLabelsAdded)
		{
			this.getParent().add(sourceLabel);
			this.getParent().add(targetLabel);
			this.getParent().add(middleLabel);
			this.getParent().setComponentZOrder(sourceLabel, 0);
			this.getParent().setComponentZOrder(targetLabel, 0);
			this.getParent().setComponentZOrder(middleLabel, 0);
			this.getParent().setComponentZOrder(this, 0);
			
			isLabelsAdded=true;
		}
	}
	
	
	// CPoint change property EVENT
	protected void CPointsChanged()
	{
		SetPins();
	}
	
	private void SetPins()
	{
		sourceLabel.setChangePins(this.getCPoints()[0].x, getCPoints()[0].y);
		middleLabel.setChangePins(this.getCenterPoint().x, this.getCenterPoint().y);
		targetLabel.setChangePins(this.getCPoints()[getcCPoints()-1].x, getCPoints()[getcCPoints()-1].y);
	}
	
	//
	//
	//
	public void setSourceText(String text)
	{
		sourceLabel.setText(text);
	}
	
	public void setTargetText(String text)
	{
		targetLabel.setText(text);
	}
	
	public void setMiddleText(String text)
	{
		middleLabel.setText(text);
	}
	
	public String getSourceText()
	{
		return sourceLabel.getText();
	}
	
	public String getTargetText()
	{
		return targetLabel.getText();
	}
	
	public String getMiddleText()
	{
		return middleLabel.getText();
	}
	
}
