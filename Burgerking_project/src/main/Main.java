package main;

import java.awt.Frame;
import java.awt.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import mainPage.ThreadMain;

public class Main {

	public static void main(String[] args) {
		
		Frame f = new Frame("BurgerKing");
		f.setBounds(600,30,700,1000);
		f.setLayout(null);
		
		ResetThread rt = new ResetThread(f);
		rt.setDaemon(true);
		rt.start();
		
		new ThreadMain(f);
		
		f.setResizable(false);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
}