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

		// 결제
		if (file.exists()) {
			try {
				fis = new FileInputStream(path);
				fis.read(by);

				String str2 = new String(by);
				res = Integer.parseInt(str2);

				if (res >= money1) {
					res -= money1;

					JOptionPane.showMessageDialog(frame2, "주문이 완료되었습니다.");
					FileOutputStream fos = null;
					frame2.removeAll();
					new ThreadMain(frame2);
					
				} else {
					JOptionPane.showMessageDialog(frame2, "금액이 부족합니다.");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		} else {
			JOptionPane.showMessageDialog(frame2, "카드정보 오류입니다.");
		}

	}

}
