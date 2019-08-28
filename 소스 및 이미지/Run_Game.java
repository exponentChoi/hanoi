package yetop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Run_Game implements ActionListener{

	ResultSet rss = null;

	PreparedStatement pstmts = null;
	Statement sts = null;
	Connection cons= null;
	String url = "jdbc:mysql://localhost:3306/hanoi?serverTimezone=UTC";
	
	private static JFrame f = new JFrame();
	private static Tower t;  
	private static JMenuBar menu_bar=new JMenuBar(); //메뉴바 생성
	public static Object val;
	
	private JMenuItem	
	new_game=new JMenuItem("하노이 탑 선택"),
	best_time=new JMenuItem("최고 기록"),	
	exit=new JMenuItem("게임종료"),
	about=new JMenuItem("게임설명");


	private JMenu
	help=new JMenu("도움말"),
	game=new JMenu("게임 메뉴");

	public Run_Game(String title) {
		f.setTitle(title);
		build();
	}

	public void actionPerformed(ActionEvent ev){
		
		if(ev.getSource()==new_game){
			Object values[]= { 3,4,5,6,7,8,9 }; //원판 갯수
			//JOptionPane pp = new JOptionPane();
			//Object ii=pp.getSelectionValues();
			val=JOptionPane.showInputDialog(f,"디스크 갯수 선택  ","하노이 게임",
					JOptionPane.INFORMATION_MESSAGE,null,values,values[0]);  //INFORMATION_MESSAGE= 느낌표아이콘표시
									
			if((int)val!=JOptionPane.CANCEL_OPTION) {
				t.init((int)val);				
			}		
					
		}
		else if(ev.getSource()==best_time) 
		{
			/*String name="강택";
			int num;
			AcessDB dbr = new AcessDB();
			num=ps
			dbr.select_db(num,name)  */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cons = DriverManager.getConnection(url, "root", "1234");
			sts = cons.createStatement();
			
			String sql="select time,count,name,nick from hanoi.score where count =(select min(count) from hanoi.score)";
			//String sql="select * from hanoi.score";
			//pstmts = cons.prepareStatement(sql);
			ResultSet rst = sts.executeQuery(sql); 					
			rst.next();			
			{
			String named = rst.getString("name");
			int counts = rst.getInt("count");
			JOptionPane.showMessageDialog(null,"최고기록 : "+ rst.getString("name")+"("+rst.getString("nick")+")"
					+"         [ "+ rst.getString("count")+" 번]","랭킹",JOptionPane.PLAIN_MESSAGE);
			System.out.print(named);
			}
		} catch(Exception e){}	
		}
		else if(ev.getSource()==exit) 
		{
			Menu07 m = new Menu07();		
			m.display();
			//System.exit(0);
		}		
		
	}

	private void build(){

		game.add(new_game);
		game.add(best_time);
		game.add(exit);
		help.add(about);

		menu_bar.add(game);
		menu_bar.add(help);
		

		new_game.addActionListener(this);
		best_time.addActionListener(this);
		exit.addActionListener(this);

			
		t=new Tower();              
		f.getContentPane().add(t);   //frame에 tower 부착


		f.setJMenuBar(menu_bar);
		f.setSize(660, 480); 
		f.setResizable(false);
		f.setLocationRelativeTo(null); 
		f.setVisible(true);
		//	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	} 

	public static void main(String[] args) {
		//SwingUtilities.invokeLater(
		//		() -> {
		//			Run_Game obj = new Run_Game("하노이 게임");
		//			t=new Tower();
		//			f.getContentPane().add(t); 
		//		});
	}
}