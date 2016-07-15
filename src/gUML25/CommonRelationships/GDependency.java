package gUML25.CommonRelationships;

import gBaseModeler.GConnectorArrows;
import gBaseModeler.GGElement;

public class GDependency extends GUMLBaseConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	
	public GDependency(String ConnectionName, GGElement Source, GGElement Target)
	{
		super(ConnectionName, Source, Target, 3, GConnectorArrows.AT_Empty, GConnectorArrows.AT_Arrow);
	}
	
	//
	//
	//
	public void setSourceArrow(boolean enable)
	{
		if (enable)
			super.setSourceArrowType(GConnectorArrows.AT_Arrow);
		else
			super.setSourceArrowType(GConnectorArrows.AT_Empty);
	}
	
	public void setTargetArrow(boolean enable)
	{
		if (enable)
			super.setTargetArrowType(GConnectorArrows.AT_Arrow);
		else
			super.setTargetArrowType(GConnectorArrows.AT_Empty);
	}
	
	
	
} // End of Class
