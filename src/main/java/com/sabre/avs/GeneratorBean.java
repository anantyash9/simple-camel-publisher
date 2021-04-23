package com.sabre.avs;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("GeneratorBean")

public class GeneratorBean {

//message types
    @Value("${avs}")
    private String avs;
    @Value("${mas}")
    private String mas;
    @Value("${ava}")
    private String ava;
    
//carrier types    
    @Value("${carrier1}")
    private String carrier1;
    @Value("${carrier2}")
    private String carrier2;
    @Value("${carrier3}")
    private String carrier3;
    @Value("${carrier4}")
    private String carrier4;
    @Value("${carrier5}")
    private String carrier5;
    @Value("${carrier6}")
    private String carrier6;
    @Value("${carrier7}")
    private String carrier7;
    @Value("${carrier8}")
    private String carrier8;
    @Value("${carrier9}")
    private String carrier9;
    @Value("${carrier10}")
    private String carrier10;
  
    private String[] messages;
    private String[] carriers;
    private String[] randomMessages=new String[1000];;
    
    // Generate a bank of messages to be published to pubsub topics
    @PostConstruct  
    private void postConstruct()
    {	
    	 messages= new String[] {avs,mas,ava};
    	 carriers = new String[] {carrier1,carrier2,carrier3,carrier4,carrier5,carrier6,carrier7,carrier8,carrier9,carrier10};
    	 Random random = new Random();
    	 for (int j=0;j<1000;j++) {
    	    	int index = random.nextInt(messages.length);
    	    	String message = messages[index];
    	    	index = random.nextInt(carriers.length);
    	    	String carrier = carriers[index];
    	    	int lineCounts=random.nextInt(5);
    	    	for(int i = 0 ; i<=lineCounts ; i++) {
    	    		message+=carrier+RandomStringUtils.random(17, true, true)+"\n";
    	    	}
    	    	randomMessages[j]=message;
    		 
    		 
    	 }
    }
    // returns a random message from message bank (array)
    public String getRandomMessage() {
    	Random random = new Random();
    	int index = random.nextInt(randomMessages.length);
    	return randomMessages[index] ;
    }
    
    // utility function for extracting the carrier code from message body
    public String getCarrier(String body) {
    	String lines[] = body.split("\\r?\\n");
        return lines[3].substring(0,2);
    }
}
