package gModelerUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class GMenuBar
{
	public static void addMenues(final GMain gg)
	{
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Menu bar
		JMenuBar menubar=new JMenuBar();
		// ////////////////////////////////////// <file>
		JMenu menu=new JMenu("File");
		menu.setMnemonic('f');
		menubar.add(menu);
		// ////////////////////////////////////// <file.New>
		JMenuItem newProject=new JMenuItem("New Project...");
		newProject.setMnemonic('n');
		newProject.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		menu.add(newProject);
		// ////////////////////////////////////// </file.New>
		// ////////////////////////////////////// <file.Open Project>
		JMenuItem openProject=new JMenuItem("Open Project...");
		openProject.setMnemonic('n');
		openProject.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		menu.add(openProject);
		// ////////////////////////////////////// </file.Open Project>
		// ////////////////////////////////////// <file.Save>
		JMenuItem saveProject=new JMenuItem("Save");
		saveProject.setMnemonic('n');
		saveProject.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		menu.add(saveProject);
		// ////////////////////////////////////// </file.Save>
		// ////////////////////////////////////// <file.Print>
		JMenuItem print=new JMenuItem("Print...");
		print.setMnemonic('p');
		print.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new GSimplePrinter().printDiagram(gg.getBasePanel());
			}
		});
		menu.add(print);
		// ////////////////////////////////////// </file.Print>
		// ////////////////////////////////////// <file.Exit>
		JMenuItem exit=new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gg.getFrmMain().dispose();
				System.exit(0);
			}
		});
		menu.add(exit);
		// ////////////////////////////////////// </file.Exit>
		// ////////////////////////////////////// </file>
		// ////////////////////////////////////// <Help>
		JMenu menu2=new JMenu("Help");
		menu.setMnemonic('h');
		menubar.add(menu2);
		// ////////////////////////////////////// <Help.About>
		JMenuItem aboutProject=new JMenuItem("About");
		aboutProject.setMnemonic('n');
		aboutProject.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "GModeler 0.01 \n by: Gholamali Irani \n University of Bonab.");
			}
		});
		menu2.add(aboutProject);
		// ////////////////////////////////////// </Help.About>
		// ////////////////////////////////////// </Help>
		
		gg.getFrmMain().setJMenuBar(menubar);
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END of
		// menu
	}
	
}
