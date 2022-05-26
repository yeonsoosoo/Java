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
      
      // 패널 생성 및 크기 조정
      
      JPanel jp6 = new JPanel();
      jp6.setPreferredSize(new Dimension(700, 120));
      jp6.setBackground(Color.white);
//      jp6.setLayout(new FlowLayout());
   
      
      // 라벨 생성 및 크기 조정
   Label l1 = new Label("삭제할 번호");
   l1.setPreferredSize(new Dimension(80, 90));
   l1.setFont(f2);
    RoundedButton btnDel = new RoundedButton("삭제하기");
    btnDel.setPreferredSize(new Dimension(80, 35));
    
    Cart cart = new Cart(list);
    
    // 삭제하기
    
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
               JOptionPane.showMessageDialog(f, "주문할 상품이 없습니다.\n상품 선택 화면으로 돌아갑니다.");
               f.removeAll();
               new Main_Menu.Main(f, list);
            }else {
               JOptionPane.showMessageDialog(f, "상품이 삭제되었습니다.");
               f.removeAll();
               new Basket(f, list);
            }
         } catch (Exception e2) {
            JOptionPane.showMessageDialog(f, "번호를 확인하세요.");
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