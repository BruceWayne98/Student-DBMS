import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
@SuppressWarnings("serial")
public class InsertTable extends JFrame 
{
	private JFrame f;
	private Connection connection1;
	private PreparedStatement prepstatement,prepstatement2;
	private JPasswordField pass;
	private JLabel l1,l2,l4,l5,l6,l7,l8,l13;
	private JTextField t1,t2,t4,t5,t6,t7,t8;
	private JPanel panel;
	private JButton sub,close;
	public InsertTable(Connection connection) throws Exception
	{
		f=new JFrame("Student Registration");
		connection1=connection;
		f.setLayout(new FlowLayout());
		panel=new JPanel();
		panel.setLayout(new GridLayout(14,2,4,4));
		prepstatement=connection.prepareStatement("INSERT INTO student "+"VALUES(?,?,?,?,?,?,?)");
		prepstatement2=connection.prepareStatement("INSERT INTO users "+"VALUES(?,?,?)");
		pass=new JPasswordField(30);
		l1=new JLabel("ROLL");
		t1=new JTextField(50);
		panel.add(l1);
		panel.add(t1);
		l2=new JLabel("FNAME");
		t2=new JTextField(50);
		panel.add(l2);
		panel.add(t2);
		l4=new JLabel("GENDER");
		t4=new JTextField(50);
		panel.add(l4);
		panel.add(t4);
		l5=new JLabel("DOB");
		t5=new JTextField(50);
		panel.add(l5);
		panel.add(t5);
		l6=new JLabel("YEAR");
		t6=new JTextField(50);
		panel.add(l6);
		panel.add(t6);
		l7=new JLabel("DEPT");
		t7=new JTextField(50);
		panel.add(l7);
		panel.add(t7);
		l8=new JLabel("MOBILE_NO");
		t8=new JTextField(50);
		panel.add(l8);
		panel.add(t8);
		l13=new JLabel("PASSWORD");
		panel.add(l13);
		panel.add(pass);
		sub=new JButton("Register");
		close=new JButton("Close");
		panel.add(sub);
		panel.add(close);
		sub.setSize(10, 10);
		close.setSize(10, 10);
		f.add(panel);
		SubmitHandler handler1=new SubmitHandler();
		CloseHandler handler2=new CloseHandler();
		sub.addActionListener(handler1);
		close.addActionListener(handler2);
		f.setResizable(false);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1200, 800);
	}
	public class SubmitHandler implements ActionListener
	{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event)
		{
			try
			{
				prepstatement.setString(1, t1.getText());
				prepstatement.setString(2, t2.getText());
				prepstatement.setString(3, t4.getText());
				prepstatement.setString(4, t5.getText());
				prepstatement.setString(5, t6.getText());
				prepstatement.setString(6, t7.getText());
				prepstatement.setString(7, t8.getText());
				prepstatement.executeUpdate();
				prepstatement2.setString(1, t1.getText());
				prepstatement2.setString(2, t2.getText());
				prepstatement2.setString(3, pass.getText());
				prepstatement2.executeUpdate();
				JOptionPane.showMessageDialog(null,"REGISTRATION SUCCESSFUL","SUCCESS!!", JOptionPane.INFORMATION_MESSAGE);
				t1.setText("");
				t2.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
				t7.setText("");
				t8.setText("");
				pass.setText("");
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(f, ex.getMessage());
			}
		}
	}
	public class CloseHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try 
			{
				new StudentLogin();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
}
