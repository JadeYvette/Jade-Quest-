package cmsc420.meeshquest.part1;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import cmsc420.drawing.CanvasPlus;
import cmsc420.xml.XmlUtility;
import java.lang.*;

//PRQuad Tree structure based 
//http://courses.cs.vt.edu/~cs3114/Summer10/Notes/T07.PRQuadTreeImplementation.pdf
// PRQUADTREE
public class PRQuadTree {
	
	
	
	static class PRQuadNode {
		
		
		static class Internal extends PRQuadNode{
			
			public int x;
			public int y;
		    public PRQuadNode NW;
		    public PRQuadNode NE;
		    public PRQuadNode SW;
		    public PRQuadNode SE;
		    
			
			
			public Internal(int x, int y){
				this.x = x;
				this.y = y;
		        NW = null;
		        NE = null;
		        SW = null;
		        SE = null;
			}
			
			
		}
		
		
		
		static class Leaf extends PRQuadNode{
			public City value;
			
			public Leaf(City cityIn){
				value = cityIn;
			}
			
			
		}
		
		
	}
	
	
	
ArrayList<String> keys;
 PRQuadNode root;
 CanvasPlus canvas;
 int spatialWidth;
 int spatialHeight;


 public PRQuadTree(int canvasLimit, CanvasPlus canvasIn) {
  root = null;
  spatialWidth = canvasLimit;
  spatialHeight = canvasLimit;
  canvas = canvasIn;
  keys = new ArrayList<String>();
 }
 


 private void drawLine(int height, int width) {
  canvas.addLine(width, 0, width, height*2, Color.BLACK);
  canvas.addLine(0, height, width*2, height, Color.BLACK);
 }


 public void drawPoint(City city) {
  canvas.addPoint(city.getName(), city.x, city.y, Color.BLACK);
 }
 
 
 
 
 
 
 
 
 
 //FOUND LEAF DIVISIONS BASED ON Game Development source
 public void foundLeaf(int width, int height, int level, int currX, int currY, int location) {
  level = level +2;
  if (location == 1) {


   canvas.addLine(currX - (new Double(spatialWidth / Math.pow(2, level))).intValue(),
    (currY + (new Double(spatialHeight / Math.pow(2, level ))).intValue()) - ( (new Double(spatialHeight / Math.pow(2, level ))).intValue()),
    currX - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY * 2,
    Color.BLACK);


   canvas.addLine(((currX - (new Double(spatialWidth / Math.pow(2, level))).intValue())) - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY +  (new Double(spatialHeight / Math.pow(2, level ))).intValue(), 
    currX,
    currY +  (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    Color.BLACK);



  }

  if (location == 2) {


   canvas.addLine(currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    (currY + (new Double(spatialHeight / Math.pow(2, level))).intValue()) - (new Double(spatialHeight / Math.pow(2, level))).intValue(),
    currX + (new Double(spatialWidth / Math.pow(2, level))).intValue(),
   ( currY + (new Double(spatialHeight / Math.pow(2, level ))).intValue()) + (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    Color.black);

   canvas.addLine((currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue()) - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY + (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    (currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue()) + (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY + (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    Color.black);



  }

  if (location == 3) {

   canvas.addLine(currX - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
   (currY - (new Double(spatialHeight / Math.pow(2, level ))).intValue()) - (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    currX - (new Double(spatialWidth / Math.pow(2, level))).intValue(),
    currY,
    Color.BLACK);



   canvas.addLine((currX  - (new Double(spatialWidth / Math.pow(2, level))).intValue()) - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
	currY - (new Double(spatialHeight / Math.pow(2, level ))).intValue(), 
    (currX - (new Double(spatialWidth / Math.pow(2, level ))).intValue()) + (new Double(spatialWidth / Math.pow(2, level))).intValue(),
    currY - (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    Color.BLACK);


  }

  if (location == 4) {


   canvas.addLine(currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    (currY - (new Double(spatialHeight / Math.pow(2, level))).intValue()) - (new Double(spatialHeight / Math.pow(2, level))).intValue(),
    currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY,
    Color.BLACK);


   canvas.addLine((currX + (new Double(spatialWidth / Math.pow(2, level))).intValue()) - (new Double(spatialWidth / Math.pow(2, level ))).intValue(),
    currY - (new Double(spatialHeight / Math.pow(2, level ))).intValue(),
    currX + (new Double(spatialWidth / Math.pow(2, level ))).intValue() + (new Double(spatialWidth / Math.pow(2, level))).intValue(),
    currY - (new Double(spatialHeight / Math.pow(2, level ))).intValue(), Color.BLACK);


  }




 }

//BASED ON SPLIT METHOD IN GAME DEVELOPMENT SOURCE 
 private int[] adjustNodes(int Location, int level, int x, int y) {
  level = level + 2;
  int[] adjustedCoords = new int[3];
  if (Location == 1) {
   adjustedCoords[0] = x - (new Double(spatialWidth / Math.pow(2, level))).intValue();
   adjustedCoords[1] = y + (new Double(spatialHeight / Math.pow(2, level))).intValue();

  }

  if (Location == 2) {
   adjustedCoords[0] = x + (new Double(spatialWidth / Math.pow(2, level ))).intValue();
   adjustedCoords[1] = y + (new Double(spatialHeight / Math.pow(2, level ))).intValue();
  }

  if (Location == 3) {
   adjustedCoords[0] = x - (new Double(spatialWidth / Math.pow(2, level ))).intValue();
   adjustedCoords[1] = y - (new Double(spatialHeight / Math.pow(2, level ))).intValue();
  }


  if (Location == 4) {
   adjustedCoords[0] = x + (new Double(spatialWidth / Math.pow(2, level ))).intValue();
   adjustedCoords[1] = y - (new Double(spatialHeight / Math.pow(2, level ))).intValue();
  }


  return adjustedCoords;

 }





 public City insert(City city, PRQuadNode InternalIn, int level) {
  int newHeight = spatialHeight / 2;
  int newWidth = spatialWidth / 2;
  
  if (InternalIn instanceof PRQuadNode.Internal) {
   PRQuadNode.Internal curr = (PRQuadNode.Internal) InternalIn;

   int tracker = 0;
   int currX = curr.x;
   int currY = curr.y;
   double cityX = city.x;
   double cityY = city.y;



   if (cityX < currX && cityY >= currY) {
    tracker = 1;

    if (curr.NW instanceof PRQuadNode.Leaf) {
     foundLeaf(spatialWidth, spatialHeight, level, currX, currY, 1);
    }



    if (tracker == 1) {

     if (curr.NW != null) {

      int[] adjustCoords = adjustNodes(1, level, currX, currY);
      level++;
      
      if (curr.NW instanceof PRQuadNode.Leaf) {
    	  
       PRQuadNode.Leaf NWLeaf = (PRQuadNode.Leaf) curr.NW;
       City NW = NWLeaf.value;
       curr.NW = new PRQuadNode.Internal(adjustCoords[0], adjustCoords[1]);

       insert(NW, curr.NW, level );
       insert(city, curr.NW, level);
       return city;
      }


      if (!(curr.NW instanceof PRQuadNode.Leaf)) {
       insert(city, curr.NW, level );
       return city;
      }
     }


     if (curr.NW == null) {
      curr.NW = new PRQuadNode.Leaf(city);
      drawPoint(city);
      return null;

     }



    }

   }


   if (cityX >= currX && cityY >= currY) {

    tracker = 2;

    if (curr.NE instanceof PRQuadNode.Leaf) {
     foundLeaf(spatialWidth, spatialHeight, level, currX, currY, 2);

    }

    if (tracker == 2) {

     if (curr.NE == null) {
      curr.NE = new PRQuadNode.Leaf(city);
      drawPoint(city);
      return null;

     }

     if (curr.NE != null) {
      int[] adjustCoords = adjustNodes(2, level, currX, currY);
      level++;
      if (curr.NE instanceof PRQuadNode.Leaf) {
       PRQuadNode.Leaf NELeaf = (PRQuadNode.Leaf) curr.NE;
       City NE = NELeaf.value;
       curr.NE = new PRQuadNode.Internal(adjustCoords[0], adjustCoords[1]);

       insert(NE, curr.NE, level);
       insert(city, curr.NE, level);
       return city;
      }

      if (!(curr.NE instanceof PRQuadNode.Leaf)) {
       insert(city, curr.NE, level);
       return city;
      }
     }
    }



   }





   if (cityX < currX && city.y < currY) {
    tracker = 3;

    if (curr.SW instanceof PRQuadNode.Leaf) {
     foundLeaf(spatialWidth, spatialHeight, level, currX, currY, 3);

    }

    if (tracker == 3) {

     if (curr.SW == null) {
      curr.SW = new PRQuadNode.Leaf(city);
      drawPoint(city);
      return null;

     }

     if (curr.SW != null) {
      int[] adjustCoords = adjustNodes(3, level, currX, currY);
      level++;
      if (curr.SW instanceof PRQuadNode.Leaf) {
    	
       PRQuadNode.Leaf SWLeaf = (PRQuadNode.Leaf) curr.SW;
       City SW = SWLeaf.value;
       curr.SW = new PRQuadNode.Internal(adjustCoords[0], adjustCoords[1]);

       insert(SW, curr.SW, level );
       insert(city, curr.SW, level);
       return city;
      }
      if (!(curr.SW instanceof PRQuadNode.Leaf)) {
        insert(city, curr.SW, level);
        return city;
      }
     }

    }




   }



   if (cityX >= currX && cityY < currY) {

    tracker = 4;

    if (curr.SE instanceof PRQuadNode.Leaf) {
     foundLeaf(spatialWidth, spatialHeight, level, currX, currY, 4);
    }


    if (tracker == 4) {

     if (curr.SE == null) {
      curr.SE = new PRQuadNode.Leaf(city);
      drawPoint(city);
      return null;



     }

     if (curr.SE != null) {
      int[] adjustCoords = adjustNodes(4, level, currX, currY);
      level++;
      if (curr.SE instanceof PRQuadNode.Leaf) {
       PRQuadNode.Leaf SELeaf = (PRQuadNode.Leaf) curr.SE;
       City SE =  SELeaf.value ;
       curr.SE = new PRQuadNode.Internal(adjustCoords[0], adjustCoords[1]);

       insert(SE, curr.SE, level );
       insert(city, curr.SE, level);
       return city;
      }

      if (!(curr.SE instanceof PRQuadNode.Leaf)) {
           insert(city, curr.SE, level );
           return city;
      }
     }

    }

   }

  }


  if (InternalIn instanceof PRQuadNode.Leaf) {


   PRQuadNode.Leaf processNode = (PRQuadNode.Leaf) InternalIn;
   City process = processNode.value;
   PRQuadNode.Internal newguide = new PRQuadNode.Internal(newHeight, newWidth);
   root = newguide;
   drawLine(newHeight, newWidth);



   insert(process, root, level);
   insert(city, root, level);
   
   return city;
  }


  if (InternalIn == null) {
   root = new PRQuadNode.Leaf(city);
   drawPoint(city);
  }



  return null;
 }



 PRQuadTree delete(City cityIn, PRQuadNode nodeIn,TreeMap<String,City> nameIn, CanvasPlus canvas, int spatialHeight) {
	PRQuadTree newTree = new PRQuadTree(spatialHeight, canvas);
	 for (String stillMapped: this.keys){
		 
		 City currCity = nameIn.get(stillMapped);
		 newTree.keys.add(stillMapped);
		 newTree.insert(currCity, newTree.root, 0);
	
	 }
	 
	return newTree;
	
 
 }



 
 
 private City find(City cityIn, PRQuadNode nodeIn) {
  if (nodeIn == null) {
   return null;
  }

  if (nodeIn instanceof PRQuadNode.Leaf) {

   PRQuadNode.Leaf newLeaf = (PRQuadNode.Leaf) nodeIn;
   City leafCity = newLeaf.value;

   if (leafCity.x== cityIn.x) {
    if (leafCity.y == cityIn.y) {
     return leafCity;
    }
   }

  }


  if (nodeIn instanceof PRQuadNode.Internal) {
   PRQuadNode.Internal currNode = ((PRQuadNode.Internal) nodeIn);
  

   if (cityIn.x < currNode.x && cityIn.y >= currNode.y) {
    return find(cityIn, currNode.NW);
   }


   if (cityIn.x < currNode.x && cityIn.y < currNode.x) {
    return find(cityIn, currNode.SW);
   }


   if (cityIn.x >= currNode.x && cityIn.y >= currNode.y) {
    return find(cityIn, currNode.NE);
   }

   if (cityIn.x >= currNode.x && cityIn.y < currNode.y) {
    return find(cityIn, currNode.SE);
   }



  }
  return null;
 }



 public  void drawMap(){
	 
	 if(this.root instanceof PRQuadNode.Internal){
		 int split = 0;
		 PRQuadNode.Internal currNode = ((PRQuadNode.Internal) root);
		if(currNode.NE != null){
			split++;
		}
		
		
		if(currNode.NW != null){
			split++;
		}
		
		if(currNode.SW != null){
			split++;
		}
		
		if(currNode.SE != null){
			split++;
		}
		 
		
		 if(split > 1){
		
		//canvas.addLine(horbound/2, 0, horbound/2, verbound, Color.BLACK);
		//canvas.addLine(0, verbound/2, horbound, verbound/2, Color.BLACK);
		 
		 
	    }
		
		
		 
	 }
	 
	 
	
	 
	 
	 
	 
		 
		 
	 }
	 
	 
	 
	 
	 
 }







