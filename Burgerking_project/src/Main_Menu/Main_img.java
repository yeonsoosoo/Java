package Main_Menu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main_img extends JPanel {
	public Main_img() {
		// TODO Auto-generated constructor stub
		ImageIcon img = new ImageIcon("./img/Main_img1.png");
		JLabel jl = new JLabel(img);
		
		this.add(jl);
		
		
		this.setPreferredSize(new Dimension(690,150));
		this.setBackground(new Color(79,38,22));
	}
}
