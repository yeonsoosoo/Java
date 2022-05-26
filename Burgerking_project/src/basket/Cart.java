package basket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.List;

import Cart.Products;

public class Cart extends Panel {

	TextArea ta = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

	int total1 = 0;
	int cnt = 0;
	Panel cart1;

	public Cart(List<Products> list) {
		cart1 = new Panel();
		cart1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		cart1.setBounds(10, 150, 570, 300);
		cart1.setBackground(Color.white);

		ta.setText("               번호                       상품명                     가격\n\n");

		// 글자 길이에 따른 간격 조정
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().length() < 4) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t\t   " + list.get(i).getName() + "\t\t\t     "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 5) {
				ta.append("\t\t    " + (i + 1) + "\t\t\t   " + list.get(i).getName() + "\t\t\t     "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 6) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t\t " + list.get(i).getName() + "\t\t\t     "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 7) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t      " + list.get(i).getName() + "\t\t     "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 8) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t     " + list.get(i).getName() + "\t\t    "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 10) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t   " + list.get(i).getName() + "\t\t     "
						+ list.get(i).getPrice() + "\n");
			} else if (list.get(i).getName().length() < 11) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t  " + list.get(i).getName() + "\t\t    " + list.get(i).getPrice()
						+ "\n");

			} else if (list.get(i).getName().length() < 12) {
				ta.append("\t\t   " + (i + 1) + "\t\t\t" + list.get(i).getName() + "\t    " + list.get(i).getPrice()
						+ "\n");
			} else if (list.get(i).getName().length() < 14) {
				ta.append("\t\t   " + (i + 1) + "\t\t   " + list.get(i).getName() + "\t    " + list.get(i).getPrice()
						+ "\n");
			}

			ta.append("----------------------------------------------------------\n");
		}

//      ta.append("\t\t" + (0)+" \t\t\t\t" +list.get(0).getName()+"\t\t\t"+list.get(0).getPrice());

		for (int i = 0; i < list.size(); i++) {
			total1 += Integer.parseInt(list.get(i).getPrice());
		}

		ta.setBackground(Color.white);
		ta.setEditable(false);
		ta.setFont(font1);
		cart1.add(ta, BorderLayout.CENTER);

	}

}
