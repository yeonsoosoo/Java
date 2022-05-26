package basket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cart.Products;
import Main_Menu.Menu;
import Main_Menu.RoundedButton;
import main.Main;

public class Banner3 extends JPanel {

   List<Products> list;
   Frame f;
   
   public Banner3(   List<Products> list, Frame f) {
      
      this.list  = list;
      this.f = f;
      
      Font f1 = new Font(null, Font.BOLD, 15);
      Font f2 = new Font(null, Font.BOLD, 13);
      TextField del1 = new TextField(10);
      
      // �г� ���� �� ũ�� ����
      
      JPanel jp6 = new JPanel();
      jp6.setPreferredSize(new Dimension(700, 120));
      jp6.setBackground(Color.white);
//      jp6.setLayout(new FlowLayout());
   
      
      // �� ���� �� ũ�� ����
   Label l1 = new Label("������ ��ȣ");
   l1.setPreferredSize(new Dimension(80, 90));
   l1.setFont(f2);
    RoundedButton btnDel = new RoundedButton("�����ϱ�");
    btnDel.setPreferredSize(new Dimension(80, 35));
    
    Cart cart = new Cart(list);
    
    // �����ϱ�
    
    btnDel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         String str = cart.ta.getText();
         String[] arr = str.split("----------------------------------------------------------");
         try {
            int  n = Integer.parseInt(del1.getText())-1;
            if(list.get(n).getCount()== 1) {
               str = str.replace(arr[Integer.parseInt(del1.getText())-1], "");
               str = str.replace("----------------------------------------------------------", "");
               cart.ta.setText(str);
               list.remove(n);
            }else {
               list.get(n).setCount(list.get(n).getCount()-1);
            }
            
            if(list.size() == 0) {
               JOptionPane.showMessageDialog(f, "�ֹ��� ��ǰ�� �����ϴ�.\n��ǰ ���� ȭ������ ���ư��ϴ�.");
               f.removeAll();
               new Main_Menu.Main(f, list);
            }else {
               JOptionPane.showMessageDialog(f, "��ǰ�� �����Ǿ����ϴ�.");
               f.removeAll();
               new Basket(f, list);
            }
         } catch (Exception e2) {
            JOptionPane.showMessageDialog(f, "��ȣ�� Ȯ���ϼ���.");
         }
         
      }
   });
    
    del1.setBounds(100, 100, 100, 100);
    jp6.add(l1);
    jp6.add(del1);
    
    jp6.add(btnDel);
    
    this.add(jp6);
    
   
   
   
   }
}