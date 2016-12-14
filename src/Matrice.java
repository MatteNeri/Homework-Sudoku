import java.util.ArrayList;
import java.io.*;

public class Matrice {
	
	private Casella[][] matrice;
	
	private ArrayList<ArrayList<Casella>> blocchi;
	
	private ArrayList<ArrayList<Casella>> righe;
	
	private ArrayList<ArrayList<Casella>> colonne;
	
	private int celleVuote;
	
	// campi per la struttura ad albero
	private Matrice padre;
	private ArrayList<Matrice> figli;
	private Integer[] coordinateCasella = new Integer[2];
	
	public Matrice(String pathname){
		this.celleVuote = 81;
		this.padre = null;
		this.figli = new ArrayList<Matrice>();
		this.coordinateCasella[0] = null;
		this.coordinateCasella[1] = null;
		matrice = new Casella[9][9];
		blocchi = new ArrayList<ArrayList<Casella>>();
		righe = new ArrayList<ArrayList<Casella>>();
		colonne = new ArrayList<ArrayList<Casella>>();
		for(int i=0; i<9; i++){
			blocchi.add(new ArrayList<Casella>());
			righe.add(new ArrayList<Casella>());
			colonne.add(new ArrayList<Casella>());
		}
		try {
			FileReader file = new FileReader(pathname);
			BufferedReader b = new BufferedReader(file);
			for(int i=0; i<9; i++){
				String riga = b.readLine();
				for(int k=0; k<9; k++){
					char n = riga.charAt(k);
					if (n != '.'){
						Casella casella = new Casella(Character.getNumericValue(n));
						matrice[i][k] = casella;
						blocchi.get(trovaBlocco(i,k)).add(casella);
						righe.get(i).add(casella);
						colonne.get(k).add(casella);
						celleVuote--;
					}
					else {
						Casella casella = new Casella();
						matrice[i][k] = casella;
						blocchi.get(trovaBlocco(i,k)).add(casella);
						righe.get(i).add(casella);
						colonne.get(k).add(casella);
					}
				}
			}
			file.close();
			calcoloCellePossibili();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * crea una nuova matrice uguare alla matrice p,, ma con la casella
	 * in posizione (i,k) settata a c
	 */
	public Matrice(int i, int k, Matrice p, Casella c){
		this.padre = p;
		this.celleVuote = this.getPadre().getCelleVuote()-1;
		this.figli = new ArrayList<Matrice>();
		this.coordinateCasella[0] = i;
		this.coordinateCasella[1] = k;
		this.matrice = new Casella[9][9];
		this.blocchi = new ArrayList<ArrayList<Casella>>();
		this.righe = new ArrayList<ArrayList<Casella>>();
		this.colonne = new ArrayList<ArrayList<Casella>>();
		for(int o=0; o<9; o++){
			this.blocchi.add(new ArrayList<Casella>());
			this.righe.add(new ArrayList<Casella>());
			this.colonne.add(new ArrayList<Casella>());
		}
		for(int x=0; x<9; x++){
			for(int y=0; y<9; y++){
				if (i==x && k==y){
					this.matrice[x][y] = c;
					this.blocchi.get(trovaBlocco(x,y)).add(c);
					this.righe.get(x).add(c);
					this.colonne.get(y).add(c);
				}
				else{
					Casella newCasella = null;
					if(p.getCasella(x, y).isVuota()){
						newCasella = new Casella(p.getCasella(x, y).getValoriPossibili());
					}
					else{
						newCasella = new Casella(p.getCasella(x, y).getValore());
					}
					this.matrice[x][y] = newCasella;
					this.blocchi.get(trovaBlocco(x,y)).add(newCasella);
					this.righe.get(x).add(newCasella);
					this.colonne.get(y).add(newCasella);
				}
			}
		}
		//this.aggiornaMatrice(i, k, c);
	}
	
	public Matrice getPadre(){
		return this.padre;
	}
	
	public Casella getCasella(int i, int k){
		return matrice[i][k];
	}
	
	public void addFiglio(Matrice m){
		this.figli.add(m);
	}
	
	public ArrayList<ArrayList<Casella>> getBlocchi(){
		return blocchi;
	}
	
	public ArrayList<Object> getNextcasellaVuota(){
		Casella c = null;
		ArrayList<Object> list = new ArrayList<Object>();
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				c = matrice[i][k];
				if(c.isVuota()){
					list.add(c);
					list.add(i);
					list.add(k);
					return list;
				}
			}
		}
		list = null;
		return list;
	}
	
	public int trovaBlocco(int i, int k){
		int n = -1;
		if (0<=i && i<=2){
			if (0<=k && k<=2){
				n = 0;
			}
			else if (3<=k && k<=5){
				n = 1;
			}
			else if (6<=k && k<=8){
				n = 2;
			}
		}
		if (3<=i && i<=5){
			if (0<=k && k<=2){
				n = 3;
			}
			else if (3<=k && k<=5){
				n = 4;
			}
			else if (6<=k && k<=8){
				n = 5;
			}
		}
		if (6<=i && i<=8){
			if (0<=k && k<=2){
				n = 6;
			}
			else if (3<=k && k<=5){
				n = 7;
			}
			else if (6<=k && k<=8){
				n = 8;
			}
		}
		return n;
	}
	
	public void aggiornaMatrice(int i, int k, Casella c){
		Casella c1 = this.matrice[i][k];
		int indiceBlocco = -1;
		this.matrice[i][k] = c;
		this.righe.get(i).set(k, c);
		this.colonne.get(k).set(i, c);
		for(Casella l : this.blocchi.get(this.trovaBlocco(i, k))){
			if(l.equals(c1)){
				indiceBlocco = this.blocchi.get(this.trovaBlocco(i, k)).indexOf(l);
			}			
		}
		this.blocchi.get(trovaBlocco(i,k)).set(indiceBlocco, c);
		/*
		System.out.println(this.toString());
		System.out.println(this.getBlocchi().get(0).get(0).getHashCode());
		System.out.println(this.getColonne().get(0).get(0).getHashCode());
		System.out.println(this.getRighe().get(0).get(0).getHashCode());
		*/
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				s += matrice[i][k].toString();
			}
			s += "\n";
		}
		return s;
	}
	
	public int getCelleVuote(){
		return celleVuote;
	}
	
	/*
	 * Per ogni cella, se è vuota, calcola i valori 
	 * possibili che può prendere la cella, in base ai valori
	 * già presenti nella colonna, nella riga e nel blocco della cella.
	 */
	private void calcoloCellePossibili(){
		for(int i=0; i<9; i++){
			for(int k=0; k<9; k++){
				if(matrice[i][k].isVuota()){
					for(int j=0; j<9; j++){
						Casella casellaBlocco = blocchi.get(trovaBlocco(i,k)).get(j);
						Casella casellaRiga = righe.get(i).get(j);
						Casella casellaColonna = colonne.get(k).get(j);
						if(!casellaBlocco.isVuota()){//controlla se la cella è vuota
							matrice[i][k].eliminaValore(casellaBlocco.getValore());// nel caso elimina iol valore della cella dai valori possibili della cella esaminata
						}
						else if(!casellaRiga.isVuota()){//se la casella è vuota elimina il valore dalla lista dei valori possibili
							matrice[i][k].eliminaValore(casellaRiga.getValore());
						}
						else if(!casellaColonna.isVuota()){
							matrice[i][k].eliminaValore(casellaColonna.getValore());
						}
					}
				}
			}
		}
	}

	public ArrayList<ArrayList<Casella>> getRighe() {
		return righe;
	}

	public void setRighe(ArrayList<ArrayList<Casella>> righe) {
		this.righe = righe;
	}

	public ArrayList<ArrayList<Casella>> getColonne() {
		return colonne;
	}

	public void setColonne(ArrayList<ArrayList<Casella>> colonne) {
		this.colonne = colonne;
	}
	
	/*
	 * controlla se la casella in posizione (i,k) ha un valore corretto
	 * in base ai valori delle caselle presenti nel blocco, nella riga
	 * e nella colonna della casella stessa
	 */
	public boolean controllaCorrettezza(int i, int k, Casella c){
		if(this.getColonne().get(k).contains(c)){
			return false;
		}
		else if(this.getRighe().get(i).contains(c)){
			return false;
		}
		else if(this.getBlocchi().get(trovaBlocco(i,k)).contains(c)){
			return false;
		}
		return true;
	}
}
