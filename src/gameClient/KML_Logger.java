package gameClient;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import utils.Point3D;

public class KML_Logger {

	public void createIconStyle(String name,String adress) {
		String Icon="<Style id="+name+">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>"+adress+"</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>";
	}

	/**************************************************************************************************
	 * this method returns a string that represent the placemark to the KML file for a givin icon and 
	 * his location
	 * @param nameIcon
	 * @param Location
	 * @return string that represent the placemark to the KML file
	 *************************************************************************************************/
	public String placeMark(String nameIcon,Point3D Location) {
		LocalTime timeNow=LocalTime.now();
		String placemark="<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+timeNow+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#"+nameIcon+"</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+Location.y()+Location.x()+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>";

		return placemark;
	}

	/****************************************************************************************************
	/**
	 * this method guts all the placemarks and create a KML file with all the icons and the placemarks
	 ****************************************************************************************************/
	public String alltDoc(ArrayList<String> icons, ArrayList<String> paleceMarks,int level) {
		String theKMLDoc="";
		String name ="KML file of level number "+level;
		String start="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
				"  <Document>\r\n" + 
				"    <name>"+name+"</name>\r\n";

		String iconss="";
		Iterator<String> allIcons=icons.iterator();	
		while(allIcons.hasNext()) {
			String ii=allIcons.next();
			
		}



		String fileBody="";
		Iterator<String> allPlaces=paleceMarks.iterator();	
		while(allPlaces.hasNext()) {
			String tt=allPlaces.next();
			fileBody+=tt+"\r\n";

		}

		String end=" </Document>\r\n" + 
				"</kml>";

		theKMLDoc=start+fileBody+end;
		return theKMLDoc;		
	}

	/****************************************************************************************************
	 ********************************contractor for the create KML file**********************************
	 ***************************************************************************************************/
	public KML_Logger() {;}
}//end of the class