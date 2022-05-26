package mainPage;

import Main_Menu.*;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Cart.Products;
import main.ResetThread;

public class MainPage1 extends Panel{

	boolean stop = true;
	
	List<Products> list = new ArrayList<Products>();
	
	public MainPage1(Frame f) {
	this.setBounds(0,0,700,1000);
	this.setBackground(new Color(80,37,21));
	
	
	ImageIcon img = new ImageIcon("images/bgkmain.png");
	JLabel jl = new JLabel(img);
	
	jl.addMouseListener(new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			ResetThread.reset();
			f.removeAll();
			stop = false;
			// 다음페이지 추가할것
			Main main = new Main(f, list);
		}
		
	});
	
	this.add(jl);
	f.add(this);
	f.setVisible(true);
	
	} 

}