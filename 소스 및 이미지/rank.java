package yetop;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class rank {
	public static void main(String[] args) {
		rank01 rn = new rank01();
		rn.display();
		
		
	}
}

class rank01 extends JFrame {
	ResultSet rss = null;

	PreparedStatement pstmts = null;
	Statement sts = null;
	Connection cons= null;
	String url = "jdbc:mysql://localhost:3306/hanoi?serverTimezone=UTC";
	//JPanel pa;
	JButton bts;
	JLabel ts,ra;
	
	
	rank01()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cons = DriverManager.getConnection(url, "root", "1234");
			sts = cons.createStatement();
			
			//String sql="select time,count,name from hanoi.score where count =(select min(count) from hanoi.score)";
			String sql="select * from hanoi.score";
			//pstmts = cons.prepareStatement(sql);
			ResultSet rst = sts.executeQuery(sql); 
			ts=new JLabel("최고기록: ");
			
			while(rst.next())			
			{
			String named = rst.getString("name");
			
			int counts = rst.getInt("count");
			ra=new JLabel(rst.getString("name")+"    탑 갯수 : "+rst.getString("tower")+"       [ "+rst.getString("count")+" 번]\n");
			
			add(ts);
			add(ra);
			//JOptionPane.showMessageDialog(null,"최고기록 : "+ rst.getString("name")+
				//	"    [ "+ rst.getString("count")+" 번]","랭킹",JOptionPane.PLAIN_MESSAGE);
			System.out.print(named+"\n");
			}
		} catch(Exception e){}	
	//pa.add(ts);
	add(ts);
	
	}
	void display()
	   {
	      setTitle("랭킹");
	      setLayout(new FlowLayout());
	      setSize(1920,1040);
	      setVisible(true);
	      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   }
}