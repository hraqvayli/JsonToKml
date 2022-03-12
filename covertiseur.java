package forTest2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.io.Writer;

import java.nio.file.DirectoryStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;


import org.json.JSONException;

import javax.json.Json;
import javax.json.JsonArray;

import javax.json.JsonReader;

public class covertiseur {

	public static void kmlfile(Path file) throws JSONException, FileNotFoundException{
		

	String kmltest = "" ;
	ArrayList<String> content = new ArrayList<String>();
	String str =  file.getFileName().toString();
	System.out.println(str);
	InputStream is;
	try {
	is = new FileInputStream(file.toFile());
	JsonReader reader = Json.createReader(is);
    JsonArray trajs = reader.readArray();
    String kmlstart ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" +
            "<Document>\n";
	
    content.add(0,kmlstart);
    
    for(int i=0;i< trajs.size() ;i++){
    	String kmlelement ="\t\t<Placemark id=\"" +  trajs.getJsonObject(i).get("id") + "\">\n" +
                "\t\t\t<Point id =\""+ i+1 + "\">\n" +
                "\t\t\t\t<coordinates>"+ trajs.getJsonObject(i).get("lng")+"," + trajs.getJsonObject(i).get("lat")+","+ 0.0 + "</coordinates>\n" +
                "\t\t\t</Point>\n" +
                "\t\t</Placemark>\n";
    	        	
    	content.add(i+1,kmlelement);
    }  

    String kmlend = "\t</Document>\n"+
    		"</kml>";
    content.add(content.size(),kmlend);
    for(int i=0;i< trajs.size()+2 ;i++){
    	kmltest = kmltest + content.get(i) ;
   }
    
	String name =  str.replaceFirst("[.][^.]+$", "");
	File testexists = new File(name+".kml");
    Writer fwriter;

    if(!testexists.exists()){
        try {
            fwriter = new FileWriter(name+".kml");
            fwriter.write(kmltest);
            fwriter.flush();
            fwriter.close();
        }catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }   
    }
	} catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	

}
	public static void main(String[] args) throws JSONException, IOException {
		
	try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("/Users/ahark/test2/data/trajectories"))) {
	    for (Path file: stream) {
	    	kmlfile(file); 
	    	} 
		}
	}
}		

