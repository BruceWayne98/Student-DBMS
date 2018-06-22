import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.*;
import javax.swing.*;
@SuppressWarnings("serial")
public class DisplayResults extends JFrame
{
	private JFrame f;
	private JTable table;
	private Connection connection;
	public ResultSetTableModel resultmodel;
	private JTextArea textarea;
	private JTextField filtertext,rolltext,name,year,department;
	String def1=null;
	private JLabel label1,label2,label3,label4;
	private JPanel panel=new JPanel();
	String x,y,z;
	private TableRowSorter<TableModel> rowsort=null;
	public DisplayResults(String def,String roll)
	{
		f=new JFrame("Student Database");
		try
		{
			def1=def;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			resultmodel=new ResultSetTableModel("jdbc:oracle:thin:@localhost:1521:xe","system","arihanthkumar",def);
			connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","arihanthkumar");
			Statement statement=connection.createStatement();
			ResultSet resultset=statement.executeQuery("select fname,dept,yr from student where RollNo="+roll);
			while(resultset.next())
			{
				x=resultset.getString(1);
				z=resultset.getString(2);
				y=resultset.getString(3);
			}
			rolltext=new JTextField(20);
			JButton logout=new JButton("LogOut");
			LogoutHandler loghandler=new LogoutHandler();
			logout.addActionListener(loghandler);
			rolltext.setText(roll);
			rolltext.setEditable(false);
			rolltext.setBackground(Color.WHITE);
			department=new JTextField(20);
			department.setText(z);
			department.setEditable(false);
			department.setBackground(Color.WHITE);
			name=new JTextField(20);
			name.setText(x);
			name.setEditable(false);
			name.setBackground(Color.WHITE);
			year=new JTextField(20);
			year.setText(y);
			year.setEditable(false);
			year.setBackground(Color.WHITE);
			textarea=new JTextArea(def,3,100);
			textarea.setWrapStyleWord(true);
			textarea.setLineWrap(true);
			label1=new JLabel("Roll:");
			label2=new JLabel("Name:");
			label3=new JLabel("Year:");
			label4=new JLabel("Department:");
			panel.setLayout(new GridLayout(4,2,2,2));
			panel.add(label1);
			panel.add(rolltext);
			panel.add(label2);
			panel.add(name);
			panel.add(label3);
			panel.add(year);
			panel.add(label4);
			panel.add(department);
			JScrollPane pane=new JScrollPane(textarea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			JButton button=new JButton("Update");
			
			Handler handler=new Handler();
			button.addActionListener(handler);
			Box box=Box.createHorizontalBox();
			box.add(pane);
			box.add(button);
			box.add(logout);
			table=new JTable(resultmodel);
			f.add(box,BorderLayout.NORTH);
			JButton button1=new JButton("Filter");
			filtertext=new JTextField();
			filtertext.setBackground(Color.WHITE);
			Box box1=Box.createVerticalBox();
			box1.add(panel,BorderLayout.NORTH);
			box1.add(Box.createRigidArea(new Dimension(0,30)));
			box1.add(new JScrollPane(table),BorderLayout.CENTER);
			Box box2=Box.createHorizontalBox();
			box2.add(filtertext);
			box2.add(button1);
			f.add(box1);
			f.add(box2,BorderLayout.SOUTH);
			rowsort=new TableRowSorter<TableModel>(resultmodel);
			table.setRowSorter(rowsort);
			JTableHeader header=table.getTableHeader();
			header.setBackground(Color.YELLOW);
			RowHandler hand=new RowHandler();
			button1.addActionListener(hand);
			f.setVisible(true);
			f.setLocationRelativeTo(null);
			f.setResizable(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setSize(1200,1000);
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Database error",JOptionPane.ERROR_MESSAGE);
			resultmodel.disconnect();
		}
	}
	public class Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			try
			{
				if(textarea.getText().equals(""))
				{
					textarea.setText(def1);
					resultmodel.setQuery(textarea.getText());
				}
				else
					resultmodel.setQuery(textarea.getText());
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Database error",JOptionPane.ERROR_MESSAGE);
				try
				{
					textarea.setText(def1);
					resultmodel.setQuery(def1);
				}
				catch(Exception ex1)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Database error",JOptionPane.ERROR_MESSAGE);
					resultmodel.disconnect();
				}
			}
		}
	}
	public class RowHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String text=filtertext.getText();
			if(text.length()==0)
				rowsort.setRowFilter(null);
			else
			{
				try
				{
					rowsort.setRowFilter(RowFilter.regexFilter(text));
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Database error",JOptionPane.ERROR_MESSAGE);
				}
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
	
}
