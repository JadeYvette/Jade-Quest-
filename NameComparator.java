package cmsc420.meeshquest.part1;
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

public class NameComparator implements Comparator<String> {

public int compare(String c1, String c2) {
			
	return c2.compareTo(c1);
	          
		}
	}

