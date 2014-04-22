package applet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;


public class Main extends JApplet implements ActionListener {

	private Timer timer;
	private Game game = new Game();
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		
		timer = new Timer(20, this);
		
		setLayout(new BorderLayout());
		
		setSize(600, 500);
		add(game, BorderLayout.CENTER);
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
