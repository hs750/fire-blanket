package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class responsible for the command line like behaviour in the UI. Handles enter keypress
 * event and then accordingly moves the data around. Used by the mainWindow class.
 * Should only interact with an object of this class using writeToConsole method.
 * @author Tadas
 * 
 */
public class Console implements ActionListener {

	/**
	 * Class constructor
	 * @param textField UI component used for entering commands
	 * @param output UI component that displays console outputs
	 * @param prsr Pass the parser that will parse all the text commands and then communicate with the rest of the engine
	 */
	Console(JTextField textField, JTextArea output, Parser prsr) {
		txtField = textField;
		txtArea = output;
		parser = prsr;
	}

	/**
	 * Gets automatically called when user presses enter with the text field in
	 * focus. Essentially just moves the text from the input area to output
	 * area.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = txtField.getText();

		writeToConsole('>' + text);
		String parserOutput = "";

	
		parserOutput = parser.parse(text);
		
		

		if (parserOutput.length() != 0)
			writeToConsole(parserOutput);

		txtField.setText("");
	}

	/**
	 * Appends a string at the of the command line. Always adds a newline
	 * character to the start of the string.
	 * 
	 * @param text
	 *            The string that you want to be appended to the command line
	 *            output
	 */
	public void writeToConsole(String text) {
		txtArea.append('\n' + text);
		txtArea.setCaretPosition(txtArea.getDocument().getLength());
	}

	private JTextField txtField;
	private JTextArea txtArea;
	private Parser parser;

}