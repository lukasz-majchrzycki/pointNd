import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Number;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class PointNd <T extends Number> {
	
private ArrayList<T> x=new ArrayList<T>();

private enum CoordSystem {
	CYLINDRICAL,
	SPHERICAL,
	POLAR;
}

// Constructor


@SuppressWarnings({ "unchecked" })
public PointNd(T ...x) {		//Point Nd={x[0]; x[1]; ... ; x[n]}
	
	for(int i=0;i<x.length;i++) {
		this.x.add(x[i]);
	}
}

@SuppressWarnings("unchecked")
public PointNd(PointNd<T> p) {
	this((T[]) p.x.toArray());
}

// Setter

public int SetPoint(int poz, T x) {
	
	try {
		this.x.set(poz, x);
	} 
	catch (ArrayIndexOutOfBoundsException e) {
		return -1;
	}
	return poz;	
}

@SuppressWarnings("unchecked")
public int SetPoint(T ...x) {
	try {
		for(int i=0;i<x.length;i++)
			this.x.set(i+1, x[i]);
		}
		
	catch (ArrayIndexOutOfBoundsException e){
		return -1;
	}
return x.length;
}


public int SetN(int N) {
	int j, i=this.x.size();
	if(i<N) {
		this.x.ensureCapacity(N);
		for(j=i;j<N;j++) {
			this.x.add(j,this.setZero(this.x.get(0)));
		}
	}
	else if (i>N) {
		for(j=i;j>N;j--) {
			this.x.remove(j-1);
		}
	}

	return N-i;
}



//Getter
public T GetPoint(int i)
{
	return this.x.get(i);

}

public int GetN() {
	return this.x.size();
}

public ArrayList<Double> changeCoordSystem(CoordSystem sys) throws ArithmeticException{
	
	ArrayList<Double> newSys = new ArrayList<Double>();
	double old[]=new double[3];
	for(int i=0;i<3;i++) {
	try{
		old[i]=this.x.get(i).doubleValue();
	}	catch(ArrayIndexOutOfBoundsException e) {
		old[i]=0.0;
	}
	}
	
	switch (sys) {
	case CYLINDRICAL: case POLAR:
		if(this.x.size()==3||this.x.size()==2) {

			newSys.add(Math.sqrt(Math.pow(old[0], 2)+Math.pow(old[1], 2)));		//radius
			newSys.add(Math.atan2(old[1],old[0]  ));							//azimuth
			if(this.x.size()==3) {
				newSys.add(old[2]);												//height
			}
			return newSys;
		}else throw new ArithmeticException();
		
	case SPHERICAL:{
		if(this.x.size()==3) {
			
			newSys.add(Math.sqrt(Math.pow(old[0], 2)+Math.pow(old[1], 2)+Math.pow(old[2], 2)));		//radius 
			newSys.add(Math.atan2(old[1],old[0]  ));												//azimuth 
			newSys.add(Math.acos(old[2]/newSys.get(0)  ));											//inclination 																	//inclination 
			return newSys;
		}else throw new ArithmeticException();
	}
		
	default:
		break;
	}
	
		
	return null;
}

//Utilities

private <U extends Number> T add(T x, U y) {
	
	BigDecimal sum = new BigDecimal(x.toString());
	sum=sum.add(new BigDecimal(y.toString()));
	return convert(sum,x);	
}


@SuppressWarnings({ "deprecation", "unchecked" })
private T convert(BigDecimal c, Number y) {	
	if(y instanceof Long)
		return (T) new Long( c.longValueExact());
	else if (y instanceof Integer)
		return (T) new Integer (c.intValueExact());
	else if (y instanceof Short)
		return (T) new Short( c.shortValueExact());
	else if (y instanceof Byte)
		return (T) new Byte (c.byteValueExact());
	else if (y instanceof Double)
		return (T) new Double(c.doubleValue());
	else if (y instanceof Float)
		return (T) new Float( c.floatValue());

	else if (y instanceof BigDecimal)
		return (T) c;	
	
	else return null;
}


@SuppressWarnings({ "deprecation", "unchecked" })
private T setZero(Number y) {
	if(y instanceof Long)
		return (T) new Long(0);
	else 	if(y instanceof Integer)
		return(T) new Integer(0);
	else 	if(y instanceof Short)
		return (T) new Short((short)0);
	else 	if(y instanceof Byte)
		return (T) new Byte((byte)0);
	else 	if(y instanceof Double)
		return (T) new Double(0.0d);
	else 	if(y instanceof Float)
		return (T) new Float(0.0f);
	
	else if (y instanceof BigInteger)
		return (T)  BigInteger.ZERO;	
	
	else return null;
}

@SuppressWarnings("unchecked")
public <U extends Number> int Translate (U ...x) {
	int i;
	int m=Math.min(x.length, this.x.size());
	
		for(i=0;i<m;i++) {
			this.x.set(i, this.add(this.x.get(i), x[i]));
		}
	return i;
}

@SuppressWarnings("unchecked")
public <U extends Number> int Translate (PointNd<U> p) {
	return Translate((T[]) p.x.toArray());
}

}
