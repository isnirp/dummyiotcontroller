package com.flimbis;

//import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import io.reactivex.rxjava3.core.*;
import java.util.concurrent.LinkedBlockingQueue;


public class MqttCommunication implements MqttCallback {
    private MqttClient client;
    private LinkedBlockingQueue<String> msgQueue;

    public MqttCommunication(String mqttHost, int mqttPort){
        String endpoint = String.format("tcp://%s:%d", mqttHost, mqttPort);
        String clientId = "client123";
        try {
            this.client = new MqttClient(endpoint, clientId,  new MemoryPersistence());
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }

        msgQueue = new LinkedBlockingQueue<>();
    }

    public void connect(){
        MqttConnectOptions options = new MqttConnectOptions(); /* exposes options to customize some aspects of the protocol */
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);

        try {
            this.client.setCallback(this);
            this.client.connect(options);
        } catch (MqttException e) {
            System.out.println("Exception "+e);
        }
        
    }

    public void sendMessage(String topic, String msg){
        MqttMessage mqtt_message = new MqttMessage();
        mqtt_message.setQos(0);
        mqtt_message.setRetained(true);
        mqtt_message.setPayload(msg.getBytes());
        try {
            this.client.publish(topic, mqtt_message);
        } catch (MqttException e) {
            System.out.println("Exception "+e);
        }
    }

    public void subTopic(String topic){
        try {
            this.client.subscribe(topic, 1);
        } catch (MqttException e) {
            System.out.println("Exception "+e);
        }
    }
    
    public Observable<String> getTraffic(){
        Observable<String> trafficObservable = null;
        try {
            trafficObservable = Observable.just(msgQueue.take());      
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }
        return trafficObservable;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost because: " + throwable);
        try {
            this.client.disconnect();
        } catch (MqttException e) {
            System.out.println("Exception "+e);
        }
        System.exit(1);
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
        msgQueue.add(new String(message.getPayload()));
    }

    protected class Traffic{
        String topic;
        String msg;
        public Traffic(String topic, String msg){
            this.topic = topic;
            this.msg = msg;
        }
    }

}