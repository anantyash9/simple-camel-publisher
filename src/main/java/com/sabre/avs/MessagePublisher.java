package com.sabre.avs;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.pubsub.GooglePubsubConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class MessagePublisher extends RouteBuilder {

    // we can use spring dependency injection
    @Autowired
    GeneratorBean generatorBean;
    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {
        // start from a timer
        from("timer:hello?delay=-1").routeId("publisher")
                // generate a random message body
                .bean(generatorBean, "getRandomMessage")
                // get carrier id and set ordering key to carrier id
                .setHeader(GooglePubsubConstants.ORDERING_KEY,method(generatorBean,"getCarrier"))
                //route the message to pub sub topic based on carrier id
                .choice()
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier1")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier1}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier2")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier2}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier3")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier3}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier4")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier4}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier5")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier5}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier6")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier6}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier7")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier7}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier8")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier8}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier9")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier9}}{{pub-sub-parameters}}")
                
                .when(header(GooglePubsubConstants.ORDERING_KEY).isEqualTo(env.getProperty("carrier10")))
                .toD("{{pub-sub-prefix}}{{pub-sub-carrier10}}{{pub-sub-parameters}}")
        .to("{{pub-sub-topic}}")
                .end()
                .to("log:Throughput Logger?level=INFO&groupInterval=10000&groupDelay=60000&groupActiveOnly=false");

    }

}
