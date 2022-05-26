package mainPage;

import java.awt.Frame;
import java.awt.List;

import main.ResetThread;

public class ThreadPage extends Thread{

	Frame f;
	
	public ThreadPage(Frame f) {
		this.f = f;
	}
	
	@Override
	public void run() { // Thread run() ¸Þ¼­µå
		int n = 1;
		
		MainPage1 p1 = new MainPage1(f);
		MainPage2 p2 = new MainPage2(f);
		MainPage3 p3 = new MainPage3(f);
		
		f.setLayout(null);
		
		while(p1.stop&&p2.stop&&p3.stop) {
			ResetThread.reset();
			try {
			f.removeAll();
			
			switch(n) {
			case 1:
				p1 = new MainPage1(f);
				n = 2;
				break;
			case 2:
				p2 = new MainPage2(f);
				n = 3;
				break;
			case 3:
				p3 = new MainPage3(f);
				n = 1;
				break;
			}
			
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
