package Main_Menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel{
	
	public JButton[] menuBtn = new JButton[8];
	
	public Menu() {
		
		//메뉴 카테고리
		String[] menu = {"스폐셜&할인팩", "프리미엄", "와퍼", "주니어&버거", "올데이킹&치킨버거", "사이드", "음료&디저트", "리얼 독퍼"};
		GridLayout grid = new GridLayout(2,4);
		
		this.setLayout(grid);
		
		for(int i=0; i<menu.length; i++) {
			this.add(menuBtn[i] = new JButton(menu[i]));
			menuBtn[i].setBackground(new Color(255,255,255));
		}
		
			
		//패널 크기 조절
		this.setPreferredSize(new Dimension(690,100));
		
	}
}
