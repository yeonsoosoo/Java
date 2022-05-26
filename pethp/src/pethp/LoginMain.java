package pethp;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Vector;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//�α��� ����â
public class LoginMain extends JFrame implements ActionListener{
	JTextField id;
	JPasswordField passwd;
	JButton log, clear, join, log_find, passwd_find, update;
	JPanel top, bottom, bottom_2;
	
	
	LoginMain(){
		setTitle("�α���");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		top = new JPanel();
		bottom = new JPanel();
		bottom_2 = new JPanel();
		top.setLayout(new GridLayout(7,1));
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottom_2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel main = new JLabel("����� ��������");
		Font f1 = new Font("SansSerif", Font.BOLD, 30);
		main.setFont(f1);
		title.add(main);
		
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); JPanel b3 = new JPanel(); //�����ֱ�
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout()); b3.setLayout(new FlowLayout()); //�����ֱ� 
		
		//�α��� ���̵� �Է�â
		JPanel p1 = new JPanel(); 
		p1.setLayout(new FlowLayout());
		JLabel l1 = new JLabel("���̵�   "); //�α��ξ��̵�
		id = new JTextField(10); //���̵� �Է�â
		clear = new JButton("CLEAR"); //���� ���� ��� �о������ ��ư
		p1.add(l1); p1.add(id); p1.add(clear);
		
		//�α��� ��й�ȣ �Է�â
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		JLabel l2 = new JLabel("��й�ȣ"); //�α��κ�й�ȣ
		log = new JButton("�α���");
		passwd = new JPasswordField(10); //��й�ȣ �Է�â
		p2.add(l2); p2.add(passwd); p2.add(log);
		
		//�ϴ� ȸ�� ����, ���̵�, ��й�ȣ ã��
		join = new JButton("ȸ������"); //ȸ������ ��ư
		log_find = new JButton("���̵� ã��"); //���̵� ã�� ��ư
		passwd_find = new JButton("��й�ȣ ã��"); //��й�ȣ ã�� ��ư
		update = new JButton("ȸ�� ���� ����");
		
		//��ư, �׼Ǹ����� ����
		clear.addActionListener(this); //textfield ���� ���� �� �ʱ�ȭ ��Ű��
		log.addActionListener(this); //�α��� �ϱ�
		join.addActionListener(this); //ȸ�������ϱ�
		log_find.addActionListener(this); //���̵� ã��
		passwd_find.addActionListener(this); //��й�ȣã��
		update.addActionListener(this); //ȸ������ ����
		
		bottom.add(join); bottom.add(log_find); bottom.add(passwd_find);
		bottom_2.add(update);
		top.add(b2); 
		top.add(p1); top.add(p2); top.add(b3);top.add(bottom); top.add(bottom_2);
		
		ct.add(title, BorderLayout.NORTH);
		ct.add(top, BorderLayout.CENTER);
		
		
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		//�޾ƿ� ���� ��
		if(s == "ȸ�� ���� ����") {
			UpdateMem um = new UpdateMem();
			um.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			um.setSize(400, 550);
			um.setLocation(400, 400);
			um.show();
		}else if(s == "CLEAR") {
			id.setText("");
			passwd.setText("");
		}else if(s == "ȸ������") {
			JoinMember JMem = new JoinMember();
			JMem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JMem.setSize(400, 500);
			JMem.setLocation(400, 400);
			JMem.show();
		}else if(s == "���̵� ã��") {
			FindId fi = new FindId();
			fi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fi.setSize(450, 350);
			fi.setLocation(400, 400);
			fi.show();
		}else if(s == "��й�ȣ ã��") {
			FindPasswd fp = new FindPasswd();
			fp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			
		}else { //�α����� ���� ���
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			}catch(ClassNotFoundException e){
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB ���� �Ϸ�");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
	            
	            
	            String strSql;	String t_id, t_passwd;
	            t_id = id.getText(); 	t_passwd = passwd.getText();
	            if(t_id.isEmpty() || t_passwd.isEmpty()) {
            		JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ��� �������ּ���","�α���", JOptionPane.ERROR_MESSAGE);
	            }else {
	            	strSql = "SELECT * FROM petHp_user WHERE user_id = '"+t_id+"' and passwd = '" + t_passwd + "';";
	            	
	            	ResultSet result = dbSt.executeQuery(strSql);
	            	if(result.next()) {
	            		String t_name = result.getString("user_name");
	            		JOptionPane.showMessageDialog(null, t_name + "�� ȯ���մϴ�.", "�α���", JOptionPane.INFORMATION_MESSAGE);
	            		//�α��� ������, ���� ȭ������ ����
	            		PethpMain winm = new PethpMain();
				winm.setTitle("����� ��������");
	            		winm.setSize(1000,750);
	            		winm.setLocation(300, 150);
	            		winm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            		winm.setVisible(true);
	            		winm.setResizable(false);
	            		dispose();
	            	
	            	}else {
	            		JOptionPane.showMessageDialog(null, "���̵� ��й�ȣ�� Ʋ�Ƚ��ϴ�.", "�α���", JOptionPane.ERROR_MESSAGE);
	            	}
	            }
	            dbSt.close();
	            con.close();
			}catch(SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			
		}
		
		
		
		
	}//actionPerformed end

//���̵� ã��
public class FindId extends JFrame implements ActionListener{
	
	JTextField name, p2, p3;
	String code[] = {"010", "070", "02", "031", "032"};
	JComboBox p1;
	JButton find, clear;
	JPanel top, btn;
	
	FindId(){
		setTitle("���̵� ã��");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		top = new JPanel();
		btn = new JPanel();
		
		top.setLayout(new GridLayout(7,1));
		btn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JPanel title_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lt = new JLabel("���̵� ã��");	
		lt.setFont(f1);
		title_1.add(lt);
		
		
		//�����ִ� ���̾ƿ�
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); 
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout());
		//�̸�
		JPanel p_name = new JPanel();
		p_name.setLayout(new FlowLayout());
		JLabel l_name = new JLabel("�̸�");
		name = new JTextField(15);
		p_name.add(l_name); p_name.add(name);
		
		//��ȭ��ȣ
		JPanel p_hp = new JPanel();
		p_hp.setLayout(new FlowLayout());
		JLabel l_hp = new JLabel("��ȭ��ȣ");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		p_hp.add(l_hp); p_hp.add(p1); p_hp.add(p2); p_hp.add(p3);
		
		//��ư ����
		
		find = new JButton("ã��");
		clear = new JButton("CLEAR");
		
		//��ư �׼� ������ ����
		
		find.addActionListener(this);
		clear.addActionListener(this);
		
		btn.add(find); btn.add(clear);
		top.add(b1); top.add(b2); top.add(title_1); top.add(p_name); top.add(p_hp);
		
		ct.add(top, BorderLayout.CENTER);
		ct.add(btn, BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if(s == "CLEAR") {
			name.setText("");
			p2.setText("");
			p3.setText("");
		}else {
			try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
            }catch(ClassNotFoundException e){
               System.err.println("����̹� �ε忡 �����߽��ϴ�.");
            }
          try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
            System.out.println("DB ���� �Ϸ�");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
            
            String strSql; String t_name = name.getText(), t_tel = p1.getSelectedItem().toString(), t_tel_number = p2.getText() + p3.getText();
            String foundId;
            
            strSql = "SELECT * FROM pethp_user WHERE user_name ='"+t_name+"' and user_tel = '" +t_tel+"' and user_tel_number = '" +t_tel_number+"';";

            ResultSet result = dbSt.executeQuery(strSql);
            if(result.next()) {
            	foundId = result.getString("user_id");
            	JOptionPane.showMessageDialog(null, "���̵�� " + foundId + "�Դϴ�.", "���̵� ã��", JOptionPane.INFORMATION_MESSAGE);
            	
            	
            }else {
            	JOptionPane.showMessageDialog(null, "�ش�Ǵ� ���̵� ã�� �� �����ϴ�.", "���̵� ã��", JOptionPane.ERROR_MESSAGE);
            }

            dbSt.close();
            con.close();
         }catch(SQLException e){
            System.out.println("SQLException : " + e.getMessage());
         }
       }
	}


}


//��й�ȣ ã��
class FindPasswd extends JFrame{
	
	JTextField id,name, p2, p3;
	JPasswordField passwd, passwd_Ck;
	String code[] = {"010", "070", "02", "031", "032"};
	JComboBox p1;
	JButton find, clear;
	JPanel top, btn, firstPanel, se_top, se_btn;
   
	
	JButton se_clear, se_ok;
	public String foundId = "";
	
	JFrame f;
	Container ct; 
	CardLayout card;
	FindPasswd(){
		f = new JFrame("��й�ȣ �缳��");
		f.setVisible(true);
		f.setSize(450, 350);
		f.setLocation(400,400);
		ct = f.getContentPane();
		//ct.add(f);
		card = new CardLayout();
		ct.setLayout(card);
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		
		JPanel first = new JPanel(new BorderLayout());
		JPanel second = new JPanel(new BorderLayout());
		
		JPanel title_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lt = new JLabel("��й�ȣ ã��");	
		lt.setFont(f1);
		title_1.add(lt);
		

		//ù��° ī�� �ǳ� �߰�
		top = new JPanel(new GridLayout(7,1));
		btn = new JPanel();
		
		
		first.add(top, BorderLayout.CENTER);
		first.add(btn, BorderLayout.SOUTH);
		
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); //JPanel b3 = new JPanel();
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout()); 

		//���̵�
		JPanel p_id = new JPanel();
		p_id.setLayout(new FlowLayout());
		JLabel l_id = new JLabel("���̵�");
		id = new JTextField(18);
		p_id.add(l_id); p_id.add(id);
		
		//�̸�
		JPanel p_name = new JPanel();
		p_name.setLayout(new FlowLayout());
		JLabel l_name = new JLabel("�̸�");
		name = new JTextField(15);
		p_name.add(l_name); p_name.add(name);
		
		//��ȭ��ȣ
		JPanel p_hp = new JPanel();
		p_hp.setLayout(new FlowLayout());
		JLabel l_hp = new JLabel("��ȭ��ȣ");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		p_hp.add(l_hp); p_hp.add(p1); p_hp.add(p2); p_hp.add(p3);
		
		//��ư ����
		find = new JButton("ã��");
		clear = new JButton("CLEAR");
		
		//��ư �׼� ������ ����
		find.addActionListener(new firstAction());
		clear.addActionListener(new firstAction());
		
		btn.add(find); btn.add(clear);
		top.add(b2);
		top.add(title_1);  
		top.add(p_id); top.add(p_name); top.add(p_hp);
	
		//�ι�° ī��  ���̾ƿ�
		se_top = new JPanel(new GridLayout(7,1));
		se_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));	
		second.add(se_top, BorderLayout.CENTER);
		second.add(se_btn, BorderLayout.SOUTH);
		
		JPanel title_2 = new JPanel (new FlowLayout(FlowLayout.CENTER));
		JLabel se_b3 = new JLabel("��й�ȣ �缳��");
		se_b3.setFont(f1);
		title_2.add(se_b3);
		
		//�����ִ� ���̾ƿ�
		JPanel se_b1 = new JPanel(new FlowLayout()); JPanel se_b2 = new JPanel(new FlowLayout()); 

		//��й�ȣ
		JPanel p_pass = new JPanel();
		p_pass.setLayout(new FlowLayout());
		JLabel l_pass = new JLabel("��й�ȣ");
		passwd = new JPasswordField(15);
		p_pass.add(l_pass); p_pass.add(passwd);	
		
		//��й�ȣ �缳��
		JPanel p_Ck = new JPanel();
		p_Ck.setLayout(new FlowLayout());
		JLabel l_Ck = new JLabel("��Ȯ��");
		passwd_Ck = new JPasswordField(15);
		p_Ck.add(l_Ck); p_Ck.add(passwd_Ck); 	

		
		//�ϴ� ��ư ����
		se_clear = new JButton("CLEAR");
		se_ok = new JButton("�缳��");

		
		se_btn.add(se_ok); se_btn.add(se_clear);
		se_top.add(se_b1); se_top.add(se_b2); se_top.add(title_2); se_top.add(p_pass); se_top.add(p_Ck);	
	
		se_clear.addActionListener(new secondAction());
		se_ok.addActionListener(new secondAction());	
		
		ct.add(first,"1");
		ct.add(second,"2");
		
	}
	
	private class firstAction implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			String s = ae.getActionCommand();
			
			if(s == "CLEAR") {
				name.setText("");
				p2.setText("");
				p3.setText("");
			}else {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
	                System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
				}catch(ClassNotFoundException e) {
					System.err.println("����̹� �ε忡 �����߽��ϴ�.");
				}
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
		            System.out.println("DB ���� �Ϸ�");
		            Statement dbSt = con.createStatement();
		            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
		            
		            
		            String strSql; String t_id = id.getText(), t_name = name.getText(), t_tel = p1.getSelectedItem().toString(), t_tel_number = p2.getText() + p3.getText();
		            
		            
		            strSql = "SELECT * FROM pethp_user WHERE user_id = '"+t_id+"' and user_name = '"+t_name+"'  and user_tel = '" +t_tel+"' and user_tel_number = '" +t_tel_number+"';";
		            
		            ResultSet result = dbSt.executeQuery(strSql);
		            if(result.next()) {
		            	foundId = t_id;
		            	JOptionPane.showMessageDialog(null, "��й�ȣ �缳������ ��", "��й�ȣ ã��", JOptionPane.INFORMATION_MESSAGE);
		            	card.next(ct);
		            	
		            }else {
		            	JOptionPane.showMessageDialog(null, "ã�� �� ���� �����Դϴ�.", "��й�ȣ ã��", JOptionPane.ERROR_MESSAGE);
		            }
		            
		            dbSt.close();
		            con.close();
				}catch(SQLException e) {
					System.out.println("SQLException : " + e.getMessage());
				}
				
			}
			
			
		}
	}//end of firstAction
	
	private class secondAction implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			String ss = ae.getActionCommand();
			char[] g_passwd = passwd.getPassword();
			char[] g_passwd_ck = passwd_Ck.getPassword();
			String t_passwd = "";
			
			
			if(ss == "CLEAR") {
				passwd.setText("");
				passwd_Ck.setText("");
			}else {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
				}catch(ClassNotFoundException e){
					System.err.println("����̹� �ε忡 �����߽��ϴ�.");
				}
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
					System.out.println("DB ���� �Ϸ�");
					Statement dbSt = con.createStatement();
					System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
					
					String foundPw = null;
	            
					if(!Arrays.equals(g_passwd, g_passwd_ck)) {
						JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ʽ��ϴ�.", "��й�ȣ �缳��", JOptionPane.ERROR_MESSAGE);
	 	    			passwd.setText("");
	 	            	passwd_Ck.setText("");		
					}else {
						String strSql_2;
						strSql_2 = "SELECT * FROM pethp_user WHERE user_id = '"+foundId+"';";
						
						ResultSet result_2 = dbSt.executeQuery(strSql_2);
						if(result_2.next()) {
							 foundPw = result_2.getString("passwd");
						}
							
						for(int i = 0; i <g_passwd.length; i++)
	 	            		t_passwd += g_passwd[i];
						
						if(t_passwd.equals(foundPw)) {
							JOptionPane.showMessageDialog(null, "������ ���� ��й�ȣ�Դϴ�.", "��й�ȣ �缳��", JOptionPane.ERROR_MESSAGE);
		 	    			passwd.setText("");
		 	            	passwd_Ck.setText("");	
						}else {
							JOptionPane.showMessageDialog(null,  "��й�ȣ�� �����Ͽ����ϴ�.", "��й�ȣ �缳��", JOptionPane.INFORMATION_MESSAGE);
							strSql_2 = "UPDATE pethp_user SET passwd = '"+t_passwd+"' WHERE user_id = '"+foundId+"';";
							dbSt.executeUpdate(strSql_2);
							
							
						}
					
					}
					dispose();
					dbSt.close();
					con.close();
				}catch(SQLException e) {
					System.out.println("SQLException : " + e.getMessage());
				}
		
			}			
		}
	}//end of secondAction
	
	
}



public class JoinMember extends JFrame implements ActionListener{
	JTextField id, name, p2, p3;
	JPasswordField passwd, passwd_Ch;
	String code[] = {"010", "070", "02", "031", "032"};
	JComboBox p1;
	JButton Overlap, ok, cancel; 
	boolean Ck_Id = false, Ck_pw = false;
	
	
	JoinMember(){
		setTitle("ȸ������");
		Container ct = getContentPane();
		ct.setLayout(null); //������ǥ�� ����
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JLabel lt = new JLabel("ȸ�� ����");	
		lt.setFont(f1);
		
		//���̵� ��� ĭ
		JLabel l_id = new JLabel("���̵�");
		id = new JTextField(10);
		Overlap = new JButton("�ߺ� Ȯ��");
		
		
		//��й�ȣ ��� ĭ
		JLabel l_passwd = new JLabel("��й�ȣ");
		passwd = new JPasswordField(17);
		
		//��й�ȣ Ȯ�� ��� ĭ
		JLabel l_pasChk = new JLabel("��й�ȣ Ȯ��");
		passwd_Ch = new JPasswordField(15);
		
		
		//�̸� ��� ĭ
		JLabel l_name = new JLabel("�̸�  ");
		name = new JTextField(15);
		
		//��ȭ��ȣ ��� ĭ
		JLabel ph = new JLabel("��ȭ��ȣ");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		JLabel dash = new JLabel("-");
		
		ok = new JButton("���");
		cancel = new JButton("CLEAR");

		//JLabel ��ġ ����
		lt.setBounds(130,50, 150,50);
		l_id.setBounds(40, 120, 60, 20);
		l_passwd.setBounds(40, 170, 60, 20);
		l_pasChk.setBounds(40, 220, 90, 20);
		l_name.setBounds(40, 270, 60, 20);
		ph.setBounds(40, 320, 60, 20);
		
		
		//JTextField, JPasswordField ��ġ ����
		id.setBounds(140, 120, 140, 20);
		passwd.setBounds(140, 170, 140, 20);
		passwd_Ch.setBounds(140, 220, 140, 20);
		name.setBounds(140, 270, 140, 20);
		p1.setBounds(140, 320, 50, 20);
		p2.setBounds(200, 320, 50, 20);
		dash.setBounds(255,320,10,20);
		p3.setBounds(265, 320, 50, 20);
		
		//��ư ��ġ ����
		Overlap.setBounds(300,120,70,20);
		ok.setBounds(200, 400, 70, 30);
		cancel.setBounds(280, 400, 80, 30);
		
		
		ct.add(lt);
		ct.add(l_id);	ct.add(id);	ct.add(Overlap);
		ct.add(l_passwd);	ct.add(passwd);
		ct.add(l_pasChk);	ct.add(passwd_Ch); 
		ct.add(l_name);		ct.add(name);
		ct.add(ph);		ct.add(p1);	ct.add(p2);	ct.add(dash);	ct.add(p3);
		ct.add(ok); ct.add(cancel);
		
		Overlap.addActionListener(this); //���̵� �ߺ� Ȯ��
		ok.addActionListener(this); //ȸ������ 
		cancel.addActionListener(this); //â 
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_id ="", t_passwd ="", t_name = "", t_tel = "", t_tel_number = "";
		char[] g_passwd = passwd.getPassword();
		char[] g_passwd_ch = passwd_Ch.getPassword();
		
		
		if(s == "CLEAR") {
			id.setText(" ");
			passwd.setText(" ");
			passwd_Ch.setText(" ");
			name.setText(" ");
			p1.setSelectedItem((Object) code[0]);
			p2.setText(" ");
			p3.setText(" ");
		}else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			}catch(ClassNotFoundException e){
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB ���� �Ϸ�");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
	            String strSql; 
	            t_id = id.getText();
	            
	            
	            
	            if(s == "�ߺ� Ȯ��") {
	            	strSql = "SELECT * FROM petHp_user WHERE user_id = '" + t_id + "';";
		            Ck_Id = true;
		            int choice;
		            ResultSet result = dbSt.executeQuery(strSql);
		            while(result.next()) {
		            	Ck_Id = false; //�ߺ�Ȯ���� �´� ��� false�� �ٲ�
		            }
		            
		            if (Ck_Id) {
		            	choice = JOptionPane.showConfirmDialog(null, id.getText() + "�� ��� ������ ���̵��Դϴ�. ����Ͻðڽ��ϱ�?", "���̵� �ߺ� Ȯ��", JOptionPane.YES_NO_OPTION);
		            	if(choice == JOptionPane.YES_OPTION) {
		            		Ck_Id = true;
		            		id.setEnabled(false);
		            	}
		            	else {
		            		Ck_Id = false;
		            		id.setText(" ");
		            		id.setEnabled(true);
		            	}
		            }
		            else {
		            	JOptionPane.showMessageDialog(null, "�ߺ��� ���̵��Դϴ�.", "���̵� �ߺ� Ȯ��", JOptionPane.ERROR_MESSAGE);
		            	
		            }
		            
	            }//End of �ߺ�Ȯ��
	            
	           
	            if(s == "���") {
	            	
	            	 if(!Arrays.equals(g_passwd, g_passwd_ch)) {
	 	    			JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ʽ��ϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE);
	 	    			passwd.setText("");
	 	            	passwd_Ch.setText("");	
	 	            	Ck_pw=false;
	 	            }else {
	 	            	Ck_pw = true;
	 	            	for(int i = 0; i <g_passwd.length; i++)
	 	            		t_passwd += g_passwd[i];
	 	            }
	            	
	            	if(id.getText().isEmpty() || g_passwd.length == 0 || g_passwd_ch.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty())
	            		JOptionPane.showMessageDialog(null, "�� ĭ�� �ֽ��ϴ�.", "ȸ������", JOptionPane.ERROR_MESSAGE); // ��ĭ�� ������� ����ó��
	            	else if(Ck_Id == false || Ck_pw == false) {
	            			if(Ck_Id == false)
	            				JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ���� ���ּ���", "ȸ������", JOptionPane.ERROR_MESSAGE);
	            			else if(Ck_pw != true)
	            				JOptionPane.showMessageDialog(null, "��й�ȣ Ȯ�� ���ּ���", "ȸ������", JOptionPane.ERROR_MESSAGE);
	            	}else if((p2.getText().length() < 3 || p2.getText().length() > 5)|| p3.getText().length() != 4) {
	            		JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� Ȯ�����ּ���", "ȸ������", JOptionPane.ERROR_MESSAGE);
	            	}
	            	
	            	else {
	            		t_name = name.getText();
	            		t_tel = (String)p1.getSelectedItem(); t_tel_number = p2.getText() + p3.getText(); //t_tel_num_last = p3.getText();
	            		strSql = "INSERT INTO petHp_user (user_id, passwd, user_name, user_tel, user_tel_number) VALUES ('" + t_id + "','" + t_passwd + "','" + t_name + "','" + t_tel + "','" + t_tel_number+ "'); ";
		           
	           			dbSt.executeUpdate(strSql);
	           			System.out.println("������ ���� �Ϸ�");
	           			JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.", "ȸ�� ����", JOptionPane.INFORMATION_MESSAGE);
	           			
	           			dispose();
	           		}
	            	
	            }//end of ���
	            dbSt.close();
				con.close();
	        }catch(SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			
			
		}
		
	}//end of actionPerformed 

	
	
}//end of class



public class UpdateMem extends JFrame implements ActionListener{
	
	JTextField id, name, p2, p3;
	JPasswordField passwd, passwd_Ch;
	JComboBox p1;
	String code[] = {"010", "070", "02", "031", "032"};
	
	JButton delete, clear, ok, find;
	
	
	UpdateMem(){
		setTitle("ȸ�� ���� ����");
		Container ct = getContentPane();
		ct.setLayout(null); //������ǥ�� ����
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JLabel lt = new JLabel("ȸ�� ���� ����");	
		lt.setFont(f1);
		
		//���̵� ��� ĭ
		JLabel l_id = new JLabel("���̵�");
		id = new JTextField(10);
		find = new JButton("�˻�");
		
		//��й�ȣ ��� ĭ
		JLabel l_passwd = new JLabel("��й�ȣ");
		passwd = new JPasswordField(17);
		
		//��й�ȣ Ȯ�� ��� ĭ
		JLabel l_pasChk = new JLabel("��й�ȣ Ȯ��");
		passwd_Ch = new JPasswordField(15);
		
		//�̸� ��� ĭ
		JLabel l_name = new JLabel("�̸�  ");
		name = new JTextField(15);
		
		//��ȭ��ȣ ��� ĭ
		JLabel ph = new JLabel("��ȭ��ȣ");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		JLabel dash = new JLabel("-");
		
		ok = new JButton("���");
		clear = new JButton("CLEAR");
		delete = new JButton("����");

		//JLabel ��ġ ����
		lt.setBounds(125,50, 150,50);
		l_id.setBounds(40, 120, 60, 20);
		l_passwd.setBounds(40, 170, 60, 20);
		l_pasChk.setBounds(40, 220, 90, 20);
		l_name.setBounds(40, 270, 60, 20);
		ph.setBounds(40, 320, 60, 20);
		
		
		//JTextField, JPasswordField ��ġ ����
		id.setBounds(140, 120, 140, 20);
		passwd.setBounds(140, 170, 140, 20);
		passwd_Ch.setBounds(140, 220, 140, 20);
		name.setBounds(140, 270, 140, 20);
		p1.setBounds(140, 320, 50, 20);
		p2.setBounds(200, 320, 50, 20);
		dash.setBounds(255,320,10,20);
		p3.setBounds(265, 320, 50, 20);
		find.setBounds(300,120,70,20);
		
		//��ư ��ġ ����
		ok.setBounds(120, 420, 70,30);
		delete.setBounds(200, 420, 70, 30);
		clear.setBounds(280, 420, 80, 30);
		
		
		ct.add(lt);
		ct.add(l_id);	ct.add(id); ct.add(find);
		ct.add(l_passwd);	ct.add(passwd);
		ct.add(l_pasChk);	ct.add(passwd_Ch); 
		ct.add(l_name);		ct.add(name);
		ct.add(ph);		ct.add(p1);	ct.add(p2);	ct.add(dash);	ct.add(p3);
		ct.add(ok); ct.add(clear); ct.add(delete);
		
		find.addActionListener(this);//���̵� �˻�
		clear.addActionListener(this); //��������� �ʱ�ȭ
		ok.addActionListener(this); //���� Ȯ��
		delete.addActionListener(this); // ȸ������ ���� 
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_id = id.getText(), t_passwd = "", t_name = "", t_tel = "", t_tel_number="", t_p2 = "", t_p3 = "";
		char[] g_passwd = passwd.getPassword();
		char[] g_passwd_Ch = passwd_Ch.getPassword();
		
	
		
		if(s=="CLEAR") {
			id.setText("");
			passwd.setText("");
			passwd_Ch.setText("");
			name.setText("");
			p1.setSelectedItem(0);
			p2.setText("");
			p3.setText("");
			
		}else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			}catch(ClassNotFoundException e){
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB ���� �Ϸ�");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
				String strSql;
				ResultSet rs;
	            
				if(s == "�˻�") {
					strSql = "SELECT * FROM pethp_user WHERE user_id = '"+t_id+"';";
					rs = dbSt.executeQuery(strSql);
					if(rs.next()) {
						t_id = rs.getString("user_id");
						t_passwd = rs.getString("passwd");
						t_name = rs.getString("user_name");
						t_tel = rs.getString("user_tel");
						t_tel_number = rs.getString("user_tel_number");
					
					
						int startP = t_tel_number.length()/2;
						t_p2 = t_tel_number.substring(0, startP);
						t_p3 = t_tel_number.substring(t_tel_number.length()-4, t_tel_number.length());
					
						id.setText(t_id); id.setEnabled(true);
						passwd.setText(t_passwd);
						name.setText(t_name);
						p2.setText(t_p2);
						p3.setText(t_p3);
					
						int i = 0;
						while(code[i] == t_tel) {
							p1.setSelectedIndex(i);
							i++;
						}
					}else {
						JOptionPane.showMessageDialog(null, "������ �����ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
					}
				}else if(s=="���") {
	
						String showTxt;
						//�н����� üũ�� �ִ� ��� ��й�ȣ �缳���̶� ����. 
						if(g_passwd_Ch.length != 0 ) {
							if(!Arrays.equals(g_passwd, g_passwd_Ch)) {
								// Ȯ��â�� ���� ���� ���
								JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� �ʽ��ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
		 	    				passwd.setText("");
		 	            		passwd_Ch.setText("");
							}else if(id.getText().isEmpty() || g_passwd.length == 0 || g_passwd_Ch.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "�� ĭ�� �ֽ��ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
							}else{
								//��й�ȣ�� ��й�ȣ Ȯ��â�̶� ���� ���
								for(int i = 0; i<g_passwd.length; i++) {
									t_passwd += g_passwd[i];
								}
							
								t_name = name.getText();
								t_tel = p1.getSelectedItem().toString();
								t_tel_number = p2.getText() + p3.getText();
							
								showTxt = "�����Ͻ� ������ \n ���̵�: " + t_id + "\n�̸� : " + t_name +"\n ��ȭ��ȣ: " + t_tel + t_tel_number + "\n �� �½��ϱ�?";
								int choice = JOptionPane.showConfirmDialog(null, showTxt, "ȸ�� ���� ����", JOptionPane.YES_NO_OPTION);
							
								if(choice == JOptionPane.YES_OPTION) {
									strSql = "UPDATE pethp_user SET user_name = '"+t_name+"' , user_tel = '"+t_tel+"' , user_tel_number = '"+t_tel_number+"' , passwd = '"+t_passwd+"'WHERE user_id = '"+t_id+"';";
									dbSt.executeUpdate(strSql);
									JOptionPane.showMessageDialog(null, "������ ����Ǿ����ϴ�.", "ȸ�� ���� ����", JOptionPane.INFORMATION_MESSAGE);
									dispose();
								}
							}
						
						}else{
						//�н����� üũ�� �ƹ��͵� ������ ���� ���
							if(id.getText().isEmpty() || g_passwd.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty())
								JOptionPane.showMessageDialog(null, "�� ĭ�� �ֽ��ϴ�.", "ȸ�� ���� ����", JOptionPane.ERROR_MESSAGE);
							else {
								t_name = name.getText();
								t_tel = p1.getSelectedItem().toString();
								t_tel_number = p2.getText() + p3.getText();
						
								showTxt = "�����Ͻ� ������ \n ���̵�: " + t_id + "\n�̸� : " + t_name +"\n ��ȭ��ȣ: " + t_tel + t_tel_number + "\n �� �½��ϱ�?";
								int choice = JOptionPane.showConfirmDialog(null, showTxt, "ȸ�� ���� ����", JOptionPane.YES_NO_OPTION);
						
								if(choice == JOptionPane.YES_OPTION) {
								strSql = "UPDATE pethp_user SET user_name = '"+t_name+"' and user_tel = '"+t_tel+"' and user_tel_number = '"+t_tel_number+"' WHERE user_id = '"+t_id+"';";
								dbSt.executeUpdate(strSql);
								JOptionPane.showMessageDialog(null, "������ ����Ǿ����ϴ�.", "ȸ�� ���� ����", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								}
							}
						}//�н����� üũ �ƹ��͵� ���� ���ǹ� ��
					
				}else if(s == "����") {
					int choice = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "ȸ�� ���� ����", JOptionPane.YES_NO_OPTION);
				
					if(choice == JOptionPane.YES_OPTION) {
						strSql = "DELETE FROM pethp_user WHERE user_id = '"+t_id+"';";
						dbSt.execute(strSql);
						JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.", "ȸ�� ���� ����", JOptionPane.INFORMATION_MESSAGE);
						
						dispose();
					}
					
				}
				dbSt.close();
				con.close();
			}catch(SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			
		}//�ٱ� if
		
	}//actionPerformed 
	
}

	public static void main(String[] args) {
		LoginMain win = new LoginMain();
		win.setSize(450, 350);
		win.setLocation(400,400);
		win.show();
	}
}
