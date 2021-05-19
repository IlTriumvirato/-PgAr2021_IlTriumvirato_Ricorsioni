package triumvirato;

public class EuleroPhi {
	
	public static int getDivisorsCount(int n) {
		
		if(n==0) {
			return -1;
		}else{
			int counter=1;
			
			for(int i=1;i<=n/2;i++) {
				if(n%i==0) {
					counter++;
				}
					
			}
			
			return counter;
		}
		
	}
	
	public static boolean isPrime(int n) {
		if(getDivisorsCount(n)==2) {
			return true;
		}else {
			return false;
		}
	}
	
	private static int getPrimePowerBase(int n) {
		//function is never called with value 1
		if(isPrime(n))return n;   
		int toReturn=-1;
		
		for(int i=2;i<n&&toReturn==-1;i++) {      //25   8   12   1   13  
			if(isPrime(i)) {
				for(int j=2;j<n;j++) {
					int potenza=(int)Math.pow(i, j);
					if(potenza==n) {
						toReturn=i;
					}else {
						if(potenza>n) {
							break;
						}
					}
				}
			}
		}
		
		return toReturn;
	}
	
	
	public static int casoFinale(int p, int k) {
		return (p-1)*((int)(Math.pow(p, k-1)));
	}
	
	private static double getLogWithBase(int base, int logValue) {
		return Math.log(logValue)/Math.log(base);
	}
	
	/*private static euclidMCD() {
		   12   2^2*3     63      9   7
	}*/
	
	private static int getFirstDivisor(int n) {
		int primoValore=1;
		int secondoValore=n;
		for(int i=2;i<n;i++) {
			if(n%i==0){
				primoValore=1;
				while(secondoValore%i==0) {
					secondoValore/=i;
					primoValore*=i;
				}
				
				break;
			}
		}
		
		return primoValore;
	}
	
	
	public static int ricorsioneEulero(int n) {
		
		if(n==1)return 1;
		
		int primePowerBase=getPrimePowerBase(n); //-1 if it is not
		
		if(primePowerBase!=-1){
			
			//sappiamo che n è p^k
			int k=(int)getLogWithBase(primePowerBase,n);
			return casoFinale(primePowerBase,k);
			
		}else {
			
			int a=getFirstDivisor(n);
			int b=n/a;
			return ricorsioneEulero(a)*ricorsioneEulero(b);
			
		}
	}

	
	
}
