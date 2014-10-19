package vinf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;


public class Main {
	
	public static ArrayList<Page> allPages = new ArrayList<Page>();
	public static ArrayList<Doc> allAbstracts  = new ArrayList<Doc>();
	
	public static void main(String[] args) throws Exception{
		
		//String path = ".\\res\\wiki_parts";
		
		/* Reading files  */ 
		//iterateOverFiles(path, false);
		singleFileReader("sample", false);
		//singleFileReader("enwiki-latest-pages-articles7",false);
		
		System.out.println("--------------------------------");
		System.out.println("Pocet vyhovujucich zaznamov: " + allPages.size());
		System.out.println("--------------------------------");
		//createFilePage(allPages,"enwiki-latest-pages-articles7");
		//createFilePage(allPages,"sample");
			
		/* Reading abstracts */
		//singleFileReader("enwiki-latest-abstract22", true);
		//System.out.println("--------------------------------");
		//System.out.println("Pocet vsetkych abstraktov: " + allAbstracts.size());
		//createFileDoc(allAbstracts,"enwiki-latest-abstract22");
		
	}
	
	private static void createFilePage(ArrayList<Page> pages, String filename){
		System.out.println("Zacinam vytvarat subor: "+ filename+".txt");
		try {
			String path = new File(".\\res\\temp\\temp_"+filename+".txt").getCanonicalPath();
			File file = new File(path);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			ListIterator<Page> li = pages.listIterator();
			
			while(li.hasNext()){
				Page actualPage = li.next();
				bw.append("title: Wikipedia: "+actualPage.getTitleTag());
				bw.newLine();
				bw.append("terms: ");
				
				Iterator<Map.Entry<String, Integer>> it = actualPage.getTermFrequency().entrySet().iterator();
				while(it.hasNext()){
					Entry<String, Integer> en = it.next();
					bw.append(en.getKey()+"["+en.getValue()+"], ");
				}
				bw.newLine();
				bw.newLine();
			}
		bw.close();
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		} finally{
			System.out.println("Subor: temp_"+filename+".txt bol vytvoreny");
		}
	}
	
	private static void createFileDoc(ArrayList<Doc> docs, String filename){
		System.out.println("Zacinam vytvarat subor: "+ filename+".txt");
		try {
			String path = new File(".\\res\\temp\\temp_"+filename+".txt").getCanonicalPath();
			File file = new File(path);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			ListIterator<Doc> li = docs.listIterator();
			
			while(li.hasNext()){
				Doc actualDoc = li.next();
				bw.append("title: "+actualDoc.getTitleTag());
				bw.newLine();
				bw.append("terms: ");
				
				Iterator<Map.Entry<String, Integer>> it = actualDoc.getTermFrequency().entrySet().iterator();
				while(it.hasNext()){
					Entry<String, Integer> en = it.next();
					bw.append(en.getKey()+"["+en.getValue()+"], ");
				}
				bw.newLine();
				bw.newLine();
			}
		bw.close();
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		} finally{
			System.out.println("Subor: temp_"+filename+".txt bol vytvoreny");
		}
	}
	
	public static void iterateOverFiles(String path, Boolean isAbstractFile) throws XMLStreamException, IOException{
		XMLInputFactory2 inputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
		
		File dir = new File(path);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				//System.out.println("*************************************");
				System.out.println("File name: "+ child.getName());
				//System.out.println("*************************************");
				
				InputStream in = new FileInputStream(child);
				
				
				Parser myParser = new Parser();				
				XMLStreamReader2 streamReader = (XMLStreamReader2)inputFactory.createXMLStreamReader(in);
				
				//reading Article file => set false
				myParser.readXML(streamReader, isAbstractFile);
				
				in.close();
				streamReader.close();
				
				//System.out.println("********************************");
				//System.out.println("Pocet zaznamov v subore: "+ myParser.getAllPages().size());
				//System.out.println("********************************");
				
				System.out.println("Pocet zaznamov bez redirectov: " + myParser.getAllPages().size());
				System.out.println("************************************************");		
				
				allPages.addAll(myParser.getAllPages());
				
				String fileName = child.getName();
				createFilePage(allPages,fileName.substring(0, fileName.length()-4));
			}
		}
	}
	
	public static void singleFileReader(String filename, Boolean isAbstractFile) throws IOException, XMLStreamException{
		XMLInputFactory2 inputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
		String path = new File(".\\res\\"+filename+".xml").getCanonicalPath();
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		XMLStreamReader2 streamReader = (XMLStreamReader2)inputFactory.createXMLStreamReader(in);
		
		Parser myParser = new Parser();
				
		//reading Article file => set false
		myParser.readXML(streamReader, isAbstractFile);
		
		if(isAbstractFile)
			allAbstracts.addAll(myParser.getAllAbstracts());
		else
			allPages.addAll(myParser.getAllPages());
		
	}
	
	

}
