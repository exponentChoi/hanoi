package yetop;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Tower extends JPanel implements MouseListener, MouseMotionListener {

	// public static final long serialVersionUID=0xff;

	private Stack<Rectangle2D.Double> s[] = new Stack[3];
	private Stack<Color> disk_color[] = new Stack[3];
	private static Rectangle2D.Double top = null;
	private Color top_color = null;
	private double ax, ay, w, h; // x위치,y위치,폭,높이
	private boolean draggable = false, firstTime = false;
	public int a = 0; // val 가져온 숫자 val=원판갯
	long moveCount; // 움직인 횟수 체크

	public Tower() {
		firstTime = true;
		init(3); // 처음 원판
		addMouseListener(this); // 마우스리스너
		addMouseMotionListener(this); // 마우스모션리스너
	}


	public void init(int val) {
		Color c[] = { Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta,
				Color.gray, Color.lightGray }; // 원판 색상

		s[0] = new Stack<Rectangle2D.Double>(); // 1번 원판 크기조절
		s[1] = new Stack<Rectangle2D.Double>(); // 2번 원판 크기조절
		s[2] = new Stack<Rectangle2D.Double>(); // 3번 원판 크기조절

		disk_color[0] = new Stack<Color>(); // 원판 색상입력
		disk_color[1] = new Stack<Color>(); // 원판 색상입력
		disk_color[2] = new Stack<Color>(); // 원판 색상입력

		for (int i = 0; i < val; i++) { // val = 원판 갯수
			Rectangle2D.Double r = new Rectangle2D.Double(); // r double함수 적용

			double x = getWidth() / 6; // 폭을 6등분으로 나눈값
			x = (x == 0) ? 109 : x; // 시작위치
			double wr = val * 25 - 20 * i; // 원판의 폭
			r.setFrame(x - wr / 2, 387 - i * 20, wr, 20); // r의 함수=(위치x,위치y,폭,높이)
			s[0].push(r); // 원판을 삽입
			disk_color[0].push(c[i]); // 원판

		}

		a = val;

		top = null;
		top_color = null;
		ax = 0.0;
		ay = 0.0;
		w = 0.0;
		h = 0.0;
		draggable = false;
		repaint();

	}

	public void mouseClicked(MouseEvent ev) {
	}

	public void mousePressed(MouseEvent ev) {
		Point pos = ev.getPoint(); // 마우스포인트를 누르면 pos에 저장
		int n = current_tower(pos); // n에 current_tower(pos) 저장

		if (!s[n].empty()) { // 원파에 n이 비어있지 않을시
			top = s[n].peek(); // 가장 위에있는것을 읽는다
			if (top.contains(pos)) { // 선택한것이 맨위에 있는지 확인
				top = s[n].pop(); // 가장위에있는 판을 제거시킨다
				top_color = disk_color[n].pop(); // 판의 색상을 같이 이동시킨다
				ax = top.getX(); // 맨위 원판 마우스좌표입력
				ay = top.getY(); // 맨위 원판 마우스좌표입력
				w = pos.getX() - ax; // w에 클릭위치 -
				h = pos.getY() - ay;
				draggable = true; // allowing dragging if current mouse position is in top disk
			} else { // 선택한것이 맨위에 있지 않을시 아무것도 실행되지 않는다
				top = null;
				top_color = Color.black;
				draggable = false;
			}
		}
	}

	public void mouseReleased(MouseEvent ev) {
		if (top != null && draggable == true) {

			int tower = current_tower(ev.getPoint());
			double x, y;
			if (s[tower].empty()) { // s[tower]가 비어있을시
				moveCount++; // 한번 이동시마다 횟수를 기록

			}
			if (!s[tower].empty()) { // s[tower]가 비어있지 않을시

				if (s[tower].peek().getWidth() > top.getWidth()) { // s[tower]로 옮기려는 원판이 깔려있는 판보다 크기가 작을때

					y = s[tower].peek().getY() - 20; // calculating ay for dragged disk for placement

					moveCount++;

				} else {

					JOptionPane.showMessageDialog(this, "큰 판 위에 작은 판을 올리세요  " + moveCount + "번움직임", "Tower Of Hanoi",
							JOptionPane.ERROR_MESSAGE);
					tower = current_tower(new Point((int) ax, (int) ay));
					if (!s[tower].empty())
						y = s[tower].peek().getY() - 20;
					else
						y = getHeight() - 40;
					// return; //cannot put bigger disk on a smaller one
				}
			} else
				y = getHeight() - 42; // if no previous disk in tower

			x = (int) (getWidth() / 6 + (getWidth() / 3) * tower - top.getWidth() / 2);
			top.setFrame(x, y, top.getWidth(), top.getHeight()); // top을 세팅 (x위치,y위치,폭,높이)
			s[tower].push(top); // s[tower]에 top을 삽입
			disk_color[tower].push(top_color); // 색상 입력

			//Integer.parseInt(val.toString());
			if (y < (410 - (a * 20))) { // 원판3개높이 = 350에 끝 원판4높이=330에 끝
				if (x > 500) {

					JOptionPane.showMessageDialog(this, "축하합니다!!\n" +a+"층 타워를 " +moveCount + "번만에 성공하셨습니다.!!", "Tower Of Hanoi",
							JOptionPane.INFORMATION_MESSAGE);
					JPanel labels = new JPanel(new GridLayout(0,1,2,2));
					JPanel pa= new JPanel(new BorderLayout(5,5));
					labels.add(new JLabel("이름", SwingConstants.RIGHT));
				    labels.add(new JLabel("별명", SwingConstants.RIGHT));
				    pa.add(labels, BorderLayout.WEST);

			        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
			        JTextField names = new JTextField();
			        JTextField nicks = new JTextField();
			        
			        controls.add(names);
			        controls.add(nicks);
			        pa.add(controls, BorderLayout.CENTER);

			       
			        
			        JOptionPane.showMessageDialog(
			            null, pa, "Log In", JOptionPane.QUESTION_MESSAGE);
			        
					//String id = JOptionPane.showInputDialog(null, "이름입력", "기록저장", JOptionPane.OK_CANCEL_OPTION);

					//String name = id;
					int sec = 0;
					//String nick="123";
					 String name = names.getText();
				        String nick = nicks.getText();

					//moveCount = 0;
					AcessDB db = new AcessDB();
					db.insert_db(sec,moveCount, name,a,nick);
					moveCount=0;

				}
			}
			top = null;
			top_color = Color.black;
			draggable = false;
			repaint();
		}
	}

	public void mouseEntered(MouseEvent ev) {
	}

	public void mouseExited(MouseEvent ev) {
	}

	public void mouseMoved(MouseEvent ev) {
	}

	public void mouseDragged(MouseEvent ev) {
		int cx = ev.getX(); // 마우스 위치에 따른 x좌표
		int cy = ev.getY(); // 마우스 위치에 따른 y좌표
		if (top != null && draggable == true) {
			top.setFrame(cx - w, cy - h, top.getWidth(), top.getHeight());
			repaint(); // repainting if dragging a disk
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g; // 배경 그리기지정위해
		g2d.setColor(Color.pink); // 배경색
		g2d.fillRect(0, 0, getWidth(), getHeight()); // g2의 위치,폭,높이

		int holder_x = getWidth() / 6; // 폭 지정
		int holder_y1 = getHeight() - 200, holder_y2 = getHeight() - 20; // y1 = 막대기 시작높이 , y2 = 밑판 시작높이

		g2d.setColor(new Color(191, 96, 0)); // g2d 막대기 색상 지정
		g2d.setStroke(new BasicStroke(7)); // 막대 굵기 설정
		g2d.drawLine(holder_x, holder_y1, holder_x, holder_y2); // 첫번째 기둥 그리기 (x,y1)에서 (x,y2)
		g2d.drawLine(3 * holder_x, holder_y1, 3 * holder_x, holder_y2);// 두번째 기둥 그리기
		g2d.drawLine(5 * holder_x, holder_y1, 5 * holder_x, holder_y2); // 세번째 기둥 그리기
		g2d.drawLine(0, holder_y2, holder_x * 6, holder_y2); // 밑판 그리기

		// g2d.setStroke(new BasicStroke(1));
		g2d.setColor(top_color); // 원판의 색상을 변경

		if (draggable == true && top != null)
			g2d.fill(top); // drawing dragged disk

		drawtower(g2d, 0); // drawing disks of each tower
		drawtower(g2d, 1);
		drawtower(g2d, 2);
	}

	private void drawtower(Graphics2D g2d, int n) {
		if (!s[n].empty()) { // s[n] 이 비어있지 않을시
			for (int i = 0; i < s[n].size(); i++) { // 원반의 갯수만큼 사이즈가 i+1씩 증가한다.
				g2d.setColor(disk_color[n].get(i)); // 배열로 저장해놓은 색깔을 입힌다.
				g2d.fill(s[n].get(i)); // 색깔을 사이즈 크기만큼 채운다
			}
		}
	}

	private int current_tower(Point p) { // return current tower position with respect to Point p
		Rectangle2D.Double // 범위 설정
		rA = new Rectangle2D.Double(), // Rectangle2D를 선언
		rB = new Rectangle2D.Double(), rC = new Rectangle2D.Double();

		rA.setFrame(0, 0, getWidth() / 3, getHeight()); // rA 위치 및 폭,높이 지정
		rB.setFrame(getWidth() / 3, 0, getWidth() / 3, getHeight()); // rB 위치 및 폭,높이 지정
		rC.setFrame(2 * getWidth() / 3, 0, getWidth() / 3, getHeight()); // rC 위치 및 폭,높이 지정

		if (rA.contains(p)) // rA에 p가 포함되어있으면
			return 0;
		else if (rB.contains(p))
			return 1;
		else if (rC.contains(p))
			return 2;

		else
			return -1;
	}

	/////////////////////////////////////////////////

}