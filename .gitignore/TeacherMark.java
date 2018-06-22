import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
public class TeacherMark extends JFrame
{
	private DefaultTableModel model;
	private JTable table;
	private JButton button;
	private int row=0;
	private Connection connection;
	private JFrame f;
	private String[] cols= {"ID","Assess1","Assess2"};
	private ResultSet resultset;
	String[][] rows;
	public TeacherMark(String roll)
	{
		try{
			f=new JFrame("Teacher Mark Entry");
			f.setVisible(true);
			JButton logout=new JButton("LogOut");
			LogoutHandler loghandler=new LogoutHandler();
			logout.addActionListener(loghandler);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","arihanthkumar");
			Statement state1=connection.createStatement();
			ResultSet resultset=state1.executeQuery("select name from teacher where teacherid="+roll);
			String a="";
			JButton grace=new JButton("Grace Marks");
			GraceHandler gracehand=new GraceHandler();
			grace.addActionListener(gracehand);
			while(resultset.next())
				a=resultset.getString(1);
			System.out.println(a);
			button=new JButton("Submit");
			PreparedStatement statement=connection.prepareStatement("select rollno from global where courseid in(select courseid from course where teacherid in(select teacherid from teacher where name=?))");
			statement.setString(1, a);
			ResultSet result=statement.executeQuery();
			PreparedStatement statement1=connection.prepareStatement("select rollno from global where courseid in(select courseid from course where teacherid in(select teacherid from teacher where name=?))");
			statement1.setString(1, a);
			ResultSet result1=statement1.executeQuery();
			while(result1.next())
				row=row+1;
			System.out.println(row);
			rows=new String[row][3];
			int i=0;
			while(result.next())
			{
				rows[i][0]=result.getString(1);
				System.out.println(rows[i][0]);
				i++;
			}
			model=new DefaultTableModel(rows,cols);
			table=new JTable(model);
			JPanel panel=new JPanel();
			JTableHeader header=table.getTableHeader();
			header.setBackground(Color.YELLOW);
			panel.add(new JScrollPane(table));
			button.setSize(10, 10);
			ButtonHandler handler =new ButtonHandler();
			button.addActionListener(handler);
			panel.add(button);
			panel.add(grace);
			panel.add(logout);
			f.add(panel);
			f.setSize(600,600);
			f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String[][] marks=new String[row][2];
			try
			{
				for(int i=0;i<row;i++)
				{
					for(int j=1;j<3;j++)
					{
						marks[i][j-1]=(String) table.getModel().getValueAt(i, j);
						System.out.print(marks[i][j-1]+" ");
					}
					System.out.println();
				}
				PreparedStatement state=connection.prepareStatement("insert into marks(id,as1,as2) values(?,?,?)");
				int i=0;
				while(i<row)
				{
					state.setString(1, rows[i][0]);
					state.setString(2, marks[i][0]);
					state.setString(3, marks[i][1]);
					state.executeUpdate();
					System.out.println("Success");
					i++;
				}
				JOptionPane.showMessageDialog(f, "Success");
			}
			catch(Exception ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	public class LogoutHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			new CommonFor();
		}
	}
	public class GraceHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			CallableStatement call;
			try 
			{
				call = connection.prepareCall("{call gracemarks}");
				call.executeUpdate();
				JOptionPane.showMessageDialog(f, "Succesful provision of grace marks");
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, "Error giving gracemarks");
			}
			
		}
	}
}
