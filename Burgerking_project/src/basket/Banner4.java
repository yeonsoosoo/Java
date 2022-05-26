package basket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.util.List;

import javax.swing.JPanel;

import Cart.Products;

public class Banner4 extends JPanel{

   List<Products> list;
   Frame f;
   
   public Banner4(List<Products> list, Frame f) {
      this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
      this.list = list;
      this.f = f;
      
      Font font = new Font(Font.SERIF, Font.BOLD, 41);
      
      
      Label l1 = new Label("Burger King");
      l1.setFont(font);
      l1.setForeground(Color.white);
      JPanel jp7 = new JPanel();
      jp7.setPreferredSize(new Dimension(700, 120));
      jp7.setBackground(new Color(138, 0, 0));
      
      jp7.add(l1);
      
      this.add(jp7);
      
   }
   
   
}