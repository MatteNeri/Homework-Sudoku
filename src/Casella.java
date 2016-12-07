import java.util.*;

public class Casella {

	private Integer valore;
	private boolean vuota;
	private ArrayList<Integer> valoriPossibili = new ArrayList<Integer>();
	
	Casella(int n){
		this.setVuota(false);
		this.setValore((int)((char)n));
		}
	Casella(){
		this.setValore(-1);;
		this.setVuota(true);
		for(int i=1; i<10; i++){
			valoriPossibili.add(i);
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
}
