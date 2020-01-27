package gameClient;
/**
 * This class represent a KML file maker , this cless create a KML file.
 * It create the placemarks , the icon details and the start and the end of the file.
 * @author tzion
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import utils.Point3D;

public class KML_Logger {

	/***************************************************************************************************
	 * this method create a KML format string from a icon details
	 * @param name
	 * @param adress
	 * @return String that represent icon
	 **************************************************************************************************/
	public String createIconStyle(String name,String adress) {
		char Apostrophes=34;
		String Icon="<Style id="+Apostrophes+name+Apostrophes+">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>"+adress+"</href>\r\n" + 
				"        </Icon>\r\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>\r\n";

		return Icon;
	}

	/**************************************************************************************************
	 * this method returns a string that represent the placemark to the KML file for a giving icon and 
	 * his location
	 * @param nameIcon
	 * @param Location
	 * @return string that represent the placemark for a KML file
	 *************************************************************************************************/
	public String placeMark(String nameIcon,Point3D Location) {
		LocalTime timeNow=LocalTime.now();
		String placemark="<Placemark>\r\n" + 
				"      <TimeStamp>\r\n" + 
				"        <when>"+timeNow+"</when>\r\n" + 
				"      </TimeStamp>\r\n" + 
				"      <styleUrl>#"+nameIcon+"</styleUrl>\r\n" + 
				"      <Point>\r\n" + 
				"        <coordinates>"+Location.x()+","+Location.y()+"</coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>\r\n";

		return placemark;
	}

	/****************************************************************************************************
	/**
	 * this method guts all the details to KML file and create a KML file from them
	 ****************************************************************************************************/
	public void alltDoc(ArrayList<String> icons, ArrayList<String> placeMarks,int level,String file_name) {
		try {
			PrintWriter printKMLfile = new PrintWriter(new File(file_name));
			StringBuilder KMLline = new StringBuilder();

			String name ="KML file of level number "+level;
			String start="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
					"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
					"  <Document>\r\n" + 
					"    <name>"+name+"</name>\r\n";
			KMLline.append(start);

			//***********icons to the file
			Iterator<String> allIcons=icons.iterator();	
			while(allIcons.hasNext()) {
				String ii=allIcons.next();
				KMLline.append(ii);
			}

			//***********all the placemarks to the file
			Iterator<String> allPlaces=placeMarks.iterator();	
			while(allPlaces.hasNext()) {
				String tt=allPlaces.next();
				KMLline.append(tt);
			}

			//***********end of the KML file 
			String end=" </Document>\r\n" + 
					"</kml>";
			KMLline.append(end);
			printKMLfile.write(KMLline.toString());
			printKMLfile.close();
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	/********************************************************************************************
	 * 
	 ********************************************************************************************
	 */
	public void readToString() {
		
	}

	/****************************************************************************************************
	 ********************************contractor for the create KML file**********************************
	 ***************************************************************************************************/
	public KML_Logger() {;}
}//end of the class