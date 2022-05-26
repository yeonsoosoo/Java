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
	//할인 유형
	boolean b1, b2, b3;
	

	// 금액 산출 배너

	public Banner2(List<Products> list, Frame f) {

		this.list = list;
		this.f = f;

		Cart cart = new Cart(list);
		Font f1 = new Font(null, Font.BOLD, 15);

		// 패널 생성 및 크기 조정
		JPanel jp5 = new JPanel();
		jp5.setPreferredSize(new Dimension(700, 120));
		jp5.setBackground(Color.white);
		jp5.setLayout(new FlowLayout());

		// 라벨 생성 및 크기 조절
		Label l1 = new Label();
		Label l2 = new Label("총 주문금액:    " + cart.total1 + "원");

		// 할인 유형 체크
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().contains("쿠폰")) {
				b1 = true;
			} else if (list.get(i).getName().contains("행사")) {
				b2 = true;
			} else {
				;
			}
		}
		
		if(b1 && b2) {
			l1.setText("할인 : 쿠폰, 행사");
		} else if (b1 && !b2) {
			l1.setText("할인 : 쿠폰");
		} else if (!b1 && b2) {
			l1.setText("할인 : 행사");
		} else {
			l1.setText("할인 : X");
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
