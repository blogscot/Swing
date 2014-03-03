package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

		JPanel prefsPane = new JPanel();
		Insets spinnerPadding = new Insets(10, 0, 10, 0);
		Insets buttonPadding = new Insets(0, 0, 10, 0);
		
		this.setContentPane(prefsPane);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		setSize(new Dimension(200, 120));
		setResizable(false);
		setLocationRelativeTo(parent);

		// set up Spinner
		gc.insets = spinnerPadding;
		prefsPane.add(new JLabel("Port: "), gc);
		prefsPane.add(portSpinner, gc);
		
		gc.weighty = 1;
		gc.gridy += 2;
		
		// Set up buttons
		gc.insets = buttonPadding;		
		prefsPane.add(okButton, gc);		
		prefsPane.add(cancelButton, gc);
		
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
