package Main_Menu;

import Cart.Products;
import basket.Basket;
import coupon.Coupon;
import main.ResetThread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

public class Main extends JPanel {
	public static Main_img mg;
	public static Menu menu;
	public static Main_Pay mp;
	public static JButton couppon;
	Frame frame;
	
	List<Products> list;

	public Main(Frame frame, List<Products> list) {
		// TODO Auto-generated constructor stub
		this.frame = frame;

		Font f1 = new Font(null, Font.BOLD, 18);

		//����Ʈ
		this.list = list;
		
		Products pro = new Products();

		// ��ü ���̾ƿ��� �÷ο췹�̾ƿ����� ����
		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		// ���� �̹���
		mg = new Main_img();

		// �޴�
		menu = new Menu();

		// �������
		Special special = new Special(frame, list);

		// ����
		mp = new Main_Pay(list, frame);

		// ����
		couppon = new JButton("���� ����ϱ�");
		couppon.setFont(f1);
		couppon.setBackground(new Color(138, 0, 0));
		couppon.setForeground(Color.white);
		couppon.setPreferredSize(new Dimension(690, 70));

		// ���� ��� ��
		if (!list.isEmpty()) {

			int sum = 0;
			
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i));
				sum += Integer.parseInt(list.get(i).getPrice());
			}
			
			mp.won = sum;
			mp.jl2.setText("�� �ֹ��ݾ� " + Main.mp.won + "��");

		}

		// ������� Ŭ���� ȭ�� ����
		couppon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.removeAll();
				new Coupon(frame, list);
				frame.revalidate();
				frame.repaint();
			}
		});

		frame.add(mg);
		frame.add(menu);
		frame.add(special);
		frame.add(mp);
		frame.add(couppon);

		// ��ư���� ������ �̺�Ʈ ������(�������̽�) ����
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// e.getActionCommand() -> ��ư ���� �������ִ� �ؽ�Ʈ ���
				System.out.println(e.getActionCommand());
				ResetThread.reset();
				switch (e.getActionCommand()) {
				case "�����&������":
					System.out.println("1�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Special(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "�����̾�":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Premium(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "����":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Whopper(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "�ִϾ�&����":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Burger(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "�õ���ŷ&ġŲ����":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Allday(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "���̵�":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Side(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "����&����Ʈ":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Drink(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "���� ����":
					System.out.println("2�� ��ư�̴���");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Dogper(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				}
			}
		};

		// ��ư�� �������߰�
		for (int i = 0; i < menu.menuBtn.length; i++) {
			menu.menuBtn[i].addActionListener(al);
		}

		frame.setBounds(600, 30, 700, 1000);
		frame.setBackground(new Color(255, 255, 255));
		frame.setVisible(true);
		frame.setResizable(false);

		// ���� ��ư
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}
