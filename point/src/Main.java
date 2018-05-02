import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5};
		
		Integer[] b = {12 , 18 , 5};
		

//		PointNd<Integer> c = new PointNd<Integer>();
		
		PointNd<Double> d = new PointNd<Double>(1.0, 2.0, 3.0);
		
		PointNd<Integer> e = new PointNd<Integer>(b);
		
		PointNd<Integer> f = new PointNd<Integer>(1,2,3,4,5);
		
		
		//PointNd w=new PointNd(a);
		
		//PointNd<Double> r = new PointNd<Double>(a);
		
//		for(int i=0; i<d.GetN();i++) System.out.printf("p%d=%s\n",i, d.GetPoint(i).toString());
				
		System.out.printf("wykonano %d\n", e.Translate(f));
		
		for(int i=0; i<e.GetN();i++) System.out.printf("p%d=%s\n",i, e.GetPoint(i).toString());
		e.SetN(5);
		for(int i=0; i<e.GetN();i++) System.out.printf("p%d=%s\n",i, e.GetPoint(i).toString());
		System.out.printf("wykonano %d\n", e.Translate(f));
		
		for(int i=0; i<e.GetN();i++) System.out.printf("p%d=%s\n",i, e.GetPoint(i).toString());
		

	}

}
