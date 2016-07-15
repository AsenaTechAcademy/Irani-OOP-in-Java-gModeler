package gUML25;

import gBaseModeler.GDiagram;
import gBaseModeler.GGElement;
import gModelerUI.GMain;
import gModelerUI.GRelationHelper;
import gUML25.ActivityDiagram.GActivityDiagram;
import gUML25.CommonRelationships.GAssociation;
import gUML25.CommonRelationships.GControlFlow;
import gUML25.CommonRelationships.GDependency;
import gUML25.CommonRelationships.GInheritance;
import gUML25.UseCaseDiagram.GUseCaseDiagram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ToolsPanel extends JPanel
{
	private static final long	serialVersionUID	=3L;
	
	private JLabel				title;
	private JLabel				selDiagram;
	private JComboBox			cboDiagram;
	private JButton				btnNewDiagram		=new JButton();
	private JPanel				tp;
	
	private String				currentCon			="";
	private GMain				gg;
	
	
	
	public ToolsPanel(GMain mainForm)
	{
		super();
		Initialize();
		gg=mainForm;
	}
	
	private void Initialize()
	{
		this.setSize(100, 300);
		this.setPreferredSize(new Dimension(100, 300));
		
		this.setBackground(new Color(210, 210, 210));
		this.setLayout(new FlowLayout());
		
		title=new JLabel("Tools Panel");
		title.setOpaque(true);
		title.setBackground(Color.yellow);
		title.setPreferredSize(new Dimension(this.getWidth()-4, 15));
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title);
		
		selDiagram=new JLabel("<html><u>Select Diagram:</u></html>");
		selDiagram.setHorizontalAlignment(JLabel.LEFT);
		selDiagram.setPreferredSize(new Dimension(this.getWidth()-4, 15));
		selDiagram.setFont(new Font("Tahoma", 11, 11));
		this.add(selDiagram);
		
		cboDiagram=new JComboBox();
		cboDiagram.setPreferredSize(new Dimension(this.getWidth()-2, 20));
		cboDiagram.setFont(new Font("Tahoma", 11, 11));
		cboDiagram.setEditable(false);
		cboDiagram.addItem("Use Case Diagram");
		cboDiagram.addItem("Activity Diagram");
		cboDiagram.setSelectedIndex(0);
		this.add(cboDiagram);
		
		cboDiagram.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				btnNewDiagram.setText("<html><p align=center>Create new "+cboDiagram.getSelectedItem().toString()+" </p></html>");
			}
		});
		
		btnNewDiagram.setText("<html><p align=center>Create new "+cboDiagram.getSelectedItem().toString()+" </p></html>");
		btnNewDiagram.setFont(new Font("Tahoma", 10, 10));
		btnNewDiagram.setPreferredSize(new Dimension(100, 40));
		this.add(btnNewDiagram);
		
		btnNewDiagram.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int answer=JOptionPane.showConfirmDialog(null, "Are you sure, \n to Create New Diagram ???",
						"New Diagram Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (answer==JOptionPane.NO_OPTION)
					return;
				
				if (cboDiagram.getSelectedItem().toString()=="Use Case Diagram")
					gg.setBasePanel(new GUseCaseDiagram("a Use Case"));
				else if (cboDiagram.getSelectedItem().toString()=="Activity Diagram")
					gg.setBasePanel(new GActivityDiagram("an Activity Diagram"));
				
				ChangeDiagram(cboDiagram.getSelectedItem().toString());
			}
		});
		
	} // Initialize
	
	
	private void ChangeDiagram(String DiagramName)
	{
		if (tp!=null)
			this.remove(tp);
		
		tp=new JPanel();
		tp.setBackground(Color.yellow);
		tp.setLayout(new FlowLayout());
		tp.setPreferredSize(new Dimension(95, 300));
		this.add(tp);
		
		if (DiagramName=="Use Case Diagram")
		{
			tp.add(new JLabel("Elements"));
			//--------------------------------------------
			GUML25ToolsElementButton actor=new GUML25ToolsElementButton("Actor", "gUML25.UseCaseDiagram.GActor", gg, tp);
			GUML25ToolsElementButton usecase=new GUML25ToolsElementButton("UseCase", "gUML25.UseCaseDiagram.GUseCase", gg, tp);
			GUML25ToolsElementButton boundary=new GUML25ToolsElementButton("Boundary", "gUML25.UseCaseDiagram.GSystemBoundary",
					gg, tp);
			
			
			tp.add(new JLabel("Relationships"));
			//---------------------------------------------
			GUML25ToolsRelationButton association=new GUML25ToolsRelationButton("Association", "Association", this, tp);
			GUML25ToolsRelationButton Inheritance=new GUML25ToolsRelationButton("Inheritance", "Inheritance", this, tp);
			GUML25ToolsRelationButton dependency=new GUML25ToolsRelationButton("Dependency", "Dependency", this, tp);
			
			//---------------------------------------------
			tp.add(new JLabel("Common"));
			
			
		}// if use case diagram
		
		else if (DiagramName=="Activity Diagram")
		{
			tp.add(new JLabel("Elements"));
			//--------------------------------------------
			GUML25ToolsElementButton start=new GUML25ToolsElementButton("Start Point", "gUML25.ActivityDiagram.GStartPoint", gg,
					tp);
			GUML25ToolsElementButton activity=new GUML25ToolsElementButton("Activity", "gUML25.ActivityDiagram.GActivity", gg, tp);
			GUML25ToolsElementButton condition=new GUML25ToolsElementButton("Condition", "gUML25.ActivityDiagram.GCondition", gg,
					tp);
			GUML25ToolsElementButton end=new GUML25ToolsElementButton("End Point", "gUML25.ActivityDiagram.GEndPoint", gg, tp);
			
			tp.add(new JLabel("Relationships"));
			//--------------------------------------------
			GUML25ToolsRelationButton controlflow=new GUML25ToolsRelationButton("Control Flow", "Control Flow", this, tp);
			
			tp.add(new JLabel("Common"));
			//---------------------------------------------
			
		}// if Activity Diagram
	}
	
	
	
	public void SourceTargetConnectorComplete(GGElement source, GGElement target)
	{
		GRelationHelper.setSourceTargetSelectionTime(false);
		((GDiagram) gg.getBasePanel()).ignoreHelpPoints();
		
		if (currentCon=="Association")
			gg.addConnector(new GAssociation("testAssociation", source, target));
		else if (currentCon=="Inheritance")
			gg.addConnector(new GInheritance("testInheritance", source, target));
		else if (currentCon=="Dependency")
			gg.addConnector(new GDependency("testDependency", source, target));
		else if (currentCon=="Control Flow")
			gg.addConnector(new GControlFlow("testControlFlow", source, target));
	}
	
	//
	//
	//
	//
	
	public String getCurrentCon()
	{
		return currentCon;
	}
	
	public void setCurrentCon(String currentCon)
	{
		this.currentCon=currentCon;
	}
	
	
}// End of Class
