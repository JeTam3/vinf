package vinf;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.regex.Pattern;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Parser {
		
	private static final int startElement = XMLEvent.START_ELEMENT;
	private static final int endElement = XMLEvent.END_ELEMENT;
	private static final int textOfElement = XMLEvent.CHARACTERS;
	
	private String elementText = "";
	private String title = "";
	private String abstractText = "";
	
	private Integer counter = 1;
	private String currentNamespace = "";
	private Integer redirects = 0;

	private StringBuilder strBuild = new StringBuilder();
		
	public Page newPage;
	public Doc newDoc;
	
	private ArrayList<Page> allPages = new ArrayList<Page>();
	private ArrayList<Doc> allAbstracts = new ArrayList<Doc>();
	
		
	public void readXML(XMLStreamReader stream, boolean isAbstractFile) throws XMLStreamException{
		while (stream.hasNext()) {
        	int eventType = stream.next();
                switch (eventType) {
                	case XMLEvent.CDATA:
                	case XMLEvent.SPACE:
                	case XMLEvent.COMMENT:
	                case textOfElement:
	                	captureText(stream.getText());
	                    break;
	                case endElement:
	                	if(isAbstractFile)
	                		endCurrentElementDoc(stream.getLocalName());
	                	else
	                		endCurrentElementPage(stream.getLocalName());
	                			                		
	                	//endCurrentElement(stream.getLocalName(), isAbstractFile);
	                    break;
	                case startElement:
	                	createElement(stream.getLocalName());
	                    break;
                }
        }
	}
	
	private void createElement(String elementName){
		strBuild.setLength(0);
	}
	
	private void captureText(String insideText){
		strBuild.append(insideText);
	}
	
	private void endCurrentElementDoc(String elementName){	
		if(elementName == "doc" && !abstractText.equals("")){
			newDoc = new Doc();
			newDoc.setTitleTag(title);
			//newDoc.setAbstractTextTag(abstractText);
			newDoc.setTermFrequncy(createTermFrequency(abstractText));
			//System.out.println(title);
			allAbstracts.add(newDoc);
			abstractText = "";
		}
		
		if(elementName == "abstract"){
			String temp = strBuild.toString().trim();
			if(temp.matches("^[A-Za-z0-9].+")){
				/*System.out.println("----"+title);
				System.out.println(abstractText);*/
				abstractText = temp;
			}
		}
		
		if(elementName == "title")
			title = strBuild.toString().trim();
	}
	
	private void endCurrentElementPage(String elementName){	
		switch (elementName) {
			case "ns":
				currentNamespace = strBuild.toString();	
				break;
			case "title":
				title = strBuild.toString().trim();
				break;
			case "text":
				if(currentNamespace.equals("0")){
					if(!isRedirect(strBuild.toString().substring(0, 10)) ){
						newPage = new Page();
						newPage.setTitleTag(title);
						
						elementText = strBuild.toString();
						elementText = cleanWikiPageText(elementText);

						/* \\{\\{.*\\}\\}|\\{\\{.*(.*\\n)*\\}\\}|\\n  */    //ORIGINAL VERSION
						
						newPage.setTermFrequncy(createTermFrequency(elementText));
						//newPage.setTextTag(elementText);
						
						System.out.println(counter+".) "+title);
						counter++;
						
						allPages.add(newPage);			
					}
				}
				break;
			default:
				break;
		}
				
		//Ak chcem vypisat len iny namespace tak dam TU do podmienky !currentNamespace.equals("X")
		/*if(elementName == "ns")
			currentNamespace = strBuild.toString();		
			//System.out.println(counter+".) "+title + ", namespace: "+ currentNamespace);		
		
		if(elementName == "title")
			title = "Wikipedia: "+strBuild.toString().trim();		
		
		
		if(elementName == "text" && currentNamespace.equals("0")){
			if(!isRedirect(strBuild.toString().substring(0, 10)) ){
				newPage = new Page();
				newPage.setTitleTag(title);
				
				elementText = strBuild.toString();
				elementText = cleanWikiPageText(elementText);

				// \\{\\{.*\\}\\}|\\{\\{.*(.*\\n)*\\}\\}|\\n      //ORIGINAL VERSION
				
				newPage.setTermFrequncy(createTermFrequency(elementText));
				newPage.setTextTag(elementText);
				
				System.out.println(counter+".) "+title);
				counter++;
				
				allPages.add(newPage);			
			}
		}*/
	}
	
	private Boolean isRedirect(String text){
		return text.toLowerCase().matches("^#redirect.*");
	}
	
	private String cleanWikiPageText(String text){
		String result = "";
		
		//if(title.equals("Wikipedia: Demographics of Canada")){
			
		
		result = text;
		
		//Remove comments
		result = result.replaceAll("<!--[^>]+>", "");
		
		//Remove {{#...}}
		result = result.replaceAll("\\{\\{#.+?\\}{2}", "");
		
		//Remove text and curly brackets inline
		result = result.replaceAll("\\{\\{.+?\\}{2}", "");
		
		//Remove everything else in curly brackets and new lines
		result = result.replaceAll("\\*? ?\\{\\{[^}]+\\}\\}","");
		
		/*--------BONUS REMOVERS------*/
	
		
		//Remove images,categories,files... in categories
		result = result.replaceAll("\\[\\[.+:.*\\]\\]", "");
		
		//Remove bold and italic marks
		result = result.replaceAll("\\'{2,3}", "");
		
		//Remove headers
		result = result.replaceAll("={2,}.+={2,}", "");	
		
		//Remove new lines
		//result = result.replaceAll("\\n", "");
		
		/*result = result.replaceAll("\\|(.+?)\\]\\]", "#@#$1");
		result = result.replaceAll("\\[{2}(.+?)\\]\\]", "$1");
		result = result.replaceAll("\\[{2}.+?#@#", "");*/
		
		//result = result.replaceAll("\\[{2}(.+)?\\|(.+)?\\]{2}","$2");
			
		
		//}
		//System.out.println(result);
		return result;
	}
			
	private HashMap<String,Integer> createTermFrequency(String text){
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		
		/*String[] words = text.split("\\s|\\.|,");
		
		for (int i = 0; i < words.length; i++) {
			if(!words[i].equals(""))
				if(map.containsKey(words[i]))
					map.put(words[i], map.get(words[i])+1);
				else
					map.put(words[i], 1);
		}*/
		
		StringReader reader = new StringReader(text);
		StandardTokenizer tokenizer = new StandardTokenizer(reader);
		
		CharTermAttribute charTermAttrib = tokenizer.getAttribute(CharTermAttribute.class);
		
		try {
			tokenizer.reset();
			
			while (tokenizer.incrementToken()) {
				
				if(map.containsKey(charTermAttrib.toString()))
					map.put(charTermAttrib.toString(), map.get(charTermAttrib.toString())+1);
				else
					map.put(charTermAttrib.toString(), 1);
				
			}
			tokenizer.end();
			tokenizer.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	
		return map;
	}

	/*TOTO JE POVODNA VERZIA VYTVARANIA STRANOK !!!!*/ 
	/*private void createElement(String elementName){
		if(elementName == "page")
			newPage = new Page();
	}
	
	private void endCurrentElement(String elementName){
		if(elementName == "page"){
			allPages.add(newPage);
			counter++;}
		if(elementName == "title"){
			newPage.setTitleTag(elementText);
			System.out.println(counter+".) "+newPage.getTitleTag());}
		if(elementName == "text"){
			if(!newPage.isRedirect(elementText)){
				newPage.setTextTag(elementText);
				newPage.setRedirect(false);
			}
		}
	}*/
	
	public ArrayList<Page> getAllPages(){
		return allPages;
	}
	
	public ArrayList<Doc> getAllAbstracts(){
		return allAbstracts;
	}
}
