import java.util.List;

public class Domination {
	
	private static final double z = 0.00001;
	
	public static boolean dominates(Row i, Row j, List<Num> goals) {
		double s1 = z;
		double s2 = z;
		double n = z + goals.size();

		for (Num goal : goals) {
			Cell ca = i.get(goal.col - 1);
			Cell cb = j.get(goal.col - 1);
			
			double a = goal.norm(ca.doubleValue);
			double b = goal.norm(cb.doubleValue);

			s1 -= Math.pow(10, goal.w * (a - b) / n);
			s2 -= Math.pow(10, goal.w * (b - a) / n);
		}
		
		double res = s1 / n - s2 / n;

		return res < 0;
	}
	public static int countDominates(Row i, Row j, List<Num> goals) {
		double s1 = z;
		double s2 = z;
		double n = z + goals.size();
		int count =0;
		for (Num goal : goals) {
			Cell ca = i.get(goal.col - 1);
			Cell cb = j.get(goal.col - 1);
			
			double a = goal.norm(ca.doubleValue);
			double b = goal.norm(cb.doubleValue);

			if(goal.w * (a - b)>0)
				count++;
			else if(goal.w * (a - b)<0)
				count--;
		}
		
		return count;
		
	}

}
