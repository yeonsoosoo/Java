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
		
		// �г� ����
		
		JPanel jp3 = new JPanel();
		jp3.setPreferredSize(new Dimension(630, 60));
		jp3.setLayout(new FlowLayout());
		
		// ��ư ����
		RoundedButton btnClose = new RoundedButton("���");
		RoundedButton btnOk = new RoundedButton("�����ϱ�");

		btnClose.setFont(f1);
		btnOk.setFont(f1);

		btnClose.setPreferredSize(new Dimension(300, 55));
		btnOk.setPreferredSize(new Dimension(300, 55));

		jp3.add(btnClose);
		jp3.add(btnOk);

		this.add(jp3);
		
		// �ڷΰ��� ��ư
		
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.removeAll();
				new Main(f, list);
			}
		});
		
		// ���� ��ư
		
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f.removeAll();
				new Payment(f, list);

			}
		});
	}
}
