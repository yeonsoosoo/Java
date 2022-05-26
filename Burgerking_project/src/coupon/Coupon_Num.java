package coupon;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import javax.swing.JOptionPane;

import Cart.Products;
import Main_Menu.Main;
import Main_Menu.Main_Pay;

// 공차 Card 참고
public class Coupon_Num {

	static int res = 0;
	List<Products> list;

	public Coupon_Num(String coupnum, Frame f, List<Products> list) {
		String path = "./coupon/" + coupnum + ".txt";

		File file = new File(path);
		byte[] br = new byte[(int) file.length()];

		this.list = list;
		
		if (file.exists()) {

			try {
				BufferedReader buf = new BufferedReader(new FileReader(path));

				String str;
				String pName = "";
				String pPrice = "";
				
				int i=0;
				while ((str = buf.readLine()) != null) {
					if(i==0) {
						pName = str;
					} else if(i == 1) {
						pPrice = str;
					}
					i++;
				}
				
				String cn = file.getName();
				Products pro = new Products();
				
				pro.setName(pName);
				pro.setCount(1);
				pro.setPrice(pPrice);
				list.add(pro);
				Main.mp.won += Integer.parseInt(pPrice);

				if (Integer.parseInt(cn.substring(0, 8)) == Integer.parseInt(coupnum)) {
					JOptionPane.showMessageDialog(f, "주문화면으로 이동합니다.");
					f.removeAll();
					Main main = new Main(f, list);
					f.revalidate();
					f.repaint();
				}

				buf.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(f, "잘못입력하셨습니다.");
		}
	}
}
