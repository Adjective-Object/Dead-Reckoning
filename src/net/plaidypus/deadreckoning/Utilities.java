package net.plaidypus.deadreckoning;

import java.util.Random;

import net.plaidypus.deadreckoning.board.Tile;

public class Utilities {
	public static int width=800;
	public static int height=600;
	
	private static Random r = new Random();
	
	public static float fPart(float frac){
		return frac-(int)frac;
	}
	
	public static double getDistance(Tile a, Tile b){
		return Math.sqrt( Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2) );
	}
	
	public static int randInt (int lower, int higher){
		return r.nextInt(higher-lower)+lower;
	}
	
	public static int booleanPlusMin(boolean b){
		if(b){return 1;}
		return -1;
	}
	
	public static int limitTo(int toLimit, int lowerBound, int upperBound){
		if(toLimit<lowerBound){ return lowerBound; }
		if(toLimit>=upperBound){ return upperBound-1; }
		return toLimit;
	}
	
	public static float limitTo(float toLimit, float lowerBound, float upperBound){
		if(toLimit<lowerBound){ return lowerBound; }
		if(toLimit>upperBound){ return upperBound; }
		return toLimit;
	}
	
	public static int[] moveTowards(int x,int y,int TargetX,int TargetY, int movespeed){
		if(Math.sqrt(Math.pow(x-TargetX,2)+Math.pow(y-TargetY,2))<=movespeed){
			double[] v =rotate(movespeed,0,findAngle(x,y,TargetX,TargetY));
			
			//System.out.println(vx+"  "+vy);
			x+=v[0];
			y+=v[1];
		}
		else{
			x=TargetX;
			y=TargetY;
		}
		return new int[] {x,y};
	}
	
	public static double[] rotate (double x, double y,double angle){
		return new double[] {x*Math.cos(angle)-y*Math.sin(angle) , x*Math.sin(angle)+y*Math.cos(angle)};
	}
	
	//this is derptacular and doesn't really work, but it's ok for now
	//it's meant to find the angle in radians from the origin (x,y) to the destination(destx,desty)
	public static double findAngle(double x, double y, double destx, double desty){
		double anglepart= Math.atan( (y-desty)/(x-destx) );
		if(destx<x){
			anglepart+=Math.PI;
		}
		return anglepart;
	}

	public static float randFloat() {
		return r.nextFloat();
	}
	
	public static int roundUp(float target){
		if(fPart(target)>0){
			return (int)(target)+1;
		}
		return (int)(target);
	}
	
	public static int roundDown(float target){
		return (int)(target);
	}

	public static int roundUp(double d) {
		return roundUp((float)d);
	}
	
	public static int roundDown(double d){
		return roundDown((float)d);
	}
}
