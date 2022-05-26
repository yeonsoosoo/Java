package Main_Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dogper extends JPanel {
	
	public String[] name = {"리얼 독퍼"};
	public int[] price = {2500};
	
	
	public Dogper(Frame f, List list) {
		// TODO Auto-generated constructor stub
		// 그리드 레이아웃 지정
		GridLayout grid = new GridLayout(2, 2);
		Font menuName = new Font(null, Font.BOLD, 20);
		Font menuPrice = new Font(null, Font.BOLD, 15);

		// 각 메뉴 패널 생성
		ImageIcon img1 = new ImageIcon("./img_whopper/dog1.png");
		JPanel jp1 = new JPanel();
		jp1.setLayout(new BorderLayout());

		JLabel jl1 = new JLabel(img1);
		JLabel jl11 = new JLabel(name[0]);
		jl11.setFont(menuName);
		JLabel jl111 = new JLabel(price[0]+"원");
		jl111.setFont(menuPrice);
		jl111.setForeground(Color.RED);

		jl11.setHorizontalAlignment(JLabel.CENTER);
		jl111.setHorizontalAlignment(JLabel.CENTER);

		jp1.add(jl1, BorderLayout.NORTH);
		jp1.add(jl11, BorderLayout.CENTER);
		jp1.add(jl111, BorderLayout.SOUTH);

		
		jp1.setBorder(BorderFactory.createLineBorder(Color.gray));
	
		jp1.setBackground(new Color(255,255,255));
		
		jp1.addMouseListener(new PanelMouse(name[0], price[0],false, list, f,7));
		
		this.add(jp1);

		// 패널 크기 조절
		this.setPreferredSize(new Dimension(690, 430));
		this.setLayout(grid);
		this.setBackground(new Color(255, 255, 255));

		f.add(this);
	}
}
