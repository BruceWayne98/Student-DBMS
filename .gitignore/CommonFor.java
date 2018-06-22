import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CommonFor extends JFrame 
{
	private JButton b1,b2;
	private JFrame f;
	public CommonFor()
	{
		this.setDefaultLookAndFeelDecorated(true);
		f=new JFrame("Login");
		JPanel panel=new JPanel();
		f.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel.setLayout(new GridLayout(2,1,4,40));
		f.setVisible(true);
		b1=new JButton("Student Login");
		b2=new JButton("Teacher Login");
		b1.setSize(10,10);
		b2.setSize(10,10);
		panel.add(b1);
		panel.add(b2);
		f.add(panel);
		StuLoginHandler handler1=new StuLoginHandler();
		TeaLoginHandler handler2=new TeaLoginHandler();
		b1.addActionListener(handler1);
		b2.addActionListener(handler2);
		f.setSize(300, 300);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
	}
	public class StuLoginHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try {
				new StudentLogin();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public class TeaLoginHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			f.dispose();
			try {
				new TeacherLogin();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(f, e.getMessage());
			}
		}
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new CommonFor();
	}

}
