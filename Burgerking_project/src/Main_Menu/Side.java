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

public class Side extends JPanel {
	
	public String[] sideName = {"치킨 도넛 2조각", "너겟킹", "치즈 스틱", "바삭킹"};
	public int[] sidePrice = {3500, 2200, 1000, 2600};
	
	public Side(Frame f, List list) {
		// TODO Auto-generated constructor stub
		// 그리드 레이아웃 지정
		GridLayout grid = new GridLayout(2, 2);
		Font menuName = new Font(null, Font.BOLD, 20);
		Font menuPrice = new Font(null, Font.BOLD, 15);

		// 각 메뉴 패널 생성
		ImageIcon img1 = new ImageIcon("./img_whopper/si1.png");
		JPanel jp1 = new JPanel();
		jp1.setLayout(new BorderLayout());

		JLabel jl1 = new JLabel(img1);
		JLabel jl11 = new JLabel(sideName[0]);
		jl11.setFont(menuName);
		JLabel jl111 = new JLabel(sidePrice[0]+"원");
		jl111.setFont(menuPrice);
		jl111.setForeground(Color.RED);

		jl11.setHorizontalAlignment(JLabel.CENTER);
		jl111.setHorizontalAlignment(JLabel.CENTER);

		jp1.add(jl1, BorderLayout.NORTH);
		jp1.add(jl11, BorderLayout.CENTER);
		jp1.add(jl111, BorderLayout.SOUTH);

		// 각 메뉴 패널 생성
		ImageIcon img2 = new ImageIcon("./img_whopper/si2.png");
		JPanel jp2 = new JPanel();
		jp2.setLayout(new BorderLayout());

		JLabel jl2 = new JLabel(img2);
		JLabel jl22 = new JLabel(sideName[1]);
		jl22.setFont(menuName);
		JLabel jl222 = new JLabel(sidePrice[1]+ "원");
		jl222.setFont(menuPrice);
		jl222.setForeground(Color.RED);

		jl22.setHorizontalAlignment(JLabel.CENTER);
		jl222.setHorizontalAlignment(JLabel.CENTER);

		jp2.add(jl2, BorderLayout.NORTH);
		jp2.add(jl22, BorderLayout.CENTER);
		jp2.add(jl222, BorderLayout.SOUTH);

		// 각 메뉴 패널 생성
		ImageIcon img3 = new ImageIcon("./img_whopper/si3.png");
		JPanel jp3 = new JPanel();
		jp3.setLayout(new BorderLayout());

		JLabel jl3 = new JLabel(img3);
		JLabel jl33 = new JLabel(sideName[2]);
		jl33.setFont(menuName);
		JLabel jl333 = new JLabel(sidePrice[2] + "원");
		jl333.setFont(menuPrice);
		jl333.setForeground(Color.RED);

		jl33.setHorizontalAlignment(JLabel.CENTER);
		jl333.setHorizontalAlignment(JLabel.CENTER);

		jp3.add(jl3, BorderLayout.NORTH);
		jp3.add(jl33, BorderLayout.CENTER);
		jp3.add(jl333, BorderLayout.SOUTH);

		// 각 메뉴 패널 생성
		ImageIcon img4 = new ImageIcon("./img_whopper/si4.png");
		JPanel jp4 = new JPanel();
		jp4.setLayout(new BorderLayout());

		JLabel jl4 = new JLabel(img4);
		JLabel jl44 = new JLabel(sideName[3]);
		jl44.setFont(menuName);
		JLabel jl444 = new JLabel(sidePrice[3]+ "원");
		jl444.setFont(menuPrice);
		jl444.setForeground(Color.RED);

		jl44.setHorizontalAlignment(JLabel.CENTER);
		jl444.setHorizontalAlignment(JLabel.CENTER);

		jp4.add(jl4, BorderLayout.NORTH);
		jp4.add(jl44, BorderLayout.CENTER);
		jp4.add(jl444, BorderLayout.SOUTH);

		jp1.setBorder(BorderFactory.createLineBorder(Color.gray));
		jp2.setBorder(BorderFactory.createLineBorder(Color.gray));
		jp3.setBorder(BorderFactory.createLineBorder(Color.gray));
		jp4.setBorder(BorderFactory.createLineBorder(Color.gray));

		jp1.setBackground(new Color(255,255,255));
		jp2.setBackground(new Color(255,255,255));
		jp3.setBackground(new Color(255,255,255));
		jp4.setBackground(new Color(255,255,255));
		
		jp1.addMouseListener(new PanelMouse(sideName[0], sidePrice[0],false, list, f,5));
		jp2.addMouseListener(new PanelMouse(sideName[1], sidePrice[1],false, list, f,5));
		jp3.addMouseListener(new PanelMouse(sideName[2], sidePrice[2],false, list, f,5));
		jp4.addMouseListener(new PanelMouse(sideName[3], sidePrice[3],false, list, f,5));
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);

		// 패널 크기 조절
		this.setPreferredSize(new Dimension(690, 430));
		this.setLayout(grid);
		this.setBackground(new Color(255, 255, 255));

		f.add(this);
	}
}
