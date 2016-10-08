package cmsc420.meeshquest.part1;
import java.awt.geom.Point2D;
import java.util.Comparator;
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

public class CoordComparator implements Comparator<Point2D.Float>{

	
		
		public int compare(Point2D.Float x1, Point2D.Float x2) {
			
			//Compare Y coordinates first
			// Coordinate with larger coordinate is ranked as larger
			
			if(x1.getY() > x2.getY()){
				return 1;
			}
			
			
			if(x1.getY() < x2.getY()){
				return -1;	
			}
			
			
			// If Y coords are equal compare based on Y coordinate
			if(x1.getY() == x2.getY()){
				
				if(x1.getX() > x2.getX()){
					return 1;
				}
				
				if(x1.getX() < x2.getX()){
					return -1;
				}
				
				if(x1.getX() == x2.getX()){
					return 0;
				}
			}
			return 0;
			
			
		}

	}

