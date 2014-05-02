package Util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import environment.LemmingMind;

/** 
 * 
 * @author $Author: cdalle$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */



/**
 * Parser xml pour la génération du monde
 * @author Clement
 *
 */
public class XMLParser{

	public int width;
	public int height;
	public int numberLemmings;
	public ArrayList<Position> Walls = new ArrayList<Position>();
	public int spx;
	public int spy;
	public int endAreax;
	public int endAreay;
	
	public XMLParser() 
	{
		
	}

	/**
	 * @param fileIn is the document to parse
	 */
	public void readXML(String fileIn ) throws IOException, ParserConfigurationException, SAXException {
	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(fileIn));
		doc.getDocumentElement ().normalize ();
		xmlConfig(doc);
	}

	private void xmlConfig(Document doc) throws IOException 
	{
		xmlEnvironment(doc.getElementsByTagName("environment"));
	}


	/**
	 * Create the environment with the information given in the node environment
	 * It's call methods for each subnode from environment'snode
	 */
	private void xmlEnvironment(NodeList nodeList) throws IOException 
	{
		for(int s=0;s<nodeList.getLength();s++)
		{
			Node element = nodeList.item(s);
			Element elementEnv = (Element)element;
			try
			{
				this.width = Integer.valueOf(elementEnv.getAttribute("width"));
				this.height = Integer.valueOf(elementEnv.getAttribute("height"));
			}
			catch (NumberFormatException e)
			{
				System.err.println("Environment");
			}
			if (element.hasChildNodes())
			{
				NodeList listChild = element.getChildNodes();
				for (int i=0; i < listChild.getLength(); i++) {
					Node subnode = listChild.item(i);
					if (subnode.getNodeType() == Node.ELEMENT_NODE) {
						if (subnode.getNodeName().equals("Spawner")) 
						{
							if(subnode.hasChildNodes())
							{
								xmlSpawner(subnode);
							}
						}
						if (subnode.getNodeName().equals("Wall")) 
						{
							if(subnode.hasChildNodes())
							{
								xmlWalls(subnode);
							}
						}
						if (subnode.getNodeName().equals("Endarea")) 
						{
							if(subnode.hasChildNodes())
							{
								xmlEndarea(subnode);
							}
						}
					}
				}
			}
		}		
	}

	/**
	 * lecture du node endarea
	 * @param subnode
	 */
	private void xmlEndarea(Node subnode) {
		Node element = subnode;
		Element elementEnv = (Element)element;
		endAreax = Integer.valueOf(elementEnv.getAttribute("x"));
		endAreay = Integer.valueOf(elementEnv.getAttribute("y"));
	}

	/**
	 * lecture du node Wall
	 * @param subnode
	 */
	private void xmlWalls(Node subnode) {
		Node element = subnode;
		Element elementEnv = (Element)element;
		Walls.add(new Position(Integer.valueOf(elementEnv.getAttribute("x")), Integer.valueOf(elementEnv.getAttribute("y")), Integer.valueOf(elementEnv.getAttribute("width")), Integer.valueOf(elementEnv.getAttribute("height"))));
	}

	/**
	 * lecture du node Spawner
	 * @param childNodes
	 */
	private void xmlSpawner(Node childNodes) {
		Node element = childNodes;
		Element elementEnv = (Element)element;
		spx = Integer.valueOf(elementEnv.getAttribute("x"));
		spy = Integer.valueOf(elementEnv.getAttribute("y"));
		numberLemmings = Integer.valueOf(elementEnv.getAttribute("numberLemmings"));
			
	}	
}