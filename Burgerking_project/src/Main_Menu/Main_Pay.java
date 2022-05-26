package Main_Menu;

import java.awt.BorderLayout;
import Cart.Products;
import basket.Basket;
import main.ResetThread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Cart.Products;

public class Main_Pay extends JPanel {

	public String[] rowData1 = { "��ǰ�� ", "����", "����" };
	public String[][] in1 = new String[10][];
	List<Products> list;
	public int won = 0;
	public JLabel jl2;
	Frame f;

	public Main_Pay(List<Products> list, Frame f) {
		// TODO Auto-generated constructor stub
		Font f1 = new Font(null, Font.BOLD, 15);
		Font f2 = new Font(null, Font.BOLD, 25);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

		this.list = list;
		this.f = f;

		JPanel jp1 = new JPanel();
		jp1.setPreferredSize(new Dimension(630, 30));
		jp1.setLayout(new BorderLayout());
		
		JLabel jl1 = new JLabel("īƮ");
		
		jl1.setFont(f1);

		jp1.add(jl1, BorderLayout.CENTER);

		JPanel jp2 = new JPanel();
		jl2 = new JLabel("�� �ֹ��ݾ� " + won + "��");
		jl2.setFont(f2);
		jl2.setHorizontalAlignment(JLabel.CENTER);
		jp2.add(jl2);

		jp2.setPreferredSize(new Dimension(630, 70));

		JPanel jp3 = new JPanel();
		jp3.setPreferredSize(new Dimension(630, 57));
		jp3.setLayout(new FlowLayout());
		RoundedButton btnClose = new RoundedButton("īƮ ����");
		RoundedButton btnOk = new RoundedButton("�����ϱ�");

		btnClose.setFont(f1);
		btnOk.setFont(f1);

		btnClose.setPreferredSize(new Dimension(300, 55));
		btnOk.setPreferredSize(new Dimension(300, 55));

		jp3.add(btnClose);
		jp3.add(btnOk);

		// ��� �� ���� ���ݰ� ����Ʈ �ʱ�ȭ
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (list.size() != 0) {
					ResetThread.reset();
					list.clear();
					Main.mp.won = 0;
					Main.mp.jl2.setText("�� �ֹ��ݾ� " + Main.mp.won + "��");
					JOptionPane.showMessageDialog(null, "�ֹ� ����� �ʱ�ȭ�Ǿ����ϴ�. \n��ǰ�� �ٽ� �������ּ���.");
				} else {
					JOptionPane.showMessageDialog(null, "�ֹ� ����� �����ϴ�.");
				}
				
			}
		});

		// �����ϱ� ���� �� ����ȭ�� ����
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(list.size() + "����Ʈ ������");
				if (!list.isEmpty()) {
					ResetThread.reset();
					f.removeAll();
					Basket ba = new Basket(f, list);
					f.revalidate();
					f.repaint();
				} else {
					JOptionPane.showMessageDialog(f, "īƮ�� ����ֽ��ϴ�.", "īƮ Ȯ��", JOptionPane.WARNING_MESSAGE);

					System.out.println("īƮ �������");
				}
			}
		});

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);

		this.setPreferredSize(new Dimension(690, 211));

	}
}
