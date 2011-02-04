/*
 *
 *  Copyright (C) 2010 GSyC/LibreSoft, Universidad Rey Juan Carlos.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. 
 *
 *  Author : Jorge Fernández González <jfernandez@libresoft.es>
 *
 */

package com.libresoft.sdk.ARviewer.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.libresoft.sdk.ARviewer.Types.GeoNode;
import com.libresoft.sdk.ARviewer.Types.Note;

public class Parsers{
    public static final String TAG = "Parser";

    public ArrayList<GeoNode> parseKML2Note(String url) throws
    		ParserConfigurationException, SAXException, IOException{
    	
    	ArrayList<GeoNode> result = null;
    	try{
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            LibraryXMLReader reader = new LibraryXMLReader();
           	sp.parse(url, reader);
           	result = reader.getArrayGeoNode();
        }catch(ParserConfigurationException pcex){
        	//Log.w(TAG, pcex.toString());
        	throw pcex;
        }catch(SAXException saxex){
        	//Log.w(TAG, saxex.toString());
        	throw saxex;
        } catch (IOException ioex) {
        	//Log.w(TAG, ioex.toString());
        	throw ioex;
        }
        return result;
    }
    
    private class LibraryXMLReader extends DefaultHandler {
    	private String content = ""; // This variable stores the tag's content.
    	private ArrayList<GeoNode> arrayGeoNode = new ArrayList<GeoNode>();
    	
    	private String name = null;
    	private String description = null;
    	private double latitude = -1.0;
    	private double longitude = -1.0;
    	private double altitude = -1.0;
    	
    	/**
    	 * Returns an ArrayList of GeoNode objects as result of parsing a document.
    	 * 
    	 * @return An ArrayList of GeoNode objects as result of parsing a document.
    	 */
    	public ArrayList<GeoNode> getArrayGeoNode(){
    		return arrayGeoNode;
    	}

    	public void startDocument() throws SAXException{
    	}
    	
    	public void endDocument()throws SAXException{
    	}

     	/** 
    	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
    	 */
    	public void startElement(String uri, String localName, 
    							String qName, Attributes attributes){
    	}

    	/**
    	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
    	 */
    	public void characters(char buf[], int offset, int len) throws SAXException{
    		content = new String(buf, offset, len);
    	}
        
    	/**
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
         */
    	public void endElement(String uri, String localName, String qName) {    		
    		if("name".equals(localName)){	
    			name = content;
    		}else if("description".equals(localName)) {
    			description = content; 	    	  
    		}else if("coordinates".equals(localName)) {
    			System.out.println(content.substring(0, content.indexOf(",")));
    			System.out.println(content.substring(content.indexOf(",")+1));
    			System.out.println(content.substring(content.lastIndexOf(",")+1));
    			latitude = new Double(content.substring(0, content.indexOf(","))).doubleValue(); 
    			longitude = new Double(content.substring(content.indexOf(",")+1, content.lastIndexOf(","))).doubleValue();
    			altitude = new Double(content.substring(content.lastIndexOf(",")+1)).doubleValue();
    		}else if("Placemark".equals(localName)) {
    			arrayGeoNode.add(new Note(null, name, description,
    								latitude, longitude, altitude,
    									null, null,	null, null));
    		}
    	}
    }
}