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

//로그인 메인창
public class LoginMain extends JFrame implements ActionListener{
	JTextField id;
	JPasswordField passwd;
	JButton log, clear, join, log_find, passwd_find, update;
	JPanel top, bottom, bottom_2;
	
	
	LoginMain(){
		setTitle("로그인");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		top = new JPanel();
		bottom = new JPanel();
		bottom_2 = new JPanel();
		top.setLayout(new GridLayout(7,1));
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottom_2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel main = new JLabel("욕망의 동물병원");
		Font f1 = new Font("SansSerif", Font.BOLD, 30);
		main.setFont(f1);
		title.add(main);
		
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); JPanel b3 = new JPanel(); //공백주기
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout()); b3.setLayout(new FlowLayout()); //공백주기 
		
		//로그인 아이디 입력창
		JPanel p1 = new JPanel(); 
		p1.setLayout(new FlowLayout());
		JLabel l1 = new JLabel("아이디   "); //로그인아이디
		id = new JTextField(10); //아이디 입력창
		clear = new JButton("CLEAR"); //적은 정보 모두 밀어버리기 버튼
		p1.add(l1); p1.add(id); p1.add(clear);
		
		//로그인 비밀번호 입력창
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		JLabel l2 = new JLabel("비밀번호"); //로그인비밀번호
		log = new JButton("로그인");
		passwd = new JPasswordField(10); //비밀번호 입력창
		p2.add(l2); p2.add(passwd); p2.add(log);
		
		//하단 회원 가입, 아이디, 비밀번호 찾기
		join = new JButton("회원가입"); //회원가입 버튼
		log_find = new JButton("아이디 찾기"); //아이디 찾기 버튼
		passwd_find = new JButton("비밀번호 찾기"); //비밀번호 찾기 버튼
		update = new JButton("회원 정보 수정");
		
		//버튼, 액션리스너 연결
		clear.addActionListener(this); //textfield 적은 정보 다 초기화 시키기
		log.addActionListener(this); //로그인 하기
		join.addActionListener(this); //회원가입하기
		log_find.addActionListener(this); //아이디 찾기
		passwd_find.addActionListener(this); //비밀번호찾기
		update.addActionListener(this); //회원정보 수정
		
		bottom.add(join); bottom.add(log_find); bottom.add(passwd_find);
		bottom_2.add(update);
		top.add(b2); 
		top.add(p1); top.add(p2); top.add(b3);top.add(bottom); top.add(bottom_2);
		
		ct.add(title, BorderLayout.NORTH);
		ct.add(top, BorderLayout.CENTER);
		
		
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		//받아온 문단 비교
		if(s == "회원 정보 수정") {
			UpdateMem um = new UpdateMem();
			um.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			um.setSize(400, 550);
			um.setLocation(400, 400);
			um.show();
		}else if(s == "CLEAR") {
			id.setText("");
			passwd.setText("");
		}else if(s == "회원가입") {
			JoinMember JMem = new JoinMember();
			JMem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JMem.setSize(400, 500);
			JMem.setLocation(400, 400);
			JMem.show();
		}else if(s == "아이디 찾기") {
			FindId fi = new FindId();
			fi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fi.setSize(450, 350);
			fi.setLocation(400, 400);
			fi.show();
		}else if(s == "비밀번호 찾기") {
			FindPasswd fp = new FindPasswd();
			fp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			
		}else { //로그인을 했을 경우
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException e){
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB 연결 완료");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
	            
	            
	            String strSql;	String t_id, t_passwd;
	            t_id = id.getText(); 	t_passwd = passwd.getText();
	            if(t_id.isEmpty() || t_passwd.isEmpty()) {
            		JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 모두 기입해주세요","로그인", JOptionPane.ERROR_MESSAGE);
	            }else {
	            	strSql = "SELECT * FROM petHp_user WHERE user_id = '"+t_id+"' and passwd = '" + t_passwd + "';";
	            	
	            	ResultSet result = dbSt.executeQuery(strSql);
	            	if(result.next()) {
	            		String t_name = result.getString("user_name");
	            		JOptionPane.showMessageDialog(null, t_name + "님 환영합니다.", "로그인", JOptionPane.INFORMATION_MESSAGE);
	            		//로그인 성공시, 메인 화면으로 연결
	            		PethpMain winm = new PethpMain();
				winm.setTitle("욕망의 동물병원");
	            		winm.setSize(1000,750);
	            		winm.setLocation(300, 150);
	            		winm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            		winm.setVisible(true);
	            		winm.setResizable(false);
	            		dispose();
	            	
	            	}else {
	            		JOptionPane.showMessageDialog(null, "아이디나 비밀번호가 틀렸습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
	            	}
	            }
	            dbSt.close();
	            con.close();
			}catch(SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			
		}
		
		
		
		
	}//actionPerformed end

//아이디 찾기
public class FindId extends JFrame implements ActionListener{
	
	JTextField name, p2, p3;
	String code[] = {"010", "070", "02", "031", "032"};
	JComboBox p1;
	JButton find, clear;
	JPanel top, btn;
	
	FindId(){
		setTitle("아이디 찾기");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		top = new JPanel();
		btn = new JPanel();
		
		top.setLayout(new GridLayout(7,1));
		btn.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JPanel title_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lt = new JLabel("아이디 찾기");	
		lt.setFont(f1);
		title_1.add(lt);
		
		
		//여백주는 레이아웃
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); 
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout());
		//이름
		JPanel p_name = new JPanel();
		p_name.setLayout(new FlowLayout());
		JLabel l_name = new JLabel("이름");
		name = new JTextField(15);
		p_name.add(l_name); p_name.add(name);
		
		//전화번호
		JPanel p_hp = new JPanel();
		p_hp.setLayout(new FlowLayout());
		JLabel l_hp = new JLabel("전화번호");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		p_hp.add(l_hp); p_hp.add(p1); p_hp.add(p2); p_hp.add(p3);
		
		//버튼 생성
		
		find = new JButton("찾기");
		clear = new JButton("CLEAR");
		
		//버튼 액션 리스너 연결
		
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
                System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
            }catch(ClassNotFoundException e){
               System.err.println("드라이버 로드에 실패했습니다.");
            }
          try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
            System.out.println("DB 연결 완료");
            Statement dbSt = con.createStatement();
            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
            
            String strSql; String t_name = name.getText(), t_tel = p1.getSelectedItem().toString(), t_tel_number = p2.getText() + p3.getText();
            String foundId;
            
            strSql = "SELECT * FROM pethp_user WHERE user_name ='"+t_name+"' and user_tel = '" +t_tel+"' and user_tel_number = '" +t_tel_number+"';";

            ResultSet result = dbSt.executeQuery(strSql);
            if(result.next()) {
            	foundId = result.getString("user_id");
            	JOptionPane.showMessageDialog(null, "아이디는 " + foundId + "입니다.", "아이디 찾기", JOptionPane.INFORMATION_MESSAGE);
            	
            	
            }else {
            	JOptionPane.showMessageDialog(null, "해당되는 아이디를 찾을 수 없습니다.", "아이디 찾기", JOptionPane.ERROR_MESSAGE);
            }

            dbSt.close();
            con.close();
         }catch(SQLException e){
            System.out.println("SQLException : " + e.getMessage());
         }
       }
	}


}


//비밀번호 찾기
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
		f = new JFrame("비밀번호 재설정");
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
		JLabel lt = new JLabel("비밀번호 찾기");	
		lt.setFont(f1);
		title_1.add(lt);
		

		//첫번째 카드 판넬 추가
		top = new JPanel(new GridLayout(7,1));
		btn = new JPanel();
		
		
		first.add(top, BorderLayout.CENTER);
		first.add(btn, BorderLayout.SOUTH);
		
		JPanel b1 = new JPanel(); JPanel b2 = new JPanel(); //JPanel b3 = new JPanel();
		b1.setLayout(new FlowLayout()); b2.setLayout(new FlowLayout()); 

		//아이디
		JPanel p_id = new JPanel();
		p_id.setLayout(new FlowLayout());
		JLabel l_id = new JLabel("아이디");
		id = new JTextField(18);
		p_id.add(l_id); p_id.add(id);
		
		//이름
		JPanel p_name = new JPanel();
		p_name.setLayout(new FlowLayout());
		JLabel l_name = new JLabel("이름");
		name = new JTextField(15);
		p_name.add(l_name); p_name.add(name);
		
		//전화번호
		JPanel p_hp = new JPanel();
		p_hp.setLayout(new FlowLayout());
		JLabel l_hp = new JLabel("전화번호");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		p_hp.add(l_hp); p_hp.add(p1); p_hp.add(p2); p_hp.add(p3);
		
		//버튼 생성
		find = new JButton("찾기");
		clear = new JButton("CLEAR");
		
		//버튼 액션 리스너 연결
		find.addActionListener(new firstAction());
		clear.addActionListener(new firstAction());
		
		btn.add(find); btn.add(clear);
		top.add(b2);
		top.add(title_1);  
		top.add(p_id); top.add(p_name); top.add(p_hp);
	
		//두번째 카드  레이아웃
		se_top = new JPanel(new GridLayout(7,1));
		se_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));	
		second.add(se_top, BorderLayout.CENTER);
		second.add(se_btn, BorderLayout.SOUTH);
		
		JPanel title_2 = new JPanel (new FlowLayout(FlowLayout.CENTER));
		JLabel se_b3 = new JLabel("비밀번호 재설정");
		se_b3.setFont(f1);
		title_2.add(se_b3);
		
		//여백주는 레이아웃
		JPanel se_b1 = new JPanel(new FlowLayout()); JPanel se_b2 = new JPanel(new FlowLayout()); 

		//비밀번호
		JPanel p_pass = new JPanel();
		p_pass.setLayout(new FlowLayout());
		JLabel l_pass = new JLabel("비밀번호");
		passwd = new JPasswordField(15);
		p_pass.add(l_pass); p_pass.add(passwd);	
		
		//비밀번호 재설정
		JPanel p_Ck = new JPanel();
		p_Ck.setLayout(new FlowLayout());
		JLabel l_Ck = new JLabel("재확인");
		passwd_Ck = new JPasswordField(15);
		p_Ck.add(l_Ck); p_Ck.add(passwd_Ck); 	

		
		//하단 버튼 설정
		se_clear = new JButton("CLEAR");
		se_ok = new JButton("재설정");

		
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
	                System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
				}catch(ClassNotFoundException e) {
					System.err.println("드라이버 로드에 실패했습니다.");
				}
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
		            System.out.println("DB 연결 완료");
		            Statement dbSt = con.createStatement();
		            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
		            
		            
		            String strSql; String t_id = id.getText(), t_name = name.getText(), t_tel = p1.getSelectedItem().toString(), t_tel_number = p2.getText() + p3.getText();
		            
		            
		            strSql = "SELECT * FROM pethp_user WHERE user_id = '"+t_id+"' and user_name = '"+t_name+"'  and user_tel = '" +t_tel+"' and user_tel_number = '" +t_tel_number+"';";
		            
		            ResultSet result = dbSt.executeQuery(strSql);
		            if(result.next()) {
		            	foundId = t_id;
		            	JOptionPane.showMessageDialog(null, "비밀번호 재설정으로 감", "비밀번호 찾기", JOptionPane.INFORMATION_MESSAGE);
		            	card.next(ct);
		            	
		            }else {
		            	JOptionPane.showMessageDialog(null, "찾을 수 없는 정보입니다.", "비밀번호 찾기", JOptionPane.ERROR_MESSAGE);
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
					System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
				}catch(ClassNotFoundException e){
					System.err.println("드라이버 로드에 실패했습니다.");
				}
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
					System.out.println("DB 연결 완료");
					Statement dbSt = con.createStatement();
					System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
					
					String foundPw = null;
	            
					if(!Arrays.equals(g_passwd, g_passwd_ck)) {
						JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.", "비밀번호 재설정", JOptionPane.ERROR_MESSAGE);
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
							JOptionPane.showMessageDialog(null, "이전과 같은 비밀번호입니다.", "비밀번호 재설정", JOptionPane.ERROR_MESSAGE);
		 	    			passwd.setText("");
		 	            	passwd_Ck.setText("");	
						}else {
							JOptionPane.showMessageDialog(null,  "비밀번호를 변경하였습니다.", "비밀번호 재설정", JOptionPane.INFORMATION_MESSAGE);
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
		setTitle("회원가입");
		Container ct = getContentPane();
		ct.setLayout(null); //절대좌표로 설정
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JLabel lt = new JLabel("회원 가입");	
		lt.setFont(f1);
		
		//아이디 등록 칸
		JLabel l_id = new JLabel("아이디");
		id = new JTextField(10);
		Overlap = new JButton("중복 확인");
		
		
		//비밀번호 등록 칸
		JLabel l_passwd = new JLabel("비밀번호");
		passwd = new JPasswordField(17);
		
		//비밀번호 확인 등록 칸
		JLabel l_pasChk = new JLabel("비밀번호 확인");
		passwd_Ch = new JPasswordField(15);
		
		
		//이름 등록 칸
		JLabel l_name = new JLabel("이름  ");
		name = new JTextField(15);
		
		//전화번호 등록 칸
		JLabel ph = new JLabel("전화번호");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		JLabel dash = new JLabel("-");
		
		ok = new JButton("등록");
		cancel = new JButton("CLEAR");

		//JLabel 위치 설정
		lt.setBounds(130,50, 150,50);
		l_id.setBounds(40, 120, 60, 20);
		l_passwd.setBounds(40, 170, 60, 20);
		l_pasChk.setBounds(40, 220, 90, 20);
		l_name.setBounds(40, 270, 60, 20);
		ph.setBounds(40, 320, 60, 20);
		
		
		//JTextField, JPasswordField 위치 설정
		id.setBounds(140, 120, 140, 20);
		passwd.setBounds(140, 170, 140, 20);
		passwd_Ch.setBounds(140, 220, 140, 20);
		name.setBounds(140, 270, 140, 20);
		p1.setBounds(140, 320, 50, 20);
		p2.setBounds(200, 320, 50, 20);
		dash.setBounds(255,320,10,20);
		p3.setBounds(265, 320, 50, 20);
		
		//버튼 위치 설정
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
		
		Overlap.addActionListener(this); //아이디 중복 확인
		ok.addActionListener(this); //회원가입 
		cancel.addActionListener(this); //창 
		
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
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException e){
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB 연결 완료");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
	            String strSql; 
	            t_id = id.getText();
	            
	            
	            
	            if(s == "중복 확인") {
	            	strSql = "SELECT * FROM petHp_user WHERE user_id = '" + t_id + "';";
		            Ck_Id = true;
		            int choice;
		            ResultSet result = dbSt.executeQuery(strSql);
		            while(result.next()) {
		            	Ck_Id = false; //중복확인이 맞는 경우 false로 바꿈
		            }
		            
		            if (Ck_Id) {
		            	choice = JOptionPane.showConfirmDialog(null, id.getText() + "는 사용 가능한 아이디입니다. 사용하시겠습니까?", "아이디 중복 확인", JOptionPane.YES_NO_OPTION);
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
		            	JOptionPane.showMessageDialog(null, "중복된 아이디입니다.", "아이디 중복 확인", JOptionPane.ERROR_MESSAGE);
		            	
		            }
		            
	            }//End of 중복확인
	            
	           
	            if(s == "등록") {
	            	
	            	 if(!Arrays.equals(g_passwd, g_passwd_ch)) {
	 	    			JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.", "회원가입", JOptionPane.ERROR_MESSAGE);
	 	    			passwd.setText("");
	 	            	passwd_Ch.setText("");	
	 	            	Ck_pw=false;
	 	            }else {
	 	            	Ck_pw = true;
	 	            	for(int i = 0; i <g_passwd.length; i++)
	 	            		t_passwd += g_passwd[i];
	 	            }
	            	
	            	if(id.getText().isEmpty() || g_passwd.length == 0 || g_passwd_ch.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty())
	            		JOptionPane.showMessageDialog(null, "빈 칸이 있습니다.", "회원가입", JOptionPane.ERROR_MESSAGE); // 빈칸이 있을경우 예외처리
	            	else if(Ck_Id == false || Ck_pw == false) {
	            			if(Ck_Id == false)
	            				JOptionPane.showMessageDialog(null, "아이디 중복확인을 해주세요", "회원가입", JOptionPane.ERROR_MESSAGE);
	            			else if(Ck_pw != true)
	            				JOptionPane.showMessageDialog(null, "비밀번호 확인 해주세요", "회원가입", JOptionPane.ERROR_MESSAGE);
	            	}else if((p2.getText().length() < 3 || p2.getText().length() > 5)|| p3.getText().length() != 4) {
	            		JOptionPane.showMessageDialog(null, "전화번호를 확인해주세요", "회원가입", JOptionPane.ERROR_MESSAGE);
	            	}
	            	
	            	else {
	            		t_name = name.getText();
	            		t_tel = (String)p1.getSelectedItem(); t_tel_number = p2.getText() + p3.getText(); //t_tel_num_last = p3.getText();
	            		strSql = "INSERT INTO petHp_user (user_id, passwd, user_name, user_tel, user_tel_number) VALUES ('" + t_id + "','" + t_passwd + "','" + t_name + "','" + t_tel + "','" + t_tel_number+ "'); ";
		           
	           			dbSt.executeUpdate(strSql);
	           			System.out.println("데이터 삽입 완료");
	           			JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.", "회원 가입", JOptionPane.INFORMATION_MESSAGE);
	           			
	           			dispose();
	           		}
	            	
	            }//end of 등록
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
		setTitle("회원 정보 수정");
		Container ct = getContentPane();
		ct.setLayout(null); //절대좌표로 설정
		
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		JLabel lt = new JLabel("회원 정보 수정");	
		lt.setFont(f1);
		
		//아이디 등록 칸
		JLabel l_id = new JLabel("아이디");
		id = new JTextField(10);
		find = new JButton("검색");
		
		//비밀번호 등록 칸
		JLabel l_passwd = new JLabel("비밀번호");
		passwd = new JPasswordField(17);
		
		//비밀번호 확인 등록 칸
		JLabel l_pasChk = new JLabel("비밀번호 확인");
		passwd_Ch = new JPasswordField(15);
		
		//이름 등록 칸
		JLabel l_name = new JLabel("이름  ");
		name = new JTextField(15);
		
		//전화번호 등록 칸
		JLabel ph = new JLabel("전화번호");
		p1 = new JComboBox(code);
		p2 = new JTextField(6);
		p3 = new JTextField(6);
		JLabel dash = new JLabel("-");
		
		ok = new JButton("등록");
		clear = new JButton("CLEAR");
		delete = new JButton("삭제");

		//JLabel 위치 설정
		lt.setBounds(125,50, 150,50);
		l_id.setBounds(40, 120, 60, 20);
		l_passwd.setBounds(40, 170, 60, 20);
		l_pasChk.setBounds(40, 220, 90, 20);
		l_name.setBounds(40, 270, 60, 20);
		ph.setBounds(40, 320, 60, 20);
		
		
		//JTextField, JPasswordField 위치 설정
		id.setBounds(140, 120, 140, 20);
		passwd.setBounds(140, 170, 140, 20);
		passwd_Ch.setBounds(140, 220, 140, 20);
		name.setBounds(140, 270, 140, 20);
		p1.setBounds(140, 320, 50, 20);
		p2.setBounds(200, 320, 50, 20);
		dash.setBounds(255,320,10,20);
		p3.setBounds(265, 320, 50, 20);
		find.setBounds(300,120,70,20);
		
		//버튼 위치 설정
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
		
		find.addActionListener(this);//아이디 검색
		clear.addActionListener(this); //적어놓은거 초기화
		ok.addActionListener(this); //수정 확인
		delete.addActionListener(this); // 회원정보 삭제 
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
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			}catch(ClassNotFoundException e){
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB 연결 완료");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				String strSql;
				ResultSet rs;
	            
				if(s == "검색") {
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
						JOptionPane.showMessageDialog(null, "정보가 없습니다.", "회원 정보 수정", JOptionPane.ERROR_MESSAGE);
					}
				}else if(s=="등록") {
	
						String showTxt;
						//패스워드 체크가 있는 경우 비밀번호 재설정이라 간주. 
						if(g_passwd_Ch.length != 0 ) {
							if(!Arrays.equals(g_passwd, g_passwd_Ch)) {
								// 확인창이 같지 않은 경우
								JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다.", "회원 정보 수정", JOptionPane.ERROR_MESSAGE);
		 	    				passwd.setText("");
		 	            		passwd_Ch.setText("");
							}else if(id.getText().isEmpty() || g_passwd.length == 0 || g_passwd_Ch.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "빈 칸이 있습니다.", "회원 정보 수정", JOptionPane.ERROR_MESSAGE);
							}else{
								//비밀번호와 비밀번호 확인창이랑 같은 경우
								for(int i = 0; i<g_passwd.length; i++) {
									t_passwd += g_passwd[i];
								}
							
								t_name = name.getText();
								t_tel = p1.getSelectedItem().toString();
								t_tel_number = p2.getText() + p3.getText();
							
								showTxt = "변경하실 정보가 \n 아이디: " + t_id + "\n이름 : " + t_name +"\n 전화번호: " + t_tel + t_tel_number + "\n 가 맞습니까?";
								int choice = JOptionPane.showConfirmDialog(null, showTxt, "회원 정보 수정", JOptionPane.YES_NO_OPTION);
							
								if(choice == JOptionPane.YES_OPTION) {
									strSql = "UPDATE pethp_user SET user_name = '"+t_name+"' , user_tel = '"+t_tel+"' , user_tel_number = '"+t_tel_number+"' , passwd = '"+t_passwd+"'WHERE user_id = '"+t_id+"';";
									dbSt.executeUpdate(strSql);
									JOptionPane.showMessageDialog(null, "정보가 변경되었습니다.", "회원 정보 수정", JOptionPane.INFORMATION_MESSAGE);
									dispose();
								}
							}
						
						}else{
						//패스워드 체크에 아무것도 적히지 않은 경우
							if(id.getText().isEmpty() || g_passwd.length == 0 || name.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty())
								JOptionPane.showMessageDialog(null, "빈 칸이 있습니다.", "회원 정보 수정", JOptionPane.ERROR_MESSAGE);
							else {
								t_name = name.getText();
								t_tel = p1.getSelectedItem().toString();
								t_tel_number = p2.getText() + p3.getText();
						
								showTxt = "변경하실 정보가 \n 아이디: " + t_id + "\n이름 : " + t_name +"\n 전화번호: " + t_tel + t_tel_number + "\n 가 맞습니까?";
								int choice = JOptionPane.showConfirmDialog(null, showTxt, "회원 정보 수정", JOptionPane.YES_NO_OPTION);
						
								if(choice == JOptionPane.YES_OPTION) {
								strSql = "UPDATE pethp_user SET user_name = '"+t_name+"' and user_tel = '"+t_tel+"' and user_tel_number = '"+t_tel_number+"' WHERE user_id = '"+t_id+"';";
								dbSt.executeUpdate(strSql);
								JOptionPane.showMessageDialog(null, "정보가 변경되었습니다.", "회원 정보 수정", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								}
							}
						}//패스워드 체크 아무것도 기입 조건문 끝
					
				}else if(s == "삭제") {
					int choice = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "회원 정보 수정", JOptionPane.YES_NO_OPTION);
				
					if(choice == JOptionPane.YES_OPTION) {
						strSql = "DELETE FROM pethp_user WHERE user_id = '"+t_id+"';";
						dbSt.execute(strSql);
						JOptionPane.showMessageDialog(null, "삭제되었습니다.", "회원 정보 수정", JOptionPane.INFORMATION_MESSAGE);
						
						dispose();
					}
					
				}
				dbSt.close();
				con.close();
			}catch(SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			
		}//바깥 if
		
	}//actionPerformed 
	
}

	public static void main(String[] args) {
		LoginMain win = new LoginMain();
		win.setSize(450, 350);
		win.setLocation(400,400);
		win.show();
	}
}
