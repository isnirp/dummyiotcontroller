package com.flimbis;

import io.reactivex.rxjava3.core.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static MqttCommunication mqttClient;

    public App(){}

    public static void main( String[] args )
    {
        mqttClient = new MqttCommunication("localhost",1884);
        mqttClient.connect();
        
        System.out.println( "Hello World!" );
        // subscribe to topic
        mqttClient.subTopic("/dummy/temp", 1);
        // consume temperature data
        //      publish to iot core
        Observable<String> observable = Observable.just("Hello");
    }
}
