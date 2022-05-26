package basket;

import Cart.*;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import Cart.Products;
import Main_Menu.Main_img;

public class Basket extends Panel {

	public static BasketPay bp;

	List<Products> list;

	public Basket(Frame frame, List<Products> list) {
		// TODO Auto-generated constructor stub
//		List<Products> list = new ArrayList<Products>();

		Main_img mg = new Main_img();

		this.list = list;

		// 배너
		Banner be = new Banner(list, frame);
		Banner2 be2 = new Banner2(list, frame);
		Banner3 be3 = new Banner3(list, frame);
		Banner4 be4 = new Banner4(list, frame);

		// basket 결제
		bp = new BasketPay(list, frame);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
		Font font2 = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		Font font3 = new Font(Font.SANS_SERIF, Font.BOLD, 20);

		// 장바구니 FieldArea
		Cart cart = new Cart(list);
		cart.cart1.setBounds(65, 200, 570, 300);

		frame.add(mg);
		frame.add(be);
		frame.add(cart.cart1);
		frame.add(be2);
		frame.add(be3);
		frame.add(bp);
		frame.add(be4);

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

}
