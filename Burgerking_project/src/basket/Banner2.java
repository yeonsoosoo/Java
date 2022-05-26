package basket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.util.List;

import javax.swing.JPanel;

import Cart.Products;
import Main_Menu.Main;

public class Banner2 extends JPanel {

	List<Products> list;
	Frame f;
	int won = 0;
	//���� ����
	boolean b1, b2, b3;
	

	// �ݾ� ���� ���

	public Banner2(List<Products> list, Frame f) {

		this.list = list;
		this.f = f;

		Cart cart = new Cart(list);
		Font f1 = new Font(null, Font.BOLD, 15);

		// �г� ���� �� ũ�� ����
		JPanel jp5 = new JPanel();
		jp5.setPreferredSize(new Dimension(700, 120));
		jp5.setBackground(Color.white);
		jp5.setLayout(new FlowLayout());

		// �� ���� �� ũ�� ����
		Label l1 = new Label();
		Label l2 = new Label("�� �ֹ��ݾ�:    " + cart.total1 + "��");

		// ���� ���� üũ
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().contains("����")) {
				b1 = true;
			} else if (list.get(i).getName().contains("���")) {
				b2 = true;
			} else {
				;
			}
		}
		
		if(b1 && b2) {
			l1.setText("���� : ����, ���");
		} else if (b1 && !b2) {
			l1.setText("���� : ����");
		} else if (!b1 && b2) {
			l1.setText("���� : ���");
		} else {
			l1.setText("���� : X");
		}

		l1.setPreferredSize(new Dimension(300, 110));
		l2.setPreferredSize(new Dimension(300, 55));

		l1.setFont(f1);
		l2.setFont(f1);

		jp5.add(l1);
		jp5.add(l2);

		this.add(jp5);

	}

}
