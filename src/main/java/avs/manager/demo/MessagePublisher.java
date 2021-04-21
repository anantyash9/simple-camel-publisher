/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package avs.manager.demo;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.google.pubsub.GooglePubsubConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
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
