package coupon;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Main_Menu.Main;
import basket.Payment;
import basket.RoundedButton2;
import main.ResetThread;
import mainPage.ThreadMain;

public class Coupon extends Panel {
   static String numStr = "";
   Frame f;
   List list;
   
   public Coupon(Frame f, List list) {
      // TODO Auto-generated constructor stub
      Font font = new Font(null, Font.BOLD, 25);

      this.f = f;
      this.list = list;
      
      this.setBounds(500, 30, 700, 1000);
      this.setLayout(null);
      this.setBackground(new Color(79,38,22));
      
      // ������ȣ ������ �г�
      ImageIcon img = new ImageIcon("./img/Main_img1.png");
      JLabel bn = new JLabel(img);
      bn.setBounds(0, 0, 700, 160);
      
      Panel p1 = new Panel();
      p1.setFont(font);
      p1.setBackground(new Color(79,38,22));
      p1.setBounds(100, 200, 500, 55);

      Label coupnum = new Label("���� ��ȣ");
      TextField tf = new TextField(23);
      coupnum.setForeground(Color.white);
      tf.setEditable(false);

      p1.add(coupnum);
      p1.add(tf);
      
      GridLayout grid = new GridLayout(4, 3, 10, 10);
      
      Panel p2 = new Panel();
      p2.setBounds(100, 280, 500, 500);
      p2.setLayout(grid);
      p2.setBackground(new Color(79,38,22));

      RoundedButton2 btn1 = new RoundedButton2(" 1 ");
      btn1.setFont(font);
      
      RoundedButton2 btn2 = new RoundedButton2(" 2 ");
      btn2.setFont(font);
      
      RoundedButton2 btn3 = new RoundedButton2(" 3 ");
      btn3.setFont(font);
      
      RoundedButton2 btn4 = new RoundedButton2(" 4 ");
      btn4.setFont(font);
      
      RoundedButton2 btn5 = new RoundedButton2(" 5 ");
      btn5.setFont(font);
      
      RoundedButton2 btn6 = new RoundedButton2(" 6 ");
      btn6.setFont(font);
      
      RoundedButton2 btn7 = new RoundedButton2(" 7 ");
      btn7.setFont(font);
      
      RoundedButton2 btn8 = new RoundedButton2(" 8 ");
      btn8.setFont(font);
      
      RoundedButton2 btn9 = new RoundedButton2(" 9 ");
      btn9.setFont(font);
      
      RoundedButton2 btnDel = new RoundedButton2(" Del ");
      btnDel.setFont(font);
      
      RoundedButton2 btn0 = new RoundedButton2(" 0 ");
      btn0.setFont(font);
      
      RoundedButton2 btnOk = new RoundedButton2(" OK ");
      btnOk.setFont(font);
      
      // ���� ��ư�� ��� �� �̺�Ʈ
      ActionListener al = new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            ResetThread.reset();
            numStr += e.getActionCommand().trim(); // �ش� ���ڹ�ư�� �� ���ڿ��� ������
            tf.setText(numStr); // ���� �� numStr�� �߰�

         }
      };

      btn1.addActionListener(al);
      btn2.addActionListener(al);
      btn3.addActionListener(al);
      btn4.addActionListener(al);
      btn5.addActionListener(al);
      btn6.addActionListener(al);
      btn7.addActionListener(al);
      btn8.addActionListener(al);
      btn9.addActionListener(al);
      btn0.addActionListener(al);

      // Ű�е�� �Է¹��� ���� ������ư
      btnDel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            ResetThread.reset();
            // Ű�е�� ���� ������ �� ���̿��� ������ư�� ���������� �� ���ھ� ����
            // �� ���̰� 0���� ������ ������� �ʴ´�.

            if (numStr.length() > 0) {
            	numStr = numStr.substring(0, numStr.length() - 1);
               tf.setText(numStr);
            }

         }
      });

      // ������ȣ Ȯ�ι�ư
      btnOk.setEnabled(false);

      // ������ȣ ��ġ�� ��쿡�� Ȯ�θ޼��� ����
      tf.addTextListener(new TextListener() {

         @Override
         public void textValueChanged(TextEvent e) {
            // tf�� ������ ����ִٸ� �Է¹�ư ��Ȱ��ȭ
            if (tf.getText().contentEquals("")) {
               btnOk.setEnabled(false);
            } else {
               btnOk.setEnabled(true);
            }

         }
      });

      // --------------------------------------------------------
      btnOk.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            ResetThread.reset();

            new Coupon_Num(tf.getText(), f, list);
            tf.setText(""); // TextField�� ���� ī���ȣ�� �Է������� ��ȣ�� �������� �ٽ� �Է�
            numStr = "";
            tf.requestFocusInWindow();

         }
      });
      
      RoundedButton2 btnBack = new RoundedButton2("Home");
      btnBack.setBounds(100, 815, 500, 80);
      btnBack.setFont(font);
      
      btnBack.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent arg0) {
            ResetThread.reset();
            tf.setText("");
            numStr = "";
            f.removeAll();
            Main main = new Main(f, list);
			f.revalidate();
			f.repaint();            
         }
      });
      

      p2.add(btn1);
      p2.add(btn2);
      p2.add(btn3);
      p2.add(btn4);
      p2.add(btn5);
      p2.add(btn6);
      p2.add(btn7);
      p2.add(btn8);
      p2.add(btn9);
      p2.add(btnDel);
      p2.add(btn0);
      p2.add(btnOk);
      this.add(p1);
      this.add(bn);
      this.add(p2);
      this.add(btnBack);
      f.add(this);
   }
}