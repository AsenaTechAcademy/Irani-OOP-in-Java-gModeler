package gModelerUI;

import gBaseModeler.GBaseDiagramAdapter;
import gBaseModeler.GConnector;
import gBaseModeler.GDiagram;
import gBaseModeler.GGElement;
import gUML25.ToolsPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

//

public class GMain
{
	private JFrame				frmMain;
	private GBaseDiagramAdapter	basePanel;
	private JScrollPane			scroll;
	private ToolsPanel			toolsPanel;
	private JPanel				rightPanel;
	
	
	public GMain()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		frmMain=new JFrame();
		frmMain.setTitle("Welcome to GModeler 0.01");
		frmMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmMain.setBounds(100, 50, 1250, 700);
		//frmMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmMain.setLayout(new BorderLayout(3, 3));
		
		// ////// Tools Panel
		toolsPanel=new ToolsPanel(this);
		frmMain.add(toolsPanel, BorderLayout.WEST);
		
		scroll=new JScrollPane();
		frmMain.add(scroll, BorderLayout.CENTER);
		
		//
		//
		//
		//
		
		// ////// Right Panel
		rightPanel=new JPanel();
		rightPanel.setBackground(Color.yellow);
		rightPanel.setPreferredSize(new Dimension(250, 300));
		rightPanel.setLayout(new FlowLayout());
		frmMain.add(rightPanel, BorderLayout.EAST);
		// @@@@@@@ Project Browser Panel
		JPanel projectbrowserPanel=new JPanel();
		projectbrowserPanel.setBackground(Color.cyan);
		projectbrowserPanel.setPreferredSize(new Dimension(248, 400));
		projectbrowserPanel.add(new JLabel("Project Browser"));
		rightPanel.add(projectbrowserPanel);
		// @@@@@@@ Properties Panel
		JPanel propertiesPanel=new JPanel();
		propertiesPanel.setBackground(Color.cyan);
		propertiesPanel.setPreferredSize(new Dimension(248, 400));
		propertiesPanel.add(new JLabel("Properties"));
		rightPanel.add(propertiesPanel);
		//
		//
		frmMain.setVisible(true);
	}
	
	public void setBasePanel(GDiagram newDiagram)
	{
		basePanel=newDiagram;
		scroll.setViewportView(basePanel);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		
		toolsPanel.revalidate();
		toolsPanel.repaint();
	}
	
	public void addElement(GGElement x)
	{
		basePanel.add(x);
		basePanel.repaint();
	}
	
	public void addConnector(GConnector x)
	{
		basePanel.add(x);
		basePanel.repaint();
	}
	
	public static void main(String[] args)
	{
		GMain gg=new GMain();
		GMenuBar.addMenues(gg);
	}
	
	public GBaseDiagramAdapter getBasePanel()
	{
		return (GBaseDiagramAdapter) basePanel;
	}
	
	public JFrame getFrmMain()
	{
		return frmMain;
	}
	
	
	
}// End of Class
