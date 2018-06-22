import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
@SuppressWarnings("serial")
public class Common  extends JFrame
{
	private JFrame f;
	private JLabel title;
	private JCheckBox check;
	private Connection connection1;
	private JPanel panel;
	private JButton b1,b2,b3;
	String roll,password,x,y;
	public Common(Connection connection,String rollNo,String pass) throws Exception
	{
		f=new JFrame("Login AS");
		check=new JCheckBox("Show Password",false);
		//JPanel panel=new JPanel();
		roll=rollNo;
		password=pass;
		connection1=connection;
		Statement statement=connection.createStatement();
		Statement statement1=connection.createStatement();
		ResultSet resultset=statement.executeQuery("select Fname from student where RollNo="+roll);
		ResultSet result1=statement1.executeQuery("select dept from student where RollNo="+roll);
		while(resultset.next())
			x=resultset.getString(1);
		while(result1.next())
			y=result1.getString(1);
		String wel="Welcome, '"+x+"'";
		title=new JLabel(wel);
		title.setSize(50, 50);
		f.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel=new JPanel();
		b2=new JButton("VIEW DATA");
		b3=new JButton("ENROLL");
		panel.setLayout(new GridLayout(3,1,4,40));
		//panel.add(b1);
		JPanel panel1=new JPanel();
		panel1.add(title);
		panel1.setBackground(Color.WHITE);
		panel.add(panel1);
		panel.add(b2);
		panel.add(b3);
		//b1.setSize(10, 10);
		b2.setSize(10, 10);
		b3.setSize(10, 10);
		f.add(panel);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setSize(300, 300);
		//TeacherHandler t=new TeacherHandler();
		StudentHandler s=new StudentHandler();
		EnrollHandler u=new EnrollHandler();
		//b1.addActionListener(t);
		b2.addActionListener(s);
		b3.addActionListener(u);
	}
	public class StudentHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try 
			{
				String def="select courseid,(select coursename from  course where courseid=global.courseid) as CourseName ,as1,as2,total,grade from marks inner join global on id=rollno where rollno="+roll;
				new DisplayResults(def,roll);
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public class EnrollHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			new Enroll(connection1,y,roll);
		}
	}
}
