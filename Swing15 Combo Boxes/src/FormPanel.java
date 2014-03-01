import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okBtn;
	private FormListener formListener;
	private JList<AgeCategory> ageList;
	private JComboBox<String> empCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;

	public FormPanel() {
		Dimension dim = getPreferredSize();

		dim.width = 280;
		setPreferredSize(dim);

		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList<AgeCategory>();
		empCombo = new JComboBox<String>();
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID");

		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		genderGroup = new ButtonGroup();
		maleRadio.setSelected(true);
		maleRadio.setActionCommand("Male");
		femaleRadio.setActionCommand("Female");

		okBtn = new JButton("OK");

		// Set up Mnemonics
		okBtn.setMnemonic(KeyEvent.VK_O);
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);

		
		// Set up gender radios
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);

		// Set up Tax ID
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);

		citizenCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean isTicked = citizenCheck.isSelected();

				taxLabel.setEnabled(isTicked);
				taxField.setEnabled(isTicked);
			}
		});

		// Set up List Box
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<AgeCategory>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 64"));
		ageModel.addElement(new AgeCategory(2, "Over 65"));

		ageList.setModel(ageModel);
		ageList.setPreferredSize(new Dimension(114, 66));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(0);

		// Set up ComboBox
		DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<String>();
		empModel.addElement("Employed");
		empModel.addElement("Self-Employed");
		empModel.addElement("Unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);

		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = ageList.getSelectedValue();
				String empCat = (String) empCombo.getSelectedItem();
				boolean usCitzen = citizenCheck.isSelected();
				String taxText = usCitzen ? taxField.getText() : null;

				String genderCommand = genderGroup.getSelection()
						.getActionCommand();

				FormEvent event = new FormEvent(this, name, occupation, ageCat
						.toString(), empCat, taxText, usCitzen, genderCommand);

				if (formListener != null) {
					formListener.formEventOccurred(event);
				}
			}
		});

		Border insideBorder = BorderFactory.createTitledBorder("Add Person");
		Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outsideBorder,
				insideBorder));

		layoutComponents();
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		Insets withoutPadding = new Insets(0, 0, 0, 0);
		Insets withPadding = new Insets(0, 0, 0, 5);

		// Add Name Label - First Row
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = withPadding;
		add(nameLabel, gc);

		// add Name field
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = withoutPadding;
		add(nameField, gc);

		// add Occupation Label - Next Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = withPadding;
		add(occupationLabel, gc);

		// add Occupation field
		gc.gridx++;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = withoutPadding;
		add(occupationField, gc);

		// add List Box Label - Next Row
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = withPadding;
		add(new JLabel("Age: "), gc);

		// Add List Box
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(ageList, gc);

		// add Combo Box Label - Next Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(new JLabel("Employment: "), gc);

		// Add Combo Box
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(empCombo, gc);

		// add Check Box Label - Next Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = withPadding;
		add(new JLabel("US Citizen: "), gc);

		// Add Check Box
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(citizenCheck, gc);

		// add Text Field Label - Next Row
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = withPadding;
		add(taxLabel, gc);

		// Add Text Field
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taxField, gc);

		// add Radio Button Label - Next Row
		gc.weightx = 1;
		gc.weighty = 0.01;

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = withPadding;
		add(new JLabel("Gender: "), gc);

		// Add Male Radio Button
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio, gc);

		// Add Female Radio Button - Next Row
		gc.gridy++;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio, gc);

		// Add button - Next Row
		gc.weightx = 1;
		gc.weighty = 2.0;

		gc.gridy++;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okBtn, gc);
	}

	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}
}

class AgeCategory {

	private int id;
	private String text;

	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}
}
