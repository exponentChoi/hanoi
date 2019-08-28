package yetop;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class toto {
	public static void main(String[] args) {
		Menu07 m = new Menu07();		
		m.display();
	}
}

class Menu07 extends JFrame implements ActionListener{
	//JPanel main;
	JButton start;
	JLabel back;
	ImageIcon main,btn; 
	JScrollPane scrollPane;

	Menu07(){	
		main = new ImageIcon("./main.png");
		btn = new ImageIcon("./button.png");
		start = new JButton(btn);
  
	
		//배경 Panel 생성후 컨텐츠페인으로 지정      
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1: Dispaly image at at full size
				g.drawImage(main.getImage(), 0, 0, null);
				// Approach 2: Scale image to size of component
				Dimension d = getSize();
				g.drawImage(main.getImage(), 0, 0, d.width, d.height, null);
				// Approach 3: Fix the image position in the scroll pane
				// Point p = scrollPane.getViewport().getViewPosition();
				// g.drawImage(icon.getImage(), p.x, p.y, null);
				setOpaque(false); //그림을 표시하게 설정,투명하게 조절
				super.paintComponent(g);
			}
		};

		background.setLayout(new FlowLayout());
		
		start.addActionListener(this);
		JLabel label = new JLabel("");
		start.setPreferredSize(new Dimension(280, 85));        
		label.setPreferredSize(new Dimension(20, 400));        
		background.add(label);        
		background.add(start);
		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
	}

	void display()
	{		
		setTitle("두근두근 하노이☆");
		setSize(1600,850);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==start) {			
			Run_Game rg = new Run_Game("두근두근 하노이☆");
			setVisible(false);
		}
	}
}
	
