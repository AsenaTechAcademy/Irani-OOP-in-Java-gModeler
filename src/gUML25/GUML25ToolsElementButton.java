package gUML25;

import gBaseModeler.GGElement;
import gModelerUI.GMain;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GUML25ToolsElementButton extends JButton
{
	private static final long	serialVersionUID	=3L;
	
	
	private GMain				gg;
	private JPanel				tp;
	private String				className;
	
	
	public GUML25ToolsElementButton(String text, String ClassName, GMain gg, JPanel tp)
	{
		this.gg=gg;
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
				GGElement aa=null;
				Constructor c=null;
				try
				{
					c=Class.forName(GUML25ToolsElementButton.this.className).getConstructor();
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				
				
				try
				{
					aa=(GGElement) c.newInstance();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				
				
				GUML25ToolsElementButton.this.gg.addElement(aa);
			}
		});
		
		this.tp.add(this);
	}
	
}
