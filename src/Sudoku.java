import java.io.*;
import java.math.BigInteger;

public class Sudoku {
	
	private Matrice m;
	private long inizio;
	private long fine;
	
	public Sudoku(String pathname){
		this.setInizio(System.currentTimeMillis());
		m = new Matrice(pathname);
		this.setFine(System.currentTimeMillis());
	}
	
	public String fattoreDiRiempimento(){
		double n = 100.0 / 81 * (81.0-m.getCelleVuote());
		return "celle vuote: "+m.getCelleVuote()+"\n"+"fattore di riempimento: " + Math.round(n) + "%";
	}
	
	public String spazioDiRicerca(){
		BigInteger n = BigInteger.valueOf(1);
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				if (m.getCasella(i, k).isVuota()){
					n = n.multiply(BigInteger.valueOf(m.getCasella(i, k).getValoriPossibili().size()));
				}
			}
		}
		return "spazio di ricerca:  " + n.toString()+ ""
				+ " ("+n.toString().length()+" cifre)";
	}
	
	public void seqSoluzioni(){
		// calcolo delle possibili soluzioni sequenzialmente
	}
	
	public static void main(String[] args) throws IOException {
		Sudoku s = new Sudoku("C:/Users/Matteo/workspace/Sudoku/game3.txt");
		System.out.println("tempo costruzione oggetto Sudoku: "+(s.getFine()-s.getInizio())+"ms");
		s.setInizio(System.currentTimeMillis());
		System.out.println(s.fattoreDiRiempimento());
		System.out.println(s.spazioDiRicerca());
		s.setFine(System.currentTimeMillis());
		System.out.println("tempo calcolo fattore di riempimento e spazio di ricerca: "
				+(s.getFine()-s.getInizio())+"ms");
		System.out.println("");
		System.out.println("risolvendo sequenzialmente...");
		s.setInizio(System.currentTimeMillis());
		
		
		
		//System.out.println(s.m);
		/*System.out.println("BLOCCHI");
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				Casella c = s.m.getCasella(i, k);
				System.out.println(c.getValore() +"-->"+c.isVuota()+" "+ c.getValoriPossibili());
			}
		}/*
		System.out.println("RIGHE");
		for(ArrayList<Casella> list : s.m.getRighe()){
			System.out.println(list);
		}
		System.out.println("COLONNE");
		for(ArrayList<Casella> list : s.m.getColonne()){
			System.out.println(list.get());
		}*/
	}

	public long getInizio() {
		return inizio;
	}

	public void setInizio(long inizio) {
		this.inizio = inizio;
	}

	public long getFine() {
		return fine;
	}

	public void setFine(long fine) {
		this.fine = fine;
	}

}
