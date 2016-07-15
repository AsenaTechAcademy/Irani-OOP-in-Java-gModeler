package gUML25.CommonRelationships;

import gBaseModeler.GConnectorArrows;
import gBaseModeler.GGElement;

public class GControlFlow extends GUMLBaseConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	
	public GControlFlow(String ConnectionName, GGElement Source, GGElement Target)
	{
		super(ConnectionName, Source, Target, 0, GConnectorArrows.AT_Empty, GConnectorArrows.AT_Arrow);
	}
	
	//
	//
	//
	
	public void setSourceText(String text)
	{
		super.setSourceText("["+text+"]");
	}
	
	public void setTargetText(String text)
	{
		super.setTargetText("["+text+"]");
	}
	
	public void setMiddleText(String text)
	{
		super.setMiddleText("["+text+"]");
	}
	
	public String getSourceText()
	{
		return super.getSourceText();
	}
	
	public String getTargetText()
	{
		return super.getTargetText();
	}
	
	public String getMiddleText()
	{
		return super.getMiddleText();
	}
	
	
} // End of Class
