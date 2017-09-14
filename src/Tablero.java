import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tablero {

	JPanel Tablero;
	Pieza p[][];
	String estado;
	int segundos;
	int[][] color = new int[8][12];
	int puntos;
	int movimientos;
	int parar;
	int tiempo;
	int juntos;
	Timer t;

	public Tablero(){
		int colores;
		setEstado("Espera");
		setTiempo(10);
		parar = 0;

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 481, 321);
		panel.setLayout(null);
		
		p = new Pieza[8][12];

		for(int i=0;i<8;i++){
			for(int j=0;j<12;j++){
				if(j<6){
					colores = 0;
				}
				else{
					colores = 1;
				}
				p[i][j] = new Pieza(colores, 40*j, 40*i);
				color[i][j] = p[i][j].getColor();
				panel.add(p[i][j]);
			}
		}
		
		Timer t = new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getTiempo()>0) setTiempo(getTiempo()-1);

				if(getTiempo()==0){
					int i = 0;
					while(i<8 && color[i][0]==0){
						i++;
					}

					if(i==8){
						for(int x=0;x<8;x++){
							for(int y=0;y<11;y++){
								p[x][y].setColor(p[x][y+1].getColor());
								p[x][y+1].setColor(0);
							}
						}

						for(int x=0;x<8;x++){
							p[x][11].setColor((int)((Math.random()*4)+1));
						}
					}
					

					else{
						parar = 1;
					}
					
					pintar();
					imprimeColores();
					setTiempo(10);

					if(parar==1){
						JOptionPane.showMessageDialog(null, String.format("Fin del juego, Puntuación: %d", getPuntos()));
						((Timer)e.getSource()).stop();
					}
				
				}
			}

		});
		
		t.start();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.addMouseListener(new MouseHandler());
		setTablero(panel);
		setPuntos(0);
		setMovimientos(0);

	}

	public JPanel getTablero(){
		return Tablero;
	}

	public void setTablero(JPanel p){
		Tablero = p;
	}

	private class MouseHandler extends MouseAdapter {
		public void mouseClicked (MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			x = (int)(((double)x)/40);
			y = (int)(((double)y)/40);

			if(x==12) x=11;
			if(y==8) y=7;
			
			if(parar==0){
				int colores = p[y][x].getColor();
				juntos = 0;
				eliminar(x, y, colores);
				pintar();
				setMovimientos(getMovimientos()+1);
					
				Timer t = new Timer(500, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						int cambios = 0;
							
						for(int i=0;i<12;i++){
							for(int j=0;j<8;j++){
								actualizar();
										
								if(j>0 && color[j][i] == 0 && color[j-1][i]!=0){
									for(int z=0;z<j;z++){
										p[z+1][i].setColor(color[z][i]);
										p[0][i].setColor(0);
										cambios ++;
									}
								}
							}
						}
						if(cambios>0) imprimeColores();
							
						cambios = 0;
						int z = 11;
						while(z>=0){
							int blancas = 0;
							actualizar();
							for(int j=0;j<8;j++){
								if(z<11 && color[j][z+1]==0) blancas++;
							}
							if(z<11 && color[7][z]!=0 && blancas==8){
								for(int j=0;j<8;j++){
									actualizar();
									p[j][z+1].setColor(color[j][z]);
									p[j][z].setColor(0);
									cambios++;
								}
								z = 11;
							}
							else z--;
						}
						if(cambios>0) imprimeColores();
						pintar();
						((Timer)e.getSource()).stop();
					}
				});
				t.start();
			}
		}
	}

	public void setEstado(String s){
		estado = s;
	}

	public String getEstado(){
		return estado;
	}

	private void eliminar(int x, int y, int colores){
		actualizar();
		
		if(color[y][x]!=0){
			if(x+1<=11 && colores==color[y][x+1]){
				juntos++;
			}

			else if(x-1>=0 && colores==color[y][x-1]){
				juntos++;
			}

			else if(y+1<=7 && colores==color[y+1][x]){
				juntos++;
			}

			else if(y-1>=0 && colores==color[y-1][x]){
				juntos++;
			}
		}
		
		if(juntos>0){
			p[y][x].setColor(0);
			setPuntos(getPuntos()+50);
			if(x+1<=11 && colores==color[y][x+1]){
				eliminar(x+1, y, colores);
			}
			
			if(x-1>=0 && colores==color[y][x-1]){
				eliminar(x-1, y, colores);
			}
			
			if(y+1<=7 && colores==color[y+1][x]){
				eliminar(x, y+1, colores);
			}
			
			if(y-1>=0 && colores==color[y-1][x]){
				eliminar(x, y-1, colores);
			}
		}
	}

	public void setPuntos(int p){
		puntos = p;
	}

	public int getPuntos(){
		return puntos;
	}

	public void setMovimientos(int m){
		movimientos = m;
	}

	public int getMovimientos(){
		return movimientos;
	}

	public Pieza[][] getPiezas(){
		return p;
	}

	public void setTiempo(int t){
		tiempo = t;
	}

	public int getTiempo(){
		return tiempo;
	}
	
	private void imprimeColores(){
		for(int z=0;z<8;z++){
			for(int j=0;j<12;j++){
				String c="";
				
				if(p[z][j].getColor()==0){
					c = "-";
				}
				
				if(p[z][j].getColor()==1){
					c = "R";
				}
				
				if(p[z][j].getColor()==2){
					c = "B";
				}
				
				if(p[z][j].getColor()==3){
					c = "G";
				}
				
				if(p[z][j].getColor()==4){
					c = "Y";
				}
				System.out.printf("%s ", c);
			}
			System.out.println();
		}
		System.out.printf("Puntos: %d	Movimientos: %d \n", getPuntos(), getMovimientos());
	}
	
	private void actualizar(){
		for(int i=0;i<8;i++){
			for(int j=0;j<12;j++){
				color[i][j] = p[i][j].getColor();
			}
		}
	}
	
	private void pintar(){
		for(int i=0;i<8;i++){
			for(int j=0;j<12;j++){
				p[i][j].repaint();
				}
			}
	}
}
