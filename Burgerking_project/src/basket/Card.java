package basket;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

import Cart.Products;
import Main_Menu.Main;
import mainPage.ThreadMain;

public class Card {

	static int res = 0;
	List<Products> list;

	public Card(String cardNum, Frame frame2, int money1, List list) {
		String path = "./card/" + cardNum + ".txt";

		this.list = list;
		
		File file = new File(path);
		byte[] by = new byte[(int) file.length()];

		FileInputStream fis = null;

		// ����
		if (file.exists()) {
			try {
				fis = new FileInputStream(path);
				fis.read(by);

				String str2 = new String(by);
				res = Integer.parseInt(str2);

				if (res >= money1) {
					res -= money1;

					JOptionPane.showMessageDialog(frame2, "�ֹ��� �Ϸ�Ǿ����ϴ�.");
					FileOutputStream fos = null;
					frame2.removeAll();
					new ThreadMain(frame2);
					
				} else {
					JOptionPane.showMessageDialog(frame2, "�ݾ��� �����մϴ�.");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		} else {
			JOptionPane.showMessageDialog(frame2, "ī������ �����Դϴ�.");
		}

	}

}
