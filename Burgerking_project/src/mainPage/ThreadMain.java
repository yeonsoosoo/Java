package mainPage;

import java.awt.Frame;
import java.awt.List;

public class ThreadMain {
	
	public ThreadMain(Frame f) {
		
		ThreadPage tp = new ThreadPage(f);
		tp.start();
		
	}
	
}
