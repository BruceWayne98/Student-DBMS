import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
public class Enroll extends JFrame
{
	JFrame f;
	private JComboBox combo1,combo2,combo3,combo4;
	private JTextField text1,text2,text3,text4;
	private JList list1;
	private Connection connection;
	JButton button1,button2,button3;
	JLabel label1,label2,label3,label4;
	String[] depts= {"Civil","CSE","ECE","IT","Mech"};
	String[] courses=new String[8];
	private PreparedStatement statement;
	private ResultSet resultset;
	int i=0;
	private String a,b,a1,b1;
	String r;
	public Enroll(Connection connection1,String dept_name,String roll)
	{
		f=new JFrame("Student Enrollement");
		connection=connection1;
		r=roll;
		try 
		{
			statement=connection.prepareStatement("select coursename from course where deptid in(select deptid from dept where deptname=?)");
			statement.setString(1, dept_name);
			resultset=statement.executeQuery();
			while(resultset.next())
			{
				courses[i]=resultset.getString(1);
				System.out.println(courses[i]);
				i++;
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(f, e.getMessage());
		}
		combo2=new JComboBox();
		for(int i=1;i<=4;i++)
			combo2.addItem(String.valueOf(i));
		combo2.setBounds(50, 50,90,20);
		list1=new JList(courses);
		list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list1.setVisibleRowCount(3);
		//combo3=new JComboBox(names);
		//combo3.setMaximumRowCount(3);
		//combo3.setBounds(50, 50,90,20);
		//combo4=new JComboBox(names);
		//combo4.setMaximumRowCount(3);
		//ComboHandler1 handler1=new ComboHandler1();
		//ComboHandler2 handler2=new ComboHandler2();
		//combo3.addActionListener(handler1);
		//combo4.addActionListener(handler2);
		//combo4.setBounds(50, 50,90,20);
		combo2.setBackground(Color.WHITE);
		//combo3.setBackground(Color.WHITE);
		//combo4.setBackground(Color.WHITE);
		text1=new JTextField(20);
		text2=new JTextField(20);
		text3=new JTextField(20);
		text4=new JTextField(20);
		text1.setBackground(Color.WHITE);
		text2.setBackground(Color.WHITE);
		text3.setBackground(Color.WHITE);
		text4.setBackground(Color.WHITE);
		text1.setEditable(false);
		text2.setEditable(false);
		text3.setEditable(false);
		text4.setEditable(false);
		text1.setSize(5, 20);
		text2.setSize(5, 20);
		button1=new JButton("Submit");
		button2=new JButton("Reset");
		button3=new JButton("Ok");
		ResetHandler handler3=new ResetHandler();
		button2.addActionListener(handler3);
		ListHandler handler4=new ListHandler();
		button3.addActionListener(handler4);
		Box box1=Box.createHorizontalBox();
		Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		Box box4=Box.createHorizontalBox();
		Box box5=Box.createHorizontalBox();
		//label1=new JLabel("Select department:");
		label2=new JLabel("Select year:");
		label3=new JLabel("Select 2 courses:");
		label4=new JLabel("Corresponding Selected staff: ");
		//box1.add(label1);
		//box1.add(Box.createRigidArea(new Dimension(50,0)));
		//box1.add(combo1);
		box2.add(label2);
		box2.add(Box.createRigidArea(new Dimension(50,0)));
		box2.add(combo2);
		box3.add(label3);
		box3.add(Box.createRigidArea(new Dimension(20,0)));
		box3.add(new JScrollPane(list1));
		box3.add(Box.createRigidArea(new Dimension(20,0)));
		box3.add(button3);
		box3.add(Box.createRigidArea(new Dimension(20,0)));
		box3.add(text1);
		box3.add(Box.createRigidArea(new Dimension(20,0)));
		box3.add(text2);
		box4.add(button1);
		box4.add(Box.createRigidArea(new Dimension(40,0)));
		box4.add(button2);
		box5.add(label4);
		//box5.add(combo3);
		box5.add(Box.createRigidArea(new Dimension(20,0)));
		box5.add(text3);
		box5.add(Box.createRigidArea(new Dimension(20,0)));
		//box5.add(combo4);
		box5.add(Box.createRigidArea(new Dimension(20,0)));
		box5.add(text4);
		Box over=Box.createVerticalBox();
		over.add(box1);
		over.add(Box.createRigidArea(new Dimension(0,30)));
		over.add(box2);
		over.add(Box.createRigidArea(new Dimension(0,30)));
		over.add(box3);
		over.add(Box.createRigidArea(new Dimension(0,30)));
		over.add(box5);
		over.add(Box.createRigidArea(new Dimension(0,30)));
		over.add(box4);
		Container content = f.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(over, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
		EnrollHandler handler=new EnrollHandler();
		button1.addActionListener(handler);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public class ListHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			try 
			{
				PreparedStatement statement1=connection.prepareStatement("select name from teacher where teacherid in(select teacherid from course where coursename=?)");
				PreparedStatement statement2=connection.prepareStatement("select name from teacher where teacherid in(select teacherid from course where coursename=?)");
				int[] select=list1.getSelectedIndices();
				a=(String) list1.getModel().getElementAt(select[0]);
				b=(String) list1.getModel().getElementAt(select[1]);
				text1.setText(a);
				text2.setText(b);
				statement1.setString(1, a);
				statement2.setString(1, b);
				ResultSet result_a=statement1.executeQuery();
				ResultSet result_b=statement2.executeQuery();
				while(result_a.next())
					a1=result_a.getString(1);
				while(result_b.next())
					b1=result_b.getString(1);
				text3.setText(a1);
				text4.setText(b1);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public class ResetHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			list1.clearSelection();
			combo2.setSelectedIndex(0);
			text1.setText("");
			text2.setText("");
			text3.setText("");
			text4.setText("");
		}
	}
	public class EnrollHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			try
			{
				PreparedStatement s1=connection.prepareStatement("select courseid from course where coursename=?");
				PreparedStatement s2=connection.prepareStatement("select courseid from course where coursename=?");
				s1.setString(1, a);
				s2.setString(1, b);
				ResultSet r1=s1.executeQuery();
				ResultSet r2=s2.executeQuery();
				String m="",y="";
				while(r1.next())
					m=r1.getString(1);
				while(r2.next())
					y=r2.getString(1);
				String z=(String) combo2.getSelectedItem();
				PreparedStatement state=connection.prepareStatement("insert into global values(?,?,?,?)");
				PreparedStatement state1=connection.prepareStatement("select globalid from global where globalid=(select max(globalid) from global)");
				ResultSet s=state1.executeQuery();
				String g="";
				while(s.next())
					g=s.getString(1);
				int x=Integer.parseInt(g);
				x=x+1;
				g=Integer.toString(x);
				System.out.println(g);
				state.setString(1, g);
				state.setString(2, r);
				state.setString(3, m);
				state.setString(4, z);
				state.executeUpdate();
				PreparedStatement state_a=connection.prepareStatement("insert into global values(?,?,?,?)");
				PreparedStatement state1a=connection.prepareStatement("select globalid from global where globalid=(select max(globalid) from global)");
				ResultSet s3=state1a.executeQuery();
				String g1="";
				while(s3.next())
					g1=s3.getString(1);
				int x1=Integer.parseInt(g1);
				x1=x1+1;
				g1=Integer.toString(x1);
				System.out.println(g1);
				state_a.setString(1, g1);
				state_a.setString(2, r);
				state_a.setString(3, y);
				state_a.setString(4, z);
				state_a.executeUpdate();
				JOptionPane.showMessageDialog(f, "Success");
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(f, ex.getMessage());
			}
		}
	}
}
