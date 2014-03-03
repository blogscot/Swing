package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3306, 0,
			9999, 1);
	private JSpinner portSpinner = new JSpinner(spinnerModel);
	private JTextField userField = new JTextField(10);
	private JPasswordField passwordField = new JPasswordField(10);

	private PreferencesListener prefsListener;

	public PreferencesDialog(JFrame parent) {
		super(parent, "Preferences", false);

		setSize(new Dimension(280, 180));
		setResizable(false);
		setLocationRelativeTo(parent);

		layoutControls();

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = userField.getText();
				char[] password = passwordField.getPassword();
				Integer port = (int) portSpinner.getValue();
				passwordField.setEchoChar('*');

				if (prefsListener != null) {
					prefsListener.preferencesSet(user, new String(password),
							port);
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

	private void layoutControls() {

		JPanel controlsPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		int space = 15;

		okButton.setPreferredSize(cancelButton.getPreferredSize());

		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);

		Border spaceBorder = BorderFactory.createEmptyBorder(space, space,
				space, space);
		Border titledBorder = BorderFactory
				.createTitledBorder("Database Preferences");

		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,
				titledBorder));

		controlsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		// set up User
		gc.gridy = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("User: "), gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(userField, gc);

		// Set up Password - New Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Password: "), gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(passwordField, gc);

		// set up Spinner
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Port: "), gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(portSpinner, gc);

		// Set up buttons
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// add panels to dialog
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
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
