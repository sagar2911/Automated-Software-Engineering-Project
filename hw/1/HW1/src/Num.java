import java.util.ArrayList;
import java.util.List;

public class Num extends Col {
	


	private List<Integer> list = new ArrayList<Integer>();
	double mean=0;
	double sd=0;
	double sum=0;
	double sumSquares=0;
	
	
	public void addNum(int num) {
		// TODO: implement
		
		
		if(list.size()==0)
		{
			list.add(num);
			mean=num;
			sum=num;
			sumSquares=Math.pow(num, 2);
			sd = 0;
		}
		else{
			list.add(num);
			int n =list.size();
			mean=(mean*(n-1)+num)/n;
			sum+=num;
			sumSquares+=Math.pow(num, 2);
			sd = Math.sqrt(((n)*sumSquares-Math.pow(sum, 2))/(Math.pow(n,2)));
		}
	}
	
	public void deleteNum(int num) {
		list.remove(list.size()-1);
		if(list.size()==0)
		{
			list.add(num);
			mean=num;
			sum=num;
			sumSquares=Math.pow(num, 2);
			sd = 0;
		}
		else {
			int n =list.size();
			mean=(mean*(n+1)-num)/(n);
			sum-=num;
			sumSquares-=Math.pow(num, 2);
			sd = Math.sqrt(((n)*sumSquares-Math.pow(sum, 2))/(Math.pow(n,2)));
		}
		
	}
	
	public double getMean() {
		return mean;
	}

	public double getSd() {
		return sd;
	}

}
