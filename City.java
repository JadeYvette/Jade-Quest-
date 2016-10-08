package cmsc420.meeshquest.part1;

import java.awt.geom.*;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import cmsc420.drawing.CanvasPlus;
import cmsc420.xml.XmlUtility;

public class City {
	

	private String name;
	String color;
	int radius;
    float x ; 
    float y;
    
    
	public City(String nameIn, String colorIn, int xCoord, int yCoord, int radiusIn){
		
		x = xCoord;
		y = yCoord;
		name = nameIn;
		color = colorIn;
		radius = radiusIn;
	
	}

	public String getName() {
		return name;
	}


	public Color colorConvert (String color){
		
		 return Color.getColor(color);
		
		
		
	}

	
	
}