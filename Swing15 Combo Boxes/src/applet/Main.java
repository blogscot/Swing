package applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;


public class Main extends JApplet implements ActionListener {

	private Timer timer;
	private Game game = new Game();
	private StartPanel startPanel;
	private CardLayout cards;
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		
		cards = new CardLayout();
		startPanel = new StartPanel();
		
		startPanel.setListener(new StartPanelListener() {
			
			@Override
			public void startGame() {
				cards.show(Main.this.getContentPane(), "game");
			}
		});
		
		timer = new Timer(20, this);
		
		setLayout(cards);
		setSize(600, 500);
		add(startPanel, "start");
		add(game, "game");
	}

	@Override
	public void start() {
		timer.start();
	}

	@Override
	public void stop() {
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.update();
	}
}
