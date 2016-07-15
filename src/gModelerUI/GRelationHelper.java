package gModelerUI;

import gBaseModeler.GGElement;
import gUML25.ToolsPanel;

public class GRelationHelper
{
	private static boolean		isSourceTargetSelectionTime	=false;
	
	private static GGElement	source;
	private static GGElement	target;
	private static ToolsPanel	caller;
	
	
	//
	//
	//
	
	public static void setElementSelection(GGElement e)
	{
		if (!isSourceTargetSelectionTime)
			return;
		
		if (source==null)
			source=e;
		else if (target==null)
		{
			target=e;
			//complete and say to Toolspanel
			caller.SourceTargetConnectorComplete(source, target);
		}
		
		else
		{
			source=null;
			target=null;
		}
	}
	
	
	public static boolean isSourceTargetSelectionTime()
	{
		return isSourceTargetSelectionTime;
	}
	
	public static void setSourceTargetSelectionTime(boolean flag, ToolsPanel callerPanel)
	{
		isSourceTargetSelectionTime=flag;
		source=null;
		target=null;
		caller=callerPanel;
	}
	
	public static void setSourceTargetSelectionTime(boolean flag)
	{
		isSourceTargetSelectionTime=flag;
		source=null;
		target=null;
	}
	
	
	public static GGElement getSource()
	{
		return source;
	}
	
	
	public static GGElement getTarget()
	{
		return target;
	}
	
	
}
