import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private TextPanel textPanel;
	private Toolbar toolBar;
	private FormPanel formPanel;
	
	public MainFrame() {
		super("Hello, World!");
		
		setLayout(new BorderLayout());
		
		toolBar = new Toolbar();
		textPanel = new TextPanel();
		formPanel = new FormPanel();

		// Set up Listeners
		toolBar.setStringListener(new StringListener() {
			
			@Override
			public void textEmitted(String text) {
				textPanel.appendText(text);
			}
		});
		
		formPanel.setFormListener(new FormListener() {
			public void formEventOccurred(FormEvent e){
				String name = e.getName();
				String occupation = e.getOccupation();
				String ageCat = e.getAgeCategory();
				String empCat = e.getEmploymentCategory();
				String taxId = e.getTaxId();
				boolean usCitizen = e.IsUsCitizen();
				String gender = e.getGender();
				
				textPanel.appendText(name + "\n" + occupation + "\n" + ageCat
						+ "\n" + gender + "\n" + empCat + "\n"
						+ (usCitizen ? "US Citizen" : "Non-US citizen") + "\n"
						+ "TaxId: " + (taxId != null ? taxId : " -") + "\n");
			}
		});
		
		add(toolBar, BorderLayout.NORTH);
		add(textPanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);
		
		
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
