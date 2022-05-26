package basket;

import Cart.*;
import Main_Menu.*;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Payment extends Panel {

	int money = 0;
	String numStr = "";
	Font font = new Font(null, Font.BOLD, 25);
	Font font2 = new Font(null, Font.BOLD, 40);
	Font font3 = new Font(null, Font.BOLD, 20);
	List<Products> list;

	public Payment(Frame frame2, List<Products> list) {

		this.list = list;

		for (int i = 0; i < list.size(); i++) {
			money += Integer.parseInt(list.get(i).getPrice());
		}

		frame2.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 20));

		Payment_img pm = new Payment_img();

		// 번호 패널
		JPanel pCenter = new JPanel();
		pCenter.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		pCenter.setLayout(new BorderLayout());
		pCenter.setPreferredSize(new Dimension(550, 50));
		pCenter.setFont(font);
		JLabel cardnum = new JLabel("카드번호");
		cardnum.setForeground(new Color(255, 255, 255));
		cardnum.setPreferredSize(new Dimension(100, 30));
		cardnum.setHorizontalAlignment(JLabel.CENTER);
		TextField tf = new TextField(10);

		pCenter.setBackground(new Color(0, 0, 0, 0));

		tf.setEditable(false);

		pCenter.add(cardnum, BorderLayout.WEST);
		pCenter.add(tf, BorderLayout.CENTER);

		// 버튼 이벤트 발생
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				numStr += e.getActionCommand().trim();
				tf.setText(numStr);
			}
		};

		// 숫자 버튼
		JPanel pNum1 = new JPanel();
		pNum1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		pNum1.setPreferredSize(new Dimension(580, 500));
		pNum1.setLayout(new GridLayout(4, 3, 10, 10));

		pNum1.setBackground(new Color(79, 38, 22));

		RoundedButton2 btnNum1 = new RoundedButton2(" 1 ");
		RoundedButton2 btnNum2 = new RoundedButton2(" 2 ");
		RoundedButton2 btnNum3 = new RoundedButton2(" 3 ");
		RoundedButton2 btnNum4 = new RoundedButton2(" 4 ");
		RoundedButton2 btnNum5 = new RoundedButton2(" 5 ");
		RoundedButton2 btnNum6 = new RoundedButton2(" 6 ");
		RoundedButton2 btnNum7 = new RoundedButton2(" 7 ");
		RoundedButton2 btnNum8 = new RoundedButton2(" 8 ");
		RoundedButton2 btnNum9 = new RoundedButton2(" 9 ");
		RoundedButton2 btnNumDel = new RoundedButton2(" DEL ");
		RoundedButton2 btnNum0 = new RoundedButton2(" 0 ");
		RoundedButton2 btnNumOk = new RoundedButton2(" OK ");

		btnNum1.setFont(font);
		btnNum2.setFont(font);
		btnNum3.setFont(font);
		btnNum4.setFont(font);
		btnNum5.setFont(font);
		btnNum6.setFont(font);
		btnNum7.setFont(font);
		btnNum8.setFont(font);
		btnNum9.setFont(font);
		btnNumDel.setFont(font);
		btnNum0.setFont(font);
		btnNumOk.setFont(font);

		btnNum1.addActionListener(al);
		btnNum2.addActionListener(al);
		btnNum3.addActionListener(al);
		btnNum4.addActionListener(al);
		btnNum5.addActionListener(al);
		btnNum6.addActionListener(al);
		btnNum7.addActionListener(al);
		btnNum8.addActionListener(al);
		btnNum9.addActionListener(al);
		btnNum0.addActionListener(al);

		btnNumDel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numStr.length() > 0) {

					numStr = numStr.substring(0, numStr.length() - 1);
					tf.setText(numStr);
				}
			}
		});

		pNum1.add(btnNum1);
		pNum1.add(btnNum2);
		pNum1.add(btnNum3);
		pNum1.add(btnNum4);
		pNum1.add(btnNum5);
		pNum1.add(btnNum6);
		pNum1.add(btnNum7);
		pNum1.add(btnNum8);
		pNum1.add(btnNum9);
		pNum1.add(btnNumDel);
		pNum1.add(btnNum0);
		pNum1.add(btnNumOk);

		// 결제 버튼
		JPanel pS = new JPanel();
		pS.setBackground(new Color(79, 38, 22));
		pS.setBorder(BorderFactory.createEmptyBorder(30, 40, 0, 0));
		pS.setPreferredSize(new Dimension(550, 150));
		RoundedButton3 home = new RoundedButton3("Home");
		home.setFont(font3);
		home.setPreferredSize(new Dimension(250, 55));

		pS.add(home);

		frame2.add(pm);
		frame2.add(pCenter);
		frame2.add(pNum1);
		frame2.add(pS);

		// 홈으로 이동
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame2.removeAll();
				new Main(frame2, list);
				frame2.revalidate();
				frame2.repaint();
			}
		});

		// TF에 카드번호 입력 후 결제완료 버튼 활성화
		tf.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				if (tf.getText().equals("")) {
					btnNumOk.setEnabled(false);
				} else {
					btnNumOk.setEnabled(true);
				}

			}
		});

		//결제 완료
		btnNumOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new Card(tf.getText(), frame2, money, list);
				tf.setText("");
				numStr = "";
				tf.requestFocusInWindow();
			}
		});
		frame2.setVisible(true);
		frame2.setBackground(new Color(79, 38, 22));

	}

}
