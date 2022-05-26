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

	//��ǰ��� ����
	String name;
	int price;
	// ��Ʈ���� �Ǻ�
	boolean check;
	List list;
	Frame f;
	//��ư Ŭ���� ȭ�� ��ȯ
	int number;

	// Ŭ���� ��ü �������
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
			int result = JOptionPane.showConfirmDialog(null, "��Ʈ�޴��� �ֹ��Ͻðڽ��ϱ�?\n��Ʈ�޴��� �ֹ��� ��� 2000���� �߰��˴ϴ�.", "��Ʈ ����",
					JOptionPane.YES_NO_OPTION);

			// 0�� ��� ��Ʈ�޴�
			if (result == 0) {
				ResetThread.reset();
				pro.setName(name + " ��Ʈ");
				pro.setPrice(Integer.toString(price+2000));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price+2000;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("�� �ֹ��ݾ� " + Main.mp.won + "��");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "�� īƮ�� �����ϴ�.");
				
			} else {
				ResetThread.reset();
				pro.setName(name);
				pro.setPrice(Integer.toString(price));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("�� �ֹ��ݾ� " + Main.mp.won + "��");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "�� īƮ�� �����ϴ�.");
			}
		} else {
			int result = JOptionPane.showConfirmDialog(null, "īƮ�� �߰��Ͻðڽ��ϱ�?", "īƮ �߰�", JOptionPane.YES_NO_OPTION);

			// 0�� ��� īƮ�߰�, �ƴϸ� ���
			if (result == 0) {
				ResetThread.reset();
				pro.setName(name);
				pro.setPrice(Integer.toString(price));
				pro.setCount(1);
				list.add(pro);
				Main.mp.won += price;
				System.out.println(Main.mp.won);
				Main.mp.jl2.setText("�� �ֹ��ݾ� " + Main.mp.won + "��");
				System.out.println(pro.getName());
				f.removeAll();
				f.add(Main.mg);
				f.add(Main.menu);
				changeView(number);
				f.add(Main.mp);
				f.add(Main.couppon);
				f.revalidate();
				f.repaint();
				JOptionPane.showMessageDialog(null, pro.getName() + "�� īƮ�� �����ϴ�.");
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
