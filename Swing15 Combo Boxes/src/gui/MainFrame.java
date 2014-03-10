package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Toolbar toolBar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;
	private PreferencesDialog prefsDialog;
	private Preferences prefs = Preferences.userRoot().node("db");

	public MainFrame() {
		super("Hello, World!");

		setLayout(new BorderLayout());

		toolBar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		prefsDialog = new PreferencesDialog(this);

		controller = new Controller();

		tablePanel.setData(controller.getPeople());

		tablePanel.setPersonTableListener(new PersonTableListener() {
			public void rowDeleted(int row) {
				controller.removePerson(row);
				toolBar.setSaveButtonEnabled(true);
			}
		});

		prefsDialog.setPreferencesListener(new PreferencesListener() {

			@Override
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);
			}
		});

		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		int port = prefs.getInt("port", 3306);

		prefsDialog.setDefaults(user, password, port);

		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new PersonFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		setJMenuBar(createMenuBar());

		// Set up Listeners
		toolBar.setToolBarListener(new ToolBarListener() {

			@Override
			public void saveEventOccurred() {
				try {
					controller.connect();
					controller.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"Database Save failed.", "Database Save Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							e.getMessage(), "Database Connection Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void refreshEventOccurred() {

				try {
					controller.connect();
					controller.load();
				} catch (SQLException e) {
					JOptionPane
							.showMessageDialog(MainFrame.this,
									"Database Refresh failed.",
									"Database Refresh Error",
									JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							e.getMessage(), "Database Connection Error",
							JOptionPane.ERROR_MESSAGE);
				}
				tablePanel.refresh();
			}
		});

		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e) {
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // This is a work around to avoid InterruptedException on exit
                // I've never seen it so it might be an old problem that John Purcell used to see
                dispose();
                System.gc();
            }
        });

		add(tablePanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.NORTH);
		add(formPanel, BorderLayout.WEST);

		setSize(600, 500);
		setLocation(500, 200);
		setMinimumSize(new Dimension(550, 450));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenuItem importDataItem = new JMenuItem("Import Data");
		JMenuItem exportDataItem = new JMenuItem("Export Data");
		JMenuItem exitItem = new JMenuItem("Exit");

		// Set up Menu Item - File
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		fileMenu.add(importDataItem);
		fileMenu.add(exportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		// Set up Menu Item - Window
		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");

		menuBar.add(windowMenu);

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);

		prefsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prefsDialog.setVisible(true);

			}
		});

		// Set up Mnemonics
		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		// Set up Accelerators
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));

		// Set up ActionListeners
		showFormItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
				formPanel.setVisible(menuItem.isSelected());
			}
		});

		importDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"File import failed!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this,
								"File save failed!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to quit?", "Confirm Exit",
						JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();

                    for (WindowListener listener: listeners) {
                        listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                    }
				}
			}
		});

		return menuBar;
	}
}
