import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Number;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;

public class PointNd <T extends Number> {
private T[] x;

private enum CoordSystem {
	CARTESIAN,
	CYLINDRICAL,
	SPHERICAL,
	CIRCULAR;
}

// Constructor

/*public PointNd() {	//Point 2d={0;0}
	this(2);
	N=2;
}

@SuppressWarnings("unchecked")
public PointNd(int N) {		//Point Nd={0;...;0} 
	//this.x= (T[]) new Number [N];
	this.x=(T[]) Array.newInstance(this.x.getClass(), x.length);
	for(int i=0;i<N;i++)
		this.x[i]= this.createZeroNumber(x); 
	this.N=N;
}*/

@SuppressWarnings({ "unchecked" })
public PointNd(T ...x) {		//Point Nd={x[0]; x[1]; ... ; x[n]}
	this.x= (T[]) new Number [x.length];

	for(int i=0;i<x.length;i++) {
		this.x[i]=x[i];
	}
}

public PointNd(PointNd<T> p) {
	this(p.x);
}

// Setter

public int SetPoint(int poz, T x) {
	
	try {
		this.x[poz]=x;
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
			this.x[i]=x[i];
		}
		
	catch (ArrayIndexOutOfBoundsException e){
		return -1;
	}
return x.length;
}

@SuppressWarnings({ "deprecation", "unchecked" })
public int SetN(int N) {
	T[] newx=(T[]) Array.newInstance(this.x[0].getClass(), N);
	int j=0;
	int i=this.x.length;
	System.arraycopy(this.x, 0, newx, 0, i);
	
	try
	{
	for(;i<N;i++, j++) {
		newx[i]=newx[i-1];
	
		if(newx[i] instanceof Long)
			newx[i]= (T) new Long(0);
		else 	if(newx[i] instanceof Integer)
			newx[i]= (T) new Integer(0);
		else 	if(newx[i] instanceof Short)
			newx[i]= (T) new Short((short)0);
		else 	if(newx[i] instanceof Byte)
			newx[i]= (T) new Byte((byte)0);
		else 	if(newx[i] instanceof Double)
			newx[i]= (T) new Double(0.0d);
		else 	if(newx[i] instanceof Float)
			newx[i]= (T) new Float(0.0f);
		
		else if (newx[i] instanceof BigInteger)
			newx[i]= (T)  BigInteger.ZERO;	
		
		else newx[i]= null;	
	}
	this.x=newx;
	}
	catch (IndexOutOfBoundsException e)
	{
		return -1;
	}
	return j;
}



//Getter
public T GetPoint(int i)
{
	return this.x[i];

}

public int GetN() {
	return this.x.length;
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

@SuppressWarnings("unchecked")
public <U extends Number> int Translate (U ...x) {
	int i;
	if(x.length<=this.x.length)
		for(i=0;i<x.length;i++) this.x[i]=this.add(this.x[i], x[i]);
	else
		for(i=0;i<this.x.length;i++) this.x[i]=this.add(this.x[i], x[i]);
	return i;
}

public <U extends Number> int Translate (PointNd<U> p) {
	return Translate(p.x);
}

}
