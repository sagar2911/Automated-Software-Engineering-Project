import java.util.HashMap;

public class Sym extends Col {
	private HashMap<String,Integer> list = new HashMap<>();

	private String mode = "";
	private Integer most = 0;
	private Double entropy;
	private Integer n = 0;
	
	public Sym(int col, String value) {
		super(col, value);
	}
	
	public void addSym(String sym) {
		if(list.size()==0) {
			list.put(sym,1);
			mode=sym;
			most=1;
			entropy=0.0;
			n=1;
		}
		else{
				int count = list.containsKey(sym) ? list.get(sym) : 0;
				list.put(sym, count + 1);
				if(list.get(sym)>list.get(mode)) {
					mode=sym;
				}
				n=0;
				list.forEach((k,v)->{
					n+=v;
					int p = v/list.size();
					entropy-=p*Math.log(p)/Math.log(2);
				});
			most=list.get(mode);
		}
	}
	
	public void deleteSym(String sym) {
		int count = list.containsKey(sym) ? list.get(sym) : 0;
		list.put(sym, count - 1);
		if(count<=0)
		{
			list.remove(sym);
		}
		if(mode.equals(sym))
		{
			most=list.get(sym);
		}
		n=0;
		list.forEach((k,v)->{
			n+=v;
			int p = v/list.size();
			entropy-=p*Math.log(p)/Math.log(2);
			if(v>most)
			{
				most=v;
				mode=k;
			}
		});
	
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

	public ColType getType() {
		return ColType.Sym;
	}

}
