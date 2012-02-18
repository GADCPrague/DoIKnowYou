package org.SdkYoungHeads.DoIKnowYou;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// http://www.ibm.com/developerworks/opensource/library/x-android/
public class XmlParser {
	/*
	   static final String PUB_DATE = "pubDate";
	    static final  String DESCRIPTION = "description";
	    static final  String LINK = "link";
	    static final  String TITLE = "title";
	    static final  String ITEM = "item";
	    
	    final URL feedUrl;

	    protected XmlParser(String feedUrl){
	        try {
	            this.feedUrl = new URL(feedUrl);
	        } catch (MalformedURLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    protected InputStream getInputStream() {
	        try {
	            return feedUrl.openConnection().getInputStream();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	public void deserialize(){
	
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    List<Group> groups = new ArrayList<Group>();
    try {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dom = builder.parse(this.getInputStream());
        Element root = dom.getDocumentElement();
        NodeList items = root.getElementsByTagName(ITEM);
        for (int i=0;i<items.getLength();i++){
            Message message = new Message();
            Node item = items.item(i);
            NodeList properties = item.getChildNodes();
            for (int j=0;j<properties.getLength();j++){
                Node property = properties.item(j);
                String name = property.getNodeName();
                if (name.equalsIgnoreCase(TITLE)){
                    message.setTitle(property.getFirstChild().getNodeValue());
                } else if (name.equalsIgnoreCase(LINK)){
                    message.setLink(property.getFirstChild().getNodeValue());
                } else if (name.equalsIgnoreCase(DESCRIPTION)){
                    StringBuilder text = new StringBuilder();
                    NodeList chars = property.getChildNodes();
                    for (int k=0;k<chars.getLength();k++){
                        text.append(chars.item(k).getNodeValue());
                    }
                    message.setDescription(text.toString());
                } else if (name.equalsIgnoreCase(PUB_DATE)){
                    message.setDate(property.getFirstChild().getNodeValue());
                }
            }
            messages.add(message);
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    } 
    return messages;
	}
	*/
}
