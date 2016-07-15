package gUML25;

import gModelerUI.GRelationHelper;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GUML25ToolsRelationButton extends JButton
{
	private static final long	serialVersionUID	=3L;
	
	
	private ToolsPanel			baseParent;
	private JPanel				tp;
	private String				className;
	
	
	public GUML25ToolsRelationButton(String text, String ClassName, ToolsPanel baseParent, JPanel tp)
	{
		this.baseParent=baseParent;
		this.tp=tp;
		this.className=ClassName;
		
		this.setText(text);
		this.setPreferredSize(new Dimension(90, 20));
		this.setFont(new Font("Tahoma", 11, 11));
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GRelationHelper.setSourceTargetSelectionTime(true, GUML25ToolsRelationButton.this.baseParent);
				GUML25ToolsRelationButton.this.baseParent.setCurrentCon(GUML25ToolsRelationButton.this.className);
			}
		});
		tp.add(this);
		
	}
	
}
