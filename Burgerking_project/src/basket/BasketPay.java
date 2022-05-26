package basket;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import Cart.Products;
import Main_Menu.Main;
import Main_Menu.RoundedButton;

public class BasketPay extends JPanel{

	List<Products> list;
	Frame f;
	
	public BasketPay(List<Products> list, Frame f) {

		Font f1 = new Font(null, Font.BOLD, 15);
		Font f2 = new Font(null, Font.BOLD, 25);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 29));
		
		this.list  = list;
		this.f = f;
		
		// 패널 생성
		
		JPanel jp3 = new JPanel();
		jp3.setPreferredSize(new Dimension(630, 60));
		jp3.setLayout(new FlowLayout());
		
		// 버튼 생성
		RoundedButton btnClose = new RoundedButton("취소");
		RoundedButton btnOk = new RoundedButton("결제하기");

		btnClose.setFont(f1);
		btnOk.setFont(f1);

		btnClose.setPreferredSize(new Dimension(300, 55));
		btnOk.setPreferredSize(new Dimension(300, 55));

		jp3.add(btnClose);
		jp3.add(btnOk);

		this.add(jp3);
		
		// 뒤로가기 버튼
		
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.removeAll();
				new Main(f, list);
			}
		});
		
		// 결제 버튼
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.removeAll();
				new Payment(f, list);

			}
		});
	}
}
