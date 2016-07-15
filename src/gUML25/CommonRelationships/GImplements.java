package gUML25.CommonRelationships;

import gBaseModeler.GConnectorArrows;
import gBaseModeler.GGElement;

public class GImplements extends GUMLBaseConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	
	public GImplements(String ConnectionName, GGElement Source, GGElement Target)
	{
		super(ConnectionName, Source, Target, 3, GConnectorArrows.AT_Empty, GConnectorArrows.AT_Inherit);
	}
	
	
	
	//
	//
	//
	public void setSourceText(String text)
	{
		//sourceLabel.setText(text);
	}
	
	public void setTargetText(String text)
	{
		//targetLabel.setText(text);
	}
	
	public void setMiddleText(String text)
	{
		//middleLabel.setText(text);
	}
	
	public String getSourceText()
	{
		return "";
	}
	
	public String getTargetText()
	{
		return "";
	}
	
	public String getMiddleText()
	{
		return "";
	}
	
	
} // End of Class
