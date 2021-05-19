package triumvirato;

public class OperationBranch {
	static final char[] signs= {'+','-','*','/','_'};
	char sign;
	OperationBranch left;
	OperationBranch right;
	int trueValue;
	int complexityRank;
	
	OperationBranch(OperationBranch left,char sign,OperationBranch right){
		this.left=left;
		this.right=right;
		this.sign=sign;
		
		int max=Math.max(left.complexityRank, right.complexityRank);
		this.complexityRank=max+1;
	}
	
	OperationBranch(int trueValue){
		this.trueValue=trueValue;
		this.sign='_';
		complexityRank=1;
	}
	
	OperationBranch(OperationBranch copia){
		this.complexityRank=copia.complexityRank;
		if(copia.sign=='_') {
			this.trueValue=copia.trueValue;
			this.sign='_';
		}else {
			this.left=new OperationBranch(copia.left);
			this.right=new OperationBranch(copia.right);
			this.sign=copia.sign;
		}
	}
	
	
	public int getValue(){
		int valore=0;
		
		
		switch(sign) {
			case '+':
				valore=left.getValue()+right.getValue();
			break;
			case '-':
				valore=left.getValue()-right.getValue();
			break;
			case '*':
				valore=left.getValue()*right.getValue();
			break;
			case '/':
				if(right.getValue()!=0) {
					valore=left.getValue()/right.getValue();
				}else {
					//eccezione
					System.out.println("DIVIDE BY 0 ERROR");
				}

			break;
			case '_':
				valore=trueValue;
			break;
			default:
				
			break;
		}
		
		return valore;
	}
}
