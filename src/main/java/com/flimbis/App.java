package com.flimbis;

import io.reactivex.rxjava3.core.*;

/** Hello world! */
public class App {
  // private static MqttCommunication mqttClient;

  public App() {}

  public static void main(String[] args) {
    MqttCommunication mqttClient = new MqttCommunication("localhost",1884);
    mqttClient.connect();

    System.out.println( "Hello World!" );
    // subscribe to topic
    mqttClient.subTopic("/dummy/temp");
    // consume temperature data
    //      publish to iot core
    while(true){
      Observable<String> observable = mqttClient.getTraffic();
      observable.subscribe(msg -> System.out.println("message: "+msg));
    }
  }
}
