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

public class XMLBuilder {

	
	public static ArrayList<Element> createElement(Document dIn,String[] info,String command){
		ArrayList<Element> elementList = new ArrayList<Element>();
		for (String tags: info){
			
		 elementList.add(dIn.createElement(tags));
		}
		return elementList;
	}
	
	
	public static void setElement(Document dIn,ArrayList<Element> info,City cityIn,String command ){		 
		if(command.equals("createCity")){
			
			for (Element tags: info){
				
				if(tags.getTagName() != "success" && tags.getTagName() != "output" &&
				   tags.getTagName() != "parameters"){
				
				if(tags.getTagName().equals("command")){
					tags.setAttribute("name",command);
					
				}else{
					
					if(tags.getTagName().equals("name")){
						tags.setAttribute("value",cityIn.getName());
					}
					
					if(tags.getTagName().equals("x")){
	                	tags.setAttribute("value",String.valueOf((int)cityIn.x));
						
					}
					
	                if(tags.getTagName().equals("y")){
	                	tags.setAttribute("value",String.valueOf((int)cityIn.y));
					}
	                
	                
	                if(tags.getTagName().equals("radius")){
	                	tags.setAttribute("value",Integer.toString(cityIn.radius));
					}
					
                    if(tags.getTagName().equals("color")){
                    	tags.setAttribute("value",cityIn.color);
					}
					
					
					
	                
					
					
				}
				
				
				
			}
				}
				
			}
			
		
		
		if(command.equals("deleteCity")){
			
			for (Element tags: info){
				
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", command);
			}
			
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			}
			
		}
		
		
		
     if(command.equals("listCities")){
			
			for (Element tags: info){
				
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", command);
			}
			
			
			if(tags.getTagName().equals("sortBy")){
				tags.setAttribute("value", "name");
			}
			
			}
		}
     
     
     if(command.equals("listCities2")){
			
			for (Element tags: info){
				
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name","listCities");
			}
			
			
			if(tags.getTagName().equals("sortBy")){
				tags.setAttribute("value", "coordinate");
			}
			
			}
		}
		
		
     if(command.equals("listCities1")){
			
  		for (Element tags: info){
  			
  		if(tags.getTagName().equals("city")){
 	
     			tags.setAttribute("name",cityIn.getName());
            	tags.setAttribute("x",String.valueOf((int)cityIn.x));
            	tags.setAttribute("y",String.valueOf((int)cityIn.y));
            	tags.setAttribute("radius",Integer.toString(cityIn.radius));
            	tags.setAttribute("color",cityIn.color);			
  		}
  			
  			
  		}
		
     }	
		
		
		
		
       if(command.equals("duplicateCityName")){
		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "duplicateCityName");
			}
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "createCity");
			}
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			if(tags.getTagName().equals("x")){
				tags.setAttribute("value",String.valueOf((int)cityIn.x));
            	
			}
			
			if(tags.getTagName().equals("y")){
				tags.setAttribute("value",String.valueOf((int)cityIn.y));
            	
			}
			
			if(tags.getTagName().equals("radius")){
				tags.setAttribute("value",Integer.toString(cityIn.radius));
			}
			
			if(tags.getTagName().equals("color")){
				tags.setAttribute("value",cityIn.color);
			}
			
			}
			
		}
       
       
       if(command.equals("rangeCities")){
     		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "rangeCities");
			}
			
			if(tags.getTagName().equals("x")){
				tags.setAttribute("value", String.valueOf((int)cityIn.x));
			}
			
			if(tags.getTagName().equals("y")){
				tags.setAttribute("value", String.valueOf((int)cityIn.y));
			}
			
			if(tags.getTagName().equals("radius")){
				tags.setAttribute("value", String.valueOf((int)cityIn.radius));
			}
			
			
			}
			
       }
       
       
       
       
       
       
       if(command.equals("clearAll")){
    		
    	   
   			for (Element tags: info){
   				
   			if(tags.getTagName().equals("command")){
   				tags.setAttribute("name", "clearAll");
   			}
   			
   			
   			}
   			
          }
       
       
       
       if(command.equals("duplicateCityCoordinates")){
   		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "duplicateCityCoordinates");
			}
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "createCity");
			}
			
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			if(tags.getTagName().equals("x")){
				tags.setAttribute("value",String.valueOf((int)cityIn.x));
           	
			}
			
			if(tags.getTagName().equals("y")){
				tags.setAttribute("value",String.valueOf((int)cityIn.y));
           	
			}
			
			if(tags.getTagName().equals("radius")){
				tags.setAttribute("value",Integer.toString(cityIn.radius));
			}
			
			
			if(tags.getTagName().equals("color")){
				tags.setAttribute("value",cityIn.color);
			}
			}
			
		}
       
       if(command.equals("cityDoesNotExist")){
   		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "cityDoesNotExist");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "deleteCity");
			}
			
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			
			}
			
		}
       

       
       
       if(command.equals("noCitiesToList")){
      		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "noCitiesToList");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "listCities");
			}
			
			}
			
		}
       
       
       
       if(command.equals("cityNotMapped")){
     		
    	   
    			for (Element tags: info){
    				
    			if(tags.getTagName().equals("error")){
    				tags.setAttribute("type", "cityNotMapped");
    			}
    			
    			
    			if(tags.getTagName().equals("command")){
    				tags.setAttribute("name", "mapCity");
    			}
    			
    			if(tags.getTagName().equals("name")){
    				tags.setAttribute("value", cityIn.getName());
    			}
    			
    			}
    			
    		}
       
       
       
       if(command.equals("nameNotInDictionary")){
			
    	   for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "nameNotInDictionary");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "mapCity");
			}
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			
    	   } 
			
			
		}
       
    
      
       
       if(command.equals("cityOutOfBounds")){
   		
    	   for (Element tags: info){
				
   			if(tags.getTagName().equals("error")){
   				tags.setAttribute("type", "cityOutOfBounds");
   			}
   			
   			
   			if(tags.getTagName().equals("command")){
   				tags.setAttribute("name", "mapCity");
   			}
   			
   			if(tags.getTagName().equals("name")){
   				tags.setAttribute("value", cityIn.getName());
   			}
   			
   			
       	   } 
   			
			
			
		}
       
       
       
       if(command.equals("cityNotMapped")){
      		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "cityNotMapped");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "unmapCity");
			}
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			}
			
			
		}
       
       
       if(command.equals("cityAlreadyMapped")){
     		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "cityAlreadyMapped");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "mapCity");
			}
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			}
			
		}
       
       
       
       
       
       
       
       
       
       if(command.equals("nameNotInDictionary1")){
     		
    	   
			for (Element tags: info){
				
			if(tags.getTagName().equals("error")){
				tags.setAttribute("type", "nameNotInDictionary");
			}
			
			
			if(tags.getTagName().equals("command")){
				tags.setAttribute("name", "unmapCity");
			}
			
			if(tags.getTagName().equals("name")){
				tags.setAttribute("value", cityIn.getName());
			}
			
			}
			
		}
       
       
       
       
       
       
  		
       if(command.equals("mapCity")){
     		

    	   
    			for (Element tags: info){
    				
    			if(tags.getTagName().equals("command")){
    				tags.setAttribute("name", "mapCity");
    			}
    			
    			
    			if(tags.getTagName().equals("name")){
    				tags.setAttribute("value", cityIn.getName());
    			}
    			
    			}
    			
    		}
	
      }
       
       
			
	
		
	
   public static void appendElement(Document dIn,ArrayList<Element> info,String commandIn, Element rootIn){
	    // [0] = "x";
		//[1] = "y";
		//[2] ="name";
		//[3] = "radius";
		//[4] = "color";
		//[5] = "success";
		//[6] = "command";
		//[7] = "parameters";
		//[8] = "output";
	  if(commandIn.equals("createCity")){
		rootIn.appendChild(info.get(5));
		
		//info[5] holds the success element
		info.get(5).appendChild(info.get(6));
		info.get(5).appendChild(info.get(7));
		info.get(7).appendChild(info.get(2));
		info.get(7).appendChild(info.get(0));
		info.get(7).appendChild(info.get(1));
		info.get(7).appendChild(info.get(3));
		info.get(7).appendChild(info.get(4));
		info.get(5).appendChild(info.get(8));
	   
	  }
	    //xmlElementsIn[0] = "success";
		//xmlElementsIn[1] = "command";
		//xmlElementsIn[2] = "parameters";
	    // xmlElementsIn[3] ="name";
		//xmlElementsIn[4] = "output";
	  
	  
	  
	  if(commandIn.equals("deleteCity")){
		  rootIn.appendChild(info.get(0));
		
		info.get(0).appendChild(info.get(1));
		info.get(0).appendChild(info.get(2));
		info.get(2).appendChild(info.get(3));
		info.get(0).appendChild(info.get(4));
		
	   
	  }
	    //xmlElementsIn[0] = "success";
		//xmlElementsIn[1] = "command";
		//xmlElementsIn[2] ="parameters";
		//xmlElementsIn[3] ="output";
	  
	  if(commandIn.equals("clearAll")){
		rootIn.appendChild(info.get(0));
		
		
		info.get(0).appendChild(info.get(1));
		info.get(0).appendChild(info.get(2));
		info.get(0).appendChild(info.get(3));
		
		
	  
	   
	  }
	  
	    //xmlElementsIn[0] = "success";
		//xmlElementsIn[1] = "command";
		//xmlElementsIn[2] = "parameters";
		//xmlElementsIn[3] = "sortBy";
		//xmlElementsIn[4] = "output";
		//xmlElementsIn[5] = "cityList";
		//xmlElementsIn[6] = "city";
		
	  
	  
	  if(commandIn.equals("listCities")){
			rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(1));
			info.get(0).appendChild(info.get(2));
			info.get(2).appendChild(info.get(3));
			info.get(0).appendChild(info.get(4));
			info.get(4).appendChild(info.get(5));
			
			
		
		  }
	  
	  
	  
		//xmlElementsIn[0] = "success";
		//xmlElementsIn[1] = "command";
		//xmlElementsIn[2] = "parameters";
		//xmlElementsIn[3] = "name";
		//xmlElementsIn[4] = "output";
	  
	  
		  if(commandIn.equals("mapCity")){
				rootIn.appendChild(info.get(0));
				info.get(0).appendChild(info.get(1));
				info.get(0).appendChild(info.get(2));
				info.get(2).appendChild(info.get(3));
				info.get(0).appendChild(info.get(4));
				
				
				
			
			  }
	  
	  
	  
	  
	  
	  
	  
	  
	    //xmlElementsIn[0] = "error";
		//xmlElementsIn[1] = "parameter";
		//xmlElementsIn[2] ="command";
		//xmlElementsIn[3] = "x";
	   // xmlElementsIn[4] = "y";
	   // xmlElementsIn[5] ="name";
		//xmlElementsIn[6] = "radius";
		//xmlElementsIn[7] = "color";
		
	  
	  if(commandIn.equals("duplicateCityName") || 
		 commandIn.equals("duplicateCityCoordinates")){
		  
			 rootIn.appendChild(info.get(0));
			

			info.get(1).appendChild(info.get(5));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(1).appendChild(info.get(4));
		
			info.get(1).appendChild(info.get(6));
			info.get(1).appendChild(info.get(7));
			
			
			info.get(0).appendChild(info.get(1));
			
		  
	  }
	  
	  
	  
	  if( commandIn.equals("cityDoesNotExist")){
		  rootIn.appendChild(info.get(0));
		  info.get(1).appendChild(info.get(3));
		  info.get(0).appendChild(info.get(2));
		  info.get(0).appendChild(info.get(1));
	  }
	  
	  
	   //xmlElementsIn[0] = "error";
	  //xmlElementsIn[1] = "parameter";
	 //xmlElementsIn[2] ="command";
	  
	  if( commandIn.equals("noCitiesToList")){
		    rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(0).appendChild(info.get(1));
	  }
	  
	  
	 // xmlElementsIn[0] = "error";
	//	xmlElementsIn[1] = "parameter";
	//	xmlElementsIn[2] ="command";
	//	xmlElementsIn[3] = "name";
	  
	  if(commandIn.equals("cityNotMapped")){
		    rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(0).appendChild(info.get(1));
	  }
	  
	  
	  if(commandIn.equals("nameNotInDictionary")){
		    rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(0).appendChild(info.get(1));
	  }
	  
	  
	
	  
	  if(commandIn.equals("cityAlreadyMapped")){
		    rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(0).appendChild(info.get(1));
	  }
	  
	  if(commandIn.equals("cityOutOfBounds")){
		    rootIn.appendChild(info.get(0));
			info.get(0).appendChild(info.get(2));
			info.get(1).appendChild(info.get(3));
			info.get(0).appendChild(info.get(1));
	  }
	  
	  
	  
	
	  
	  
	   
	   
	}


   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
	
}
