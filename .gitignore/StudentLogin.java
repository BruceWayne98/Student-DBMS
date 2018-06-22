import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.*;
@SuppressWarnings("serial")
public class StudentLogin extends JDialog
{
	private JTextField roll;
	public Connection connection;
	public DisplayResults display;
	private JCheckBox check;
	private JPasswordField password;
	private JLabel r,p;
	private JPanel panel;
	private JButton login,register;
	private JDialog f;
	private Font font;
	public Statement statement;
	public StudentLogin() throws Exception
	{
		f=new JDialog();
		check=new JCheckBox("Show Password");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","arihanthkumar");
		f.setTitle("Student Login");
		statement=connection.createStatement();
		panel=new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		r=new JLabel("ROLL NO");
		p=new JLabel("PASSWORD");
		roll=new JTextField(30);
		password=new JPasswordField(30);
		password.setEchoChar('*');
		login=new JButton("Login");
		register=new JButton("Register");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(r,cs);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(roll,cs);
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(p,cs);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(password, cs);
		panel.setBorder(new LineBorder(Color.GRAY));
		JPanel bp=new JPanel();
		CheckHandler checkHandler=new CheckHandler();
		check.addActionListener(checkHandler);
		check.setBounds(1, 4, 4, 1);
		bp.add(check);
		bp.add(login);
		bp.add(register);
		LoginHandler handler1=new LoginHandler();
		login.addActionListener(handler1);
		RegisterHandler handler2=new RegisterHandler();
		register.addActionListener(handler2);
		f.getContentPane().add(panel, BorderLayout.CENTER);
		f.getContentPane().add(bp, BorderLayout.PAGE_END);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
	}
	public class LoginHandler implements ActionListener
	{
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try 
			{
				String x="";
				ResultSet resultset=statement.executeQuery("select * from users where roll="+roll.getText());
				ResultSetMetaData metadata=resultset.getMetaData();
				int col=metadata.getColumnCount();
				while(resultset.next())
					x=resultset.getString(col);
				String y=password.getText();
				if(y.equals(x))
					new Common(connection,roll.getText(),password.getText());
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect Password!!!", "Incorrect Data",JOptionPane.ERROR_MESSAGE);
					password.setText("");
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public class RegisterHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try 
			{
				new InsertTable(connection);
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public class CheckHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(check.isSelected())
				password.setEchoChar((char)0);
			else
				password.setEchoChar('*');
		}
	}
}
