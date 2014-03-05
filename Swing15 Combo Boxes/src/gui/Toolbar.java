package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton saveButton;
	private JButton refreshButton;
	private ToolBarListener toolBarListener;
	private Color EnabledColor;
	private Color disabledColor = Color.gray;

	public Toolbar() {

		setBorder(BorderFactory.createEtchedBorder());
		saveButton = new JButton("Save");
		refreshButton = new JButton("Refresh");

		EnabledColor = refreshButton.getForeground();
		setSaveButtonEnabled(false);

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(saveButton);
		add(refreshButton);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (toolBarListener != null) {
					toolBarListener.saveEventOccurred();
					setSaveButtonEnabled(false);
				}
			}
		});

		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (toolBarListener != null) {
					toolBarListener.refreshEventOccurred();
					setSaveButtonEnabled(true);
				}
			}
		});
	}

	public void setSaveButtonEnabled(boolean buttonEnabled) {

		if (buttonEnabled) {
			saveButton.setForeground(EnabledColor);
			saveButton.setEnabled(true);
		} else {
			saveButton.setForeground(disabledColor);
			saveButton.setEnabled(false);
		}
	}

	public void setToolBarListener(ToolBarListener listener) {
		this.toolBarListener = listener;
	}
}
