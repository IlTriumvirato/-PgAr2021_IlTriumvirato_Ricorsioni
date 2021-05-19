package triumvirato;

import java.util.ArrayList;

public class Fibonacci {
	static ArrayList<Integer> array;
		
	Fibonacci(){

	}
	
	public static int funzioneFibonacci(int n) { //n
		array=new ArrayList<Integer>();
		array.add(0);
		array.add(1);
		
		for(int i=2;i<=n;i++) {
			array.add(array.get(i-1)+array.get(i-2));
		}
		
		return array.get(n);
	}
	
	public static int chiamaRicorsione(int n) {
		
		if(n<array.size()) {
			return array.get(n);
		}else {
			if(array==null) {
				array=new ArrayList<Integer>();
			}

			
			for(int i=array.size();i<=n;i++) {
				array.add(-1);
			}
			
			return ricorsioneFibonacci(n);
		}
		
		
	}
	
	//assumendo ovviamente n>=0
	public static int ricorsioneFibonacci(int n) {
		if(n==0) {
			return 0;
		}else if(n==1){
			return 1;
		}else {
			
			if(array.get(n)!=-1) {
				int valore=array.get(n);
				System.out.println("Stavolta ho già trovato f("+n+")="+valore+" nell'array");
				return valore;
				
			}else{
				
				int valore=ricorsioneFibonacci(n-2)+ricorsioneFibonacci(n-1);
				array.set(n, valore);
				System.out.println("Salvo f("+n+")="+valore+" nell'array");
				return valore;
			}
			
			
		}
		
	};
}
