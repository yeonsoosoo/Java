package basket;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.util.List;

import javax.swing.JPanel;

import Cart.Products;

public class Banner extends JPanel{

	List<Products> list;
	Frame f;
	
	
	// �ֹ� Ȯ�� ���
	public Banner(List<Products> list, Frame f) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 290, 30));
		
		this.list  = list;
		this.f = f;
		
		Font f2 = new Font(null, Font.BOLD, 25);
		
		// �г� ����
		JPanel jp4 = new JPanel();
		
		// �г� ũ�� ����
		jp4.setPreferredSize(new Dimension(630, 200));
		jp4.setLayout(new FlowLayout());
		
		Label l1 = new Label("�ֹ� Ȯ��");
		l1.setFont(f2);
		
		jp4.add(l1);
		
		this.add(l1);
	}
}
