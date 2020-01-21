package gameClient;

import java.time.LocalTime;

import utils.Point3D;

public class KML_Logger {

	private void setIcon(int countIcon) {

	}

	private void createIconStyle() {
		String format="";
		String Icon="<Style id=\"paddle-a\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/paddle/A."+format+"</href>\r\n" + 
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
				"        <coordinates>"+Location.iy()+Location.ix()+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>";

		return placemark;
	}

	/**
	 * 
	 * @return
	 */
	private String mergeAll () {

	}

	/**
	 * this method create the document name and the sings of the XML at the start of the document
	 */
	public String allDoc(int level) {
		String theKMLDoc=level+".kml";
		String name="";
		String end=" </Document>\r\n" + 
				"</kml>";
		String all="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
				"  <Document>\r\n" + 
				"    <name>"+name+"</name>\r\n"
				+mergeAll()+"\r\n"+end;

		retrun theKMLDoc;		
	}


	/****************************************************************************************************
	 *contractor for the create KML file
	 ***************************************************************************************************/
	public KML_Logger(int level) {
		String ans=allDoc(level);
	}
}
