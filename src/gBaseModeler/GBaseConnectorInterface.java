package gBaseModeler;

public interface GBaseConnectorInterface extends GBaseModelerInterface
{
	
	public void ChangeElementsSizeLocation(GElement e, int dx, int dy);
	
	public void setSourceElement(GElement source);
	
	public void setTargetElement(GElement target);
	
	public GElement getSourceElement();
	
	public GElement getTargetElement();
	
}
