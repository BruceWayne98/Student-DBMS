import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;
public class Student {

	public static void main(String[] args) 
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","arihanthkumar");
			System.out.println("Enter your query: ");
			Scanner input=new Scanner(System.in);
			String query=input.nextLine();
			Statement stmt=con.createStatement();
    		stmt.executeUpdate(query);
    		ResultSet rs=stmt.executeQuery("select * from student ORDER BY roll");
    		ResultSetMetaData meta=rs.getMetaData();
    		int col=meta.getColumnCount();
    		for(int i=1;i<=col;i++)
    			System.out.printf("%-50s\t",meta.getColumnName(i));
    		System.out.println();
    		while(rs.next())
    		{
    			for(int i=1;i<=col;i++)
    				System.out.printf("%-50s\t",rs.getObject(i));
    			System.out.println();
    		}
    		input.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
