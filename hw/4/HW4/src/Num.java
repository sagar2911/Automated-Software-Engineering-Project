import java.util.ArrayList;
import java.util.List;

public class Num extends Col {
	
	private List<Integer> list = new ArrayList<Integer>();
	double mean=0;
	double sd=0;
	double sum=0;
	double m2=0;
	int high =  Integer.MIN_VALUE;
	int low  = Integer.MAX_VALUE;
	
	public Num(int col, String value) {
		super(col, value);
	}
	
	public void addNum(int num) {
		if(list.size()==0)
		{
			list.add(num);
			mean=num;
			sum=num;
			m2=0;
			sd = 0;
			high = num;
			low = num;
		}
		else{
			list.add(num);
			int n =list.size();
			double temp = num-mean;
			mean+=temp/n;
			sum+=num;
			m2+=temp*(num-mean);
			sd = CalculateSD();
			if(num>high)
			{
				high=num;
			}
			if(num<low)
			{
				low=num;
			}
		}
	}
	public Double CalculateSD(){
		
		if(m2<0) return 0.0;
		if(list.size()<2) return 0.0;
		return Math.sqrt(m2/(list.size()-1));
	}
	
	public void deleteNum(int num) {
		list.remove(list.size()-1);
		if(list.size()==0)
		{
			m2=0;
			sd = 0;
		}
		else if(list.size()==1) {
			m2=num;
			sd = 0;
		}
		else {
			int n =list.size();
			double temp = num-mean;
			mean-=temp/n;
			m2-=temp*(num-mean);
			sum-=num;
			sd = CalculateSD();
		}
		
	}
	
	public double getMean() {
		return mean;
	}

	public double getSd() {
		return sd;
	}
	
	public String dump() {
		StringBuilder dump = new StringBuilder();
		dump.append("|\t|\tadd: Num1\n");
		dump.append("|\t|\tcol: "+col+"\n");
		dump.append("|\t|\thi: "+ (list.isEmpty() ? 0 : high)+"\n");
		dump.append("|\t|\tlo: "+(list.isEmpty() ? 0 : low) + "\n");
		dump.append("|\t|\tm2: "+m2+"\n");
		dump.append("|\t|\tmu: "+mean+"\n");
		dump.append("|\t|\tn: "+list.size()+"\n");
		dump.append("|\t|\tsd: "+sd+"\n");
		dump.append("|\t|\ttxt: "+txt+"\n");	
		return dump.toString();
	}
	
	public ColType getType() {
		return ColType.Num;
	}
	

	public double like(int x) {
		double var = Math.pow(sd, 2);
		double denom = Math.pow(3.14159 * 2 * var, 0.5);
		double num = Math.pow(2.71828, -Math.pow(x - mean, 2) / (2 * var + 0.0001));
		return num / (denom + Math.pow(10, -64));
	}

}
