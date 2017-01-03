import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Sudoku {

	private final ForkJoinPool pool;
	private Matrice m;
	private long inizio;
	private long fine;
	public static int soluzioni = 0;
	private Integer[] primaCasellaVuota;

	public Sudoku(String pathname){
		this.setInizio(System.currentTimeMillis());
		m = new Matrice(pathname);
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				if(!m.getCasella(i, k).isVuota()){
					this.primaCasellaVuota = new Integer[2];
					this.primaCasellaVuota[0] = i;
					this.primaCasellaVuota[1] = k;
				}
			}
		}
		pool = new ForkJoinPool();
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
					n = n.multiply(BigInteger.valueOf((long)m.getCasella(i, k).getValoriPossibili().size()));
				}
			}
		}
		return "spazio di ricerca:  " + n.toString();
	}

	public void parSoluzioni(Matrice matrice){
		CalcoloParallelo calc = new CalcoloParallelo(m);
		pool.invoke(calc);
	}

	public void seqSoluzioni(Matrice matrice){
		//System.out.println(matrice.getCelleVuote());
		if(matrice.getCelleVuote() <= 0){
			Sudoku.soluzioni++;
			//System.out.println(matrice.getCelleVuote());
			return;
		}
		else{
			//System.out.println(matrice.getCelleVuote());
			if(matrice.getNextCasellaVuota().size()==3){
				//System.out.println(matrice.getNextcasellaVuota());
				ArrayList<Object> list = matrice.getNextCasellaVuota();
				Casella c = (Casella)list.get(0);
				int x = (int)list.get(1);
				int y = (int)list.get(2);
				for(Integer n: c.getValoriPossibili()){
					Casella c1 = new Casella(n);
					//System.out.println(c1);
					//System.out.println(x+" "+y+" "+n);
					if(matrice.controllaCorrettezza(x, y,c1)){
						Matrice m1 = new Matrice(x,y,matrice,c1);
						//m1.aggiornaMatrice(x, y, c1);
						//System.out.println(matrice.toString());
						seqSoluzioni(m1);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String file = args[0];
		Sudoku s = new Sudoku(file);
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
		s.seqSoluzioni(s.m);
		System.out.println("Soluzioni:  "+Sudoku.soluzioni);
		s.setFine(System.currentTimeMillis());
		if((s.getFine()-s.getInizio()>1000))
				System.out.println("tempo sequenziale:  "+Math.round((s.getFine()-s.getInizio())/1000.0)+"sec");
		else
			System.out.println("tempo sequenziale:  "+(s.getFine()-s.getInizio())+"ms");
		Sudoku.soluzioni = 0;
		System.out.println("");
		System.out.println("risolvendo parallelamente...");
		s.setInizio(System.currentTimeMillis());
		s.parSoluzioni(s.m);
		s.setFine(System.currentTimeMillis());
		System.out.println("Soluzioni:  "+Sudoku.soluzioni);
		if((s.getFine()-s.getInizio()>1000))
			System.out.println("tempo parallelo:  "+Math.round((s.getFine()-s.getInizio())/1000.0)+"sec");
		else
		System.out.println("tempo parallelo:  "+(s.getFine()-s.getInizio())+"ms");

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
