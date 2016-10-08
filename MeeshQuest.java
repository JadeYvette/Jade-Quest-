package cmsc420.meeshquest.part1;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import cmsc420.drawing.CanvasPlus;
import cmsc420.meeshquest.part1.PRQuadTree.PRQuadNode;
import cmsc420.meeshquest.part1.PRQuadTree.PRQuadNode.Leaf;
import cmsc420.meeshquest.part1.PRQuadTree.PRQuadNode.Internal;
import cmsc420.xml.XmlUtility;
import java.lang.*;
import cmsc420.drawing.CanvasPlusDemo;

public class MeeshQuest {

	public static void main(String[] args) throws Exception {
  
		Document results = null;
		Document doc = null;
		
		TreeMap<String, City> nameMap = new TreeMap<String,City>(new NameComparator());
		TreeMap<Point2D.Float, City> coordMap = new TreeMap<Point2D.Float, City>(new CoordComparator());
		AVLTree avl = new AVLTree();

		try {
			doc = XmlUtility.validateNoNamespace(System.in);
			results = XmlUtility.getDocumentBuilder().newDocument();
		} catch (SAXException | IOException | ParserConfigurationException e){
			
			results = XmlUtility.getDocumentBuilder().newDocument();
			Element rootElement = results.createElement("fatalError");
			results.appendChild(rootElement);
			try {
				XmlUtility.print(results);
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}
			
			
			return;
		}
		

try{
		Element rootElement = results.createElement("results");
		results.appendChild(rootElement);
		
		Element commandNode = doc.getDocumentElement();
		final NodeList nl = commandNode.getChildNodes();
		int spatialWidth = 1024;
		int spatialHeight = 1024;
		
	    CanvasPlus canvas = new CanvasPlus("MeeshQuest", spatialWidth, spatialHeight);
		canvas.addRectangle(0, 0, spatialWidth, spatialHeight, Color.BLACK, false);
		PRQuadTree prQuadTree = new PRQuadTree( spatialHeight, canvas);
	    
	
		
		for(int i = 0; i < nl.getLength(); ++i) {
			Node commands = nl.item(i); 
			
			
			if(commandNode.getAttribute("spatialWidth").length() != 0 && commandNode.getAttribute("spatialHeight").length() != 0 ){
				 
				 spatialWidth = Integer.parseInt(commandNode.getAttribute("spatialWidth"));
			     spatialHeight = Integer.parseInt(commandNode.getAttribute("spatialHeight"));
			     canvas = new CanvasPlus("MeeshQuest", spatialWidth, spatialHeight);
			     canvas.addRectangle(0, 0, spatialWidth, spatialHeight, Color.BLACK, false);
			     prQuadTree = new PRQuadTree(spatialHeight, canvas);
			}


			
			
			
				
			if (nl.item(i).getNodeType() == Document.ELEMENT_NODE) {
    			commandNode = (Element) nl.item(i);
    			String nameIn = commandNode.getAttribute("name");
				String colorIn = commandNode.getAttribute("color");
				String saveMap = commandNode.getAttribute("saveMap");
				int xIn = -1;
				int yIn = -1;
				int rIn = -1;
				
				
				
				if(commandNode.getAttribute("x").length() != 0){
					 xIn = Integer.parseInt(commandNode.getAttribute("x"));
				}
				
				if(commandNode.getAttribute("y").length() != 0){
					 yIn = Integer.parseInt(commandNode.getAttribute("y"));
				}
				
				
				if(commandNode.getAttribute("radius").length() != 0){
				    rIn = Integer.parseInt(commandNode.getAttribute("radius"));
				}
				 
				
				City cityIn= new City (nameIn,colorIn,xIn,yIn,rIn) ;
				
				Float currPoint = new Point2D.Float((float) cityIn.x, (float) cityIn.y);
    			

				if(commands.getNodeName().equals("createCity")){
				

					if(nameMap.containsKey(nameIn)){

						String [] xmlElementsIn = new String[8];
			            xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] = "x";
					    xmlElementsIn[4] = "y";
					    xmlElementsIn[5] ="name";
						xmlElementsIn[6] = "radius";
						xmlElementsIn[7] = "color";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "Create City");
						XMLBuilder.setElement(results,xmlElements, cityIn,"duplicateCityName");
						XMLBuilder.appendElement(results,xmlElements, "duplicateCityName", rootElement);	
						
						
					
					}
					
				   if(coordMap.containsKey(currPoint)){
					    String [] xmlElementsIn = new String[8];
					    xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] = "x";
					    xmlElementsIn[4] = "y";
					    xmlElementsIn[5] ="name";
						xmlElementsIn[6] = "radius";
						xmlElementsIn[7] = "color";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "duplicateCityCoordinates");
						XMLBuilder.setElement(results,xmlElements, cityIn,"duplicateCityCoordinates");
						XMLBuilder.appendElement(results,xmlElements, "duplicateCityCoordinates", rootElement);
					   
						;
					}
				   
				   
					
		      if( nameMap.containsKey(nameIn) == false){  
		    		 if( coordMap.containsKey(currPoint) == false){
						    
						    coordMap.put(currPoint,cityIn);
							nameMap.put(nameIn, cityIn);
							
							String [] xmlElementsIn = new String[9];
				            xmlElementsIn[0] = "x";
							xmlElementsIn[1] = "y";
							xmlElementsIn[2] ="name";
							xmlElementsIn[3] = "radius";
							xmlElementsIn[4] = "color";
							xmlElementsIn[5] = "success";
							xmlElementsIn[6] = "command";
							xmlElementsIn[7] = "parameters";
							xmlElementsIn[8] = "output";
							
						  
					ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "createCity");
					XMLBuilder.setElement(results,xmlElements, cityIn,"createCity");
					XMLBuilder.appendElement(results,xmlElements, "createCity", rootElement);	
						}  
				    }
				}	
				
				if(commands.getNodeName().equals("deleteCity")){
					 int process = 0;
			
					if(nameMap.containsKey(nameIn)){
						if(prQuadTree.keys.contains(nameIn)== true){
						  
						 prQuadTree.keys.remove(nameIn);
						 canvas = new CanvasPlus("MeeshQuest", spatialWidth, spatialHeight);
						 canvas.addRectangle(0, 0, spatialWidth, spatialHeight, Color.BLACK, false);
						 prQuadTree = prQuadTree.delete(cityIn, prQuadTree.root,nameMap,canvas,spatialHeight);
							
						 
						 City cityInnew = nameMap.get(nameIn);
						 Float currnew = new Point2D.Float((float) cityInnew.x, (float) cityInnew.y);
						 Element cityUnmapped = results.createElement("cityUnmapped");
						 City unMapped = nameMap.get(nameIn);
						 nameMap.remove(nameIn);
						 coordMap.remove(currnew);
						 
						 
						 cityUnmapped.setAttribute("color",unMapped.color );
						 cityUnmapped.setAttribute("name",unMapped.getName() );
						 cityUnmapped.setAttribute("radius",String.valueOf((int)unMapped.radius ));
						 cityUnmapped.setAttribute("x",String.valueOf((int)unMapped.x));
						 cityUnmapped.setAttribute("y",String.valueOf((int)unMapped.y));
						 
						   
							
							String [] xmlElementsIn = new String[5];
						    xmlElementsIn[0] = "success";
							xmlElementsIn[1] = "command";
							xmlElementsIn[2] = "parameters";
						    xmlElementsIn[3] ="name";
							xmlElementsIn[4] = "output";
							ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "deleteCity");
							XMLBuilder.setElement(results,xmlElements, cityIn,"deleteCity");
							
							xmlElements.get(4).appendChild(cityUnmapped);
							XMLBuilder.appendElement(results,xmlElements, "deleteCity", rootElement);	
							process = process +1;
	//					 
							
						}else{
						
						 City cityInnew = nameMap.get(nameIn);
						Float currnew = new Point2D.Float((float) cityInnew.x, (float) cityInnew.y);
						nameMap.remove(nameIn);
						coordMap.remove(currnew);
						String [] xmlElementsIn = new String[5];
					    xmlElementsIn[0] = "success";
						xmlElementsIn[1] = "command";
						xmlElementsIn[2] = "parameters";
					    xmlElementsIn[3] ="name";
						xmlElementsIn[4] = "output";
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "deleteCity");
						XMLBuilder.setElement(results,xmlElements, cityIn,"deleteCity");
						XMLBuilder.appendElement(results,xmlElements, "deleteCity", rootElement);	
						process = process +1;
						}	
					}
					
					if(nameMap.containsKey(nameIn) == false && process == 0){ 
					    String [] xmlElementsIn = new String[4];
					    xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] ="name";
						
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "cityDoesNotExist");
						XMLBuilder.setElement(results,xmlElements, cityIn,"cityDoesNotExist");
						XMLBuilder.appendElement(results,xmlElements, "cityDoesNotExist", rootElement);
						
					}
				}
				
				 if(commands.getNodeName().equals("clearAll")){
					nameMap = new TreeMap<String, City>(new NameComparator());
					coordMap = new TreeMap<Float, City>(new CoordComparator());
					prQuadTree = new PRQuadTree(spatialWidth, canvas);
					avl = new AVLTree();

					   String [] xmlElementsIn = new String[4];
					    xmlElementsIn[0] = "success";
						xmlElementsIn[1] = "command";
						xmlElementsIn[2] ="parameters";
						xmlElementsIn[3] ="output";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "clearAll");
						XMLBuilder.setElement(results,xmlElements, cityIn,"clearAll");
						XMLBuilder.appendElement(results,xmlElements, "clearAll", rootElement);		

				}
				
			 if(commands.getNodeName().equals("listCities")){
					if(nameMap.size() == 0 || coordMap.size() == 0){ 						
					    String [] xmlElementsIn = new String[4];
					    xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] ="sortBy";
						
						
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "noCitiesToList");
						XMLBuilder.setElement(results,xmlElements, cityIn,"noCitiesToList");

						if(commandNode.getAttribute("sortBy").equals("name")){
							xmlElements.get(3).setAttribute("value", "name");
						}else{
							xmlElements.get(3).setAttribute("value", "coordinate");
						}
						XMLBuilder.appendElement(results,xmlElements, "noCitiesToList", rootElement);
					
					}
					
					if(nameMap.size() != 0 && coordMap.size() != 0){

						Element cityList = results.createElement("cityList");
					
						String [] xmlElementsIn = new String[7];
						xmlElementsIn[0] = "success";
						xmlElementsIn[1] = "command";
						xmlElementsIn[2] = "parameters";
						xmlElementsIn[3] = "sortBy";
						xmlElementsIn[4] = "output";
						xmlElementsIn[5] = "cityList";
						xmlElementsIn[6] = "city";
						
					  
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "createCity");

						if(commandNode.getAttribute("sortBy").equals("name")){
						
							Collection<City> c = nameMap.values();
						    Iterator<City> newIterator = c.iterator();

							
                            XMLBuilder.setElement(results,xmlElements, cityIn,"listCities");
							while(newIterator.hasNext()){
								
					
							City currentCity = newIterator.next();
								
							XMLBuilder.setElement(results,xmlElements, currentCity,"listCities1");
							XMLBuilder.appendElement(results,xmlElements, "listCities", cityList);

							xmlElements.get(5).appendChild(xmlElements.get(6));
							xmlElements.set(6,results.createElement("city")); 
								
								
							}
						}
						
						if(commandNode.getAttribute("sortBy").equals("coordinate")){ //sortBy equals coordinate
							Collection<City> c = coordMap.values();
							Iterator<City> itr = c.iterator();

							
							
							XMLBuilder.setElement(results,xmlElements, cityIn,"listCities2");
							while(itr.hasNext()){
								
								City itrTemp = itr.next();
								XMLBuilder.setElement(results,xmlElements, itrTemp,"listCities1");
								XMLBuilder.appendElement(results,xmlElements, "listCities2", cityList);

								xmlElements.get(5).appendChild(xmlElements.get(6));
								xmlElements.set(6,results.createElement("city")); 
									
								
							}
						}

                        
	
						XMLBuilder.appendElement(results,xmlElements, "listCities", rootElement);
					}

				}
			
				if(commands.getNodeName().equals("mapCity")){
					City tempCity = nameMap.get(commandNode.getAttribute("name"));
					boolean errorThrown = false;
					
					
					
                   if((nameMap.containsKey(commandNode.getAttribute("name"))) == false){
						
					    String [] xmlElementsIn = new String[4];
					    xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] ="name";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "nameNotInDictionary");
						XMLBuilder.setElement(results,xmlElements, cityIn,"nameNotInDictionary");
						XMLBuilder.appendElement(results,xmlElements, "nameNotInDictionary", rootElement);
						
						
						errorThrown = true;
					}
					
					
					
                   if((nameMap.containsKey(commandNode.getAttribute("name"))) == true){
					
					 if(prQuadTree.keys.contains(nameIn) == true){
						     String [] xmlElementsIn = new String[4];
						    xmlElementsIn[0] = "error";
							xmlElementsIn[1] = "parameters";
							xmlElementsIn[2] ="command";
							xmlElementsIn[3] ="name";
							
							ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "cityAlreadyMapped");
							XMLBuilder.setElement(results,xmlElements, cityIn,"cityAlreadyMapped");
							XMLBuilder.appendElement(results,xmlElements, "cityAlreadyMapped", rootElement);
							errorThrown = true;
						}
                   }	
					
	              
	 if(nameMap.containsKey(commandNode.getAttribute("name")) == true){
		 
		 if((tempCity.x < 0) || (tempCity.x >= spatialHeight) || ( tempCity.y < 0 ) || (tempCity.y >= spatialWidth)){
	            String [] xmlElementsIn = new String[4];
			    xmlElementsIn[0] = "error";
				xmlElementsIn[1] = "parameters";
				xmlElementsIn[2] ="command";
				xmlElementsIn[3] ="name";
				
				ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "cityOutOfBounds");
				XMLBuilder.setElement(results,xmlElements, cityIn,"cityOutOfBounds");
				XMLBuilder.appendElement(results,xmlElements, "cityOutOfBounds", rootElement);	
				errorThrown = true;
					}
		 }
	        
	        
	        
	        
	        if(errorThrown == false){
						City cityIn1 = nameMap.get(commandNode.getAttribute("name"));
						prQuadTree.keys.add(cityIn1.getName());
						prQuadTree.insert(cityIn1,prQuadTree.root,0);
						avl.insert(cityIn1.getName());
						canvas.draw();
					    String [] xmlElementsIn = new String[5];
						xmlElementsIn[0] = "success";
						xmlElementsIn[1] = "command";
						xmlElementsIn[2] = "parameters";
						xmlElementsIn[3] = "name";
						xmlElementsIn[4] = "output";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "mapCity");
						XMLBuilder.setElement(results,xmlElements, cityIn,"mapCity");
						XMLBuilder.appendElement(results,xmlElements, "mapCity", rootElement);	
						
						
						
					}
	        
	        
	        
				}
				
				
				
				
				
				if(commands.getNodeName().equals("unmapCity")){
					 boolean errorThrown = false;
					
					
                       if((nameMap.containsKey(commandNode.getAttribute("name"))) == false){
						
					    String [] xmlElementsIn = new String[4];
					    xmlElementsIn[0] = "error";
						xmlElementsIn[1] = "parameters";
						xmlElementsIn[2] ="command";
						xmlElementsIn[3] ="name";
						
						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "nameNotInDictionary");
						XMLBuilder.setElement(results,xmlElements, cityIn,"nameNotInDictionary1");
						XMLBuilder.appendElement(results,xmlElements, "nameNotInDictionary", rootElement);
						
						
						errorThrown = true;
					}
					
                    if((nameMap.containsKey(commandNode.getAttribute("name"))) == true){
                    	
                    	
                    	if(prQuadTree.keys.contains(nameIn) == false){
                        	
                       	 String [] xmlElementsIn = new String[4];
    					    xmlElementsIn[0] = "error";
    						xmlElementsIn[1] = "parameters";
    						xmlElementsIn[2] ="command";
    						xmlElementsIn[3] ="name";
    						
    						ArrayList<Element> xmlElements = XMLBuilder.createElement(results, xmlElementsIn, "cityNotMapped");
    						XMLBuilder.setElement(results,xmlElements, cityIn,"cityNotMapped");
    						XMLBuilder.appendElement(results,xmlElements, "cityNotMapped", rootElement);
                       	 
                       	
                       	 
                       	 
                       	 errorThrown = true;
                        }
                    	
                    }
                       
                     
                       
                     
                     
                     if(errorThrown == false){
                    	 
                    		City cityIn1 = nameMap.get(commandNode.getAttribute("name"));
                    		prQuadTree.keys.remove(nameIn);
                    		canvas = new CanvasPlus("MeeshQuest", spatialWidth, spatialHeight);
                    		canvas.addRectangle(0, 0, spatialWidth, spatialHeight, Color.BLACK, false);
                    		prQuadTree = prQuadTree.delete(cityIn1,prQuadTree.root,nameMap,canvas,spatialHeight);
                    		
    						
    						Element success = results.createElement("success");
    						Element command = results.createElement("command");
    						Element parameters = results.createElement("parameters");
    						Element name = results.createElement("name");
    						Element output = results.createElement("output");
    						
    						command.setAttribute("name", "unmapCity");
    						name.setAttribute("value", cityIn1.getName());
    						
    						rootElement.appendChild(success);
    						success.appendChild(command);
    						success.appendChild(parameters);
    						parameters.appendChild(name);
    						success.appendChild(output);
                    	 
                    	 
                    	 
                     }
                       

				}
				
				 if(commands.getNodeName().equals("printPRQuadtree")){
					
					if(prQuadTree.keys.isEmpty() == false){
					 
					Element success = results.createElement("success"); 
					Element command = results.createElement("command");
					Element parameters = results.createElement("parameters");
					Element output = results.createElement("output"); 
					Element quadtree = results.createElement("quadtree");
					
					
					printPRQuadTree(prQuadTree.root,quadtree,results,nameMap);
					
					rootElement.appendChild(success);
					command.setAttribute("name","printPRQuadtree");
					success.appendChild(command);
					success.appendChild(parameters);
					success.appendChild(output);
					output.appendChild(quadtree);
				    
					}else{
						
						Element error = results.createElement("error"); 
						Element command = results.createElement("command");
						Element parameters = results.createElement("parameters");
						Element output = results.createElement("output"); 
						
						rootElement.appendChild(error);
						error.setAttribute("type", "mapIsEmpty");
						command.setAttribute("name","printPRQuadtree");
						error.appendChild(command);
						error.appendChild(parameters);
						
						
						
					}
					
					
				
					
					
				
				 }
				 
				 
				 
				 
				 
				
				 if(commands.getNodeName().equals("saveMap")){

					 
					 canvas.save(cityIn.getName());
					 Element success = results.createElement("success");
					 Element command =results.createElement("command");
					 Element parameters = results.createElement("parameters");
					 Element output = results.createElement("output");
					 Element  name = results.createElement("name");
					 
					 command.setAttribute("name", "saveMap");
					 name.setAttribute("value", cityIn.getName());
					 
					 rootElement.appendChild(success);
					 success.appendChild(command);
					 parameters.appendChild(name);
					 success.appendChild(parameters);
					 success.appendChild(output);
					
					 
					 
					 
				}
				
				 if(commands.getNodeName().equals("rangeCities")){
					 
					 Element success = results.createElement("success");
					 Element command =results.createElement("command");
					 Element parameters = results.createElement("parameters");
					 Element output = results.createElement("output");
					
					 Element x = results.createElement("x");
					 Element y = results.createElement("y");
					 Element radius = results.createElement("radius");
					 Element cityList = results.createElement("cityList");
					 Element saveMapin = results.createElement("saveMap");
						
					 
					 command.setAttribute("name", "rangeCities");
					 x.setAttribute("value",Integer.toString((int)cityIn.x));	 
					 y.setAttribute("value",Integer.toString((int)cityIn.y)); 
					 radius.setAttribute("value",Integer.toString((int)cityIn.radius));
					 parameters.appendChild(x);
					 parameters.appendChild(y);
					 parameters.appendChild(radius);
					 
					 
					 
					    Collection<String> rCities = prQuadTree.keys;
						Iterator<String> rangeCity = rCities.iterator();
						
						TreeMap<String, City> rangeCities = new TreeMap<String,City>(new NameComparator());
						
						while(rangeCity.hasNext()){
							
							String tempname = rangeCity.next();
							City tempCity = nameMap.get(tempname);
							
								
							if( Point2D.distance(tempCity.x, tempCity.y, cityIn.x, cityIn.y) <= cityIn.radius){
								
								  rangeCities.put(tempCity.getName(),tempCity);
							 }
							
						
					    }
					 
		
						 
							
					 
					
					
		   if(rangeCities.isEmpty() == false){
					
			    Collection<City> sortCities = rangeCities.values();
				Iterator<City> srangeCity = sortCities.iterator();
				
				
			     	while(srangeCity.hasNext()){
			     		City tempCity1 = srangeCity.next();
  
						Element cityCurr = results.createElement("city");
						
						
						cityCurr.setAttribute("x", String.valueOf((int)tempCity1.x));
						cityCurr.setAttribute("y", String.valueOf((int)tempCity1.y));
						cityCurr.setAttribute("name", tempCity1.getName());
						cityCurr.setAttribute("color", tempCity1.color);
						cityCurr.setAttribute("radius",String.valueOf((int)tempCity1.radius));
							
						cityList.appendChild(cityCurr);
					}
					
					 rootElement.appendChild(success);
					 output.appendChild(cityList);
					 success.appendChild(command);
					 success.appendChild(parameters);
					 success.appendChild(output);
		          } 
		  
		   
		   if(rangeCities.isEmpty()){
			   
			Element error = results.createElement("error");
			error.setAttribute("type", "noCitiesExistInRange");
			  error.appendChild(command);
			  error.appendChild(parameters);
			  rootElement.appendChild(error);
			   
		   }
		   
		   
		   
		   if(saveMap.isEmpty() == false){
			
			 saveMapin.setAttribute("value", saveMap);
			 parameters.appendChild(saveMapin);
			 Float currPoint1 = new Point2D.Float((float) cityIn.x, (float) cityIn.y);
			 canvas.addCircle(currPoint1.getX(),currPoint1.getY(), (float)cityIn.radius, Color.blue, false);
			 canvas.save(saveMap);   
			   
		   }
		   
		   
		   
		   
				}
				
				 if(commands.getNodeName().equals("nearestCity")){
					
					 
				
					 
					 
					 	Element command =results.createElement("command");
					 	Element parameters = results.createElement("parameters");
					 	Element success = results.createElement("success");
					 	Element output = results.createElement("output");
						Element x = results.createElement("x");
					 	Element y = results.createElement("y");
					 	Element city = results.createElement("city");
					 	Element error = results.createElement("error");
					 			
					 	
					 	
					 	
					 	
					 	command.setAttribute("name", "nearestCity");
					 	x.setAttribute("value", String.valueOf((int)cityIn.x));
					 	y.setAttribute("value",String.valueOf((int)cityIn.y));
					 
					 
					if(prQuadTree.keys.isEmpty() == false) {
					 	 double shortest = spatialHeight;
					 	 City closestCity = null;
					 	  for( String closeCities : prQuadTree.keys){
					 		  
					 		City  cityC = nameMap.get(closeCities);
					 		  
					 		  
					 		  if(Point2D.distance(cityC.x, cityC.y, cityIn.x, cityIn.y) < shortest){
					 			  
					 			  shortest = Point2D.distance(cityC.x, cityC.y, cityIn.x, cityIn.y);
					 			  closestCity = cityC;
					 			  
					 			  
					 		  }else{
					 		  
					 		 
	                          if(Point2D.distance(cityC.x, cityC.y, cityIn.x, cityIn.y) == shortest){
					 			  
	                        	  
	                        	  if(cityC.getName().compareTo(closestCity.getName()) > 0){
	                        	  
					 			     shortest = Point2D.distance(cityC.x, cityC.y, cityIn.x, cityIn.y);
					 			     closestCity = cityC;
	                        	  }
	                        	  
	                          }	  
					 			  
					 			  
					 		  }
					 		  
					 		  
					 		  
					 	  }
					 	
					 	
					 	
					 	city.setAttribute("name", closestCity.getName());
					 	city.setAttribute("x", String.valueOf((int)closestCity.x));
					 	city.setAttribute("y", String.valueOf((int)closestCity.y));
					 	city.setAttribute("color", closestCity.color);
					 	city.setAttribute("radius", String.valueOf((int)closestCity.radius));
					 	
					 	
					 	rootElement.appendChild(success);
					 	success.appendChild(command);
					 	success.appendChild(parameters);
						success.appendChild(output);
						parameters.appendChild(x);
						parameters.appendChild(y);
						output.appendChild(city);
					 	
					 	
					}else{
						
						error.setAttribute("type", "mapIsEmpty");
						error.appendChild(command);
						error.appendChild(parameters);
						parameters.appendChild(x);
						parameters.appendChild(y);
						rootElement.appendChild(error);
						
						
						
						
						
						
					}
					 
					 
					 
				}
				 
				 if(commands.getNodeName().equals("printAvlTree")){
					 
					 	Element avlT =results.createElement("AvlGTree");
					 	Element command =results.createElement("command");
					 	Element parameters = results.createElement("parameters");
					 	Element success = results.createElement("success");
					 	Element output = results.createElement("output");
					 	
					if(avl.size != 0 ){
					 	avl.printBalance();
					 	avlT.setAttribute("cardinality",Integer.toString(avl.size));
						avlT.setAttribute("height", Integer.toString((avl.height -1)));
						avlT.setAttribute("maxImbalance", "1");
						command.setAttribute("name", "printAvlTree");
						
							
						
						printAVLTree(avl.root,results,nameMap,avlT);
					
						
							rootElement.appendChild(success);
							success.appendChild(command);
							success.appendChild(parameters);
							success.appendChild(output);
							output.appendChild(avlT);
						
						}else{
							
						   Element error = results.createElement("error");
						   error.setAttribute("type", "emptyTree");
						   command.setAttribute("name", "printAvlTree");
							
						   error.appendChild(command);
						   error.appendChild(parameters);
						   rootElement.appendChild(error);
						}
					 	
						
					 
						
						
					}
				 
					 
				 
			  }
    		}
			
	}catch(Exception e){
		

		results = XmlUtility.getDocumentBuilder().newDocument();
		Element rootElement = results.createElement("undefinedError");
		results.appendChild(rootElement);
	
		try {
			XmlUtility.print(results);
		} catch (TransformerException e1) {
			e1.printStackTrace();
		}
	      return;
	} 

		
		
		try {
			XmlUtility.print(results);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		 
		
	}
	
	
	
	
	
	
	public static Element printPRQuadTree(PRQuadNode nodeIn,Element elementIn,Document results, TreeMap nameMap){

		//Key of element locations
		 //treeElements[0] = results.createElement("success"); 
		// treeElements[1] = results.createElement("command");
		// treeElements[2] = results.createElement("parameters");
		// treeElements[3] = results.createElement("output"); 
		// treeElements[4] = results.createElement("white");
		// treeElements[5] = results.createElement("black");
		// treeElements[6] = results.createElement("grey");
	 	
		if( nodeIn instanceof PRQuadNode.Internal ){
			PRQuadNode.Internal node1 = (Internal) nodeIn;
			

			
			
			Element grey = results.createElement("gray");
			grey.setAttribute("x",String.valueOf((int)node1.x));
        	grey.setAttribute("y",String.valueOf((int)node1.y));
        	
			printPRQuadTree(node1.NW,grey,results,nameMap);
			printPRQuadTree(node1.NE,grey,results,nameMap);
			printPRQuadTree(node1.SW,grey,results,nameMap);
			printPRQuadTree(node1.SE,grey,results,nameMap);
		    elementIn.appendChild(grey);	
		}
		
		
		if( nodeIn instanceof PRQuadNode.Leaf ){
			PRQuadNode.Leaf node2 = (Leaf) nodeIn;
		   if(node2.value != null){
			City cityFunction = node2.value;
			
			
			 Element black = results.createElement("black");
			 
				black.setAttribute("name",cityFunction.getName() );
				black.setAttribute("x",String.valueOf((int)cityFunction.x));
            	black.setAttribute("y",String.valueOf((int)cityFunction.y));
			    elementIn.appendChild(black);
		    }
		}
		
		
         if( nodeIn == null ){
        Element white = results.createElement("white");
			elementIn.appendChild(white);
		}
		
		
		
		
	
		return elementIn;
	
		
	}
	
	
	
	
	
	
	public static Element printAVLTree(AVLTree.Node rootIn,Document results,TreeMap nameMap, Element nodeIn ){
			
		
		
		 if(rootIn != null) {
			 City cityIn1 = (City) nameMap.get(rootIn.key);
			  Element node = results.createElement("node");
	        	node.setAttribute("name", rootIn.key);
	        	node.setAttribute("radius", String.valueOf((int)cityIn1.radius));
	           nodeIn.appendChild(printAVLTree(rootIn.left,results,nameMap,node)); 
	           nodeIn.appendChild(printAVLTree(rootIn.right,results,nameMap,node));
	        }else{
	        	
	        	 Element emptyChild = results.createElement("emptyChild");
	        	nodeIn.appendChild(emptyChild);
	        }
		
		return nodeIn;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}