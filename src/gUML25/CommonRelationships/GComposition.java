package gUML25.CommonRelationships;

import gBaseModeler.GConnectorArrows;
import gBaseModeler.GGElement;

public class GComposition extends GUMLBaseConnector
{
	private static final long	serialVersionUID	=3L;
	
	
	
	public GComposition(String ConnectionName, GGElement Source, GGElement Target)
	{
		super(ConnectionName, Source, Target, 0, GConnectorArrows.AT_Empty, GConnectorArrows.AT_Composition);
	}
	
	
	
	//
	//
	//	
	
} // End of Class
