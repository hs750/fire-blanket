package view;


import javax.swing.JFrame;
import javax.swing.BoxLayout;
//import javax.swing.JTextPane;
import java.awt.Component;
//import javax.swing.JSplitPane;
//import javax.swing.JSeparator;
//import javax.swing.JEditorPane;
import javax.swing.Box;
//import java.awt.TextArea;
import javax.swing.JScrollPane;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import controller.GameEngine;

import java.awt.Insets;
//import javax.swing.ScrollPaneConstants;

/**
 * Class responsible for the user interface. Creates 3 text areas and a single line input that acts like a command line.
 * User interacts through console object and updateLeftPanel and updateRightPanel functions. Everything else is taken care of.
 * @author Tadas
 *
 */
public class MainWindow 
{

	private JFrame frmFireBlanket;
	private JTextArea Modifiable;
	private JTextArea Nonmodifiable;
	private JTextArea CommandlineOutput;
	private JTextField Commandline;

	public Console console;

	/**
	 * Create the application.
	 */
	public MainWindow(GameEngine engine, String newGameMessage) 
	{
		initializeInterface(engine, newGameMessage);
		frmFireBlanket.pack();
		frmFireBlanket.setVisible(true);
	}

	/**
	 * Updates the text area that contains nonmodifiable resources
	 * @param text This parameter will replace the current text
	 */
	public void updateLeftPanel(String text)
	{
		Nonmodifiable.setText(text);
	}
	/**
	 * Updates the text area that contains modifiable resources
	 * @param text This parameter will replace the current text
	 */
	public void updateRightPanel(String text)
	{
		Modifiable.setText(text);
	}

	/**
	 * Initialize the look and contents of the frame. Big horrible function.
	 */
	private void initializeInterface(GameEngine engine, String newGameMessage) 
	{
		int textPaneVertical = 600;
		int textPaneHorizontal = 400;
		frmFireBlanket = new JFrame();
		frmFireBlanket.setResizable(false);
		frmFireBlanket.setTitle("Fire Blanket");
		frmFireBlanket.setBounds(100, 100, 769, 532);
		frmFireBlanket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFireBlanket.getContentPane().setLayout(new BoxLayout(frmFireBlanket.getContentPane(), BoxLayout.X_AXIS));

		Box verticalBox = Box.createVerticalBox();
		frmFireBlanket.getContentPane().add(verticalBox);

		Component verticalStrut_3 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_3);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_1);

		Modifiable = new JTextArea();

		JScrollPane scrollPaneModifiable = new JScrollPane(Modifiable);
		scrollPaneModifiable.setBorder(null);
		scrollPaneModifiable.setPreferredSize(new Dimension(textPaneHorizontal, textPaneVertical));
		horizontalBox.add(scrollPaneModifiable);

		Modifiable.setText("Cow");
		Modifiable.setLineWrap(true);
		Modifiable.setEditable(false);
		Modifiable.setWrapStyleWord(true);
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);

		Nonmodifiable = new JTextArea();
		Nonmodifiable.setEditable(false);
		JScrollPane scrollPaneNonModifiable = new JScrollPane(Nonmodifiable);
		scrollPaneNonModifiable.setBorder(null);
		scrollPaneNonModifiable.setPreferredSize(new Dimension(textPaneHorizontal, textPaneVertical));
		horizontalBox.add(scrollPaneNonModifiable);
		Nonmodifiable.setText("Cat");
		Nonmodifiable.setLineWrap(true);
		Nonmodifiable.setWrapStyleWord(true);

		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_2);

		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut_3);


		CommandlineOutput = new JTextArea();
		CommandlineOutput.setEditable(false);
		CommandlineOutput.setText(newGameMessage);
		CommandlineOutput.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		CommandlineOutput.setLineWrap(true);
		CommandlineOutput.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret)CommandlineOutput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane(CommandlineOutput);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(500, 110));
		horizontalBox_1.add(scrollPane);

		Component horizontalStrut_4 = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut_4);

		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);

		Component horizontalStrut_5 = Box.createHorizontalStrut(5);
		horizontalBox_2.add(horizontalStrut_5);

		Commandline = new JTextField();
		Commandline.setMargin(new Insets(0, 2, 2, 2));
		Commandline.setMaximumSize(new Dimension(2147483647, 20));
		horizontalBox_2.add(Commandline);
		Commandline.setSize(new Dimension(0, 20));
		Commandline.setColumns(20);
		Commandline.setPreferredSize(new Dimension(450, 20));

		Component horizontalStrut_6 = Box.createHorizontalStrut(5);
		horizontalBox_2.add(horizontalStrut_6);

		Component verticalStrut_1 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_1);


		console = new Console(Commandline, CommandlineOutput, new Parser(engine));
		//Hooking up all the events to action listeners.
		Commandline.addActionListener(console);
	}
	
	public String getRightPannelContence(){
		return Nonmodifiable.getText();
	}
	public String getLeftPannelContence(){
		return Modifiable.getText();
	}

}