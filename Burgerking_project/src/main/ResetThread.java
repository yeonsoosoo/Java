package main;

import java.awt.Frame;

import mainPage.ThreadMain;

public class ResetThread extends Thread{

	static int n = 0;
	
	Frame f;
	static boolean stop = false;
	
	public ResetThread(Frame f) {
		this.f = f;
	}
	
	public static void reset() {
		n = 0;
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			n++;
			System.out.println(n);
			//시간제한
			if(n >= 1000) {
				n = 0;
				new ThreadMain(f);
			}
		}
	}
	
}
