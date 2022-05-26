package pethp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class PatientInfo extends JFrame{
	JComboBox index;
	String[] code = {"ȯ�� �̸�", "��ȣ�� �̸�"};
	JTextField cont;
	JButton search, newPet, reserve, admin, treList, DELE;
	
	Vector<String> colName_1, colName_2;
	Vector<Vector<String>> rowData_1, rowData_2;
	
	JTable PatientList = null, TimeTable = null;
	DefaultTableModel model_1 = null, model_2 = null;
	JScrollPane jsp_1, jsp_2;
	
	String g_id, g_name;
	int row, col;
	
	//���� ���̺��� ���� �� ��¥ �����ֱ� ���� �� ��¥ �޾ƿ�
	Calendar cal = Calendar.getInstance();
	int to_year = cal.get(Calendar.YEAR);
	int to_month = cal.get(Calendar.MONTH) + 1;
	int to_day = cal.get(Calendar.DATE);
	
	PatientInfo(){
		setTitle("��������");
		this.addWindowListener(new showTable());
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		
		JPanel Left = new JPanel();
		JPanel Right = new JPanel();
		
		BoxLayout lf = new BoxLayout(Left, BoxLayout.Y_AXIS);
		BoxLayout rg = new BoxLayout(Right,BoxLayout.Y_AXIS);
		JPanel b1 = new JPanel();
		b1.setPreferredSize(new Dimension(1000,30));
		Right.setPreferredSize(new Dimension(250, 510));
		
		Left.setLayout(lf);
		Right.setLayout(rg);
		
		ct.add(b1);
		ct.add(Left); ct.add(Right);
		
		//������ ��ġ�� �κ�

		//�˻�â
		JPanel Search = new JPanel(new FlowLayout(FlowLayout.LEFT));
		index = new JComboBox(code);
		cont = new JTextField(30);
		search = new JButton("�˻�");
		DELE = new JButton("����");
		Search.add(index); Search.add(cont); Search.add(search); Search.add(DELE);
		DELE.setEnabled(false);
		
		//ȯ�� ����â
		JPanel Plist = new JPanel(new FlowLayout());
		colName_1 = new Vector<String>();
		rowData_1 = new Vector<Vector<String>>();
		colName_1.add("ȯ�� �ڵ�"); colName_1.add("ȯ�� �̸�"); colName_1.add("��ȣ�� �̸�"); colName_1.add("��ȣ�� ��ȣ");
		model_1 = new DefaultTableModel(rowData_1, colName_1);
		PatientList = new JTable(model_1) {
				public boolean isCellEditable(int rowIndex, int mColIndex){
					return false;
				}//�� ���� ����
	      };
		
		jsp_1 = new JScrollPane(PatientList);
		jsp_1.setPreferredSize(new Dimension(650,500));
		Plist.add(jsp_1);
		
		PatientList.setRowHeight(30);
		
		
		//���̺� �� width ����
		TableColumnModel pl = PatientList.getColumnModel();
		pl.getColumn(0).setPreferredWidth(100);
		pl.getColumn(1).setPreferredWidth(200);
		pl.getColumn(2).setPreferredWidth(150);
		pl.getColumn(3).setPreferredWidth(200);
		
		PatientList.getTableHeader().setReorderingAllowed(false); //prevent re-ordering
		PatientList.getTableHeader().setResizingAllowed(false); //prevent re-sizing
		
		Left.add(Search); Left.add(Plist);
		
		DELE.addActionListener(new btnConnect());
		search.addActionListener(new showTable());
		PatientList.addMouseListener(new tableClick());
		
		
		//������ ��ġ�� �κ�
		JPanel b2 = new JPanel();
		b2.setPreferredSize(new Dimension(250, 18));
		//�ð�ǥ
		JPanel Time = new JPanel();
		colName_2 = new Vector<String>();
		rowData_2 = new Vector<Vector<String>>();
		colName_2.add("�ð�"); colName_2.add("��ȣ��");
		model_2 = new DefaultTableModel(rowData_2, colName_2);
		TimeTable = new JTable(model_2) {
				public boolean isCellEditable(int rowIndex, int mColIndex){
					return false;
				}
		};
		jsp_2 = new JScrollPane(TimeTable);
		jsp_2.setPreferredSize(new Dimension(250, 400));
		Time.add(jsp_2);
		
		//���̺� �� width ����
		TableColumnModel tt = TimeTable.getColumnModel();
		tt.getColumn(0).setPreferredWidth(70);
		tt.getColumn(1).setPreferredWidth(180);
		
		TimeTable.getTableHeader().setReorderingAllowed(false); //prevent re-ordering
		TimeTable.getTableHeader().setResizingAllowed(false); //prevent re-sizing
		
		
		//��ư
		JPanel Btn = new JPanel(new GridLayout(2,2, 3, 3));
		Btn.setPreferredSize(new Dimension(250,80));
		newPet = new JButton("�ű� ���");
		reserve = new JButton("�ű� ����");
		admin = new JButton("�Կ�");
		treList = new JButton("����");
		Btn.add(newPet); Btn.add(reserve); Btn.add(admin); Btn.add(treList);
		
		//��ư �⺻�� ��� �Ұ���
		reserve.setEnabled(false);
		admin.setEnabled(false);
		treList.setEnabled(false);
		
		Right.add(b2);
		Right.add(Time); Right.add(Btn);
		

		//�� ������ ������ �� ���ʿ��� ��ư ����
		index.addFocusListener(new btnEnabled());
		cont.addFocusListener(new btnEnabled());
		TimeTable.addFocusListener(new btnEnabled());
		
		//��ư �׼Ǹ����� ����
		newPet.addActionListener(new btnConnect());
		reserve.addActionListener(new btnConnect());
		admin.addActionListener(new btnConnect());
		treList.addActionListener(new btnConnect());
		}//������ ����
	
	//��ư ����
	
	class btnEnabled implements FocusListener{
		public void focusGained(FocusEvent e) {
			reserve.setEnabled(false);
			admin.setEnabled(false);
			treList.setEnabled(false);
			DELE.setEnabled(false);
		}
		public void focusLost(FocusEvent e) {}
	}
	
	
	
	//���� �ϴ��� ��ư ���� �̺�Ʈ
	class btnConnect implements ActionListener{
		
		
		public void actionPerformed(ActionEvent ae){
			String s = ae.getActionCommand();
			
			if(s == "�ű� ���") {
				NewPet np = new NewPet();
				np.setSize(450, 550);
				np.setLocation(400,400);
				np.show();
			}else if(s == "�ű� ����") {
				NewReserve nr = new NewReserve();
				nr.getId(g_id);
				nr.getDate(to_year, to_month, to_day);
				nr.setSize(450, 600);
				nr.setLocation(400,400);
				nr.show();
			}else if(s == "�Կ�") {
				NewAdm na = new NewAdm();
				na.getId(g_id);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        na.setSize(450, 750);
		        na.setLocation(400, 400);
		        na.show();
			}else if(s == "����") {
				NewTl nt = new NewTl();
				nt.getId(g_id);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        nt.setSize(450, 750);
		        nt.setLocation(400, 400);
		        nt.show();
				
			}else {
				//���� ��ư ��������
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
		            System.out.println("DB ���� �Ϸ�");
		            Statement dbSt = con.createStatement();
		            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
		            String strSql;
		            ResultSet rs = null;
		            
		            int choice = JOptionPane.showConfirmDialog(null, g_name+"�� ������ ���� �����˴ϴ�.", "ȯ�� ���� ����", JOptionPane.YES_NO_OPTION);
		            
		            if(choice == JOptionPane.YES_OPTION) {
		            	strSql = "DELETE FROM pethp_patient WHERE pet_id = '"+g_id+"';";
		            	dbSt.executeUpdate(strSql);
		            	
		            }
		            
		            
				}catch(SQLException e) {
					System.out.println("SQLException : " + e.getMessage());
				}
				
			}
		}//actionPerformed ��
	}//btnConnect class end
	
	
	// �˻�â�� ������ �� ����Ǵ� acitonListenr
	class showTable implements ActionListener, WindowListener{
		
		String t_id = "", t_petName = "", t_petAge = "", t_petSex = "", t_petSpc = "", 
				t_ownName = "", t_ownTel = "", t_ownTel_num = "";

        String strSql,strSql_2;
        ResultSet rs = null;
        
		public void windowOpened(WindowEvent we) {

			System.out.println(to_year + "  " + to_month + " " + to_day + " ");
			
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
	            String r_id = "", r_name = "";
	            
	            strSql = "SELECT pet_id, pet_name, owner_name, owner_tel, owner_tell_number FROM pethp_patient ;";
	            strSql_2 = "select * from petHp_patient, petHp_res where petHp_res.pet_id = petHp_patient.pet_id and  res_year ='"+to_year+"' and res_month='"+to_month+"' and res_date='"+to_day+"';";
	            rs = dbSt.executeQuery(strSql);
	            
	            //ȯ�� ���� ����Ʈ �� ��������
	            while(rs.next()) {
	            	t_id = rs.getString("pet_id");
	            	t_petName = rs.getString("pet_name");
	            	t_ownName = rs.getString("owner_name");
	            	t_ownTel = rs.getString("owner_tel");
	            	t_ownTel_num = rs.getString("owner_tell_number");
	            	
	            	Object plusData[] = {t_id, t_petName, t_ownName, t_ownTel+t_ownTel_num};
	            	col = plusData.length;
	            	model_1.addRow(plusData);
	            }
	            
	            //���� ���̺� �� ��������
	            rs = dbSt.executeQuery(strSql_2);
	            while(rs.next()) {
	            	r_id = rs.getString("res_time");
	            	r_name = rs.getString("owner_name");
	            	
	            	Object resData[] = {r_id, r_name};
	            	model_2.addRow(resData);
	            }
	            
			}catch(SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}//wndowOpened end
		
		//�˻� ��ư�� ������ �� ����Ǵ� �̺�Ʈ
		public void actionPerformed(ActionEvent ae) {
			String show = index.getSelectedItem().toString();
			String txt = cont.getText();
			
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
	            System.out.println("DB ���� �Ϸ�");
	            Statement dbSt = con.createStatement();
	            System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
	            String r_id = "", r_name = "";
	            
	            if(txt.isEmpty()) {
	            	strSql = "SELECT * FROM pethp_patient";
	            }else if(show.equals("ȯ�� �̸�")) {
	            	strSql = "SELECT * FROM pethp_patient WHERE pet_name = '"+txt+"';";
	            }else {
	            	strSql = "SELECT * FROM pethp_patient WHERE owner_name = '"+txt+"';";
	            }
	             
	            //ȯ ��
	            rs = dbSt.executeQuery(strSql);
	            if(rs == null)
	            	JOptionPane.showMessageDialog(null, "�ش� �Ǵ� ������ �����ϴ�.", "ȯ�� ���� ��ȸ", JOptionPane.ERROR_MESSAGE);
	            else {
	            	model_1.setNumRows(0);
	            	model_2.setNumRows(0);
	            	while(rs.next()) {
	            		//Ư�� ȯ�� ���� �˻�
	            		t_id = rs.getString("pet_id");
	            		t_petName = rs.getString("pet_name");
	            		t_ownName = rs.getString("owner_name");
	            		t_ownTel = rs.getString("owner_tel");
	            		t_ownTel_num = rs.getString("owner_tell_number");
	            		
	            		
	            		Object plusData[] = {t_id, t_petName, t_ownName, t_ownTel+t_ownTel_num};
	            		model_1.addRow(plusData);
	            	}
	            	
	            	strSql_2 = "select * from petHp_patient, petHp_res where petHp_res.pet_id = petHp_patient.pet_id and  res_year ='"+to_year+"' and res_month='"+to_month+"' and res_date='"+to_day+"' ORDER BY res_time ;";
	            
	            	rs = dbSt.executeQuery(strSql_2);
		            while(rs.next()) {
		            	r_id = rs.getString("res_time");
		            	r_name = rs.getString("owner_name");
		            	
		            	Object resData[] = {r_id, r_name};
		            	model_2.addRow(resData);
		            }
	            
	            }
				dbSt.close();
				con.close();
			}catch(SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
			
			
		}//actionPerformed end
		
		
		public void windowClosing(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowActivated(WindowEvent we) {}
		public void windowDeactivated(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		
	}//showTalbe class end
	
	
	//���̺� ���� ������ �� ����Ǵ� Ŭ����
	class tableClick implements MouseListener{
		//���콺 ������
		public void mouseClicked(MouseEvent ae) {
			
			row = PatientList.getSelectedRow();
			g_id = PatientList.getValueAt(row, 0).toString();
			g_name = PatientList.getValueAt(row, 1).toString();
			
			//���� �ѹ� Ŭ����, ��ư Ȱ��ȭ
			if(ae.getClickCount() == 1) {
				reserve.setEnabled(true);
				admin.setEnabled(true);
				treList.setEnabled(true);
				DELE.setEnabled(true);
			}//�� �ι� Ŭ����, ���ο� â���� ����
			else if(ae.getClickCount() == 2) {
				
			}	
		}//mouseClicked ��
		
		public void mousePressed(MouseEvent ae) {}
		public void mouseReleased(MouseEvent ae) {}
		public void mouseEntered(MouseEvent ae) {}
		public void mouseExited(MouseEvent ae) {
		}
	}//tableClick class end
	
	

public class NewTl extends JFrame implements ActionListener{
  JTextField tCode, tName, tAge, tKind, tY, tM, tD, tTime, tDisease, tDname, tQuan, tDcode;
  JTextArea tMemo;
  JRadioButton Male, Female;
  JCheckBox check;
  JComboBox tSta;
  String [] statement = {"����Ϸ�","����","�Կ�"};
  ButtonGroup group;
  JButton ok, cancel;

  String g_id;
  NewTl(){
	  this.addWindowListener(new winEvent());
    setTitle("���� ���� �߰�");
    Container ct = getContentPane();
    ct.setLayout(null); //������ǥ�� ����

    Font f1 = new Font("SansSerif", Font.BOLD, 30);
    Font f2 = new Font("SansSerif", Font.PLAIN, 17);

    //â ����
    JLabel title = new JLabel("���� ����");
    title.setFont(f1);
 
    //���� �ڵ� ��� ĭ
    JLabel l_code = new JLabel("�����ڵ�");    
    l_code.setFont(f2);
    tCode = new JTextField(6);

    //�̸� ��� ĭ
    JLabel l_name = new JLabel("�̸�");
    l_name.setFont(f2);
    tName = new JTextField(6);
		
    //���� ��� ĭ
    JLabel l_age = new JLabel("����");
    l_age.setFont(f2);
    tAge = new JTextField(4);

    //���� ������ư
    JLabel l_sex = new JLabel("����");
    l_sex.setFont(f2);
    Male = new JRadioButton("��");
    Female = new JRadioButton("��");  
    group = new ButtonGroup();
    group.add(Male);
    group.add(Female);
    check = new JCheckBox("�߼�ȭ");
		
    //ǰ�� ��� ĭ
    JLabel l_kind = new JLabel("ǰ��");
    l_kind.setFont(f2);
    tKind = new JTextField(6);
		
    //���ᳯ¥ ��� ĭ
    JLabel l_YMD = new JLabel("���ᳯ¥");
    l_YMD.setFont(f2);
    tY = new JTextField(5);
    tM = new JTextField(3);
    tD = new JTextField(3);

    //����ð� ��� ĭ
    JLabel l_time = new JLabel("����ð�");
    l_time.setFont(f2);
    tTime = new JTextField(4);

    //���� ��� ĭ
    JLabel l_disease = new JLabel("����");
    l_disease.setFont(f2);
    tDisease = new JTextField(10);

    //���� ���� ��� ĭ
    JLabel l_state = new JLabel("����");
    l_state.setFont(f2);
    tSta = new JComboBox(statement);
 
    JLabel l_dcode = new JLabel("��ǰ�ڵ�");
    l_dcode.setFont(f2);
    tDcode = new JTextField(10);
 
    //��ǰ�� ��� ĭ
    JLabel l_dname = new JLabel("��ǰ��");
    l_dname.setFont(f2);
    tDname = new JTextField(10);

    //���� ��� ĭ
    JLabel l_quan = new JLabel("����");
    l_quan.setFont(f2);
    tQuan = new JTextField(15);

    //�޸� ��� ĭ
    JLabel l_memo = new JLabel("�޸�");
    l_memo.setFont(f2);
    tMemo = new JTextArea(4, 11);
    JScrollPane sp = new JScrollPane(tMemo);
		
    ok = new JButton("���");
    cancel = new JButton("CLEAR");

    //JLabel ��ġ ����
    title.setBounds(150, 10, 150, 70);
    l_code.setBounds(70, 90, 100, 20);
    l_name.setBounds(70, 140, 60, 20);
    l_age.setBounds(70, 190, 60, 20); 
    l_kind.setBounds(220, 190, 60, 20);
    l_sex.setBounds(70, 240, 60, 20);
    l_YMD.setBounds(70, 290, 100, 20);
    l_time.setBounds(70, 340, 100, 20);
    l_state.setBounds(220, 340, 60, 20);
    l_disease.setBounds(70, 390, 60, 20);
    l_dname.setBounds(70, 440, 100, 20);
    l_dcode.setBounds(70, 490, 100, 20);
    l_quan.setBounds(260, 490, 60, 20);
    l_memo.setBounds(70, 540, 60, 20);
		
    //JTextField, radiobutton, checkbox��ġ ����
    tCode.setBounds(170, 90, 170, 20);
    tName.setBounds(170, 140, 170, 20);
    tAge.setBounds(170, 190, 40, 20);
    tKind.setBounds(270, 190, 70, 20);
    Male.setBounds(170, 240, 40, 20);
    Female.setBounds(220, 240, 40, 20); 
    check.setBounds(270, 240, 80, 20);
    tY.setBounds(170, 290, 60, 20);
    tM.setBounds(240, 290, 40, 20);
    tD.setBounds(290, 290, 40, 20);
    tTime.setBounds(170, 340, 40, 20);
    tSta.setBounds(260, 340, 80, 20);
    tDisease.setBounds(170, 390, 170, 20);
    tDname.setBounds(170, 440, 170, 20);
    tDcode.setBounds(170, 490, 80, 20);
    tQuan.setBounds(300, 490, 40, 20);
    sp.setBounds(170, 540, 170, 80);
		
    //��ư ��ġ ����
    ok.setBounds(180, 650, 70, 30);
    cancel.setBounds(260, 650, 80, 30);
		
    ct.add(title);
    ct.add(l_code);  ct.add(tCode);
    ct.add(l_name); ct.add(tName);
    ct.add(l_age);   ct.add(tAge);
    ct.add(l_kind);  ct.add(tKind);  
    ct.add(l_sex);  ct.add(Male);  ct.add(Female);  ct.add(check);
    ct.add(l_YMD);  ct.add(tY);  ct.add(tM);  ct.add(tD);
    ct.add(l_time);  ct.add(tTime);
    ct.add(l_disease);  ct.add(tDisease);
    ct.add(l_state);  ct.add(tSta);
    ct.add(l_dcode);  ct.add(tDcode);
    ct.add(l_dname);  ct.add(tDname);
    ct.add(l_quan);  ct.add(tQuan);
    ct.add(l_memo);  ct.add(sp); 
    ct.add(ok); ct.add(cancel);
		
    ok.addActionListener(this); //ȸ������ 
    cancel.addActionListener(this); //â 
		
  }
	
  public void actionPerformed(ActionEvent ae) {
    String s = ae.getActionCommand();
    String tcode="", tname="", tage="", tsex="", tkind="", ty="", tm="", td="", ttime="", tdisease="", tsta="", tdname="", tquan="", tmemo="", tdcode="";
		
    if(s == "CLEAR") {
      tCode.setText(" ");
      tName.setText(" ");  tAge.setText(" ");
      tKind.setText(" ");  tY.setText(" ");
      tM.setText(" ");  tD.setText(" ");
      tTime.setText(" ");  tDisease.setText(" ");
      tDname.setText(" ");  tQuan.setText(" ");
      tMemo.setText(" ");  tDcode.setText(" ");
    }
    else {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
      }
      catch(ClassNotFoundException e){
        System.err.println("����̹� �ε忡 �����߽��ϴ�.");
      }		
      try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
        System.out.println("DB ���� �Ϸ�");
        Statement dbSt = con.createStatement();
        System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
        String strSql;

        boolean ok = true;
 
        tname=tName.getText();  tage=tAge.getText();  tkind=tKind.getText();  ty=tY.getText(); tm=tM.getText();
        tcode=tCode.getText(); td=tD.getText();  ttime=tTime.getText();  tdisease=tDisease.getText();
        tdname=tDname.getText();  tquan=tQuan.getText();  tmemo=tMemo.getText(); tdcode=tDcode.getText();

        if(Male.isSelected()){
          if(check.isSelected())
            tsex = "�� �߼�ȭ";
          else
            tsex = "��";
        }
        else if(Female.isSelected()){
          if(check.isSelected())
            tsex = "�� �߼�ȭ";
          else
            tsex = "��";
        }
       
        int index = tSta.getSelectedIndex();
        if(index == 0){
          tsta = "����Ϸ�";
        }
        else if(index == 1){
          tsta = "����";
        }
        else{
          tsta = "�Կ�";
        }

        String petid="", drugcode="", drugyear="", drugmonth="", drugdate="", symcode="";
        int drugst=0;

        //����ȭ �۾�
        if(tcode.isEmpty() || tname.isEmpty() || tage.isEmpty() ||  tkind.isEmpty()  || tsex.isEmpty() || ty.isEmpty() ||  tm.isEmpty() ||  td.isEmpty() ||  ttime.isEmpty() || tsta.isEmpty() || tdisease.isEmpty() || tdname.isEmpty() || tquan.isEmpty() || tdcode.isEmpty() ){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�߰� ���� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!tcode.matches("^t[1-9][0-9][0-9][0-9]*$")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "���� �ڵ带 ���Ŀ� ���� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!ty.matches("^[2-9][0-9][0-9][0-9]*$") || ty.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�⵵�� �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!tm.matches("^[0-9]*$") || tm.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "���� �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!td.matches("^[0-9]*$") || td.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "��¥�� �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!ttime.matches("^[0-9]*$") || ttime.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�ð��� �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!tage.matches("^[0-9]*$") || tage.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "���̸� �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!tquan.matches("^[0-9]*$") || tquan.equals("0")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "������ �ٸ��� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else if(!tdcode.matches("^d[1-9][0-9][0-9][0-9]*$")){
          MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "��ǰ �ڵ带 ���Ŀ� ���� �Է����ּ���.");
          md.show();  System.out.println("������ �߰� ����");
          ok = false;
        }
        else{
          boolean check1=true, check2=false, check3=false, check4=false;

          if(check1){  //�ڵ� ���� ���� 
            strSql = "SELECT * FROM pethp_tl WHERE tl_code='"+tcode+"';";
            ResultSet result = dbSt.executeQuery(strSql);
            while(result.next()){
              check1 = false;
            }
          }

          if(check2 == false){  //�� ���� ���� 
            strSql = "SELECT pet_id FROM pethp_patient WHERE pet_name='"+tname+"'  AND pet_sex='"+tsex+"' AND pet_age='"+tage+"' AND pet_spc='"+tkind+"';";  //�ڿ� ��ȣ�ڸ� �߰� �ʿ�
            ResultSet result = dbSt.executeQuery(strSql);
            while(result.next()){
              petid = result.getString("pet_id");
              check2 = true;
            }
          }

          if(check3 == false){  //�� ���� ����
            strSql = "SELECT drug_code, drug_year, drug_month, drug_date, drug_st FROM pethp_drug WHERE drug_code='"+tdcode+"' AND drug_name='"+tdname+"';";
            ResultSet result = dbSt.executeQuery(strSql);
            while(result.next()){
              drugcode = result.getString("drug_code");
              drugyear = result.getString("drug_year");
              drugmonth = result.getString("drug_month");
              drugdate = result.getString("drug_date");
              drugst = result.getInt("drug_st");
              check3 = true;
            }
          }

          if(check4 == false){  //�� ���� ���� 
            strSql = "SELECT sym_code FROM pethp_sym WHERE sym_name='"+tdisease+"';";
            ResultSet result = dbSt.executeQuery(strSql);
            while(result.next()){
              symcode = result.getString("sym_code");
              check4 = true;
            }
          }

          if(check1 != true){
            MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�����ϴ� ���� �ڵ��Դϴ�. �ٸ� �ڵ带 �Է����ּ���.");
            md.show();  System.out.println("������ �߰� ����");
          }
          else if(check2 != true){
            MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�������� �ʴ� ȯ���Դϴ�. ������ �ٽ� �Է����ּ���.");
            md.show();  System.out.println("������ �߰� ����");
          }  
          else if(check3 != true){
            MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�������� �ʴ� ��ǰ�Դϴ�. ������ �ٽ� �Է����ּ���.");
            md.show();  System.out.println("������ �߰� ����");
          }  
          else if(check4 != true){
            MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "�������� �ʴ� �����Դϴ�. �ٽ� �Է����ּ���.");
            md.show();  System.out.println("������ �߰� ����");
          }  
          else{ 
            if(drugst < Integer.parseInt(tquan)){
              MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "��ǰ ������ ���ڶ��ϴ�. ��밡���� ��ǰ ������ "+ drugst + "���Դϴ�.");
              md.show();  System.out.println("������ �߰� ����");
            }
            else{
              strSql = "INSERT INTO pethp_tl VALUES ('"+tcode+"', '"+petid+"', '"+ty+"', '"+tm+"', '"+td+"', '"+ttime+"', '"+tsta+"', '"+drugcode+"', '"+drugyear+"', '"+drugmonth+"', '"+drugdate+"', "+tquan+", '"+symcode+"', '"+tmemo+"');";       
              dbSt.executeUpdate(strSql);

              MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "���� ������ �߰��߽��ϴ�.");
              md.show();  System.out.println("������ �߰� ����");
        
              strSql = "UPDATE pethp_drug SET drug_st = drug_st-"+tquan+" WHERE drug_code='"+drugcode+"';";
              dbSt.executeUpdate(strSql);
            }
          }
        }
      }
      catch(SQLException e) {
        System.out.println("SQLException :" + e.getMessage());
      }			
    }	
  }//end of actionPerformed 

  public void getId(String g_id2) {
		g_id = g_id2;
	}
  
  class winEvent implements WindowListener{
		public void windowOpened(WindowEvent we) {
			 try{
			      Class.forName("com.mysql.cj.jdbc.Driver");
			      System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			    }catch(ClassNotFoundException e){
			      System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			    }
			    try{
			      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
			      System.out.println("DB ���� �Ϸ�");
			      Statement dbSt = con.createStatement();
			      System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
			      String strSql;
			      ResultSet rs;
			      String t_name = "", t_age = "", t_spc = "", t_sex = "";
			      String sex = "", surgery = "";
			      
			     strSql = "SELECT * FROM pethp_patient WHERE pet_id = '"+g_id+"';";
			     rs = dbSt.executeQuery(strSql);
			     if(rs.next()) {
			    	 t_name = rs.getString("pet_name");
			    	 t_age = rs.getString("pet_age");
			    	 t_spc = rs.getString("pet_spc");
			    	 t_sex = rs.getString("pet_sex");
			     
					 tName.setText(t_name); tAge.setText(t_age); tKind.setText(t_spc);
				
					 try {
						 sex = t_sex.substring(0,1);
						 surgery = t_sex.substring(2,sex.length());
					 }catch(StringIndexOutOfBoundsException e) {
						 System.out.println("��������");
					 }
					 if(!surgery.isEmpty()) {
						 check.setSelected(true);
					 }
					 if(sex.equals("��")) {
						 Male.setSelected(true);
					 }else if(sex.equals("��")) {
						 Female.setSelected(true);
					 }
					 setting();
					 
			     }
			      dbSt.close();
			      con.close();

			    }catch(SQLException e){
			      System.out.println("SQLException : " + e.getMessage());
			    }
		}
		public void windowClosing(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowActivated(WindowEvent we) {}
		public void windowDeactivated(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		
		public void setting() {
			tName.setEditable(false); tAge.setEditable(false); tKind.setEditable(false); 
			Male.setEnabled(false); Female.setEnabled(false); check.setEnabled(false);
			
		}
	}
	
  
  public static void main(String args[]){
	  	NewTl JMem = new NewTl();
        JMem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JMem.setSize(450, 750);
        JMem.setLocation(400, 400);
        JMem.show();
  }	
}//end of class

class MessageDialog3 extends JDialog implements ActionListener{
  JButton ok;
  MessageDialog3(JFrame parent, String title, boolean mode, String msg){ 
    super(parent, title, mode); 
    JPanel pc = new JPanel(); 
    JLabel label = new JLabel(msg);
    pc.add(label);
    add(pc, BorderLayout.CENTER); 
    JPanel ps = new JPanel(); 
    ok = new JButton("OK");
    ok.addActionListener(this);
    ps.add(ok);
    add(ps, BorderLayout.SOUTH);
    pack(); 
   
    this.setLocation(380, 450);
  }
  
  public void actionPerformed(ActionEvent ae) {
    dispose(); 
  }
}


public class NewReserve extends JFrame{
	
	JTextField pName, pAge, pSpc, pOwnName, uP2, uP3;
	JComboBox uP1, pYear, pMonth, pDay, pTime;
	String code[] = {"010", "070", "02", "031", "032"};
	String ye[] = {"2020", "2021","2022" ,"2023"};
	String mo[] = {"1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11","12"};
	Vector da = new Vector(); //�� 
	Vector ti = new Vector(); // ��
	ButtonGroup gSex;
	JRadioButton pMSex, pFSex;
	JCheckBox noSex;
	JButton clear, ok;
	int year, month, day;
	
	DefaultComboBoxModel model, model_2;
	
	String g_id;
	NewReserve(){
		this.addWindowListener(new winEvent());
		Container ct = getContentPane();
		ct.setLayout(null);
		
		Font f1 = new Font("����", Font.BOLD, 30);
		Font f2 = new Font("����", Font.PLAIN, 17);
		

		JLabel title = new JLabel("�ű� ����");
		title.setFont(f1);
		
		JLabel name = new JLabel("�̸�");
		name.setFont(f2);
		pName = new JTextField(15);

		JLabel age = new JLabel("����");
		age.setFont(f2);
		pAge = new JTextField(3);
		JLabel spc = new JLabel("ǰ��");
		spc.setFont(f2);
		pSpc = new JTextField(10);

		JLabel sex = new JLabel("����");
		sex.setFont(f2);
		gSex = new ButtonGroup();
		pMSex = new JRadioButton("����");
		pFSex = new JRadioButton("����");
		noSex = new JCheckBox("�߼�ȭ");

		JLabel res = new JLabel("���� ��¥");
		res.setFont(f2);
		pYear = new JComboBox(ye);
		pMonth = new JComboBox(mo);
		pDay = new JComboBox(da);
		
		model = new DefaultComboBoxModel(da);
		pDay.setModel(model);
		
		//pYear.addActionListener(new settingDay());
		pMonth.addItemListener(new settingDay());
		pDay.addItemListener(new settingDay());
		
		JLabel time = new JLabel("���� �ð�");
		time.setFont(f2);
		pTime = new JComboBox(ti);
		
		model_2 = new DefaultComboBoxModel(ti);
		pTime.setModel(model_2);
		
		JLabel owner = new JLabel("��ȣ�ڸ�");
		owner.setFont(f2);
		pOwnName = new JTextField(15);

		JLabel phone = new JLabel("��ȭ��ȣ");
		phone.setFont(f2);
		uP1 = new JComboBox(code);
		uP2 = new JTextField(6); uP3 = new JTextField(6);
		
		clear = new JButton("CLEAR");
		ok = new JButton("���");
		
		title.setBounds(140, 15, 150, 70);
		name.setBounds(70, 130, 60, 20);
		age.setBounds(70, 180, 60, 20);
		spc.setBounds(225,180,60,20);
		sex.setBounds(70, 230, 60, 20);
		res.setBounds(70, 280, 80, 20);
		time.setBounds(70, 330, 80, 20);
		owner.setBounds(70, 380, 80, 20);
		phone.setBounds(70, 420, 80, 20);
		
		pName.setBounds(155, 130, 140, 20);
		pAge.setBounds(155, 180, 50, 20);
		pSpc.setBounds(275, 180, 100, 20);
		pMSex.setBounds(155, 230, 60, 20);
		pFSex.setBounds(215, 230, 60, 20);
		noSex.setBounds(285, 230, 70, 20);
		pYear.setBounds(155, 280, 60, 20);
		pMonth.setBounds(220,280, 50, 20);
		pDay.setBounds(275, 280, 50, 20);
		pTime.setBounds(155,330, 50, 20);
		pOwnName.setBounds(155, 380, 140, 20);
		uP1.setBounds(155, 420, 50, 20);
		uP2.setBounds(210, 420, 50, 20);
		uP3.setBounds(265, 420, 50, 20);
		
		clear.setBounds(200, 500, 100, 30);
		ok.setBounds(320, 500, 80, 30);
		
		ct.add(title); ct.add(name); ct.add(spc); ct.add(res); ct.add(time);
		ct.add(age); ct.add(sex); ct.add(owner); ct.add(phone);
		
		ct.add(pName); ct.add(pAge); ct.add(pSpc);
		ct.add(pMSex); ct.add(pFSex); ct.add(noSex); ct.add(pOwnName);
		ct.add(pYear); ct.add(pMonth); ct.add(pDay); ct.add(pTime);
		ct.add(uP1); ct.add(uP2); ct.add(uP3);
		
		ct.add(clear); ct.add(ok);
		
		clear.addActionListener(new btnAction());
		ok.addActionListener(new btnAction());
	}

	public void getId(String g_id2) {
		g_id = g_id2;
	}
	public void getDate(int to_year, int to_month, int to_day) {
		year = to_year; month = to_month; day = to_day;	
	}
	
	class btnAction implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			String s = ae.getActionCommand();
		
		
			if(s == "CLEAR") {
				pYear.setSelectedIndex(0);
				pMonth.setSelectedIndex(0);
				pDay.setSelectedIndex(0);
				pTime.setSelectedIndex(0);
			}else {
				
				String getYear = pYear.getSelectedItem().toString();
				String getMonth = pMonth.getSelectedItem().toString();
				String getDay = pDay.getSelectedItem().toString();
				String getTime = pTime.getSelectedItem().toString();
				String showTxt = getYear +"�� "+ getMonth + "�� " + getDay + "�� " + getTime + "�� ������ �½��ϱ�?";
				int choice = JOptionPane.showConfirmDialog(null, showTxt, "�ű� ���� �߰�", JOptionPane.ERROR_MESSAGE);
				 
				if(choice == JOptionPane.YES_OPTION) {
					try{
						Class.forName("com.mysql.cj.jdbc.Driver");
						System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
					}catch(ClassNotFoundException e){
						System.err.println("����̹� �ε忡 �����߽��ϴ�.");
					}
					try {
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
						System.out.println("DB ���� �Ϸ�");
						Statement dbSt = con.createStatement();
						System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
						String strSql;
						ResultSet rs;
						boolean testSql = false;
			    
						if(Integer.parseInt(getYear) >= year ) {
							if(Integer.parseInt(getYear) == year) {
								if(Integer.parseInt(getMonth) >= month) {
									if(Integer.parseInt(getMonth) == month) {
										if(Integer.parseInt(getDay) >= day) testSql = true;  
									}else {
										testSql = true;
									}
								}else {
									JOptionPane.showMessageDialog(null, "�ش� ��¥�� ������ �� �����ϴ�.", "�ű� ���� ���", JOptionPane.ERROR_MESSAGE);
								}
							}else {
								testSql = true;
							}
						}else {
							JOptionPane.showMessageDialog(null, "�ش� ��¥�� ������ �� �����ϴ�.", "�ű� ���� ���", JOptionPane.ERROR_MESSAGE);
						}
						if(testSql == true) {
							strSql = "INSERT INTO pethp_res (pet_id, res_year, res_month, res_date, res_time) VALUES ('"+g_id+"','"+getYear+"','"+getMonth+"','"+getDay+"','"+getTime+"');";
			   
							dbSt.executeUpdate(strSql);
							System.out.println("��� �Էµ�");
							JOptionPane.showMessageDialog(null, "���������� �߰��Ǿ����ϴ�.", "�ű� ���� ���", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
					}catch(SQLException e){
						System.out.println("SQLException : " + e.getMessage());
					}
			
			
				}
			}//������ ��
			
		}//end of actionPeformed
	}
	
	//�޿� ���� �� �ٲ�
	class settingDay implements ItemListener{
		
		String g_year, g_month, g_day, g_time;
		String in_date[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23","24", "25", "26", "27", "28", "29","30","31"};
		String res_time [] = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
		int getDay;
		public void setday() {
			g_month = pMonth.getSelectedItem().toString();
			if(g_month == "1"||g_month == "3"||g_month == "5"||g_month == "7"||g_month=="8"||g_month=="10"||g_month=="12") {
				getDay = 31;
			}else if(g_month == "2") {
				getDay = 29;
			}else{
				getDay = 30;
			}
		}
		
		public void warnMessage() {
			JOptionPane.showMessageDialog(null, "������ �� ���� ��¥�� �ð��Դϴ�.", "ȸ�� ����", JOptionPane.ERROR_MESSAGE);
		}
		
		public void itemStateChanged(ItemEvent ie) {

			
			if(ie.getSource().equals(pYear)&&ie.getStateChange() == 1) {
				g_year = pYear.getSelectedItem().toString();
				
			}else if(ie.getSource().equals(pMonth) && ie.getStateChange() == 1) {
				setday();
				model.removeAllElements();
				for(int i = 0; i<getDay; i++) {
					model.addElement(in_date[i]);}
			}else if(ie.getSource().equals(pDay) && ie.getStateChange() == 1) {
				g_day = pDay.getSelectedItem().toString();
			
				System.out.println(g_day);
				model_2.removeAllElements();
				for(int i = 0; i<res_time.length; i++) {
					ti.addElement(res_time[i]);
				}
				
				try{
				      Class.forName("com.mysql.cj.jdbc.Driver");
				      System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
				    }catch(ClassNotFoundException e){
				      System.err.println("����̹� �ε忡 �����߽��ϴ�.");
				    }
				
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
				    System.out.println("DB ���� �Ϸ�");
				    Statement dbSt = con.createStatement();
				    System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
				    String strSql;
				    ResultSet rs;
				    String t_time = ""; 
				    
				    g_year = pYear.getSelectedItem().toString();
				    g_month = pMonth.getSelectedItem().toString();
				    System.out.println(g_year + "    " + g_month + "     " + g_day );
				    
				    strSql = "SELECT * FROM pethp_res WHERE res_year = '"+g_year+"' and res_month = '"+g_month+"'and res_date = '"+g_day+"';";
				    rs = dbSt.executeQuery(strSql); 
				    
				    //�Է��� ��¥�� ���� ������ �ð��� ������
				    while(rs.next()) {
				    	t_time = rs.getString("res_time");
				    	for(int k = 0; k<ti.size(); k++) {
				    		if(ti.get(k).equals(t_time)) {
				    			ti.remove(k);
				    		}
				    	}
				    }
				    if(ti.size() == 0) warnMessage();
				    
				    System.out.println(" ");
					dbSt.close();
				    con.close();
				}catch(SQLException e) {
					
				}
			}
		}
	}//settinDay��


	
	
	
	
	class winEvent implements WindowListener{
		public void windowOpened(WindowEvent we) {
			 try{
			      Class.forName("com.mysql.cj.jdbc.Driver");
			      System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			    }catch(ClassNotFoundException e){
			      System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			    }
			    try{
			      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
			      System.out.println("DB ���� �Ϸ�");
			      Statement dbSt = con.createStatement();
			      System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
			      String strSql;
			      ResultSet rs;
			      String t_name = "", t_age = "", t_spc = "", t_sex = "",t_own_name = "", t_tel = "", t_tel_number ="", t_p2 = "", t_p3 = "";
			      String sex = "", surgery = "";
			      
			     strSql = "SELECT * FROM pethp_patient WHERE pet_id = '"+g_id+"';";
			     rs = dbSt.executeQuery(strSql);
			     
			     if(rs.next()) {
			    	 t_name = rs.getString("pet_name");
			    	 t_age = rs.getString("pet_age");
			    	 t_sex = rs.getString("pet_sex");
			    	 t_spc = rs.getString("pet_spc");
			    	 t_own_name = rs.getString("owner_name");
			    	 t_tel = rs.getString("owner_tel");
					 t_tel_number = rs.getString("owner_tell_number");
					 
					 t_p2 = t_tel_number.substring(0, t_tel_number.length()/2);
					 t_p3 = t_tel_number.substring(t_tel_number.length()-4, t_tel_number.length());
			     
					 try {
						 sex = t_sex.substring(0,1);
						 surgery = t_sex.substring(2,t_sex.length());
					 }catch(StringIndexOutOfBoundsException e) {
						 System.out.println("��������");
					 }
					 if(!surgery.isEmpty()) {
						 noSex.setSelected(true);
					 }
					 pName.setText(t_name); pAge.setText(t_age); pSpc.setText(t_spc);
					 pOwnName.setText(t_own_name);	uP2.setText(t_p2);	uP3.setText(t_p3);
					 if(sex.equals("��")) {
						 pMSex.setSelected(true);
					 }else if(sex.equals("��")) {
						 pFSex.setSelected(true);
					 }
					 
					 int i = 0;
					 while(code[i] == t_tel) {
						 uP1.setSelectedIndex(i);
						 i++;
					 } 

					 setting(); 
			     }
			      dbSt.close();
			      con.close();

			    }catch(SQLException e){
			      System.out.println("SQLException : " + e.getMessage());
			    }
		}
		public void windowClosing(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowActivated(WindowEvent we) {}
		public void windowDeactivated(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		
		public void setting() {
			pName.setEditable(false); pAge.setEditable(false); pSpc.setEditable(false); pOwnName.setEditable(false);
			 uP2.setEditable(false); uP3.setEditable(false); uP1.setEnabled(false); pMSex.setEnabled(false); pFSex.setEnabled(false); noSex.setEnabled(false);
		}
	}
	



}



public class NewPet extends JFrame implements ActionListener{
	
	JTextField pId,pName, pAge, pSpc, pOwnName, uP2, uP3;
	JComboBox uP1;
	String code[] = {"010", "070", "02", "031", "032"};
	ButtonGroup gSex;
	JRadioButton pMSex, pFSex;
	JCheckBox noSex;
	JButton clear, ok;
	
	NewPet(){
		Container ct = getContentPane();
		ct.setLayout(null);
		
		Font f1 = new Font("����", Font.BOLD, 30);
		Font f2 = new Font("����", Font.PLAIN, 17);
		

		JLabel title = new JLabel("�ű� ���");
		title.setFont(f1);
		
		JLabel id = new JLabel("�ڵ� ");
		id.setFont(f2);
		pId = new JTextField(10);
		pId.setText("p");

		JLabel name = new JLabel("�̸�");
		name.setFont(f2);
		pName = new JTextField(15);

		JLabel age = new JLabel("����");
		age.setFont(f2);
		pAge = new JTextField(3);
		JLabel spc = new JLabel("ǰ��");
		spc.setFont(f2);
		pSpc = new JTextField(10);

		JLabel sex = new JLabel("����");
		sex.setFont(f2);
		gSex = new ButtonGroup();
		pMSex = new JRadioButton("����");
		pFSex = new JRadioButton("����");
		noSex = new JCheckBox("�߼�ȭ");

		JLabel owner = new JLabel("��ȣ�ڸ�");
		owner.setFont(f2);
		pOwnName = new JTextField(15);

		JLabel phone = new JLabel("��ȭ��ȣ");
		phone.setFont(f2);
		uP1 = new JComboBox(code);
		uP2 = new JTextField(6); uP3 = new JTextField(6);
		
		clear = new JButton("CLEAR");
		ok = new JButton("���");
		
		title.setBounds(140, 15, 150, 70);
		id.setBounds(70, 130, 60, 20);
		name.setBounds(70, 180, 60, 20);
		age.setBounds(70, 230, 60, 20);
		spc.setBounds(225,230,60,20);
		sex.setBounds(70, 280, 60, 20);
		owner.setBounds(70, 330, 80, 20);
		phone.setBounds(70, 380, 80, 20);
		
		pId.setBounds(155, 130, 140, 20);
		pName.setBounds(155, 180, 140, 20);
		pAge.setBounds(155, 230, 50, 20);
		pSpc.setBounds(275, 230, 100, 20);
		pMSex.setBounds(155, 280, 60, 20);
		pFSex.setBounds(215, 280, 60, 20);
		noSex.setBounds(285, 280, 70, 20);
		pOwnName.setBounds(155, 330, 140, 20);
		uP1.setBounds(155, 380, 50, 20);
		uP2.setBounds(210, 380, 50, 20);
		uP3.setBounds(265, 380, 50, 20);
		
		clear.setBounds(200, 450, 100, 30);
		ok.setBounds(320, 450, 80, 30);

		clear.addActionListener(this);
		ok.addActionListener(this);
		
		ct.add(title); ct.add(id); ct.add(name); ct.add(spc);
		ct.add(age); ct.add(sex); ct.add(owner); ct.add(phone);
		
		ct.add(pId); ct.add(pName); ct.add(pAge); ct.add(pSpc);
		ct.add(pMSex); ct.add(pFSex); ct.add(noSex); ct.add(pOwnName);
		ct.add(uP1); ct.add(uP2); ct.add(uP3);
		
		ct.add(clear); ct.add(ok);
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		String t_id = "", t_petName = "", t_petAge = "", t_petSex = "", t_petSpc = "",
				t_ownName="", t_ownTel="", t_ownTel_num="";
		
		if(s == "CLEAR") {
			pId.setText("p");
			pName.setText("");
			pAge.setText("");
			pSpc.setText("");
			pOwnName.setText("");
			uP1.setSelectedItem(0);
			uP2.setText("");
			uP3.setText("");
			
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
	            
	            
	            if(pId.getText().isEmpty()||pName.getText().isEmpty()||pOwnName.getText().isEmpty()||uP2.getText().isEmpty()||uP3.getText().isEmpty()) {
	            	JOptionPane.showMessageDialog(null, "�ڵ�, ȯ���̸�, ��ȣ�ڸ�, ��ȣ�� ��ȣ�� ��� �������ּ���", "�ű� ȯ�� ���", JOptionPane.ERROR_MESSAGE);
	            }else {
	            	boolean butn = false;
	            	t_id = pId.getText();
	            	if(!t_id.matches("^p[0-9][0-9][1-9]*$")){
	  		          JOptionPane.showMessageDialog(null, "�ڵ� ���Ŀ� ���� �������ּ���", "�ű� ȯ�� ���", JOptionPane.ERROR_MESSAGE);
	  		          System.out.println("������ �߰� ����");
	  		          
	  		      	}else {
	  		      		strSql = "SELECT * FROM petHp_patient WHERE pet_id = '" + t_id + "';";
	  		      		ResultSet result = dbSt.executeQuery(strSql);
	  		      		//�ڵ� �ߺ� �˻�
	  		      		if(result.next()) {
	  		      			JOptionPane.showMessageDialog(null,"�̹� �����ϴ� �ڵ��Դϴ�.", "�ű� ȯ�� ���", JOptionPane.ERROR_MESSAGE); //�ߺ�Ȯ���� �´� ��� false�� �ٲ�
	  		      			pId.setText("");
	  		      		}
		            	t_petName = pName.getText();	t_ownName = pOwnName.getText();		
	            		t_ownTel = uP1.getSelectedItem().toString();	t_ownTel_num = uP2.getText() + uP3.getText();
	            		
		            	try {
		            		Integer.parseInt(t_ownTel_num);
		            	}catch(NumberFormatException ne) {
		            		JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� ���ڷ� �������ּ���", "�ű� ȯ�� ���", JOptionPane.ERROR_MESSAGE);
		            		uP2.setText("");	uP3.setText("");
		            	}       	

	            		if(uP2.getText().length() < 3 || uP3.getText().length() != 4) 
		            		JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� Ȯ�����ּ���", "ȸ������", JOptionPane.ERROR_MESSAGE);
	            		else {
	            			if(pMSex.isSelected()) {
	            				t_petSex = "�� ";
	            				butn = true;
	            			}else if(pFSex.isSelected()){
	            				t_petSex = "�� ";
	            				butn = true;
	            			}
	            			if(noSex.isSelected())
	            				t_petSex += noSex.getText();
	            			
	            			t_petAge = pAge.getText(); 	t_petSpc = pSpc.getText();
	            			
	            			
	            			
	            			if(butn == false || t_petAge.isEmpty() || t_petSpc.isEmpty() ) {
	            				if(!t_petAge.isEmpty()) {
	            					try {
	            						Integer.parseInt(t_petAge);
	            					}catch(NumberFormatException ne) {
	            						JOptionPane.showMessageDialog(null, "���̴� ���ڷ� �������ּ���", "�ű� ȯ�� ���", JOptionPane.ERROR_MESSAGE);
	            						pAge.setText("");
	            					}
	            				}
	            				int choice = JOptionPane.showConfirmDialog(null, "���Ե��� ���� ������ �ֽ��ϴ�. \n ����Ͻðڽ��ϱ�?", "�ű� ȯ�� ����", JOptionPane.ERROR_MESSAGE);
	            				
	            				if(choice == JOptionPane.YES_OPTION) {
	            					strSql = "INSERT INTO pethp_patient (pet_id, pet_name, pet_age, pet_sex, pet_spc, owner_name, owner_tel, owner_tell_number) VALUES ('"+t_id+"','"+t_petName+"','"+t_petAge+"','"+t_petSex+"','"+t_petSpc+"','"+t_ownName+"','"+t_ownTel+"','"+t_ownTel_num+"');";
	            					dbSt.executeUpdate(strSql);
	            					System.out.println("����Էµ�");
	            					JOptionPane.showMessageDialog(null, "ȯ�� ������ �߰��Ǿ����ϴ�.", "�ű� ȯ�� ���", JOptionPane.INFORMATION_MESSAGE);
	            					dispose();
	            				}
	            				//������ �Է� â�� null �� �˻�
	            			}else {
            					strSql = "INSERT INTO pethp_patient (pet_id, pet_name, pet_age, pet_sex, pet_spc, owner_name, owner_tel, owner_tell_number) VALUES ('"+t_id+"','"+t_petName+"','"+t_petAge+"','"+t_petSex+"','"+t_petSpc+"','"+t_ownName+"','"+t_ownTel+"','"+t_ownTel_num+"');";
            					
            					dbSt.executeUpdate(strSql);
            					System.out.println("����Էµ�");	
            					JOptionPane.showMessageDialog(null, "ȯ�� ������ �߰��Ǿ����ϴ�.", "�ű� ȯ�� ���", JOptionPane.INFORMATION_MESSAGE);
            					dispose();
	            			}
	            			
	            		}//��ȭ��ȣ ���� �˻�
		           
	  		      	}  
	            
	            }
	            
	            dbSt.close();
				con.close();   
			}catch(SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
			
		}
		
	}

}




public class NewAdm extends JFrame implements ActionListener{
	
	JTextField aCode, pName, pAge, pSpc, inY, inM, inD, outY, outM, outD, aCage, pCode, sCode, pOwnName, uP2, uP3, aSym ;
	JComboBox uP1;
	ButtonGroup gSex;
	JRadioButton pMSex, pFSex;
	JCheckBox noSex;
	String code[] = {"010", "070", "02", "031", "032"};
	JButton clear, ok;
	
	String g_id; //pet id
	NewAdm(){
		this.addWindowListener(new winEvent());
		Container ct = getContentPane();
		ct.setLayout(null);
		
		Font f1 = new Font("����", Font.BOLD, 30);
		Font f2 = new Font("����", Font.PLAIN, 17);
		

		JLabel title = new JLabel("�Կ� ���");
		title.setFont(f1);
		
		JLabel adm_code = new JLabel("�Կ��ڵ�");
		adm_code.setFont(f2);
		aCode = new JTextField(10);
		
		JLabel name = new JLabel("�̸�");
		name.setFont(f2);
		pName = new JTextField(15);

		JLabel age = new JLabel("����");
		age.setFont(f2);
		pAge = new JTextField(3);
		
		JLabel spc = new JLabel("ǰ��");
		spc.setFont(f2);
		pSpc = new JTextField(10);
		
		JLabel sex = new JLabel("����");
		sex.setFont(f2);
		gSex = new ButtonGroup();
		pMSex = new JRadioButton("����");
		pFSex = new JRadioButton("����");
		noSex = new JCheckBox("�߼�ȭ");

		JLabel in_YMD = new JLabel("�Կ���¥");
		in_YMD.setFont(f2);
		inY = new JTextField(5);
    		inM = new JTextField(3);
	    	inD = new JTextField(3);


		JLabel out_YMD = new JLabel("�����¥");
		out_YMD.setFont(f2);
		outY = new JTextField(5);
    		outM = new JTextField(3);
	    	outD = new JTextField(3);

		JLabel cage = new JLabel("������ ");
		cage.setFont(f2);
		aCage = new JTextField(3);
		
		JLabel sym_code = new JLabel("�����ڵ�");
		sym_code.setFont(f2);
		aSym = new JTextField(3);

		JLabel owner = new JLabel("��ȣ�ڸ�");
		owner.setFont(f2);
		pOwnName = new JTextField(15);

		JLabel phone = new JLabel("��ȭ��ȣ");
		phone.setFont(f2);
		uP1 = new JComboBox(code);
		uP2 = new JTextField(6); uP3 = new JTextField(6);
		
		clear = new JButton("CLEAR");
		ok = new JButton("���");
		
		title.setBounds(140, 15, 150, 70);
		adm_code.setBounds(70, 100, 100, 20);
		name.setBounds(70, 150, 60, 20);
		age.setBounds(70, 200, 60, 20);
		spc.setBounds(225,200,60,20);
		sex.setBounds(70, 250, 60, 20);
		in_YMD.setBounds(70, 300, 100, 20);
		out_YMD.setBounds(70, 350, 100, 20);
		cage.setBounds(70,400,60,20);
		sym_code.setBounds(70, 450, 80, 20);
		owner.setBounds(70, 500, 80, 20);
		phone.setBounds(70, 550, 80, 20);
		
		aCode.setBounds(155, 100, 190, 20);
		pName.setBounds(155, 150, 190, 20);
		pAge.setBounds(155, 200, 50, 20);
		pSpc.setBounds(275, 200, 65, 20);
		pMSex.setBounds(155, 250, 60, 20);
		pFSex.setBounds(215, 250, 60, 20);
		noSex.setBounds(285, 250, 70, 20);
		inY.setBounds(155, 300, 60, 20);
		inM.setBounds(230, 300, 40, 20);
		inD.setBounds(285, 300, 55, 20);
		outY.setBounds(155, 350, 60, 20);
		outM.setBounds(230, 350, 40, 20);
		outD.setBounds(285, 350, 55, 20);
		aCage.setBounds(155, 400, 190, 20);
		aSym.setBounds(155, 450, 190, 20);
		pOwnName.setBounds(155, 500, 190, 20);
		uP1.setBounds(155, 550, 55, 20);
		uP2.setBounds(225, 550, 50, 20);
		uP3.setBounds(290, 550, 55, 20);
		
		clear.setBounds(200, 600, 100, 30);
		ok.setBounds(320, 600, 80, 30);

		clear.addActionListener(this);
		ok.addActionListener(this);
		
		ct.add(title); ct.add(adm_code); ct.add(name); ct.add(spc); 
		ct.add(age); ct.add(sex); 
		ct.add(in_YMD); ct.add(out_YMD);
		ct.add(cage); ct.add(sym_code); ct.add(owner); ct.add(phone);
		
		ct.add(aCode); ct.add(pName); ct.add(pAge); ct.add(pSpc);
		ct.add(pMSex); ct.add(pFSex); ct.add(noSex); ct.add(pOwnName);
		ct.add(inY); ct.add(inM); ct.add(inD); ct.add(outY); ct.add(outM); ct.add(outD); 
		ct.add(aCage); ct.add(aSym); ct.add(uP1); ct.add(uP2); ct.add(uP3);
		
		
		ct.add(clear); ct.add(ok);
	}
	
	
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		String t_code="", t_inY="", t_inM="", t_inD="", t_outY="", t_outM="", t_outD="", t_aCage="", t_sym="";
		int intT_year, intT_month, intT_date, intT_outyear, intT_outmonth, intT_outdate;
		
		if(s == "CLEAR") {
			aCode.setText("");
			inY.setText("");
			inM.setText("");
			inD.setText("");
			outY.setText("");
			outM.setText("");
			outD.setText("");
			aCage.setText("");		
			aSym.setText("");
			
			
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
	            
	            
	            if(aCode.getText().isEmpty()||inY.getText().isEmpty()||inM.getText().isEmpty()||inD.getText().isEmpty()||outY.getText().isEmpty()||outM.getText().isEmpty()||outD.getText().isEmpty()||aCage.getText().isEmpty()) {
	            	JOptionPane.showMessageDialog(null, "�ڵ�, �Կ���¥, �����¥, �������� ��� �������ּ���", "�ű� �Կ� ���", JOptionPane.ERROR_MESSAGE);
	            }else {	        
		            	t_inY = inY.getText(); t_inM = inM.getText(); t_inD = inD.getText();
		            	t_outY = inY.getText(); t_outM = outM.getText(); t_outD = outD.getText();
		            	t_aCage = aCage.getText(); t_sym = aSym.getText();
	            		
		            	try {
		            		intT_year = Integer.parseInt(t_inY); intT_month = Integer.parseInt(t_inM); intT_date = Integer.parseInt(t_inD);
		            	}catch(NumberFormatException ne) {
		            		JOptionPane.showMessageDialog(null, "�Կ���¥�� ���ڷ� �������ּ���", "�ű� �Կ� ���", JOptionPane.ERROR_MESSAGE);
		            		inY.setText("");	inM.setText(""); inD.setText("");
		            	}     
		            	
		            	try {
		            		 intT_outyear = Integer.parseInt(t_outY); intT_outmonth = Integer.parseInt(t_outM); intT_outdate = Integer.parseInt(t_outD);
		            	}catch(NumberFormatException ne) {
		            		JOptionPane.showMessageDialog(null, "�����¥�� ���ڷ� �������ּ���", "�ű� �Կ� ���", JOptionPane.ERROR_MESSAGE);
		            		outY.setText("");	outM.setText(""); outD.setText("");
		            	}   

	            		if(t_inY.length() != 4 || t_inM.length() >2 || t_inD.length() > 2 ) 
		            		JOptionPane.showMessageDialog(null, "�Կ���¥�� Ȯ�����ּ���", "�ű� �Կ� ���", JOptionPane.ERROR_MESSAGE);
		            	else if(t_outY.length() != 4 || t_outM.length() >2 || t_outD.length() >2)
		            		JOptionPane.showMessageDialog(null, "�����¥�� Ȯ�����ּ���", "�ű� �Կ� ���", JOptionPane.ERROR_MESSAGE);
	            		else {
	            				strSql = "INSERT INTO pethp_adm (adm_code, adm_year, adm_month, adm_date, adm_outyear, adm_outmonth, adm_outdate, adm_cage, sym_code, pet_id) VALUES ('"+t_code+"',"+t_inY+","+t_inM+","+t_inD+","+t_outY+","+t_outM+","+t_outD+",'"+t_aCage+"', '"+t_sym+"', '"+g_id+"');";
	            				
            					dbSt.executeUpdate(strSql);
            					System.out.println("����Էµ�");	
            					JOptionPane.showMessageDialog(null, "�Կ� ������ �߰��Ǿ����ϴ�.", "�ű� �Կ� ���", JOptionPane.INFORMATION_MESSAGE);
            					dispose(); 
	            			}
	            			}
	            			
	            		
	            dbSt.close();
				con.close();   
			}catch(SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		}
	}

	public void getId(String g_id2) {
		g_id = g_id2;
	}

	class winEvent implements WindowListener{
		public void windowOpened(WindowEvent we) {
			 try{
			      Class.forName("com.mysql.cj.jdbc.Driver");
			      System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			    }catch(ClassNotFoundException e){
			      System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			    }
			    try{
			      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
			      System.out.println("DB ���� �Ϸ�");
			      Statement dbSt = con.createStatement();
			      System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
			      String strSql;
			      ResultSet rs;
			      String t_name = "", t_age = "", t_spc = "", t_own_name = "", t_tel = "", t_tel_number ="", t_p2 = "", t_p3 = "", t_sex = "";
			      String sex = "", surgery = "";
			      strSql = "SELECT * FROM pethp_patient WHERE pet_id = '"+g_id+"';";
			      rs = dbSt.executeQuery(strSql);
			      if(rs.next()) {
			    	 t_name = rs.getString("pet_name");
			    	 t_age = rs.getString("pet_age");
			    	 t_spc = rs.getString("pet_spc");
			    	 t_own_name = rs.getString("owner_name");
			    	 t_tel = rs.getString("owner_tel");
					 t_tel_number = rs.getString("owner_tell_number");
					 
					 t_p2 = t_tel_number.substring(0, t_tel_number.length()/2);
					 t_p3 = t_tel_number.substring(t_tel_number.length()-4, t_tel_number.length());
					 
					 try {
						 sex = t_sex.substring(0,1);
						 surgery = t_sex.substring(2,sex.length());
					 }catch(StringIndexOutOfBoundsException e) {
						 System.out.println("��������");
					 }
					 if(!surgery.isEmpty()) {
						 noSex.setSelected(true);
					 }
			     
					 pName.setText(t_name); pAge.setText(t_age); pSpc.setText(t_spc);
					 pOwnName.setText(t_own_name);	uP2.setText(t_p2);	uP3.setText(t_p3);
					 
					 if(sex.equals("��")) {
						 pMSex.setSelected(true);
					 }else if(sex.equals("��")) {
						 pFSex.setSelected(true);
					 }
					 int i = 0;
					 while(code[i] == t_tel) {
						 uP1.setSelectedIndex(i);
						 i++;
					 } 
					 
					 setting();
					 
			     }
			     
			      dbSt.close();
			      con.close();

			    }catch(SQLException e){
			      System.out.println("SQLException : " + e.getMessage());
			    }
		}
		public void windowClosing(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowActivated(WindowEvent we) {}
		public void windowDeactivated(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}
		
		public void setting() {
			pName.setEditable(false); pAge.setEditable(false); pSpc.setEditable(false); pOwnName.setEditable(false);
			 uP2.setEditable(false); uP3.setEditable(false); uP1.setEditable(false);
			 pMSex.setEnabled(false); pFSex.setEnabled(false); noSex.setEnabled(false);
		}
	}

}

	public static void main(String[] args) {
		PatientInfo win = new PatientInfo();
		win.setSize(1000, 700);
		win.setLocation(200,200);
		win.show();
	}
}
