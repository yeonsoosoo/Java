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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable.*;
import javax.swing.border.*;

class PethpMain extends JFrame {
	
	PethpMain(){
		JTabbedPane jtp = new JTabbedPane();
		petHp hpp = new petHp();
		PatientInfo pinf = new PatientInfo();
		Drug drw = new Drug();
		
		jtp.addTab("����", hpp);
		jtp.addTab("ȯ������", pinf);
		jtp.addTab("��ǰ����",drw);

		Container ct = getContentPane();
		ct.add(jtp);
	}

	public static void main(String args[]){
			PethpMain winm = new PethpMain();
			winm.setTitle("����� ��������");
			winm.setSize(1000,750);
			winm.setLocation(300, 150);
			winm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			winm.setVisible(true);
			winm.setResizable(false);
	}
	
//����ȭ��
class petHp extends JPanel implements ActionListener, MouseListener {           

	//��,��,�� JLabel, ���᳻��, ���� JLabel
	JLabel JL_year, JL_month, JL_date, JL_tl, JL_res;
	//�����г�, �����г�, ��¥�г�, ��ư�г�, ���̺�1�г�, ���̺�2�г�, ���̺�3�г�
	JPanel JP_center, JP_west, JP_date, JP_button, JP_table1, JP_table2, JP_table3;
	//��,��,�� �޺��ڽ�
	JComboBox JCB_year, JCB_month, JCB_date;
	//����, ���� ��ư
	JButton btn_modify1, btn_modify2, btn_delete, btn_check;
	Vector<String> columnName1, columnName2, columnName3; //ǥ�� �� �÷� ����
	Vector<Vector<String>> rowData1, rowData2, rowData3; //ǥ�ȿ� ����ũ���� ������ ���� ���
	JTable table1 = null;
	JTable table2 = null;
	JTable table3 = null;
	DefaultTableModel model1 = null;
	DefaultTableModel model2 = null;
	DefaultTableModel model3 = null;
	JScrollPane tableSP1, tableSP2, tableSP3;
	private Vector<String> in1, in2; //���̺� ǥ�õ� 1���� ���� �����ϴ� ����
	int row1, row2; //���̺��� ���õ� ���� ��ġ�� ������ ���� 
  	String p_name, p_age, p_sex, p_spc, tyear, tmonth, tdate, state, time, sname, drugn, druguse, mem;
  	String a_name, a_age, a_spc, a_sex, a_outyear, a_outmonth, a_outdate, a_owner, a_cage; //���� Ŭ�� �� ���� �޾ƿ��� ����
  	int y, x; //���콺�� �ִ� y�� ��ġ
	String t_year, t_month, t_date; //�޺��ڽ� ��,��,���� ������ ����
	int intT_year, intT_month, intT_date; //�޺��ڽ� ��,��.���� int����ȯ	
	
	//��¥ �޺��ڽ��� �� �迭
	String year[] = {"2020"};
	String month[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12"};
	String date[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23","24", "25", "26", "27", "28", "29","30","31"};
	
	petHp() {
				
		setSize(1000,700);
	            //BorderLayout���� ����
       		setLayout(new BorderLayout());
        
		JL_tl = new JLabel("���� ����");
        		JL_res = new JLabel("�Կ� ���");
        
       		JP_table1 = new JPanel();
        		JP_table1.setPreferredSize(new Dimension(750,400));
        		JP_table2 = new JPanel();
        		JP_table2.setPreferredSize(new Dimension(750,300));
        		JP_table3 = new JPanel();
        		JP_table3.setPreferredSize(new Dimension(250,480));
        
        		JP_table1.setLayout(new FlowLayout());
        		JP_table2.setLayout(new FlowLayout());
        
        		btn_modify1 = new JButton("���᳻�� ����");
        		btn_modify2 = new JButton("�Կ���� ����");
        		btn_delete = new JButton("��  ��");
        		btn_check = new JButton("��  ȸ");

		btn_modify1.setPreferredSize(new Dimension(200,30));
		btn_modify2.setPreferredSize(new Dimension(200,30));
		btn_delete.setPreferredSize(new Dimension(200,30));
        
        		JCB_year = new JComboBox(year);
        		JCB_month = new JComboBox(month);
        		JCB_date = new JComboBox(date);
       

        		//��,��,�� JLabel
    		JL_year = new JLabel("��");
    		JL_month = new JLabel("��");
    		JL_date = new JLabel("��");
        
        		//���᳻�� ���̺� ���߰�
        		columnName1 = new Vector<String>();
        		columnName1.add("�̸�"); columnName1.add("����");
        		columnName1.add("����"); columnName1.add("ǰ��");
        		columnName1.add("���Ῥ��"); columnName1.add("�����");
        		columnName1.add("������"); columnName1.add("����ð�");
        		columnName1.add("����"); columnName1.add("��ǰ");
        		columnName1.add("����"); 
		columnName1.add("����");
		columnName1.add("�޸�");
        
        		rowData1 = new Vector<Vector<String>>();
        		model1 = new DefaultTableModel(rowData1, columnName1);
        		table1 = new JTable(model1) {
			public boolean isCellEditable(int rowData1, int columnName1){ 
          				  return false;
         			}
		}; //���̺� �� ���� ���ϰ���
        		tableSP1 = new JScrollPane(table1);
        		tableSP1.setPreferredSize(new Dimension(700,350));
        		JP_table1.add(JL_tl);
        		JP_table1.add(tableSP1);
		table1.getTableHeader().setReorderingAllowed(false); //���̺����
      		table1.getTableHeader().setResizingAllowed(false); //���̺����
        
        		//�Կ����� ���̺� ���߰�
        		columnName2 = new Vector<String>();
        		columnName2.add("�̸�"); columnName2.add("����");
        		columnName2.add("����"); columnName2.add("ǰ��");
        		columnName2.add("�������"); columnName2.add("�����");
        		columnName2.add("�����"); columnName2.add("��ȣ�ڸ�"); 
        		columnName2.add("�Կ���");
        
       		 rowData2 = new Vector<Vector<String>>();
       		 model2 = new DefaultTableModel(rowData2, columnName2);
         	      	 table2 = new JTable(model2) {
			public boolean isCellEditable(int rowData2, int columnName2){
          				  return false;
         			}
		};
          		 tableSP2 = new JScrollPane(table2);
        		 tableSP2.setPreferredSize(new Dimension(700,220));
        		 JP_table2.add(JL_res, BorderLayout.NORTH);
        		 JP_table2.add(tableSP2, BorderLayout.CENTER);
		 table2.getTableHeader().setReorderingAllowed(false);
      		 table2.getTableHeader().setResizingAllowed(false);
        
        		//�ð�ǥ ���̺� ���߰�
        		columnName3 = new Vector<String>();
        		columnName3.add("�ð�"); columnName3.add("��ȣ�ڸ�");
        	
        		rowData3 = new Vector<Vector<String>>();
        		model3 = new DefaultTableModel(rowData3, columnName3);
		table3 = new JTable(model3) {
			public boolean isCellEditable(int rowData3, int columnName3){
          				  return false;
         			}
		};
        		tableSP3 = new JScrollPane(table3);
        		tableSP3.setPreferredSize(new Dimension(200,470));
        		JP_table3.add(tableSP3);
		TableColumnModel tt = table3.getColumnModel();
		tt.getColumn(0).setPreferredWidth(70); 
      		tt.getColumn(1).setPreferredWidth(180);
		table3.getTableHeader().setReorderingAllowed(false);
      		table3.getTableHeader().setResizingAllowed(false); 
        
        		//�ð�ǥ�� ��¥, ��ư�� �����ִ� �г�
        		JP_center = new JPanel();
        		JP_center.setLayout(new FlowLayout());
        
        		//��¥ �г�
        		JP_date = new JPanel();
        		JP_date.setLayout(new FlowLayout());
        		JP_date.setPreferredSize(new Dimension(250,70));
        
        		//������ �Կ� ���� ���� ���, �Կ� ��Ȳ �����ִ� �г�
        		JP_west = new JPanel();
        		JP_west.setLayout(new BorderLayout());
        
        		//����, ���� ��ư �г�
        		JP_button = new JPanel();
        		JP_button.setLayout(new BorderLayout());

		//��ư �׼��̺�Ʈ �߰�
		btn_check.addActionListener(this);
		btn_modify1.addActionListener(this);
		btn_modify2.addActionListener(this);

		//���̺� ���콺�̺�Ʈ �߰�
		table1.addMouseListener(this); table2.addMouseListener(this);
		//���� ��ư �׼��̺�Ʈ �߰�
		btn_delete.addActionListener(this);
        
        		JP_west.add(JP_table1, BorderLayout.NORTH);
        		JP_west.add(JP_table2, BorderLayout.CENTER);
        
        		JP_date.add(JCB_year); JP_date.add(JL_year);
        		JP_date.add(JCB_month); JP_date.add(JL_month);
        		JP_date.add(JCB_date); JP_date.add(JL_date);
        		JP_date.add(btn_check);
        
        		JP_center.add(JP_date);
        		JP_center.add(JP_table3);
        		JP_center.add(JP_button);
        
        		JP_button.add(btn_modify1, BorderLayout.NORTH);
        		JP_button.add(btn_modify2, BorderLayout.CENTER);
        		JP_button.add(btn_delete, BorderLayout.SOUTH);
            
        		add(JP_west, BorderLayout.WEST);
        		add(JP_center, BorderLayout.CENTER);
        
		}

public void actionPerformed(ActionEvent ae) {
	String s = ae.getActionCommand();
		//��ȸ��ư Ŭ����
		if ( s == "��  ȸ") {
			select();		
		}
		//���� ��ư Ŭ����
		else if (s == "��  ��") {
				
			try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //mysql�� jdbc Driver �����ϱ�
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			} catch(ClassNotFoundException e) {
			System.err.println("����̹� �ε忡 �����߽��ϴ�");
			}
	
			try { //mysql�� ���� petHp �����ͺ��̽��� �����ϱ�
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC", "root", "1829730!");
			System.out.println("DB ���� �Ϸ�.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");  //DB������
			//���콺�� ���᳻�� ���̺� Ŭ���� ��
			if ( x == 1) { 
				String strSql_delete1;
				String time = (String) model1.getValueAt(row1, 7);
				strSql_delete1 = "DELETE from petHp_tl WHERE tl_year ='"+t_year+"' and tl_month='"+t_month+"' and tl_date='"+t_date+"' and tl_time='"+time+"';";
				dbSt.executeUpdate(strSql_delete1);
				select();
			}
			//���콺�� �Կ����̺� Ŭ��
			else if ( x == 2) {
				String strSql_delete2;
				String cage = (String) model2.getValueAt(row2, 8);
				strSql_delete2 = "DELETE from petHp_adm WHERE adm_cage = '"+cage+"';";
				dbSt.executeUpdate(strSql_delete2);
				select();
			}
			dbSt.close();
			con.close();
			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
			} catch(SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
			}

		}
		
		//�Կ���� ������ư Ŭ����
		else if( s == "�Կ���� ����") {
			adm_modify mf = new adm_modify("�Կ���� ����");
			mf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        			mf.setSize(450, 550); mf.setLocation(1300, 150);
        			mf.show();
      			mf.getName(a_name); mf.getAge(a_age); mf.getSex(a_sex);
        	        		mf.getSpc(a_spc); mf.getoutYear(a_outyear); mf.getoutMonth(a_outmonth);
        	        		mf.getoutDate(a_outdate); mf.getOwner(a_owner); mf.getCage(a_cage); //�Լ��� ����� ������ �Ѱ���
		}
		//���᳻�� ������ư Ŭ����
		else if( s == "���᳻�� ����"){
			Modifytl mtl = new Modifytl();
			mtl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mtl.getName(p_name);	mtl.getAge(p_age);		mtl.getSex(p_sex);
			mtl.getSpe(p_spc);		mtl.getYear(tyear);		mtl.getMonth(tmonth);
			mtl.getDate(tdate);		mtl.getState(state);		mtl.getTime(time);
			mtl.getSname(sname);	mtl.getDrugn(drugn);	mtl.getDruguse(druguse);
			mtl.getMemo(mem);
			mtl.setSize(450,750);
			mtl.setLocation(1300,150);
			mtl.show();
 		}
		else{}
	}

	

	public void mouseClicked(MouseEvent ae) {
                        //���̺��� ���콺Ŭ�� �߻�
		if ( ae.getSource() == table1 ) { x = 1; }
		else if (ae.getSource() == table2 ) { x = 2; }
		
		if( x == 1){
		row1 = table1.getSelectedRow();   
		//���õ� ����� ���� �޾ƿ�
		p_name = (String)table1.getValueAt(row1, 0);
		p_age = (String)table1.getValueAt(row1, 1);
		p_sex = (String)table1.getValueAt(row1, 2);
		p_spc = (String)table1.getValueAt(row1, 3);
		tyear = (String)table1.getValueAt(row1, 4);
		tmonth = (String)table1.getValueAt(row1, 5);
		tdate = (String)table1.getValueAt(row1, 6);
		time = (String)table1.getValueAt(row1, 7);
		state = (String)table1.getValueAt(row1, 8);
		drugn = (String)table1.getValueAt(row1, 9);
		druguse = (String)table1.getValueAt(row1, 10);
		sname = (String)table1.getValueAt(row1, 11);
		mem = (String)table1.getValueAt(row1, 12);
		}
	
		else if( x==2){
		row2 = table2.getSelectedRow();
		//���õ� ����� ���� �޾ƿ�
		a_name = (String)table2.getValueAt(row2, 0);
		a_age = (String)table2.getValueAt(row2, 1);
		a_sex = (String)table2.getValueAt(row2, 2);
		a_spc = (String)table2.getValueAt(row2, 3);
		a_outyear = (String)table2.getValueAt(row2, 4);
		a_outmonth = (String)table2.getValueAt(row2, 5);
		a_outdate = (String)table2.getValueAt(row2, 6);
		a_owner = (String)table2.getValueAt(row2, 7);
		a_cage = (String)table2.getValueAt(row2, 8);
		}
	}
	  //MouseListener�� ������ִ� �߻�޼ҵ�� ��� 5��
        	  public void mousePressed(MouseEvent ae) {}
	  public void mouseReleased(MouseEvent ae) {}
	  public void mouseEntered(MouseEvent ae) {}
	  public void mouseExited(MouseEvent ae) {}

	//��ȸ
	public void select() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //mysql�� jdbc Driver �����ϱ�
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
		} catch(ClassNotFoundException e) {
			System.err.println("����̹� �ε忡 �����߽��ϴ�");
		}
	
		try { //mysql�� ���� petHp �����ͺ��̽��� �����ϱ�
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC", "root", "1829730!");
			System.out.println("DB ���� �Ϸ�.");
			Statement dbSt = con.createStatement();
			Statement dbSt2 = con.createStatement();
			Statement dbSt3 = con.createStatement();
			System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");  //DB������

			//���̺� ���� �ֱ����� �ʱ�ȭ �����ִ� ���
			DefaultTableModel data = (DefaultTableModel)table1.getModel();      
		      	data.setNumRows(0);

			DefaultTableModel data2 = (DefaultTableModel)table2.getModel();      
		      	data2.setNumRows(0);

			DefaultTableModel data3 = (DefaultTableModel)table3.getModel();      
		      	data3.setNumRows(0);
		
			String strSql1, strSql2, strSql3;
			
			//�޺��ڽ��� ��,��,���� �о�ͼ� �ӽ� ������ �����ϱ�
			t_year = JCB_year.getSelectedItem().toString();
			t_month = JCB_month.getSelectedItem().toString();
			t_date = JCB_date.getSelectedItem().toString();

			//�޺��ڽ� ��,��,���� int������ ��ȯ
			intT_year = Integer.parseInt(t_year);
			intT_month = Integer.parseInt(t_month);
			intT_date = Integer.parseInt(t_date);
			
			//���᳻�� �˻��� ���� sql��
			strSql1 = "select * from pethp_tl, pethp_patient, pethp_drug, pethp_sym WHERE pethp_tl.pet_id = pethp_patient.pet_id and pethp_tl.drug_code = pethp_drug.drug_code and pethp_tl.sym_code = pethp_sym.sym_code and tl_year ='"+t_year+"' and tl_month='"+t_month+"' and tl_date='"+t_date+"' ORDER BY tl_time;"; //���᳻�� ���̺��� sql ���Ǿ� ����
			//�Կ���� �˻��� ���� sql��
			strSql2 = "SELECT * FROM pethp_adm, pethp_patient WHERE pethp_adm.pet_id = pethp_patient.pet_id AND pethp_adm.pet_id = pethp_patient.pet_id AND adm_year="+intT_year+" AND adm_outyear="+intT_year+" AND (((adm_month="+intT_month+" AND adm_outmonth="+intT_month+") AND ( "+intT_date+" BETWEEN adm_date AND adm_outdate)) OR ((adm_month="+intT_month+" AND adm_outmonth>"+intT_month+") AND ( "+intT_date+" BETWEEN adm_date AND 31 )) OR ((adm_month<"+intT_month+" AND adm_outmonth="+intT_month+") AND ( "+intT_date+" BETWEEN 1 AND adm_outdate ))) ORDER BY pethp_adm.adm_outdate;";
			//�ð�ǥ�� �� ������ �˻��� sql��
			strSql3 = "select * from pethp_res,pethp_patient where pethp_res.pet_id = pethp_patient.pet_id and res_year ='"+t_year+"' and res_month='"+t_month+"' and res_date='"+t_date+"' ORDER BY res_time;";

			ResultSet result = dbSt.executeQuery(strSql1); //DB�κ��� �о�� ���ڵ带 ��üȭ
			ResultSet result2 = dbSt2.executeQuery(strSql2);
			ResultSet result3 = dbSt3.executeQuery(strSql3);

			while(result.next()) { //DB���� ã�� ������ �������̹Ƿ� while��, ���̺� ���� �߰�����
				//DB�� ������ ���̺� �־���
				Vector <String> in1= new Vector<String>();
				in1.add(result.getString("petHp_patient.pet_name"));
				in1.add(result.getString("petHp_patient.pet_age"));
				in1.add(result.getString("petHp_patient.pet_sex"));
				in1.add(result.getString("petHp_patient.pet_spc"));
				in1.add(result.getString("tl_year"));
				in1.add(result.getString("tl_month"));
				in1.add(result.getString("tl_date"));
				in1.add(result.getString("tl_time"));
				in1.add(result.getString("tl_sta"));
				in1.add(result.getString("petHp_drug.drug_name"));
				in1.add(result.getString("drug_useN"));
				in1.add(result.getString("petHp_sym.sym_name"));
				in1.add(result.getString("tl_memo"));
				
				rowData1.add(in1);
			}
			
			while(result2.next()) { //DB���� ã�� ������ �������̹Ƿ� while��
			
				Vector <String> in2= new Vector<String>();
				in2.add(result2.getString("petHp_patient.pet_name"));
				in2.add(result2.getString("petHp_patient.pet_age"));
				in2.add(result2.getString("petHp_patient.pet_sex"));
				in2.add(result2.getString("petHp_patient.pet_spc"));
				in2.add(result2.getString("adm_outyear"));
				in2.add(result2.getString("adm_outmonth"));
				in2.add(result2.getString("adm_outdate"));
				in2.add(result2.getString("petHp_patient.owner_name"));
				in2.add(result2.getString("adm_cage"));
	
				rowData2.add(in2);
				
			}

			while(result3.next()) { //DB���� ã�� ������ �������̹Ƿ� while��
			
				Vector <String> in3= new Vector<String>();
				in3.add(result3.getString("res_time"));
				in3.add(result3.getString("petHp_patient.owner_name"));
	
				rowData3.add(in3);
				
			}
			dbSt.close(); dbSt2.close(); dbSt3.close();
			con.close(); //DB���� ����
			
			} catch(SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		}
		//���᳻�� ���� Ŭ����
		class Modifytl extends JFrame implements ActionListener, WindowListener{
			
  			JTextField tCode, tName, tAge, tKind, tDisease, tDname, tQuan, tDcode;
  			JTextArea tMemo;
 			JRadioButton Male, Female;
  			JCheckBox check;
 		 	JComboBox tSta, tY, tM, tD, tTime;
			String name, age, msex, spc, Year, Month, Date, State, Time, sName, drugname, drugUse, memo;
			String petid="";
			String ye[] = {"2020", "2021","2022" ,"2023"};
			String mo[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12"};
			String ti [] = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
			Vector da = new Vector(); //�� 
	            		String code="", symcode="", drugCode="";
  			String [] statement = {"����Ϸ�","����","�Կ�"};
  			ButtonGroup group;
  			JButton ok, cancel;

			DefaultComboBoxModel model;

  			Modifytl(){
				this.addWindowListener(this);
    				setTitle("���� ���� ����");
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
				tY = new JComboBox(ye);
				tM = new JComboBox(mo);
				tD = new JComboBox(da);

				model = new DefaultComboBoxModel(da);
				tD.setModel(model);

				settingDay setcla = new settingDay();

				tM.addItemListener(setcla);

    				//����ð� ��� ĭ
    				JLabel l_time = new JLabel("����ð�");
    				l_time.setFont(f2);
    				tTime = new JComboBox(ti);

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
		
    				ok = new JButton("����");
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
    				tCode.setBounds(170, 90, 170, 20);  tCode.setEnabled(false);
   				tName.setBounds(170, 140, 170, 20);  tName.setEnabled(false);
    				tAge.setBounds(170, 190, 40, 20);  tAge.setEnabled(false);
    				tKind.setBounds(270, 190, 70, 20);  tKind.setEnabled(false);
    				Male.setBounds(170, 240, 40, 20);  Male.setEnabled(false);
    				Female.setBounds(220, 240, 40, 20);  Female.setEnabled(false);
    				check.setBounds(270, 240, 80, 20);  check.setEnabled(false);
    				tY.setBounds(170, 290, 60, 20);
    				tM.setBounds(240, 290, 40, 20);
    				tD.setBounds(290, 290, 40, 20);	
				tTime.setBounds(170, 340, 40, 20);
				tSta.setBounds(260, 340, 80, 20);	tSta.setEnabled(false);
				tDisease.setBounds(170, 390, 170, 20);  tDisease.setEnabled(false);	
				tDname.setBounds(170, 440, 170, 20);  tDname.setEnabled(false);
    				tDcode.setBounds(170, 490, 80, 20);  tDcode.setEnabled(false);
    				tQuan.setBounds(300, 490, 40, 20);  tQuan.setEnabled(false);
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


		class settingDay implements ItemListener{
		
			String tg_year, tg_day;
			int tg_month;
			String in_date[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
			int datein;
	
			public void setday() {
				tg_month = tM.getSelectedIndex();
	
				if(tg_month == 0 ||tg_month ==2 ||tg_month == 4 ||tg_month == 6 ||tg_month==7 ||tg_month==9 ||tg_month==11) {
				datein = 31;
				}else if(tg_month == 1) {
					datein = 29;
				}else{
					datein = 30;
				}
			}

			public void itemStateChanged(ItemEvent ae){
			
				if(ae.getSource().equals(tM) && ae.getStateChange()==1) {
					setday();
					model.removeAllElements();
					for(int i = 0; i<datein; i++) {
						model.addElement(in_date[i]);
					}
				}
		}//actionPerformed ��
	}//settingDay��

			public void getName(String n) { name = n;  }
			public void getAge(String a) { age = a; }	
			public void getSex(String s) { msex = s; }
			public void getSpe(String sp) { spc = sp; }
			public void getYear(String y) { Year = y; }
			public void getMonth(String m) { Month = m; }
			public void getDate(String d) { Date = d; }
			public void getState(String st) { State = st; }	
			public void getTime(String t) { Time = t; }
			public void getSname(String sn) { sName = sn; }
			public void getDrugn(String dn) { drugname = dn; }
			public void getDruguse(String du) { drugUse = du; }
			public void getMemo(String me) { memo = me; }

			public void windowOpened(WindowEvent we){
			
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
                                             	Statement dbSt2 = con.createStatement();
            			System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
				String strSql;
	            
	           			strSql = "SELECT pet_id FROM pethp_patient WHERE pet_name='"+name+"' AND pet_age='"+age+"' AND pet_sex='"+msex+"' AND pet_spc='"+spc+"';";
	                     		ResultSet rs1 = dbSt.executeQuery(strSql);
	            
            			//ȯ�� ���� ����Ʈ �� ��������
	           			while(rs1.next()) {
            				petid = rs1.getString("pet_id");
            			}

				strSql = "SELECT tl_code, sym_code, drug_code FROM pethp_tl WHERE pet_id='"+petid+"' AND tl_year='"+Year+"' AND tl_month='"+Month+"' AND tl_time='"+Time+"' AND tl_sta='"+State+"' AND drug_useN='"+drugUse+"' AND  tl_memo='"+memo+"';";
	            			ResultSet rs2 = dbSt2.executeQuery(strSql);
	           			
					while(rs2.next()) {
	            				code = rs2.getString("tl_code");
						symcode = rs2.getString("sym_code");
						drugCode = rs2.getString("drug_code");
	            			}	

					dbSt2.close();	dbSt.close();
					con.close();
			}catch(SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
			
				tCode.setText(code);  tName.setText(name);  tAge.setText(age);  tKind.setText(spc);  
	                   		tY.setSelectedItem(Year);	tM.setSelectedItem(Month);  tD.setSelectedItem(Date);  tTime.setSelectedItem(Time); 

				tDname.setText(drugname);  tQuan.setText(drugUse);  tMemo.setText(memo);
				tDisease.setText(sName);	tDcode.setText(drugCode);

        				if(msex.equals("��")){
					Male.setSelected(true);
        				}
        				else if(msex.equals("��")){
					Female.setSelected(true);
        				}
				else if(msex.equals("�� �߼�ȭ")){
					Male.setSelected(true);
					check.setSelected(true);
				}
				else{
					Female.setSelected(true);
					check.setSelected(true);
				}

				if(State.equals("����Ϸ�")){
					tSta.setSelectedIndex(0);
				}
				else if(State.equals("����")){
					tSta.setSelectedIndex(1);
				}
				else{
					tSta.setSelectedIndex(2);
				}
			}//wndowOpened end
			public void windowClosing(WindowEvent we) {}
			public void windowClosed(WindowEvent we) {}
			public void windowActivated(WindowEvent we) {}
			public void windowDeactivated(WindowEvent we) {}
			public void windowIconified(WindowEvent we) {}
			public void windowDeiconified(WindowEvent we) {}

  			public void actionPerformed(ActionEvent ae) {
    				String s = ae.getActionCommand();
    				String ty="", tm="", td="", ttime="", tmemo="";
		
    			if(s == "CLEAR") {
      				tY.setSelectedIndex(0);  tM.setSelectedIndex(0);  tD.setSelectedIndex(0);
      				tTime.setSelectedIndex(0);  tMemo.setText(" "); 
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
 
        					ty=tY.getSelectedItem().toString();	tm=tM.getSelectedItem().toString();	td=tD.getSelectedItem().toString();	

					ttime=tTime.getSelectedItem().toString();	tmemo=tMemo.getText();

        					//����ȭ �۾�
					
        					if( ty.isEmpty() ||  tm.isEmpty() ||  td.isEmpty() ||  ttime.isEmpty() ){
          						MessageDialog md = new MessageDialog(this, "�߰� ����", true, "�߰� ���� �Է����ּ���.");
          						md.show();  System.out.println("������ �߰� ����");
        					}
        					else{
              					strSql = "UPDATE pethp_tl SET tl_year='"+ty+"', tl_month='"+tm+"', tl_date='"+td+"', tl_time='"+ttime+"',  tl_memo='"+tmemo+"' WHERE tl_code='"+code+"';";
              					dbSt.executeUpdate(strSql);

              					MessageDialog md = new MessageDialog(this, "���� ����", true, "���� ������ �����߽��ϴ�.");
              					md.show();  System.out.println("������ ���� ����");
						dispose();
						select();
        
        					}

					dbSt.close();
					con.close();
      				}
      				catch(SQLException e) {
        					System.out.println("SQLException :" + e.getMessage());
      				}			
    			}	
  		}//end of actionPerformed 



	}//end of class

	//�Կ� ���� Ŭ����
	class adm_modify extends JFrame implements ActionListener, WindowListener {

	JTextField aCode, pName, pAge, pSpc, inY, inM, inD, outY, outM, outD, aCage, pCode, sCode, pOwnName, uP2, uP3, aSym ;
	JComboBox uP1;
	ButtonGroup gSex;
	JRadioButton pMSex, pFSex;
	JCheckBox noSex;
	String code[] = {"010", "070", "02", "031", "032"};
	//DB���� ����� ���� ������ ������
	String petid="",admC="", admY="", admM="", admD="", symC="", ownT="", ownT2="";
	JButton clear, ok;
	String g_id; //pet id
	
	adm_modify(String title){
		this.addWindowListener(this);
		Container ct = getContentPane();
		ct.setLayout(null);
		setTitle(title);
		
		Font f1 = new Font("����", Font.BOLD, 30);
		Font f2 = new Font("����", Font.PLAIN, 17);
		

		JLabel head = new JLabel("�Կ� ����");
		head.setFont(f1);
		
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
		
		head.setBounds(140, 15, 150, 70);
		adm_code.setBounds(70, 100, 100, 20);
		name.setBounds(70, 135, 60, 20);
		age.setBounds(70, 170, 60, 20);
		spc.setBounds(225,170,60,20);
		sex.setBounds(70, 205, 60, 20);
		in_YMD.setBounds(70, 240, 100, 20);
		out_YMD.setBounds(70, 275, 100, 20);
		cage.setBounds(70,310,60,20);
		sym_code.setBounds(70, 345, 80, 20);
		owner.setBounds(70, 380, 80, 20);
		phone.setBounds(70, 415, 80, 20);
		
		aCode.setBounds(155, 100, 190, 20); aCode.setEnabled(false);
		pName.setBounds(155, 135, 190, 20); pName.setEnabled(false);
		pAge.setBounds(155, 170, 50, 20); pAge.setEnabled(false);
		pSpc.setBounds(275, 170, 65, 20); pSpc.setEnabled(false);
		pMSex.setBounds(155, 205, 60, 20); pMSex.setEnabled(false);
		pFSex.setBounds(215, 205, 60, 20); pFSex.setEnabled(false);
		noSex.setBounds(285, 205, 70, 20); noSex.setEnabled(false);
		inY.setBounds(155, 240, 60, 20);
		inM.setBounds(230, 240, 40, 20);
		inD.setBounds(285, 240, 55, 20);
		outY.setBounds(155, 275, 60, 20);
		outM.setBounds(230, 275, 40, 20);
		outD.setBounds(285, 275, 55, 20);
		aCage.setBounds(155, 310, 190, 20);
		aSym.setBounds(155, 345, 190, 20); aSym.setEnabled(false);
		pOwnName.setBounds(155, 380, 190, 20); pOwnName.setEnabled(false);
		uP1.setBounds(155, 415, 55, 20); uP1.setEnabled(false);
		uP2.setBounds(225, 415, 50, 20); uP2.setEnabled(false);
		uP3.setBounds(290, 415, 55, 20); uP3.setEnabled(false);
		
		clear.setBounds(200, 450, 100, 30);
		ok.setBounds(320, 450, 80, 30);

		clear.addActionListener(this);
		ok.addActionListener(this);
		
		ct.add(head); ct.add(adm_code); ct.add(name); ct.add(spc); 
		ct.add(age); ct.add(sex); 
		ct.add(in_YMD); ct.add(out_YMD);
		ct.add(cage); ct.add(sym_code); ct.add(owner); ct.add(phone);
		
		ct.add(aCode); ct.add(pName); ct.add(pAge); ct.add(pSpc);
		ct.add(pMSex); ct.add(pFSex); ct.add(noSex); ct.add(pOwnName);
		ct.add(inY); ct.add(inM); ct.add(inD); ct.add(outY); ct.add(outM); ct.add(outD); 
		ct.add(aCage); ct.add(aSym); ct.add(uP1); ct.add(uP2); ct.add(uP3);
		
		
		ct.add(clear); ct.add(ok);

	}
	//���̺��� ���� ������� �޼ҵ�
	public void getName(String n) {a_name = n; }
	public void getAge(String a) {a_age = a; }
	public void getSex(String s) {a_sex = s;}
	public void getSpc(String sc) {a_spc = sc;}
	public void getoutYear(String oY) {a_outyear = oY;}
	public void getoutMonth(String oM) {a_outmonth = oM;}
	public void getoutDate(String oD) {a_outdate = oD;}
	public void getOwner(String ow) {a_owner = ow;}
	public void getCage(String ca) {a_cage = ca;}
	
	public void windowOpened(WindowEvent e) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
		}catch(ClassNotFoundException ea){
			System.err.println("����̹� �ε忡 �����߽��ϴ�.");
		}
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root", "1829730!");
          			System.out.println("DB ���� �Ϸ�");
           			Statement dbSt = con.createStatement();
            		Statement dbSt2 = con.createStatement();
            		Statement dbSt3 = con.createStatement();
            		System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");
            		String strSql;
            		//���̸�, ����, ����, ǰ������ ����̵� �˻�
            		strSql = "SELECT pet_id FROM petHp_patient WHERE pet_name='"+a_name+"' and pet_age='"+a_age+"' and pet_sex='"+a_sex+"' and pet_spc='"+a_spc+"';";
            		ResultSet result1 = dbSt.executeQuery(strSql);

 		           while(result1.next()) {
            			petid = result1.getString("pet_id");
            		}
 			//�Կ��ڵ�, �����ڵ�, �Կ���¥�� �����¥�� ��������ȣ�� �˻�           
            		strSql = "SELECT adm_code, sym_code, adm_year, adm_month, adm_date from petHp_adm WHERE adm_outyear='"+a_outyear+"' and adm_outmonth='"+a_outmonth+"' and adm_outdate='"+a_outdate+"' and adm_cage='"+a_cage+"';";
			ResultSet result2 = dbSt2.executeQuery(strSql);
			
			while(result2.next()) {
				admC = result2.getString("adm_code");
				admY = result2.getString("adm_year");
				admM = result2.getString("adm_month");
				admD = result2.getString("adm_date");
				symC = result2.getString("sym_code");
			}
			//��ȣ�� ��ȭ��ȣ�� ����̵� �̿��Ͽ� ������
			strSql ="SELECT owner_tel, owner_tell_number FROM petHp_patient WHERE pet_id='"+petid+"';";
			ResultSet result3 = dbSt3.executeQuery(strSql);
			
			while(result3.next()) {
				ownT = result3.getString("owner_tel");
				ownT2 = result3.getString("owner_tell_number");
			}
			dbSt.close(); dbSt2.close(); dbSt3.close();
			con.close();
			}catch(SQLException es) {
				System.out.println("SQLException : " + es.getMessage());
			}
			//������ �������� ����â�� �̸� �������
			aCode.setText(admC); pName.setText(a_name); pAge.setText(a_age);
			pSpc.setText(a_spc); pOwnName.setText(a_owner); inY.setText(admY);
			inM.setText(admM); inD.setText(admD); outY.setText(a_outyear);
			outM.setText(a_outmonth); outD.setText(a_outdate); aCage.setText(a_cage);
			aSym.setText(symC);
		
			if(a_sex.equals("��")){
				pMSex.setSelected(true);
			}
			else if(a_sex.equals("��")){
				pFSex.setSelected(true);
			}
			else if(a_sex.equals("�� �߼�ȭ")){
				pMSex.setSelected(true);
				noSex.setSelected(true);
			}
			else{
				pFSex.setSelected(true);
				noSex.setSelected(true);
			}
			//����� ��ȣ�� 2ĭ�� ���߾� ������
			String ownT2_1 = ownT2.substring(0, ownT2.length()/2);
			String ownT2_2 = ownT2.substring(ownT2.length()-4, ownT2.length());

			for(int i = 0; i <code.length; i++) {
				if(code[i].equals(ownT)) {
					uP1.setSelectedIndex(i);
					uP2.setText(ownT2_1);
					uP3.setText(ownT2_2);
				}
			}
				
				
	}//windowopened��
	public void windowActivated(WindowEvent e) {}
    	public void windowClosed(WindowEvent e) {}
    	public void windowClosing(WindowEvent e) {}
    	public void windowDeactivated(WindowEvent e) {}
    	public void windowDeiconified(WindowEvent e) {}
    	public void windowIconified(WindowEvent e) {}
    
    	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		String t_code="", t_inY="", t_inM="", t_inD="", t_outY="", t_outM="", t_outD="", t_aCage="";
		int intT_year, intT_month, intT_date, intT_outyear, intT_outmonth, intT_outdate;
		
		if ( s=="CLEAR") { //Ŭ�����ư Ŭ���� ������ �� �ִ� ������ Ŭ����
			inY.setText("");
			inM.setText("");
			inD.setText("");
			outY.setText("");
			outM.setText("");
			outD.setText("");
			aCage.setText("");
		} else { //����� ��
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
	            		t_code = aCode.getText(); t_year = inY.getText(); t_month = inM.getText(); t_date = inD.getText();
	    			t_outY = outY.getText(); t_outM = outM.getText(); t_outD = outD.getText();
	    			t_aCage = aCage.getText();
	    	            
	    	            
	    		            if(aCode.getText().isEmpty()||inY.getText().isEmpty()||inM.getText().isEmpty()||inD.getText().isEmpty()||outY.getText().isEmpty()||outM.getText().isEmpty()||outD.getText().isEmpty()||aCage.getText().isEmpty()) {
	    	            	JOptionPane.showMessageDialog(null, "�ڵ�, �Կ���¥, �����¥, �������� ��� �������ּ���", "�Կ� ����", JOptionPane.ERROR_MESSAGE);
	    	           		}else {	    	        
	    		            	t_inY = inY.getText(); t_inM = inM.getText(); t_inD = inD.getText();
	    		            	t_outY = inY.getText(); t_outM = outM.getText(); t_outD = outD.getText();
	    		            	t_aCage = aCage.getText();		
	            		
	    		            	try {
	    		            		intT_year = Integer.parseInt(t_inY); intT_month = Integer.parseInt(t_inM); intT_date = Integer.parseInt(t_inD);
	    		            	}catch(NumberFormatException ne) {
	    		            		JOptionPane.showMessageDialog(null, "�Կ���¥�� ���ڷ� �������ּ���", "�Կ� ����", JOptionPane.ERROR_MESSAGE);
						inY.setText("");	inM.setText(""); inD.setText("");
	    		            	}     
	    		            	
	    		            	try {
	    		            		 intT_outyear = Integer.parseInt(t_outY); intT_outmonth = Integer.parseInt(t_outM); intT_outdate = Integer.parseInt(t_outD);
	    		            	}catch(NumberFormatException ne) {
	    		            		JOptionPane.showMessageDialog(null, "�����¥�� ���ڷ� �������ּ���", "�Կ� ����", JOptionPane.ERROR_MESSAGE);
	    		            		outY.setText("");	outM.setText(""); outD.setText("");
	    		            	}   

					if(t_inY.length() != 4 || t_inM.length() >2 || t_inD.length() > 2 ) 
	    		            		JOptionPane.showMessageDialog(null, "�Կ���¥�� Ȯ�����ּ���", "�Կ� ����", JOptionPane.ERROR_MESSAGE);	    	            		
	    		            	else if(t_outY.length() != 4 || t_outM.length() >2 || t_outD.length() >2)
	    		            		JOptionPane.showMessageDialog(null, "�����¥�� Ȯ�����ּ���", "�Կ� ����", JOptionPane.ERROR_MESSAGE);
	    	            		else {	    	       						
							//�Կ����� ����
	    		            			strSql = "UPDATE pethp_adm SET adm_year = "+t_inY+", adm_month="+t_inM+", adm_date="+t_inD+", adm_outyear="+t_outY+", adm_outmonth="+t_outM+", adm_outdate="+t_outD+", adm_cage='"+t_aCage+"' WHERE adm_code= '"+admC+"';";
	                					
	                					dbSt.executeUpdate(strSql);
	                					System.out.println("����Էµ�");	
	                					JOptionPane.showMessageDialog(null, "�Կ� ������ �����Ǿ����ϴ�.", "�Կ� ����", JOptionPane.INFORMATION_MESSAGE);
	                					dispose(); 
							select();
							    	            			
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

	class MessageDialog extends JDialog implements ActionListener{
  		JButton ok;
  		MessageDialog(JFrame parent, String title, boolean mode, String msg){ 
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
   
    		this.setLocation(1400, 150);
  		}
  
		  public void actionPerformed(ActionEvent ae) {
			    dispose(); 
		  }
		}


	}


//���γ�


public class PatientInfo extends JPanel{
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
		
		//this.addWindowListener(new showTable());
		
		setLayout(new FlowLayout());
		
		JPanel Left = new JPanel();
		JPanel Right = new JPanel();
		
		BoxLayout lf = new BoxLayout(Left, BoxLayout.Y_AXIS);
		BoxLayout rg = new BoxLayout(Right,BoxLayout.Y_AXIS);
		JPanel b1 = new JPanel();
		b1.setPreferredSize(new Dimension(1000,30));
		Right.setPreferredSize(new Dimension(250, 510));
		
		Left.setLayout(lf);
		Right.setLayout(rg);
		
		add(b1);
		add(Left); add(Right);
		
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
				np.setLocation(1300,150);
				np.setResizable(false);
				np.show();
			}else if(s == "�ű� ����") {
				NewReserve nr = new NewReserve();
				nr.getId(g_id);
				nr.getDate(to_year, to_month, to_day);
				nr.setSize(450, 600);
				nr.setResizable(false);
				nr.setLocation(1300,150);
				nr.show();
			}else if(s == "�Կ�") {
				NewAdm na = new NewAdm();
				na.getId(g_id);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		       	 na.setSize(450, 750);
		       	 na.setLocation(1300, 150);
			na.setResizable(false);
		        na.show();
			}else if(s == "����") {
				NewTl nt = new NewTl();
				nt.getId(g_id);
				nt.setResizable(false);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        nt.setSize(450, 750);
		        nt.setLocation(1300, 150);
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
		            
		            	dbSt.close();
				con.close();
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
	            	dbSt.close();
			con.close();
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
				patientScp ps = new patientScp("ȯ�� ���� ����");	
				ps.getid(g_id);
				ps.setSize(800, 500);
				ps.setLocation(1000, 150);
				ps.setResizable(false);
				ps.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				ps.show();

			}	
		}//mouseClicked ��
		
		public void mousePressed(MouseEvent ae) {}
		public void mouseReleased(MouseEvent ae) {}
		public void mouseEntered(MouseEvent ae) {}
		public void mouseExited(MouseEvent ae) {
		}
	}//tableClick class end
	
	

public class NewTl extends JFrame implements ActionListener{
  JTextField tCode, tName, tAge, tKind, tDisease, tDname, tQuan, tDcode;
  JTextArea tMemo;
  JRadioButton Male, Female;
  JCheckBox check;
  JComboBox tSta, tY, tM, tD, tTime;
  String [] statement = {"����Ϸ�","����","�Կ�"};
  ButtonGroup group;
  JButton ok, cancel;
  String ye[] = {"2020", "2021","2022" ,"2023"};
  String mo[] = {"1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11","12"}; 
  String ti[] = {"09", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
  Vector da = new Vector(); //�� 

DefaultComboBoxModel model;

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
    tY = new JComboBox(ye);
    tM = new JComboBox(mo);
    tD = new JComboBox(da);

   model = new DefaultComboBoxModel(da);   
   tD.setModel(model);
   
   tM.addItemListener(new settingDay());
   tD.addItemListener(new settingDay());
    //����ð� ��� ĭ
    JLabel l_time = new JLabel("����ð�");
    l_time.setFont(f2);
    tTime = new JComboBox(ti);

   


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
      tY.setSelectedIndex(0);
      tM.setSelectedIndex(0);  tD.setSelectedIndex(0);
      tTime.setSelectedIndex(0);  tDisease.setText(" ");
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
 
        tname=tName.getText();  tage=tAge.getText();  tkind=tKind.getText();  
        tcode=tCode.getText();  tdisease=tDisease.getText();
        tdname=tDname.getText();  tquan=tQuan.getText();  tmemo=tMemo.getText(); tdcode=tDcode.getText();

        td=(String)tD.getSelectedItem();
        ty=(String)tY.getSelectedItem(); 
        tm=(String)tM.getSelectedItem();
        ttime=(String)tTime.getSelectedItem(); 

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
          boolean check1=true, check3=false, check4=false;

          if(check1){  //�ڵ� ���� ���� 
            strSql = "SELECT * FROM pethp_tl WHERE tl_code='"+tcode+"';";
            ResultSet result = dbSt.executeQuery(strSql);
            while(result.next()){
              check1 = false;
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
              strSql = "INSERT INTO pethp_tl VALUES ('"+tcode+"', '"+g_id+"', '"+ty+"', '"+tm+"', '"+td+"', '"+ttime+"', '"+tsta+"', '"+drugcode+"', '"+drugyear+"', '"+drugmonth+"', '"+drugdate+"', "+tquan+", '"+symcode+"', '"+tmemo+"');";       
              dbSt.executeUpdate(strSql);

              MessageDialog3 md = new MessageDialog3(this, "�߰� ����", true, "���� ������ �߰��߽��ϴ�.");
              md.show();  System.out.println("������ �߰� ����");
        
              strSql = "UPDATE pethp_drug SET drug_st = drug_st-"+tquan+" WHERE drug_code='"+drugcode+"';";
              dbSt.executeUpdate(strSql);
            }
          }
        }
        dispose();
        dbSt.close();
        con.close();
      }
      catch(SQLException e) {
        System.out.println("SQLException :" + e.getMessage());
      }			
    }	
  }//end of actionPerformed 
class settingDay implements ItemListener{
		
		String g_year, g_month, g_day, g_time;
		String in_date[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23","24", "25", "26", "27", "28", "29","30","31"};
		
		int getDay;
		public void setday() {
			g_month = tM.getSelectedItem().toString();
			if(g_month == "1"||g_month == "3"||g_month == "5"||g_month == "7"||g_month=="8"||g_month=="10"||g_month=="12") {
				getDay = 31;
			}else if(g_month == "2") {
				getDay = 29;
			}else{
				getDay = 30;
			}
		}
		
		
		public void itemStateChanged(ItemEvent ie) {

			
			if(ie.getSource().equals(tM) && ie.getStateChange() == 1) {
				setday();
				model.removeAllElements();
				for(int i = 0; i<getDay; i++) {
					model.addElement(in_date[i]);}
			}
		}
	}//settinDay��
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
						 surgery = t_sex.substring(2,t_sex.length());
					 }catch(StringIndexOutOfBoundsException e) {
						 
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
						dbSt.close();
						con.close();
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
					 
					for(int i = 0;i <code.length; i++) {
						 if(code[i].equals(t_tel)) uP1.setSelectedIndex(i);
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
			pMSex.setSelected(false);
			pFSex.setSelected(false);
			noSex.setSelected(false);
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
	           			} else {	        
				t_code = aCode.getText();
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
				 t_sex = rs.getString("pet_sex");
			    	 t_own_name = rs.getString("owner_name");
			    	 t_tel = rs.getString("owner_tel");
					 t_tel_number = rs.getString("owner_tell_number");
					 
					 t_p2 = t_tel_number.substring(0, t_tel_number.length()/2);
					 t_p3 = t_tel_number.substring(t_tel_number.length()-4, t_tel_number.length());
					 
					 try {
						 sex = t_sex.substring(0,1);
						 surgery = t_sex.substring(2,t_sex.length());
					 }catch(StringIndexOutOfBoundsException e) {
						
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
					 for(int i = 0;i <code.length; i++) {
						 if(code[i].equals(t_tel)) uP1.setSelectedIndex(i);
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



}//�Կ��߰�




//ȯ�� �� ���� ���� ����
class patientScp extends JFrame implements ActionListener, KeyListener, ItemListener, WindowListener{	
	Vector<String> header;
	Vector<Vector<String>> content;
	JTable scpTable;
	JTextField pName, pAge, pSex, pK, pO, pTel, pTelnum1, pTelnum2, findt;
	String pname="", age="", sex="", spe="", owner="", tel="", tel_num1="", tel_num2="", gid;
	JComboBox findconstraint;
	JRadioButton Male, Female;
	JCheckBox check;
	JLabel blank;
	JButton btnModify;
	ButtonGroup group;
  
	patientScp(String title){
		this.addWindowListener(this);
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));

		JPanel scp = new JPanel();
		JPanel inform = new JPanel();

		ct.add(scp);
		ct.add(inform);
 
		scp.setLayout(new BorderLayout());

		JPanel find = new JPanel();
		find.setLayout(new FlowLayout(FlowLayout.LEFT));
		String [] constraint = {"����","�⵵","��"};
		findconstraint = new JComboBox(constraint);
		findconstraint.setPreferredSize(new Dimension(60, 30));
		findt = new JTextField(20);
		JButton findb = new JButton("�� ��");
		JButton findf = new JButton("�ʱ�ȭ");
		findb.addActionListener(this);  findf.addActionListener(this);
		find.add(findconstraint);  find.add(findt);  find.add(findb);  find.add(findf);
		find.setPreferredSize(new Dimension(450,50));
    
		content = new Vector<Vector<String>>();
		header = new Vector<String>();
		header.addElement("�̸�");
		header.addElement("����");
		header.addElement("����");
		header.addElement("��");
		header.addElement("��");
		header.addElement("��");

		DefaultTableModel model = new DefaultTableModel(content,header);
		scpTable = new JTable(model){
			public boolean isCellEditable(int rowIndex, int mColIndex){
				return false;
			}
		};
	
		//���̺� �� �� ũ�� ����
		TableColumnModel scpTa = scpTable.getColumnModel();
		scpTa.getColumn(0).setPreferredWidth(100);
		scpTa.getColumn(1).setPreferredWidth(200);
		scpTa.getColumn(2).setPreferredWidth(200);
		scpTa.getColumn(3).setPreferredWidth(100);
		scpTable.setRowHeight(25);

		//���̺� �� �̵� �Ұ�, ũ�� ���� �Ұ�
		scpTable.getTableHeader().setReorderingAllowed(false); 
		scpTable.getTableHeader().setResizingAllowed(false); 

		JScrollPane sc = new JScrollPane(scpTable);
		sc.setPreferredSize(new Dimension(450,350));

		scp.add(find, BorderLayout.NORTH); scp.add(sc, BorderLayout.CENTER);

		//��������ֱ�
		inform.setLayout(new BorderLayout());

		JPanel informscp = new JPanel();
		informscp.setLayout(new GridLayout(7,1));
		informscp.setPreferredSize(new Dimension(250,350));
		JLabel in = new JLabel("ȯ�� ������");
		in.setFont(new Font("SansSerif", Font.BOLD, 20));   

		JPanel name = new JPanel();
		name.setLayout(new FlowLayout());
		JLabel pN = new JLabel("�̸�         ");
		pN.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		blank = new JLabel("                  ");
		pName = new JTextField(10);
		pName.setEnabled(false);
		name.add(pN);  name.add(pName);  name.add(blank);

		JPanel jage = new JPanel();
		jage.setLayout(new FlowLayout());
		JLabel pA = new JLabel("����         ");
		pA.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		pAge = new JTextField(4);
		pAge.addKeyListener(this);
		blank = new JLabel(" ��                                 ");
		jage.add(pA);  jage.add(pAge);  jage.add(blank);

		JPanel s = new JPanel();
		s.setLayout(new FlowLayout());
		JLabel pS = new JLabel("����         ");
		pS.setFont(new Font("SansSerif", Font.BOLD, 15)); 
    		Male = new JRadioButton("��");
    		Female = new JRadioButton("��");  
    		group = new ButtonGroup();
    		group.add(Male);
    		group.add(Female);
		check = new JCheckBox("�߼�ȭ");
		Male.addItemListener(this);
		Female.addItemListener(this);
		check.addItemListener(this);
		blank = new JLabel("     ");
		s.add(pS);   s.add(Male);  s.add(Female);  s.add(check);  s.add(blank);

		JPanel kind = new JPanel();
		kind.setLayout(new FlowLayout());
		JLabel pk = new JLabel("ǰ��         ");
		pk.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		pK = new JTextField(5);
		pK.setEnabled(false); 
		blank = new JLabel("                                 ");
		kind.add(pk);  kind.add(pK);  kind.add(blank);

		JPanel nameO = new JPanel();
		nameO.setLayout(new FlowLayout());
		JLabel pOn = new JLabel("��ȣ�ڸ�  ");
		pOn.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		pO = new JTextField(5);
		pO.setEnabled(false);
		blank = new JLabel("                                  ");
		nameO.add(pOn); nameO.add(pO);  nameO.add(blank);

		JPanel phone = new JPanel();
		phone.setLayout(new FlowLayout());
		JLabel phonenum = new JLabel("��ȭ��ȣ  ");
		phonenum.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		pTel = new JTextField(4);
		pTel.addKeyListener(this);
		pTelnum1 = new JTextField(4);
		pTelnum2 = new JTextField(4);
		pTelnum1.addKeyListener(this);
		pTelnum2.addKeyListener(this);
		blank = new JLabel(" ");
		phone.add(phonenum);  phone.add(pTel);  phone.add(pTelnum1);  phone.add(pTelnum2);  phone.add(blank);
    
		informscp.add(in);  informscp.add(name);  informscp.add(jage);  informscp.add(s);  informscp.add(kind);  informscp.add(nameO); informscp.add(phone);   

		JPanel button = new JPanel();
		btnModify = new JButton("����");
		btnModify.addActionListener(this); 
		button.add(btnModify); 
	
		btnModify.setEnabled(false);
		inform.add(informscp, BorderLayout.CENTER); 
		inform.add(button, BorderLayout.SOUTH);
	}

	public void getid(String g_id){
		gid = g_id;
	}

	public void select(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
		}
		catch(ClassNotFoundException e){
			System.err.println("����̹� �ε忡 �����߽��ϴ�.");
		}
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC", "root", "1829730!");
			System.out.println("DB ���� �Ϸ�");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
			String strSql = "SELECT pethp_patient.pet_name, tl_sta, pethp_sym.sym_name, tl_year, tl_month, tl_date FROM pethp_tl, pethp_patient, pethp_sym WHERE pethp_tl.sym_code=pethp_sym.sym_code AND pethp_tl.pet_id=pethp_patient.pet_id AND pethp_patient.pet_id='"+gid+"';";
			ResultSet result=dbSt.executeQuery(strSql);
			String name="", state="", sym="", year="", month="", date="";

			DefaultTableModel data = (DefaultTableModel)scpTable.getModel();      
			data.setNumRows(0);

			while(result.next()){
				name = result.getString("pethp_patient.pet_name");
				state = result.getString("tl_sta");
				sym = result.getString("pethp_sym.sym_name");
				year = result.getString("tl_year");
				month = result.getString("tl_month");
				date = Integer.toString(result.getInt("tl_date"));
				Vector <String> tlContent = new Vector <String>();
				tlContent.add(name);  tlContent.add(state);  tlContent.add(sym);  
				tlContent.add(year);  tlContent.add(month);  tlContent.add(date);  

				content.add(tlContent);
			}      
			dbSt.close();
			con.close();
		}
		catch(SQLException e){
			System.out.println("SQLException : " + e.getMessage());
		}
	}//select method end

	//��񿡼� �� �˻��ؼ� �����ͼ� ���̺� ä���ֱ�
	public void windowOpened(WindowEvent ev){
		

		String tel_num="";
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
		}
		catch(ClassNotFoundException e){
			System.err.println("����̹� �ε忡 �����߽��ϴ�.");
		}
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC", "root", "1829730!");
			System.out.println("DB ���� �Ϸ�");
			Statement dbSt = con.createStatement();
			Statement dbSt2 = con.createStatement();
			System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
			String name="", state="", sym="", year="", month="", date="";
			String strSql;

			strSql = "SELECT pet_name, pet_age, pet_sex, pet_spc, owner_name, owner_tel, owner_tell_number FROM pethp_patient WHERE pet_id='"+gid+"';";	//p2019�ڸ��� ���̵� �־������
			ResultSet result1 = dbSt2.executeQuery(strSql);
	
			while(result1.next()){
				pname = result1.getString("pet_name");
				age = result1.getString("pet_age");
				sex = result1.getString("pet_sex");
				spe = result1.getString("pet_spc");
				owner = result1.getString("owner_name");
				tel = result1.getString("owner_tel");
				tel_num = result1.getString("owner_tell_number");
				tel_num1 = tel_num.substring(0, tel_num.length()-4);
				tel_num2 = tel_num.substring(tel_num.length()-4, tel_num.length());
			}        

			strSql = "SELECT pethp_patient.pet_name, tl_sta, pethp_sym.sym_name, tl_year, tl_month, tl_date FROM pethp_tl, pethp_patient, pethp_sym WHERE pethp_tl.sym_code=pethp_sym.sym_code AND pethp_tl.pet_id=pethp_patient.pet_id AND pethp_patient.pet_id='"+gid+"';";
			ResultSet result=dbSt.executeQuery(strSql);

			DefaultTableModel data = (DefaultTableModel)scpTable.getModel();      
			data.setNumRows(0);

			while(result.next()){
				name = result.getString("pethp_patient.pet_name");
				state = result.getString("tl_sta");
				sym = result.getString("pethp_sym.sym_name");
				year = result.getString("tl_year");
				month = result.getString("tl_month");
				date = Integer.toString(result.getInt("tl_date"));
				Vector <String> tlContent = new Vector <String>();
				tlContent.add(name);  tlContent.add(state);  tlContent.add(sym);  
				tlContent.add(year);  tlContent.add(month);  tlContent.add(date);  

				content.add(tlContent);
			}      
			dbSt2.close();
			con.close();
		}
		catch(SQLException e){
			System.out.println("SQLException : " + e.getMessage());
		}

		//�� �����ͼ� �ֱ�
		pName.setText(pname);  pAge.setText(age);  
		pK.setText(spe);  pO.setText(owner);  pTel.setText(tel);  pTelnum1.setText(tel_num1);  pTelnum2.setText(tel_num2);

        		if(sex.equals("��")){
			Male.setSelected(true);
        		}
        		else if(sex.equals("��")){
			Female.setSelected(true);
		}
		else if(sex.equals("�� �߼�ȭ")){
			Male.setSelected(true);
			check.setSelected(true);
		}
		else{
			Female.setSelected(true);
			check.setSelected(true);
		}

		select();

	}//select method end

	//ȯ�� �� ���� ����ִ� �޼ҵ�
	//ȯ�� id �޾ƿ��� �� �־����

	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		String page = "", psex = "", ptel = "", ptelnum = "", ptelnum1="", ptelnum2="";  //ȯ�� �� ����
		String ft="";	//�˻� â�� �Էµ� �� �ֱ�

		if(s.equals("�ʱ�ȭ")){	//�ʱ�ȭ ��ư Ŭ���ϸ� ���̺� �ʱ�ȭ�ǰ� �� �ٽ� �о����
			select();
			scpTable.updateUI();
		}
		else{
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			}
			catch(ClassNotFoundException e){
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
				System.out.println("DB ���� �Ϸ�");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
				String strSql;
				page=pAge.getText();  ptel=pTel.getText();  ptelnum1=pTelnum1.getText();  ptelnum2=pTelnum2.getText();
				if(Male.isSelected()){
					if(check.isSelected())
						psex = "�� �߼�ȭ";
					else
						psex = "��";
					}
				else if(Female.isSelected()){
					if(check.isSelected())
						psex = "�� �߼�ȭ";
					else
						psex = "��";
				}
				if(s.equals("����")){
					if(page.isEmpty() || psex.isEmpty() ||  ptel.isEmpty() || ptelnum1.isEmpty() || ptelnum2.isEmpty()){
						MessageDialog2 md = new MessageDialog2(this, "���� ����", true, "�������� �Է����ּ���");
						md.show();  System.out.println("���� ����");
					}
					else if(!page.matches("^[0-9]*$") || page.equals("0")){
						MessageDialog2 md = new MessageDialog2(this, "���� ����", true, "���̸� �ùٸ��� �Է����ּ���.");
						md.show();  System.out.println("���� ����");
					}
					else if(psex.matches("^[0-9]*$")){
						MessageDialog2 md = new MessageDialog2(this, "���� ����", true, "���ڰ� �ƴ� ���ڸ� �־��ּ���");
						md.show();  System.out.println("���� ����");
					}
					else if(!ptel.matches("^[0-9]*$") || !ptelnum.matches("^[0-9]*$")){
						MessageDialog2 md = new MessageDialog2(this, "���� ����", true, "���ڸ� �Է����ּ���.");
						md.show();  System.out.println("���� ����");
					}
					else{
						ptelnum = ptelnum1+ptelnum2;
						strSql = "UPDATE pethp_patient SET pet_age='"+page+"', pet_sex='"+psex+"', owner_tel='"+ptel+"', owner_tell_number='"+ptelnum+"' WHERE pet_id='"+gid+"';";
						dbSt.executeUpdate(strSql);
						MessageDialog2 md = new MessageDialog2(this, "���� �Ϸ�", true, "�����Ǿ����ϴ�.");
						md.show();  System.out.println("���� �Ϸ�");     
						select();
						scpTable.updateUI();   
					}
				}
				else if(s.equals("�� ��")){
					ft = findt.getText();		//�˻� â�� �Էµ� �� �޾ƿͼ� ������ �ֱ�

					if(findconstraint.getSelectedIndex() == 0){
						strSql = "SELECT pethp_patient.pet_name, tl_sta, pethp_sym.sym_name, tl_year, tl_month, tl_date FROM pethp_tl, pethp_patient, pethp_sym WHERE pethp_tl.pet_id=pethp_patient.pet_id AND pethp_tl.sym_code=pethp_sym.sym_code AND pethp_patient.pet_id='"+gid+"' AND tl_sta='"+ft+"';";      
						ResultSet result = dbSt.executeQuery(strSql);
						String name="", state="", sym="", year="", month="", date="";

						DefaultTableModel data = (DefaultTableModel)scpTable.getModel();      
						data.setNumRows(0);
						while(result.next()){
							name = result.getString("pethp_patient.pet_name");
							state = result.getString("tl_sta");
							sym = result.getString("pethp_sym.sym_name");
							year = result.getString("tl_year");
							month = result.getString("tl_month");
							date = Integer.toString(result.getInt("tl_date"));
							Vector <String> search = new Vector <String>();
							search.add(name);  search.add(state);  search.add(sym);  
							search.add(year);  search.add(month);  search.add(date);  

							content.add(search);
						}
						if(scpTable.getRowCount() == 0){
							MessageDialog2 md = new MessageDialog2(this, "�˻� ����", true, "�˻� ���� �������� �ʽ��ϴ�.");
							md.show();  System.out.println("�˻� ����");
							select();
							scpTable.updateUI();
						}               
					}
					else if(findconstraint.getSelectedIndex() == 1){
						strSql = "SELECT pethp_patient.pet_name, tl_sta, pethp_sym.sym_name, tl_year, tl_month, tl_date FROM pethp_tl, pethp_patient, pethp_sym WHERE pethp_tl.pet_id=pethp_patient.pet_id AND pethp_tl.sym_code=pethp_sym.sym_code AND pethp_patient.pet_id='"+gid+"' AND tl_year='"+ft+"';";      
						ResultSet result = dbSt.executeQuery(strSql);
						String name="", state="", sym="", year="", month="", date="";

						DefaultTableModel data = (DefaultTableModel)scpTable.getModel();      	
						data.setNumRows(0);
						while(result.next()){
							name = result.getString("pethp_patient.pet_name");
							state = result.getString("tl_sta");
							sym = result.getString("pethp_sym.sym_name");
							year = result.getString("tl_year");
							month = result.getString("tl_month");
							date = Integer.toString(result.getInt("tl_date"));
							Vector <String> search = new Vector <String>();
							search.add(name);  search.add(state);  search.add(sym);  
							search.add(year);  search.add(month);  search.add(date);  

							content.add(search);
						}
						if(scpTable.getRowCount() == 0){
							MessageDialog2 md = new MessageDialog2(this, "�˻� ����", true, "�˻� ���� �������� �ʽ��ϴ�.");
							md.show();  System.out.println("�˻� ����");
							select();
							scpTable.updateUI();
						}               
					}
					else if(findconstraint.getSelectedIndex() == 2){
						strSql = "SELECT pethp_patient.pet_name, tl_sta, pethp_sym.sym_name, tl_year, tl_month, tl_date FROM pethp_tl, pethp_patient, pethp_sym WHERE pethp_tl.pet_id=pethp_patient.pet_id AND pethp_tl.sym_code=pethp_sym.sym_code AND pethp_patient.pet_id='"+gid+"' AND tl_month='"+ft+"';";      
						ResultSet result = dbSt.executeQuery(strSql);
						String name="", state="", sym="", year="", month="", date="";

						DefaultTableModel data = (DefaultTableModel)scpTable.getModel();      
						data.setNumRows(0);
						while(result.next()){
							name = result.getString("pethp_patient.pet_name");
							state = result.getString("tl_sta");
							sym = result.getString("pethp_sym.sym_name");
							year = result.getString("tl_year");
							month = result.getString("tl_month");
							date = Integer.toString(result.getInt("tl_date"));
							Vector <String> search = new Vector <String>();
							search.add(name);  search.add(state);  search.add(sym);  
							search.add(year);  search.add(month);  search.add(date);  

							content.add(search);
						}
						if(scpTable.getRowCount() == 0){
							MessageDialog2 md = new MessageDialog2(this, "�˻� ����", true, "�˻� ���� �������� �ʽ��ϴ�.");		
							md.show();  System.out.println("�˻� ����");
							select();
							scpTable.updateUI();
						}               
					}
				}
				dbSt.close();
				con.close();
			}
			catch(SQLException e){
				System.out.println("SQLException : " + e.getMessage());
			}
		}
	}

	//�� ����Ǹ� ���� ��ư Ȱ��ȭ 
	public void keyPressed(KeyEvent ke){
		btnModify.setEnabled(true);
	}

	public void keyReleased(KeyEvent e) {}	
	public void keyTyped(KeyEvent e) {}

	public void itemStateChanged(ItemEvent ie){
		if(ie.getSource().equals(Male) && ie.getStateChange()==1){
			btnModify.setEnabled(true);
		}
		else if(ie.getSource().equals(Female) && ie.getStateChange()==1){
			btnModify.setEnabled(true);
		}
		else if(ie.getSource().equals(check) && ie.getStateChange()==1){
			btnModify.setEnabled(true);
		}
	}

	//�޽��� ����ִ� Ŭ����
	class MessageDialog2 extends JDialog implements ActionListener{	
		JButton ok;
		MessageDialog2(JFrame parent, String title, boolean mode, String msg){ 
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
		public void windowClosing(WindowEvent we) {}
		public void windowClosed(WindowEvent we) {}
		public void windowActivated(WindowEvent we) {}
		public void windowDeactivated(WindowEvent we) {}
		public void windowIconified(WindowEvent we) {}
		public void windowDeiconified(WindowEvent we) {}


} 




}//ȯ�� �� ���� â ���� ��



//��ǰ����
class Drug extends JPanel implements ActionListener, MouseListener, KeyListener{
	Vector<String> header;				//��ǰ ���� ���̺� ���
	Vector<Vector<String>> content;			//��ǰ ���� ���̺� ����
	JLabel inform;  					//��ǰ �� ����
	JTable drugTable;					//��ǰ ���� ���̺�
	JPanel listPanel;					//��ǰ ���� ���̺� �ִ� �ǳ�
	JTextField dCode, dNum, dName, dY, dM, dD, findt;	//��ǰ �� ���� ����ִ� �ؽ�Ʈ �ʵ�
	JComboBox findconstraint;				//�˻� ����
	JButton btnPlus, btnModify, btnDelete, btnCancel;	//��ư
	JLabel blank;					//�԰� ���߱�� ����

	Drug(){	//������ ����

		
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 25, 25));

		JPanel findCon = new JPanel();  //�˻� â+Ȯ��
		JPanel confirm = new JPanel();  //���� ����ִ� â
    
		add(findCon);
		add(confirm);

		//�˻� â+Ȯ��
		findCon.setLayout(new BorderLayout());

		//�˻�â 
		JPanel find = new JPanel();
		find.setLayout(new FlowLayout(FlowLayout.LEFT));
		String [] constraint = {"ǰ��","�⵵","��"};
		findconstraint = new JComboBox(constraint);
		findconstraint.setPreferredSize(new Dimension(100, 30));
		findt = new JTextField(35);
		JButton findb = new JButton("�� ��");
		JButton findf = new JButton("�� ȸ");
		findb.addActionListener(this);  findf.addActionListener(this);
		find.add(findconstraint);  find.add(findt);  find.add(findb);  find.add(findf);
		find.setPreferredSize(new Dimension(650,55));

		//��ǰ ��� ���
		content = new Vector<Vector<String>>();
		header = new Vector<String>();
		header.addElement("��ǰ �ڵ�");
		header.addElement("ǰ��");
		header.addElement("����");
		header.addElement("��");
		header.addElement("��");
		header.addElement("��");

		//���̺� �� ����
		DefaultTableModel model = new DefaultTableModel(content, header);  
    
		//���̺� ���� �� ���� �Ұ� ����
		drugTable = new JTable(model){
			public boolean isCellEditable(int rowIndex, int mColIndex){
				return false;
			}
		};

		//���̺� �� �� ũ�� ����
		TableColumnModel dt = drugTable.getColumnModel();
		dt.getColumn(0).setPreferredWidth(100);
		dt.getColumn(1).setPreferredWidth(250);
		dt.getColumn(2).setPreferredWidth(100);
		dt.getColumn(3).setPreferredWidth(100);
		drugTable.setRowHeight(25);
      
		//���̺� �� �̵� �Ұ�, ũ�� ���� �Ұ�
		drugTable.getTableHeader().setReorderingAllowed(false); //prevent re-ordering
		drugTable.getTableHeader().setResizingAllowed(false); //prevent re-sizing

		drugTable.addMouseListener(this);
		JScrollPane sc = new JScrollPane(drugTable);
		sc.setPreferredSize(new Dimension(550,560));

		//������ ������ ��� �� ������ �ͼ� ���̺� ä���ֱ�
		select();  

		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.add(sc , BorderLayout.CENTER); 

		findCon.add(find, BorderLayout.NORTH);  findCon.add(listPanel, BorderLayout.CENTER);

		//�� ��������ֱ�
		confirm.setLayout(new BorderLayout());

		JPanel inform = new JPanel();
		inform.setLayout(new GridLayout(5,1));
		inform.setPreferredSize(new Dimension(250, 350));
		JLabel in = new JLabel(" ��ǰ �� ����");
		in.setFont(new Font("SansSerif", Font.BOLD, 20));    

		JPanel code = new JPanel();
		code.setLayout(new FlowLayout());
		JLabel dc = new JLabel("��ǰ�ڵ�   ");
		dc.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		dCode = new JTextField(10);
		dCode.setEnabled(false);
		blank = new JLabel("      ");
		code.add(dc);  code.add(dCode);  code.add(blank);

		JPanel name = new JPanel();
		name.setLayout(new FlowLayout());
		JLabel dn = new JLabel("ǰ��         ");
		dn.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		dName = new JTextField(10);
		dName.addKeyListener(this);
		blank = new JLabel("     ");
		name.add(dn);  name.add(dName);  name.add(blank);

		JPanel num = new JPanel();
		num.setLayout(new FlowLayout());
		JLabel dN = new JLabel("����        ");
		dN.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		dNum = new JTextField(5);
		dNum.addKeyListener(this);
		blank = new JLabel("��                   ");
		num.add(dN);  num.add(dNum);  num.add(blank);

		JPanel ymd = new JPanel();
		ymd.setLayout(new FlowLayout());
		JLabel dymd = new JLabel("������� ");
		dymd.setFont(new Font("SansSerif", Font.BOLD, 15)); 
		dY = new JTextField(5);
		dM = new JTextField(3);
		dD = new JTextField(3); 
		dY.setEnabled(false);  dM.setEnabled(false);  dD.setEnabled(false);
		ymd.add(dymd);  ymd.add(dY);  ymd.add(dM);  ymd.add(dD); 
    
		inform.add(in);  inform.add(code);  inform.add(name);  inform.add(num);  inform.add(ymd);

		//��ǰ ���� ����, �߰�, ������ ���� �������� �ǳ�
		JPanel notice = new JPanel();
		notice.setBorder(new TitledBorder(new LineBorder(Color.gray, 2), "����"));
		notice.setLayout(new GridLayout(4,1));
		JLabel n = new JLabel(" �� ��ǰ ��������� �� �����ּ��� ��");
		n.setFont(new Font("SansSerif", Font.PLAIN, 15));   
		JLabel n1 = new JLabel("     ��������� ���� ��ǰ�� ������ �ּ���.");
		n1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		JLabel n2 = new JLabel("     ��ǰ ��� �� �ڵ�� ��������� ������");
		n2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		JLabel n3 = new JLabel("   �Ұ����ϹǷ� �ٽ� �� �� �� Ȯ�����ּ���.");
		n3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		blank = new JLabel(" ");

		notice.add(n);  notice.add(n1);  notice.add(n2);  notice.add(n3);

		//��ư �߰� �ǳ�
		JPanel button = new JPanel();
		button.setLayout(new GridLayout(5,1));
		blank = new JLabel(" ");
		btnPlus = new JButton("��  ��");
		btnModify = new JButton("��  ��");
		btnDelete = new JButton("��  ��");
		btnCancel = new JButton("��  ��");
		btnModify.setEnabled(false);
		btnPlus.addActionListener(this);  btnModify.addActionListener(this);  btnDelete.addActionListener(this);  btnCancel.addActionListener(this);
		button.add(blank);  button.add(btnPlus);  button.add(btnModify);  button.add(btnDelete);  button.add(btnCancel);
		confirm.add(inform, BorderLayout.NORTH);  confirm.add(notice, BorderLayout.CENTER);  confirm.add(button, BorderLayout.SOUTH);
	}

	//���̺� ��ǰ ���� ����ִ� �޼ҵ�
	public void select(){
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
			String strSql = "SELECT drug_code, drug_name, drug_year, drug_month, drug_date, drug_st FROM pethp_drug;";
			ResultSet result=dbSt.executeQuery(strSql);
			String name="", num="", code="", year="", month="", date="";	//�˻� �� �������� ����

			//���̺� �ʱ�ȭ �� �˻��� �� ���������� �����ϱ� ���� �� ���� 0���� �ʱ�ȭ
			DefaultTableModel data = (DefaultTableModel)drugTable.getModel();      
			data.setNumRows(0);						
			
			while(result.next()){
				code = result.getString("drug_code");
				name = result.getString("drug_name");
				year = result.getString("drug_year");
				month = result.getString("drug_month");
				date = result.getString("drug_date");
				num = Integer.toString(result.getInt("drug_st"));
				Vector <String> drugContent = new Vector <String>();
				drugContent.add(code);  drugContent.add(name);  drugContent.add(num);  
				drugContent.add(year);  drugContent.add(month);  drugContent.add(date);  

				content.add(drugContent);
			}//while end
			dbSt.close();
			con.close();
		}catch(SQLException e){
			System.out.println("SQLException : " + e.getMessage());
		}
	}//select method end

	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand(); 				// actionevent�� ��� �߻��ߴ��� ��������
		String name="", num="", code="", year="", month="", date="";	//�� ������ ���� ���� ���� ��
		String ft="";						//�˻� â�� �Էµ� �� ������ �����ϴ� ����

		if(s.equals("��  ��")){
			dName.setText("");  dNum.setText("");  
			dCode.setText("");  dY.setText("");  dM.setText("");  dD.setText("");
			drugTable.clearSelection();
		}
		else if(s.equals("��  ��")){
			new Adddrug("��ǰ �߰�");
		}
		else if(s.equals("�� ȸ")){
			select();
			drugTable.updateUI();
		}
		else{
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			}
			catch(ClassNotFoundException e){
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
				System.out.println("DB ���� �Ϸ�");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
				String strSql;   
				code=dCode.getText();  name=dName.getText();  num=dNum.getText();  year=dY.getText();  month=dM.getText();  date=dD.getText();

				if(s.equals("��  ��")){
					if(name.isEmpty() || num.isEmpty()){
						JOptionPane.showMessageDialog(null, "���� �Է��� �ּ���.", "���� ����", JOptionPane.ERROR_MESSAGE);
						System.out.println("���� ����");
					}
					else if(!num.matches("^[0-9]*$") || num.equals("0")){
						JOptionPane.showMessageDialog(null, "������ �ùٸ��� �Է��� �ּ���.", "���� ����", JOptionPane.ERROR_MESSAGE);
						 System.out.println("���� ����");
					}
					else{
						strSql = "UPDATE pethp_drug SET drug_name='"+name+"', drug_st='"+num+"' WHERE drug_code='"+code+"' AND drug_year='"+year+"' AND drug_month='"+month+"' AND drug_date='"+date+"';";    
						dbSt.executeUpdate(strSql);
						JOptionPane.showMessageDialog(null, "��ǰ ������ �����Ǿ����ϴ�.", "���� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("���� �Ϸ�");     
						select();				//table �ʱ�ȭ
						drugTable.updateUI();     	
					}
				}//���� btn end
				else if(s.equals("�� ��")){
					ft = findt.getText();		//�˻� â�� �Էµ� �� ��������

					//�˻� ���ǿ� ���� ����� �˻��ϱ� 0�� ǰ�� 1 �⵵ 2 ��
					if(findconstraint.getSelectedIndex() == 0){
						strSql = "SELECT * FROM pethp_drug WHERE drug_name='"+ft+"';";
						ResultSet result = dbSt.executeQuery(strSql);
            
						//���� ��Ÿ�� �ִ� ���̺� �а� ���� �����ؼ� �˻� �� �־��ֱ�
						DefaultTableModel data = (DefaultTableModel)drugTable.getModel();      
						data.setNumRows(0);
						while(result.next()){
							code = result.getString("drug_code");
							name = result.getString("drug_name");
							year = result.getString("drug_year");
							month = result.getString("drug_month");
							date = result.getString("drug_date");
							num = Integer.toString(result.getInt("drug_st"));
							Vector <String> search = new Vector <String>();
							search.add(code);  search.add(name);  search.add(num);  
							search.add(year);  search.add(month);  search.add(date);  
							content.add(search);
						}
						if(drugTable.getRowCount() == 0){	//���� �޾ƿ� �˻� ���� ������ �˻� ����
							JOptionPane.showMessageDialog(null, "�˻� ���� �������� �ʽ��ϴ�.", "��ǰ �˻� ����", JOptionPane.ERROR_MESSAGE);
							System.out.println("�˻� ����");
							dName.setText("");  dNum.setText("");  findt.setText(""); 
							dCode.setText("");  dY.setText("");  dM.setText("");  dD.setText("");
							select();
							drugTable.updateUI();
						}               
          					}
					else if(findconstraint.getSelectedIndex() == 1){	
						strSql = "SELECT * FROM pethp_drug WHERE drug_year='"+ft+"';";
						ResultSet result = dbSt.executeQuery(strSql);
            
						DefaultTableModel data = (DefaultTableModel)drugTable.getModel();      
						data.setNumRows(0);
						while(result.next()){
							code = result.getString("drug_code");
							name = result.getString("drug_name");
							year = result.getString("drug_year");
							month = result.getString("drug_month");
							date = result.getString("drug_date");
							num = Integer.toString(result.getInt("drug_st"));
							Vector <String> search = new Vector <String>();
							search.add(code);  search.add(name);  search.add(num);  
							search.add(year);  search.add(month);  search.add(date);  
							content.add(search);
						}
						if(drugTable.getRowCount() == 0){
							JOptionPane.showMessageDialog(null, "�˻� ���� �������� �ʽ��ϴ�.", "��ǰ �˻� ����", JOptionPane.ERROR_MESSAGE);
							System.out.println("�˻� ����");
							dName.setText("");  dNum.setText("");  findt.setText(""); 
							dCode.setText("");  dY.setText("");  dM.setText("");  dD.setText("");
							select();
							drugTable.updateUI();
						}
					}
					else{
						strSql = "SELECT * FROM pethp_drug WHERE drug_month='"+ft+"';";
						ResultSet result = dbSt.executeQuery(strSql);
 
						DefaultTableModel data = (DefaultTableModel)drugTable.getModel();      
						data.setNumRows(0);
						while(result.next()){
							code = result.getString("drug_code");
							name = result.getString("drug_name");
							year = result.getString("drug_year");
							month = result.getString("drug_month");
							date = result.getString("drug_date");
							num = Integer.toString(result.getInt("drug_st"));
							Vector <String> search = new Vector <String>();
							search.add(code);  search.add(name);  search.add(num);  
							search.add(year);  search.add(month);  search.add(date);  
							content.add(search);
						}
						if(drugTable.getRowCount() == 0){
							JOptionPane.showMessageDialog(null, "�˻� ���� �������� �ʽ��ϴ�.", "��ǰ �˻� ����", JOptionPane.ERROR_MESSAGE);
							System.out.println("�˻� ����");
							dName.setText("");  dNum.setText("");  
							dCode.setText("");  dY.setText("");  dM.setText("");  dD.setText("");
							select();
							drugTable.updateUI();
						}            
					}
				}//�˻���ư end
				else{						//���� ��ư Ŭ��
					int row = drugTable.getSelectedRow();
					if(row < 0){	//���� �� ���� ���� �� ���� ����
						JOptionPane.showMessageDialog(null, "������ ���� �������ּ���.", "��ǰ ���� ����", JOptionPane.ERROR_MESSAGE);
					}
					else{	//���õ� �� ������ ���� ����
						//���� ������ ������ ��ǰ�� ���� ������ �����ֱ� ���ؼ� foreignŰ �� �˻� ���� ������ �����ϰ� ������ �ٽ� �� �˻��ϵ��� ����
						strSql = "SET foreign_key_checks=0;";
						dbSt.executeUpdate(strSql);
						strSql = "DELETE FROM pethp_drug WHERE drug_code='"+code+"' AND drug_year='"+year+"' AND drug_month='"+month+"' AND drug_date='"+date+"' AND drug_st="+num+";";
						dbSt.executeUpdate(strSql);
						strSql = "SET foreign_key_checks=1;";
						dbSt.executeUpdate(strSql);
						JOptionPane.showMessageDialog(null, "��ǰ�� �����Ǿ����ϴ�.", "��ǰ ���� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("������ ���� �Ϸ�");
						dName.setText("");  dNum.setText("");  findt.setText("");  findconstraint.setSelectedIndex(0);
						dCode.setText("");  dY.setText("");  dM.setText("");  dD.setText("");
						select();
						drugTable.updateUI();
					}
				}
				dbSt.close();
				con.close();
			}
			catch(SQLException e){
				System.out.println("SQLException : " + e.getMessage());
			}
		}    
	}// �׼Ǹ����� ��

	// �� Ŭ�� �� ���� ���� ����ֱ�
	public void mouseClicked(MouseEvent me){
		//���õ� �� ��������
		int row = drugTable.getSelectedRow();
		TableModel data = drugTable.getModel();

		String code = (String)data.getValueAt(row,0);
		String name = (String)data.getValueAt(row,1);
		String num = (String)data.getValueAt(row,2);
		String year = (String)data.getValueAt(row,3);
		String month = (String)data.getValueAt(row,4);
		String date = (String)data.getValueAt(row,5);
    
		//���õ� �࿡ �ִ� ���� ������ �� ���� ĭ�� �Ѱ��ֱ�
		dCode.setText(code);  dName.setText(name);  dNum.setText(num);  
		dY.setText(year);  dM.setText(month);  dD.setText(date);
		btnModify.setEnabled(false);
	}//���콺 Ŭ�� ������ ��

	//�� �����Ǹ� ���� ��ư Ȱ��ȭ
	public void keyPressed(KeyEvent ke){
		btnModify.setEnabled(true);
	}

	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	//��ǰ �߰�â
	class Adddrug extends JFrame implements ActionListener, WindowListener {

		JLabel jL1, jL2, jL3, jL4, jL5;						//��ǰ �߰� ����
		JButton plus, cancel;						//��ǰ �߰�, ��� ��ư
		String dyear[] = {"2020", "2021","2022" ,"2023"};				//�߰� �⵵ �޺��ڽ��� �� �⵵ ��
		String dmonth[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12"};	//�߰� �� �޺��ڽ��� �� �� ��
		Vector da = new Vector(); 						//�߰� �� �޺��ڽ��� ����
		DefaultComboBoxModel dmodel;					//�߰� �� �޺��ڽ��� �޺��ڽ� ��
		JTextField plusCode, plusName, plusNum;				//�ڵ�, �̸�, ���� �߰�â
		JComboBox plusYear, plusMonth, plusDate;				//��, ��, �� �߰�â
	
		//������ ����
		Adddrug(String title) {
			this.addWindowListener(this);	//������ ������ ����
			setTitle(title);
			Container ct = getContentPane();
		
			//������ǥ ����
			ct.setLayout(null);
        
			jL1 = new JLabel("��ǰ �߰�");
			jL2 = new JLabel("��ǰ �ڵ�");
			jL3 = new JLabel("��ǰ��");
			jL4 = new JLabel("����");
			jL5 = new JLabel("�������");
			
			plusCode = new JTextField(10);
			plusName = new JTextField(10);
			plusNum = new JTextField(10);

			//�޺��ڽ� ����
			plusYear = new JComboBox(dyear);
			plusMonth = new JComboBox(dmonth);
			plusDate = new JComboBox(da);

			//��¥ �޺��ڽ��� �� �����ؼ� �־��ֱ�
			dmodel = new DefaultComboBoxModel(da);
			plusDate.setModel(dmodel);

			//������ �ٲ�� ��¥�� ���� ������ Ŭ���� ��ü ����
			settingDay sd = new settingDay();

			//�� �޺��ڽ��� ������ �ٲ�� ��¥ Ŭ���� �־� ������ ������ ����
			plusMonth.addItemListener(sd);
        
			plus = new JButton("���");
			cancel = new JButton("���");
 
			plus.addActionListener(this);
			cancel.addActionListener(this);
        
			//������ǥ ��ġ ����
			jL1.setBounds(30, 30, 100, 20);
			jL2.setBounds(30, 60, 60, 20 );
			plusCode.setBounds(110, 60, 135, 20 );

			jL3.setBounds(30, 90, 60, 20 );
			plusName.setBounds(110, 90, 135, 20 );

			jL4.setBounds(30, 120, 60, 20 );
			plusNum.setBounds(110, 120, 135, 20 );

			jL5.setBounds(30, 150, 60, 20 );
			plusYear.setBounds(110, 150, 55, 20 );
			plusMonth.setBounds(165, 150, 40, 20 );
			plusDate.setBounds(205, 150, 40, 20 );

			plus.setBounds(110, 190, 60, 20);
			cancel.setBounds(185, 190, 60, 20);
        
			ct.add(jL1);
			ct.add(jL2); ct.add(plusCode);
			ct.add(jL3); ct.add(plusName);
			ct.add(jL4); ct.add(plusNum);
			ct.add(jL5); ct.add(plusYear); ct.add(plusMonth); ct.add(plusDate);
			ct.add(plus); ct.add(cancel);
        
			setSize(300,300);
			setLocation(1300,150);
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}

		//���� ���� ��¥ �������ִ� Ŭ����
		class settingDay implements ItemListener{
			int dg_month;		//���õ� �� �� ���� ����
			String in_date[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};	//��¥ 
			int datein;		//���õ� ���� ���� �޶����� ��¥�� ���� ����

			public void setday() {
				dg_month = plusMonth.getSelectedIndex();	//���õ� �� �� ��������
		
				if(dg_month == 0 ||dg_month ==2 ||dg_month == 4 ||dg_month == 6 ||dg_month==7 ||dg_month==9 ||dg_month==11) {	//1,3,5,7,8,10,12 ���̸� 31�ϱ���
					datein = 31;
				}
				else if(dg_month == 1) {	//2���̸� 29�ϱ���
					datein = 29;
				}
				else{		//4,6,9,11 ���̸� 30�ϱ���
					datein = 30;
				}
			}

			public void itemStateChanged(ItemEvent ae){				
				if(ae.getSource().equals(plusMonth) && ae.getStateChange()==1) {	//�� ���濡 ���� �� �������� 
					setday();				//��¥ ������ �޾ƿ��� �޼ҵ� ����
					dmodel.removeAllElements();	//�𵨿� ����ִ� �� �����
					for(int i = 0; i<datein; i++) {		//��¥ ������ŭ
						dmodel.addElement(in_date[i]);	//�𵨿� ��¥ ä���ֱ�
					}
				}
			}//itemStateChanged ��
		}//settinDay��

		public void actionPerformed(ActionEvent ae) {
			String s = ae.getActionCommand();
			String code="", name="", num="", year="", month="", date="";	//�Էµ� ���� �޾ƿ� �������ִ� ����

			if( s == "���") {	//��� ��ư Ŭ���� ĭ ���� �ʱ�ȭ
				plusCode.setText("");  plusName.setText("");  plusNum.setText(""); 
				plusYear.setSelectedIndex(0);  plusMonth.setSelectedIndex(0);   plusDate.setSelectedIndex(0); 
			}
			else {	//������ư Ŭ���� ����
				//�Էµ� ���� �޾ƿ� ������ �������ֱ�
				code=plusCode.getText();  name=plusName.getText();  num=plusNum.getText();
				year=(String)plusYear.getSelectedItem(); month=(String)plusMonth.getSelectedItem(); date=(String)plusDate.getSelectedItem(); 
     
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
				}
				catch(ClassNotFoundException e){
					System.err.println("����̹� �ε忡 �����߽��ϴ�.");
				}
				try{
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pethp?serverTimezone=UTC","root","1829730!");
					System.out.println("DB ���� �Ϸ�");
					Statement dbSt = con.createStatement();
					System.out.println("JDBC ����̹� ���� ���� �Ϸ�");
					String strSql;      
      
					if(code.isEmpty() || name.isEmpty() || num.isEmpty() ){	//������ �Է� �� ������ �߰� ����
						JOptionPane.showMessageDialog(null, "�߰��� ���� �Է����ּ���.", "��ǰ �߰� ����", JOptionPane.ERROR_MESSAGE);
						System.out.println("������ �߰� ����");
					}
					else if(!num.matches("^[0-9]*$") || num.equals("0")){	//������ 0�̰ų� ���ڰ� �ƴϸ� �߰� ����
						JOptionPane.showMessageDialog(null, "������ �ùٸ��� �����ּ���.", "��ǰ �߰� ����", JOptionPane.ERROR_MESSAGE);
						System.out.println("������ �߰� ����");
					}
					else if(!code.matches("^d[1-9][0-9][0-9][0-9]*$")){	//�ڵ� ���Ŀ� ���� ������ �߰� ����
						JOptionPane.showMessageDialog(null, "��ǰ �ڵ带 ���Ŀ� �����ּ���.", "��ǰ �߰� ����", JOptionPane.ERROR_MESSAGE);
						System.out.println("������ �߰� ����");
					}
					else{
						boolean check = true;
						if(check){		//�ڵ� ���� �����ϴ� ������ �˻�
							strSql = "SELECT * FROM pethp_drug WHERE drug_code='"+code+"';";
							ResultSet result = dbSt.executeQuery(strSql);
							while(result.next()){
								check = false;
							}
						}
						if( check == false ){	//�̹� �����ϴ� �ڵ�� �߰� ����
							JOptionPane.showMessageDialog(null, "�̹� �����ϴ� �ڵ��Դϴ�. �ٸ� �ڵ带 ����� �ּ���", "��ǰ �߰� ����", JOptionPane.ERROR_MESSAGE);
							System.out.println("������ �߰� ����");
						}
						else{	//�˻縦 ���� ��ġ�� �߰� ����
							strSql = "INSERT INTO pethp_drug VALUES ('"+code+"','"+name+"','"+year+"','"+month+"','"+date+"','"+num+"');";
							dbSt.executeUpdate(strSql);
							JOptionPane.showMessageDialog(null, "��ǰ �߰��� �Ϸ�Ǿ����ϴ�.", "��ǰ �߰� �Ϸ�!", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("������ �߰� �Ϸ�");
						}
					}
					dispose();
					dbSt.close();
					con.close();
				}
				catch(SQLException e){
					System.out.println("SQLException : " + e.getMessage());
				}//trycatch
			}//else
		}//action
	
		public void windowOpened(WindowEvent we){ }
		public void windowClosing(WindowEvent we) { }
		public void windowClosed(WindowEvent we) { select(); }
		public void windowActivated(WindowEvent we) { }
		public void windowDeactivated(WindowEvent we) { }
		public void windowIconified(WindowEvent we) { }
		public void windowDeiconified(WindowEvent we) { }

	}//plusclassend
	

}//��ǰ��







}//â��