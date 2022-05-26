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

	public String[] rowData1 = { "제품명 ", "수량", "가격" };
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
		
		JLabel jl1 = new JLabel("카트");
		
		jl1.setFont(f1);

		jp1.add(jl1, BorderLayout.CENTER);

		JPanel jp2 = new JPanel();
		jl2 = new JLabel("총 주문금액 " + won + "원");
		jl2.setFont(f2);
		jl2.setHorizontalAlignment(JLabel.CENTER);
		jp2.add(jl2);

		jp2.setPreferredSize(new Dimension(630, 70));

		JPanel jp3 = new JPanel();
		jp3.setPreferredSize(new Dimension(630, 57));
		jp3.setLayout(new FlowLayout());
		RoundedButton btnClose = new RoundedButton("카트 비우기");
		RoundedButton btnOk = new RoundedButton("결제하기");

		btnClose.setFont(f1);
		btnOk.setFont(f1);

		btnClose.setPreferredSize(new Dimension(300, 55));
		btnOk.setPreferredSize(new Dimension(300, 55));

		jp3.add(btnClose);
		jp3.add(btnOk);

		// 취소 시 메인 가격과 리스트 초기화
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (list.size() != 0) {
					ResetThread.reset();
					list.clear();
					Main.mp.won = 0;
					Main.mp.jl2.setText("총 주문금액 " + Main.mp.won + "원");
					JOptionPane.showMessageDialog(null, "주문 목록이 초기화되었습니다. \n제품을 다시 선택해주세요.");
				} else {
					JOptionPane.showMessageDialog(null, "주문 목록이 없습니다.");
				}
				
			}
		});

		// 결제하기 누를 시 결제화면 연결
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(list.size() + "리스트 사이즈");
				if (!list.isEmpty()) {
					ResetThread.reset();
					f.removeAll();
					Basket ba = new Basket(f, list);
					f.revalidate();
					f.repaint();
				} else {
					JOptionPane.showMessageDialog(f, "카트가 비어있습니다.", "카트 확인", JOptionPane.WARNING_MESSAGE);

					System.out.println("카트 비어있음");
				}
			}
		});

		this.add(jp1);
		this.add(jp2);
		this.add(jp3);

		this.setPreferredSize(new Dimension(690, 211));

	}
}
