import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sym extends Col {
	private HashMap<String,Integer> list = new HashMap<>();

	private String mode = "";
	private Integer most = 0;
	private Double entropy;
	private Integer n = 0;
	private Integer counts = 0;
	public Integer rank;
	
	public Sym(int col, String value) {
		super(col, value);
	}
	
	public void addSym(String sym) {
		if(list.size()==0) {
			list.put(sym,1);
			mode=sym;
			most=1;
			entropy=0.0;
			counts=1;
		}
		else{
				counts++;
				int count = list.containsKey(sym) ? list.get(sym) : 0;
				list.put(sym, count + 1);
				if(list.get(sym)>list.get(mode)) {
					mode=sym;
				}
				
				
				entropy=0.0;
				for(Map.Entry<String,Integer> m : list.entrySet()) {
					double p = (double)m.getValue()/(double)counts;
					double logVal=Math.log(p);
					double basetwoLogVal = logVal/(Math.log(2.0)+1e-10);
					double currentEntropy=p*(basetwoLogVal);;
					entropy-= currentEntropy;
				}
				if(entropy.doubleValue()!=0.0)
				{
//					System.out.println("");
				}
			most=list.get(mode);
		}
	}
	
	public void deleteSym(String sym) {
		int count = list.containsKey(sym) ? list.get(sym) : 0;
		list.put(sym, count - 1);
		if(list.get(sym)<=0)
		{
			list.remove(sym);
		}
		counts--;
//		if(counts==0)
//		{
//			System.out.print("");
//		}


		if(mode.equals(sym))
		{
			most--;
			list.forEach((k,v)->{
				
				if(v>most)
				{
					most=v;
					mode=k;
				}

			});
		}
		entropy=0.0;
		for(Map.Entry<String,Integer> m : list.entrySet()) {
			double p = (double)m.getValue()/(double)counts;
			double logVal=Math.log(p);
			double basetwoLogVal = logVal/(Math.log(2.0)+1e-10);
			if(Double.isNaN(basetwoLogVal)) {
//				System.out.print("here");
			}
			double currentEntropy=p*(basetwoLogVal);;
			entropy-= currentEntropy;
		}
	
	}

	public String dump() {
		StringBuilder dump = new StringBuilder();
		dump.append("|\t|\tadd: Sym1\n");
		dump.append("|\t|\tcnt\n");
		// todo add
		list.forEach((k,v)->{
			dump.append("|\t|\t|\t" +k+":"+v+"\n");
		});
		dump.append("|\t|\tcol: " +col+"\n");
		dump.append("|\t|\tmode: "+mode+"\n");
		dump.append("|\t|\tmost: "+most+"\n");
		dump.append("|\t|\tn: "+n+"\n");
		dump.append("|\t|\ttxt: "+txt+"\n");	
		return dump.toString();
	}
	
	public String getMode() {
		return mode;
	}
	public Integer getMost() {
		return most;
	}
	public double getMostByN() {
		double mostbyn = (double)most/(double)counts;
		return mostbyn;
	}
	public Double getEntropy() {
		return Double.isNaN(entropy/Math.log(list.size()) * Math.log(2.0))?0.0:entropy/Math.log(list.size()) * Math.log(2.0);
	}
	public Double setEntropy() {
		return entropy=0.0;
	}

	public ColType getType() {
		return ColType.Sym;
	}
	public Integer getListCount() {
		return n;
	}
}
