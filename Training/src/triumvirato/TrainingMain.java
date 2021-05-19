package triumvirato;

import java.util.ArrayList;
import java.util.Scanner;

public class TrainingMain {

	/*
	 * Fibonacci
	 * phi
	 * operazioni(l'espressione ti viene data in forma di un albero|lettura diretta della stirnga espressione)
	 */
	
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		int a=0;
		ArrayList<OperationBranch> array=new ArrayList<OperationBranch>();
		
		/*a=scan.nextInt();
		
		//System.out.println(Fibonacci.funzioneFibonacci(a));
		//System.out.println(Fibonacci.chiamaRicorsione(a));
		
		System.out.println(EuleroPhi.ricorsioneEulero(a));
		
				  
		*/
		int valoreFinale;
		String espressione="[(14+33)*3]+{{2*4/(-2)-21-3-(-4)}/3-3}+(-23)";
		System.out.println(espressione+"=");
		valoreFinale=CalcolatoreEspressione.convertStringToValue(espressione);
		/*
		valoreFinale=CalcolatoreEspressione.calcolaEspressioneSenzaParentesi(espressione);
		*/
		System.out.println("="+valoreFinale);
		//System.out.println(CalcolatoreEspressione.plusMinusStringToInt(espressione));
	}
}
