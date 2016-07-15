package gUML25.CommonRelationships;

import gBaseModeler.GConnectorArrows;
import gBaseModeler.GGElement;

public class GAggregation extends GUMLBaseConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	
	public GAggregation(String ConnectionName, GGElement Source, GGElement Target)
	{
		super(ConnectionName, Source, Target, 0, GConnectorArrows.AT_Empty, GConnectorArrows.AT_Aggregation);
	}
	
	
	
	//
	//
	//	
	
} // End of Class
