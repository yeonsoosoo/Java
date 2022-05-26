package Main_Menu;

import Cart.*;
import main.ResetThread;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class PanelMouse implements MouseListener {

	//상품명과 가격
	String name;
	int price;
	// 세트인지 판별
	boolean check;
	List list;
	Frame f;
	//버튼 클릭시 화면 전환
	int number;

	// 클릭된 객체 갖고오기
	public PanelMouse(String name,int price, boolean check, List list, Frame f, int number) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.check = check;
		this.list = list;
		this.f = f;
		this.number = number;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		Products pro = new Products();
		ResetThread.reset();
		
		if (check) {
			int result = JOptionPane.showConfirmDialog(null, "세트메뉴로 주문하시겠습니까?\n세트메뉴로 주문할 경우 2000원이 추가됩니다.", "세트 선택",
					JOptionPane.YES_NO_OPTION);

			// 0일 경우 세트메뉴
			if (result == 0) {
				ResetThread.reset();
				pro.setName(name + " 세트");
				pro.setPrice(Integer.toString(price+2000));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price+2000;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("총 주문금액 " + Main.mp.won + "원");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "가 카트에 담겼습니다.");
				
			} else {
				ResetThread.reset();
				pro.setName(name);
				pro.setPrice(Integer.toString(price));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("총 주문금액 " + Main.mp.won + "원");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "가 카트에 담겼습니다.");
			}
		} else {
			int result = JOptionPane.showConfirmDialog(null, "카트에 추가하시겠습니까?", "카트 추가", JOptionPane.YES_NO_OPTION);

			// 0일 경우 카트추가, 아니면 취소
			if (result == 0) {
				ResetThread.reset();
				pro.setName(name);
				pro.setPrice(Integer.toString(price));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("총 주문금액 " + Main.mp.won + "원");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "가 카트에 담겼습니다.");
			} else {

			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	void changeView(int number) {
		switch(number) {
		case 0 :
			new Special(f, list);
			break;
		case 1 :
			new Premium(f, list);
			break;
		case 2 :
			new Whopper(f, list);
			break;
		case 3 :
			new Burger(f, list);
			break;
		case 4 :
			new Allday(f, list);
			break;
		case 5 :
			new Side(f, list);
			break;
		case 6 :
			new Drink(f, list);
			break;
		case 7 :
			new Dogper(f, list);
			break;
		}
	}

}
