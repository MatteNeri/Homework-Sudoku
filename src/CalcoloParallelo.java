import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class CalcoloParallelo extends RecursiveAction {

	private static final long serialVersionUID = 4649862095581976630L;
	private Matrice matrice;
	private ArrayList<Integer> valoriPossibili = new ArrayList<Integer>();
	private int x;
	private int y;
	private int count;
	
	
	public CalcoloParallelo(Matrice m){
		this.matrice = m;
		this.valoriPossibili = ((Casella)m.getNextCasellaVuota().get(0)).getValoriPossibili();
		this.x = (int)m.getNextCasellaVuota().get(1);
		this.y = (int)m.getNextCasellaVuota().get(2);
		this.count = 1;
	}
	@Override
	protected void compute() {
		ArrayList<CalcoloParallelo> forks = new ArrayList<CalcoloParallelo>();
		if(matrice.getCelleVuote()<=4){
			seqSoluzioni(matrice);
		}
		else{
			CalcoloParallelo primo = null;
			for(int i : valoriPossibili){
				Casella c1 = new Casella(i);
				if(matrice.controllaCorrettezza(x, y, c1)){
					Matrice m1 = new Matrice(x,y,matrice,c1);
					CalcoloParallelo calc = new CalcoloParallelo(m1);
					if(count == 1){
						calc.fork();
						forks.add(calc);
						count--;
						primo = calc;
					}
					else{
						forks.add(calc);
						calc.compute();
					}
				}
			}
			if(primo != null)
				primo.join();
		}
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
}
