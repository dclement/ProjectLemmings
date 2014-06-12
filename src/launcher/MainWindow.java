package launcher;
import gui.StartGUI;

import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class MainWindow {

	public static StartGUI window;
	public MainWindow() {
		window = new StartGUI();
		window.setResizable(false);
	}
	
	public static void run() {
		
		if (window!=null) window.setVisible(true);
		
		while (!window.getStartSimu()) {
			
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e) {
				return;
			}
			
			if (window!=null) window.repaint();
		}
		
		//Recherche du xml de creation du monde par l'utilisate
		InputStream is;
		JFrame guiFrame = new JFrame();
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(guiFrame);
		String fileIn = null;
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			fileIn = fc.getSelectedFile().getAbsolutePath();
			
		}

		System.out.println("file " + fileIn);
		Game game = new Game(fileIn);
		if (window!=null) window.setVisible(false);
		game.run();
	}
	
	public static void main(String[] args) {
		MainWindow m = new MainWindow();
		m.run();
	}


}
