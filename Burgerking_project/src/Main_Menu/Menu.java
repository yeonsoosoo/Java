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
		
		//�޴� ī�װ�
		String[] menu = {"�����&������", "�����̾�", "����", "�ִϾ�&����", "�õ���ŷ&ġŲ����", "���̵�", "����&����Ʈ", "���� ����"};
		GridLayout grid = new GridLayout(2,4);
		
		this.setLayout(grid);
		
		for(int i=0; i<menu.length; i++) {
			this.add(menuBtn[i] = new JButton(menu[i]));
			menuBtn[i].setBackground(new Color(255,255,255));
		}
		
			
		//�г� ũ�� ����
		this.setPreferredSize(new Dimension(690,100));
		
	}
}
