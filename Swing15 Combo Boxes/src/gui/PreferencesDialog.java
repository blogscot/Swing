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
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
	private JSpinner portSpinner = new JSpinner(spinnerModel);
	private JTextField userField = new JTextField(10);
	private JPasswordField passwordField = new JPasswordField(10);
	
	private PreferencesListener prefsListener;

	public PreferencesDialog(JFrame parent) {
		super(parent, "Preferences", false);

		JPanel prefsPane = new JPanel();
		Insets passwordPadding = new Insets(10, 0, 0, 0);
		Insets spinnerPadding = new Insets(10, 0, 0, 0);
		Insets buttonPadding = new Insets(0, 0, 0, 0);
		
		this.setContentPane(prefsPane);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		setSize(new Dimension(300, 180));
		setResizable(false);
		setLocationRelativeTo(parent);

		// set up Password
		gc.insets = passwordPadding;
		gc.anchor = GridBagConstraints.LINE_END;
		prefsPane.add(new JLabel("User: "), gc);
		prefsPane.add(userField, gc);
		
		gc.gridy += 2;
		passwordField.setEchoChar('*');
		prefsPane.add(new JLabel("Password: "), gc);
		prefsPane.add(passwordField, gc);
		
		// set up Spinner
		gc.gridy += 2;
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
				String user = userField.getText();
				char[] password = passwordField.getPassword();
				Integer port = (int)portSpinner.getValue();
				
				if (prefsListener != null) {
					prefsListener.preferencesSet(user, new String(password), port);
				}
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

	public void setDefaults(String user, String password, int port) {
		userField.setText(user);
		passwordField.setText(password);
		portSpinner.setValue(port);
	}
	
	public void setPreferencesListener(PreferencesListener prefsListener) {
		this.prefsListener = prefsListener;
	}
}
