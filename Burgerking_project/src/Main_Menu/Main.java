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

		//리스트
		this.list = list;
		
		Products pro = new Products();

		// 전체 레이아웃을 플로우레이아웃으로 지정
		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		// 메인 이미지
		mg = new Main_img();

		// 메뉴
		menu = new Menu();

		// 스폐셜팩
		Special special = new Special(frame, list);

		// 결제
		mp = new Main_Pay(list, frame);

		// 쿠폰
		couppon = new JButton("쿠폰 사용하기");
		couppon.setFont(f1);
		couppon.setBackground(new Color(138, 0, 0));
		couppon.setForeground(Color.white);
		couppon.setPreferredSize(new Dimension(690, 70));

		// 쿠폰 사용 시
		if (!list.isEmpty()) {

			int sum = 0;
			
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i));
				sum += Integer.parseInt(list.get(i).getPrice());
			}
			
			mp.won = sum;
			mp.jl2.setText("총 주문금액 " + Main.mp.won + "원");

		}

		// 쿠폰사용 클릭시 화면 연결
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

		// 버튼들이 참조할 이벤트 감지자(인터페이스) 생성
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// e.getActionCommand() -> 버튼 위에 쓰여져있는 텍스트 출력
				System.out.println(e.getActionCommand());
				ResetThread.reset();
				switch (e.getActionCommand()) {
				case "스폐셜&할인팩":
					System.out.println("1번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Special(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "프리미엄":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Premium(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "와퍼":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Whopper(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "주니어&버거":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Burger(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "올데이킹&치킨버거":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Allday(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "사이드":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Side(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "음료&디저트":
					System.out.println("2번 버튼이눌림");
					frame.removeAll();
					frame.add(mg);
					frame.add(menu);
					new Drink(frame, list);
					frame.add(mp);
					frame.add(couppon);
					frame.revalidate();
					frame.repaint();
					break;
				case "리얼 독퍼":
					System.out.println("2번 버튼이눌림");
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

		// 버튼에 리스너추가
		for (int i = 0; i < menu.menuBtn.length; i++) {
			menu.menuBtn[i].addActionListener(al);
		}

		frame.setBounds(600, 30, 700, 1000);
		frame.setBackground(new Color(255, 255, 255));
		frame.setVisible(true);
		frame.setResizable(false);

		// 종료 버튼
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
}
