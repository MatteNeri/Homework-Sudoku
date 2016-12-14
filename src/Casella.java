import java.util.*;
/*
 * Rappresentazione di una cella del sudoku, con i relativi valori possibili se vuota
 */
public class Casella {

	private int hash = this.hashCode();
	private Integer valore;
	private boolean vuota;
	private ArrayList<Integer> valoriPossibili = new ArrayList<Integer>();
	
	public Casella(int n){
		this.setVuota(false);
		this.setValore((int)((char)n));
		}
	public Casella(){
		this.setValore(-1);;
		this.setVuota(true);
		for(int i=1; i<10; i++){
			valoriPossibili.add(i);
		}
	}
	public Casella(ArrayList<Integer> array){
		this.setValore(-1);
		this.setVuota(true);
		for(Integer i: array){
			this.valoriPossibili.add(i);
		}
	}

	public int getValore() {
		return valore;
	}

	public void setValore(int valore) {
		this.valore = valore;
	}
	public boolean isVuota() {
		return vuota;
	}
	public void setVuota(boolean vuota) {
		this.vuota = vuota;
	}
	public ArrayList<Integer> getValoriPossibili() {
		return valoriPossibili;
	}
	public void delPossVal(Integer i){
		if (valoriPossibili.contains(i)){
			valoriPossibili.remove(i);
		}
	}
	
	public String toString(){
		if(vuota){
			return ".";
		}
		else
			return valore.toString();
	}
	
	public void eliminaValore(Integer v){
		if (valoriPossibili.contains(v))
			valoriPossibili.remove(v);
	}
	
	public int getHashCode(){
		return hash;
	}
	@Override
	public boolean equals(Object o){
		if(o==null)
			return false;
		if(o.getClass()!=this.getClass())
			return false;
		Casella c = (Casella)o;
		if(!c.isVuota()){
			return this.getValore()==c.getValore();
		}
		else{
			return this.getHashCode()==c.getHashCode();
		}
	}
}
