package mainPage;

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
import Main_Menu.Main;
import main.ResetThread;

public class MainPage2 extends Panel {

	boolean stop = true;
	public MainPage2(Frame f) {
		this.setBounds(0, 0, 700, 1000);
		this.setBackground(new Color(254,185,19));
		
		List<Products> list = new ArrayList<Products>();

		ImageIcon img = new ImageIcon("images/dogpper.png");
		JLabel jl = new JLabel(img);

		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResetThread.reset();
				System.out.println(list);
				f.removeAll();
				stop = false;
				Main main = new Main(f, list);
			}
		});

		this.add(jl);
		f.add(this);
		f.setVisible(true);

	}

}
