package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Toolbar extends JToolBar {

	private static final long serialVersionUID = 1L;

	private JButton saveButton;
	private JButton refreshButton;
	private ToolBarListener toolBarListener;
	private Color EnabledColor;
	private Color disabledColor = Color.gray;

	public Toolbar() {

		saveButton = new JButton();
        saveButton.setIcon(Utils.createIcon("/images/save.gif"));
        saveButton.setToolTipText("Save");

		refreshButton = new JButton();
        refreshButton.setIcon(Utils.createIcon("/images/refresh.png"));
        refreshButton.setToolTipText("Refresh");

		EnabledColor = refreshButton.getForeground();
		setSaveButtonEnabled(false);

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
