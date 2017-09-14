import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Pieza extends JPanel {

	private static final long serialVersionUID = 725154762980335569L;
	int color, x, y;
	
	public Pieza(int c, int X, int Y){
		
		if(c==1){
			color = (int)(Math.random()*4+1);
		}
		else{
			color = 0;
		}
		x = X;
		y = Y;
		setBounds(x, y, 40, 40);
		
	}
	
	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		if(color == 0){
			g.setColor(Color.white);
		}
		
		else if(color == 1){
			g.setColor(Color.red);
		}
		
		else if(color == 2){
			g.setColor(Color.blue);
		}
		
		else if(color == 3){
			g.setColor(Color.green);
		}
		
		else if(color == 4){
			g.setColor(Color.yellow);
		}
		
		g.fillRect(0, 0, 40, 40);
		
		g.setColor(Color.gray);
		g.drawRect(0, 0, 40, 40);

	}

	public int getColor(){
		return color;
	}
	
	public void setColor(int c){
		color = c;
	}

}
