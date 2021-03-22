package avs.manager.demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("GeneratorBean")
public class GeneratorBean {

    private int counter;

    @Value("${avs}")
    private String avs;
    @Value("${mas}")
    private String mas;
    @Value("${ava}")
    private String ava;
    
    private String[] messages= {ava,mas,ava};

    public String getRandomMessage(String body) {
//    	System.out.println(counter);
//        return "Flight "+String.valueOf(++counter);
    	String[] messages= {ava,mas,ava};
    	Random random = new Random();
    	int index = random.nextInt(messages.length);
    	return  messages[index];
    }

    public String hashIt(String body) {
//    	System.out.println(body.hashCode()%3);
        return String.valueOf(Math.abs(body.hashCode()%3));
    }

}
