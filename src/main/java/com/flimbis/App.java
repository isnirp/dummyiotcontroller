package com.flimbis;

import io.reactivex.rxjava3.core.*;
import com.flimbis.MqttCommunication.Traffic;

/** Hello world! */
public class App {
  // private static MqttCommunication mqttClient;

  public App() {}

  public static void main(String[] args) {
    MqttCommunication mqttClient = new MqttCommunication("10.216.38.134",1884);
    mqttClient.connect();

    System.out.println( "Hello World!" );
    // subscribe to topic
    mqttClient.subTopic("/dummy/temp");
    // consume temperature data
    //      publish to iot core
    while(true){
      Observable<Traffic> observable = mqttClient.getTraffic();
      observable.subscribe(traffic -> System.out.println("topic: "+traffic.getTopic()+", message: "+traffic.getMessage()));
    }
  }
}
