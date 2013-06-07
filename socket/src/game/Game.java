package game;
import java.sql.Time;

import tuplespace.Cell;

import com.javadocmd.simplelatlng.*;

import static java.lang.Math.*;

import java.util.Timer;
import java.util.TimerTask;

 
public final class Game {
	
	private static LatLng start = new LatLng(52.954441, -1.18994);
	private static double kmX = 0.500;
	private static double kmY = 0.500;
	
	private static int gridX = 20;
	private static int gridY = 20;
	
	private static LatLng[][] grid;
	private static double width;
	private static double height;
	
	private static double earthRadius = 6378;
	
	 
	public static void initiateGrid() {
		
		width = kmX / gridX;
		height = kmY / gridY;
		grid = new LatLng[gridX][gridY];
		grid[0][0] = start;
		double diagonal = sqrt(width*width + height*height);
		
		for (int i = 0; i < gridX; i++) {
			for (int j = 0; j < gridY; j++) {
				LatLng orig;
				if (i == 0 && j == 0) 
					continue;
				if (i == 0) {
					orig = new LatLng(grid[0][j-1].getLatitude(),start.getLongitude());
				}
				else if (j == 0) {
					orig = new LatLng(start.getLatitude(),grid[i-1][0].getLongitude());
				}
				else {
					orig = new LatLng(grid[i-1][j-1].getLatitude(),grid[i-1][j-1].getLongitude());
				}

				grid[i][j] = calcDerivedPos(orig, diagonal, 135);
				System.out.println(i + " " + j + " " + grid[i][j]);
			}
		}
	}
	
	public static LatLng calcDerivedPos(LatLng source, double range, double bearing)
	{
	    double latA = source.getLatitude() * (PI/180);
	    double lonA = source.getLongitude() * (PI/180);
	    double angularDistance = range / earthRadius;
	    double trueCourse = bearing * (PI/180);

	    double lat = Math.asin(
	        Math.sin(latA) * Math.cos(angularDistance) + 
	        Math.cos(latA) * Math.sin(angularDistance) * Math.cos(trueCourse));

	    double dlon = Math.atan2(
	        Math.sin(trueCourse) * Math.sin(angularDistance) * Math.cos(latA), 
	        Math.cos(angularDistance) - Math.sin(latA) * Math.sin(lat));

	    double lon = ((lonA + dlon + Math.PI) % (PI*2)) - Math.PI;

	    return new LatLng(
	        lat * (180 / PI), 
	        lon * (180 / PI));
	}

	
	public static Cell locationToGrid(LatLng loc){
		
		for (int i = 0; i < gridX; i++) {
			for (int j = 0; j < gridY; j++) {
				if (loc.getLatitude() <= grid[i][j].getLatitude() && loc.getLongitude() >= grid[i][j].getLongitude())
				{
					if (i+1 < gridX && j+1 < gridY) {
						if (loc.getLatitude() >= grid[i+1][j+1].getLatitude() && loc.getLongitude() <= grid[i+1][j+1].getLongitude()){
							return new Cell(i,j);
						}
					}
					else
						return new Cell(i,j);
					//TODO out of bounds
						
				}
			}
		}
		return null;
		
	}
	
	public static LatLng gridToLocation(Cell cell){
		
		double dx = width/2;
		double dy = height/2;
		double range = sqrt(dx*dx + dy*dy);
	
		return calcDerivedPos(grid[cell.x][cell.y], range, 135);
		
	}
	
	public static LatLng locToCentre(LatLng loc)
	{
		return gridToLocation(locationToGrid(loc));
	}
	
	

}
