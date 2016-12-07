import java.util.ArrayList;
import java.io.*;

public class Matrice {
	
	private Casella[][] matrice;
	
	private ArrayList<ArrayList<Casella>> blocchi;
	
	private ArrayList<ArrayList<Casella>> righe;
	
	private ArrayList<ArrayList<Casella>> colonne;
	
	private int celleVuote = 81;
	
	public Matrice(String pathname){
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
	
	public Casella getCasella(int i, int k){
		return matrice[i][k];
	}
	
	public ArrayList<ArrayList<Casella>> getBlocchi(){
		return blocchi;
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
}
