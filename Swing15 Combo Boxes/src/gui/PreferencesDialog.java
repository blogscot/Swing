package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
	private JSpinner portSpinner = new JSpinner(spinnerModel);

	public PreferencesDialog(JFrame parent) {
		super(parent, "Preferences", false);

		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		// set up Spinner Label
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		
		add(new JLabel("Port: "), gc);
		
		// set up Spinner
		gc.gridx++;
		add(portSpinner, gc);
		
		setSize(new Dimension(200, 100));
		setLocationRelativeTo(parent);
		
		// Set up buttons - New Row
		
		gc.gridy++;
		gc.gridx = 0;
		add(okButton, gc);
		
		gc.gridx++;
		add(cancelButton, gc);
		
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Integer value = (int)portSpinner.getValue();
				System.out.println(value);
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}
}
