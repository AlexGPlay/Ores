import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TestTablero extends JFrame{

	private static final long serialVersionUID = -6361410702987851210L;

	public static void main(String[] args) {

		JFrame app = new JFrame("Ores");
		Tablero tablero = new Tablero();
		JPanel panel = tablero.getTablero();
		JLabel puntuacion;	
		
		puntuacion = new JLabel();
		puntuacion.setText(String.format("Puntuacion: %d   Movimientos: %d   Tiempo: %d", tablero.getPuntos(), tablero.getMovimientos(), tablero.getTiempo()));
		puntuacion.setFont(new java.awt.Font("Arial", 0, 18));
		puntuacion.setLocation(10, 350);
		puntuacion.setSize(puntuacion.getPreferredSize());
		
		app.add(puntuacion);
		app.add(panel);
		
		Timer t = new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				puntuacion.setText(String.format("Puntuacion: %d   Movimientos: %d   Tiempo: %d", tablero.getPuntos(), tablero.getMovimientos(), tablero.getTiempo()));	
				puntuacion.setSize(puntuacion.getPreferredSize());
			}
		});
		t.start();

		app.setLayout(null);
		app.setResizable(false);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setBounds(0, 0, 501, 410);
		app.setVisible(true);
	}
}
