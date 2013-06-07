package envJavaSpace;

import java.awt.Point;

import net.jini.core.entry.Entry;

public class Tuple implements Entry {
	public String str;
	public Point point;
	public Integer i;
	
	public Tuple(String str, Point point, Integer i){
		this.str = str;
		this.point = point;
		this.i = i;
	}
	
	public Tuple(){
	}
	
	public String toString(){
		return "Tuple: <"+str+", point("+point.x+","+point.y+"),"+i+">";
	}
	
	public String toProlog(){
		String pointStr = "null";
		if(point!=null) {
			pointStr = "point("+point.x+","+point.y+")";
		}
		return "tuple("+str+","+pointStr+","+i+").";
	}
}
