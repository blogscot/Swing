import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton helloButton;
	private JButton goodbyeButton;
	private StringListener textListener;

	public Toolbar() {

		setBorder(BorderFactory.createEtchedBorder());
		helloButton = new JButton("Hello");
		goodbyeButton = new JButton("Goodbye");

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(helloButton);
		add(goodbyeButton);

		helloButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textListener != null) {
					textListener.textEmitted("Hello\n");
				}
			}
		});

		goodbyeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textListener != null) {
					textListener.textEmitted("GoodBye\n");
				}
			}
		});
	}

	public void setStringListener(StringListener listener) {
		this.textListener = listener;
	}
}
