package triumvirato;

import java.util.ArrayList;

public class CalcolatoreEspressione {
	ArrayList<OperationBranch> tree=new ArrayList<OperationBranch>();
	
	//array.add(new OperationBranch());
	//array.add(new OperationBranch(array.get(),'',array.get()));
	
	CalcolatoreEspressione(){
		tree=new ArrayList<OperationBranch>();
	}
	
	CalcolatoreEspressione(boolean example){
		
		//orange
		tree.add(new OperationBranch(2)); //0
		tree.add(new OperationBranch(2)); //1
		tree.add(new OperationBranch(1)); //2
		tree.add(new OperationBranch(3)); //3
		tree.add(new OperationBranch(9)); //4
		tree.add(new OperationBranch(3)); //5
		tree.add(new OperationBranch(2)); //6
		tree.add(new OperationBranch(2)); //7
		
		
		//skyblue
		tree.add(new OperationBranch(tree.get(0),'+',tree.get(1))); //8
		tree.add(new OperationBranch(tree.get(2),'-',tree.get(3))); //9
		tree.add(new OperationBranch(tree.get(5),'+',tree.get(6))); //10
		
		//green
		tree.add(new OperationBranch(tree.get(8),'*',tree.get(9))); //11
		tree.add(new OperationBranch(tree.get(10),'-',tree.get(7))); //12
		
		//dark blue
		tree.add(new OperationBranch(tree.get(4),'/',tree.get(12))); //13
		
		//purple
		tree.add(new OperationBranch(tree.get(11),'+',tree.get(13))); //14
		
		
	}

	public int calcola() {
		int max=tree.get(0).complexityRank;
		int position=0;
		for(int i=0;i<tree.size();i++) {
			if(tree.get(i).complexityRank>max) {
				max=tree.get(i).complexityRank;
				position=i;
			}
		}
		
		return tree.get(position).getValue();
	}
	
	
	//DA QUI SONO I METODI CHE VENGONO USATI PER L'ESPRESSIONE DALLA STRINGA
	
	static boolean isSign(char c) {
		return (c=='+'||c=='-'||c=='*'||c=='/');
	}
	
	static boolean isDigit(char c) {
		return (c>='0'&&c<='9');
	}
	
	static int getDigit(char c) {
		return (int)(c-'0');
	}
	
	//calcola tutte le divisioni e moltiplicazioni nel loro ordine, noto che non ci sono parentesi eccetto i casi  3*(-4)
	static String getProductDivisionLessString(String espressione) {
		int i=0;
		int n=espressione.length();
		
		while(i<n) {
			
			if(espressione.charAt(i)=='*'||espressione.charAt(i)=='/') {
				//sono ovviamente i due numeri che circondano il segno * o / che sia    che da qui scrivo */
				int value1=0;
				int value2=0;
					
				
				//il numero precedente inizia dalla posizione prima del segno */
				int j1=i-1;
				int digitWeight=0; //peso della cifra
				if(espressione.charAt(j1)==')') {  //SEPARA I DUE CASI:   nel primo abbiamo che davanti al  */ c'è un numero del tipo   (-231)   nel secondo abbiamo che davanti al  */ c'è un numero normale che potrebbe avere davanti un segno, ma è irrilevante perché in tal caso non viene sostituito e lo stesso avviene per il segno davanti alla parentesi (-2131) 
					
					j1--; //deve calare ancora di 1 per escludere la tonda
					while(j1>=0&&isDigit(espressione.charAt(j1))) { //finché ci sono numeri o non arriva a fine stringa
						value1+=(int)(getDigit(espressione.charAt(j1))*Math.pow(10, digitWeight)); //cifra alla posizione successiva nel numero moltiplicata per  10^posizione della cifra
						j1--;
						digitWeight++;
					}
					
					value1=-value1;  //prende il valore negativo per 
					j1-=2; //poi dopo gli serve sapere da che posizione sostituire quindi si segna di comprendere anche (- nei caratteri da rimuovere
					
				}else {
					while(j1>=0&&isDigit(espressione.charAt(j1))) {
						value1+=(int)(getDigit(espressione.charAt(j1))*Math.pow(10, digitWeight));
						j1--;
						digitWeight++;
					}
				}
				
				
				
				int j2=i+1;
				if(espressione.charAt(j2)=='(') {
					j2+=2;//removes the open bracket and - sign
					while(j2<espressione.length()&&(isDigit(espressione.charAt(j2)))) {
						value2*=10;
						value2+=(int)(getDigit(espressione.charAt(j2)));
						j2++;
					}
					j2++;//removes the closed bracket
					value2=-value2;
					
				}
				while(j2<espressione.length()&&(isDigit(espressione.charAt(j2)))) {
					value2*=10;
					value2+=(int)(getDigit(espressione.charAt(j2)));
					j2++;
				}
				
				
				//qui semplicemente calcola il risultato, che andrà messo al posto della stringa da sostituire, che andrà da j1 a j2 (esclusi, quindi substring  j1+1,j2 perchè la substring ha il primo incluso e il secondo escluso)
				int risultatoOperazione=0;
				if(espressione.charAt(i)=='*') {
					risultatoOperazione=value1*value2;
				}else {
					if(value2!=0) {
						risultatoOperazione=value1/value2;					
					}else {
						System.out.println("CAN'T DIVIDE BY ZERO");
					}

				}
				
				String precedente;
				String toReplace; //sarà la stringa che verrà messa al posto di  espressione.substring(j1+1,j2)
				String successiva;

				if(risultatoOperazione>=0) {  //questo è molto importante per evitare che si creino situazioni del tipo  3*-4  anche se tecnicamente non dovrebbe succedere comunque
					toReplace=""+risultatoOperazione;
				}else {
					toReplace="("+risultatoOperazione+")";
				}
				
				
				precedente=espressione.substring(0,j1+1);	//non da mai problemi prendere dall'inizio a j1 compreso			
				
				successiva=espressione.substring(j2,espressione.length());
				
				/*
				 //questo se dovesse dare problemi
				 if(j2<espressione.length()-1) {  
					successiva=espressione.substring(j2,espressione.length());
				 }else {
					successiva="";
				 }
				 
				 */
				
				
				espressione=precedente+toReplace+successiva;
				n=espressione.length();
				i=precedente.length()+toReplace.length()-1;//deve spostarsi dopo la parte che ha sostituito, per calcolarla non può più usare j1 e j2 perché la stringa è cambiata, usa la dimensione della stringa sostituita
				
				System.out.println(espressione);//stampa anche il passaggio così si vede
			}else {
				i++;
			}
			

		}
		
		return espressione;
		
	}
	
	//calcola tutte le addizioni e sottrazioni nel loro ordine, noto che non ci sono più moltiplicazioni e divisioni né parentesi eccetto i casi  3+(-4) o 3-(-4)
	static int sommaAlgebrica(String espressione) {
		int i=0;
		int n=espressione.length();
		char currentSign='+';
		int totalValue=0;
		int currentValue=0;
		
		//all'inizio il totale non vale niente, e ancora il primo numero da aggiungere, currentValue non ha un valore, il segno di default è positivo
		while(i<n) {
			if(isDigit(espressione.charAt(i))) {  //finché trova solo numeri, vuol dire che sono le prossime cifre del numero corrente quindi le aggiunge in coda(shiftando a sx le cifre precedenti con un *10)
				currentValue*=10;
				currentValue+=getDigit(espressione.charAt(i));
				
			}else if(espressione.charAt(i)=='(') { //se trova una tonda è DI SICURO un (-32432)
				
				i++; //salta il meno e semplicemente cambia il segno, in quanto il numero corrente è sicuramente 0 perché prima della parentesi c'era di sicuro un segno, a meno che non sia all'inizio dell'espressione, ma anche in tal caso è 0
				if(currentSign=='+') {
					currentSign='-';					
				}else {
					currentSign='+';
				}
				
			}else if(espressione.charAt(i)=='+'||espressione.charAt(i)=='-'){ //quando incontra un segno giustamente aggiunge currentValue alla somma totale
				
				//dopodiché deve cambiare 
				if(currentSign=='+') {
					totalValue+=currentValue;
				}else if(currentSign=='-') {
					totalValue-=currentValue;	
				}
				currentSign=espressione.charAt(i);
				
				currentValue=0;
			}		
			//else if(espressione.charAt(i)==')'   non fa assolutamente niente perché dopo la tonda c'è sempre un segno e se non c'è, vuol dire che è l'ultimo numero e quello viene sempre aggiunto dopo il while altrimenti ovviamente ci sarebbero problemi anche in caso l'espressione non finisca con un segno
			
			i++;
			
		}
		
		
		//aggiunge l'ultimo numero perché la stringa non finisce mai con un segno
		if(currentSign=='+') {
			totalValue+=currentValue;
		}else if(currentSign=='-') {
			totalValue-=currentValue;	
		}
		
		
		return totalValue;
	}
	
	static int calcolaEspressioneSenzaParentesi(String espressione) {
		String espressioneElaborataMoltiplicazioniDivisioni=getProductDivisionLessString(espressione);
		return sommaAlgebrica(espressioneElaborataMoltiplicazioniDivisioni);
	}
	
	//ritorna la stessa stringa ma il tratto indicato dando la posizione della parentesi graffa con cui inizia e quello con la parentesi graffa chiusa con cui finisce viene sostituito col valore di quella parentesi, calcolato ricorsivamente
	public static String handledBracketExpression(String daFiltrare, int start, int end) {
		
		String precedente; 
		String daSostituire=daFiltrare.substring(start+1,end); //l'espressione interna alle parentesi, le parentesi stesse vengono tagliate
		String successiva;
		
		
		precedente=daFiltrare.substring(0,start);
		successiva=daFiltrare.substring(end+1,daFiltrare.length());
		/*
		//in caso dovesse dare problemi di bounds
		if(end==daFiltrare.length()-1) {
			successiva="";
		}else {
			successiva=daFiltrare.substring(end+1,daFiltrare.length());
		}
		*/
		
		int risultatoParentesi=calcolaEspressione(daSostituire);
		
		
		if(risultatoParentesi<0) {
			daSostituire="("+risultatoParentesi+")";
		}else {
			daSostituire=""+risultatoParentesi;
		}
		
		return precedente+daSostituire+successiva;
	}
	
	
	
	//works only if () and [] are properly converted to {} and brackets with single negative values are transformed as proper in (-natural)
	public static int calcolaEspressione(String espressione) {
		int openedCounter=0;
		int closedCounter=0;
		int firstPositionOpened=-1;
		int lastPositionClosed=-1;
		
		//questo for funziona così: si segna la prima graffa(tutte le parentesi lo sono e le tonde sono solo per singoli valori negativi)
		//trovata la prima graffa, continua a scorrere l'espressione finchè non trova la sua graffa chiusa(la trova contando quante graffe aperte e chiuse trova, quando il numero è uguale, contando quello di partenza, ha trovato la } che corrisponde alla { iniziale
		
		for(int i=0;i<espressione.length();i++) {
			char c=espressione.charAt(i);
			
			if(c=='{') {
				if(openedCounter==0) {
					firstPositionOpened=i;
				}
				openedCounter++;
			}else if(c=='}') {
				closedCounter++;
				if(closedCounter==openedCounter) {
					lastPositionClosed=i;
					
					break;
				}
			}
			
		}
		
		//se ha effettivamente trovato le graffe, lui calcola il valore della graffa con una funzione che non è altro che un metodo di appoggio che di fatto chiama questa stessa funzione sull'espressione contenuta nelle graffe ed essendo ricorsiva, può farlo senza preoccuparsi se l'espressione tra le {} contiene altre {} con le loro espressioni
		if(firstPositionOpened!=-1&&lastPositionClosed!=-1) {
			espressione=handledBracketExpression(espressione,firstPositionOpened,lastPositionClosed);
			//questa funzione sostituisce proprio la graffa di questa stringa col suo valore, ma solo per la graffa indicata, potrebbero rimanerne ancora, in tal caso questa funzione verrà chiamata di nuovo sull'espressione che è stata appena filtrata
		}

		//se non ha trovato parentesi(quindi sia nell'iterazione finale di questa espressione, sia nelle parentesi graffe senza graffe al loro interno) calcola il valore contando che non ci siano {} nell'espressione e basta
		if(openedCounter==0&&closedCounter==0) {
			return calcolaEspressioneSenzaParentesi(espressione);
		}else { //se ci sono ancora graffe(anche avendole sostituite con handledBracketExpression
			return calcolaEspressione(espressione);
		}
		
	}
	
	//rimpiazza il carattere alla posizione index con il carattere c, e ritorna la stringa modificata
	public static String stringWithReplacedChar(String stringa,int index, char c) {  
		if(index<stringa.length()-1) {
			stringa=stringa.substring(0,index)+c+stringa.substring(index+1,stringa.length());
		}else {
			stringa=stringa.substring(0,index)+c;
		}
		
		return stringa;
	}
	
	//cambia TUTTE le parentesi () e []  in {}  perché tanto le due scritture sono equivalenti
	public static String convertiParentesi(String espressione) {
		String temp=espressione;
		for(int i=0;i<espressione.length();i++) {
			if(temp.charAt(i)=='('||temp.charAt(i)=='[') {
				temp=stringWithReplacedChar(temp,i,'{');
			}else if(temp.charAt(i)==')'||temp.charAt(i)==']') {
				temp=stringWithReplacedChar(temp,i,'}');
			}
		}
		
				
		return temp;
	}
	
	//Per comodità, da qui voglio che sempre tutte le parentesi siano {} eccezion fatta per quelle contenenti un singolo valore negativo, quelle voglio che siano (-3809) perché così i controlli sono molto più rapidi.
	public static String adattaParentesiConSingoloNegativo(String espressione) {
		for(int i=0;i<espressione.length()-2;i++) {
			
			if(espressione.charAt(i)=='{'&&espressione.charAt(i+1)=='-') {
				
				for(int j=i+2;j<espressione.length();j++){
					if(!isDigit(espressione.charAt(j))){
						if(espressione.charAt(j)=='}') { //se è arrivato qui senza mai incappare nell'else di questo stesso if(che fa break) vuol dire che non ha incontrato né segni né {  quindi dopo il {-  era tutto del tipo 31431} quindi è una parentesi problematica e basta, può interrompere il for interno perché ormai ha trovato la fine della parentesi; se non lo interrompesse con break darebbe errore perché continua a sostituire finché non trova segni o {
							espressione=stringWithReplacedChar(espressione,i,'(');
							espressione=stringWithReplacedChar(espressione,j,')');
							break;
						}else {//se entra in questo caso vuol dire che ha trovato un segno oppure una { aperta quindi, quindi può stare tranquillo che questo non è un caso di singolo numero negativo tra {} da sostituire con (), quindi può interrompere questo ciclo e spostare la i dopo il valore che ha trovato
							i=j-1;
							break;
						}
					}
				}
				//checks if there's any opened bracket, if that's the case, it can terminate, same with sign chars.
				//if it finds a closed bracket and hasn't already called break;  it will replace brackets
			}
		}
		
		return espressione;
	}
	
	public static int convertStringToValue(String espressione) {
		espressione=espressione.trim();
		System.out.println(espressione);
		espressione=convertiParentesi(espressione);
		System.out.println(espressione);
		espressione=adattaParentesiConSingoloNegativo(espressione);
		System.out.println(espressione);
		int valore=calcolaEspressione(espressione);
		return valore;
	}
	
	
	/*
	 * metodo più intelligente:
	 * rimpiazza TUTTE le [ e ( con delle {
	 * rimpiazza TUTTE le ] e ) con delle }
	 * funzione int getValueOfSubstring: per prima cosa calcola il contenuto delle parentesi: per farlo richiama questa stessa funzione sulle parentesi, per trovarle semplicemente fa il conteggio finché non trova lo stesso numero di aperte e di chiuse
	 * ritorna direttamente il valore int se non ci sono parentesi
	 * le parentesi vengono interpretate letteralmente come un numero e vengono sostituite con quello infatti
	 */
	
	
	
	
	
}
