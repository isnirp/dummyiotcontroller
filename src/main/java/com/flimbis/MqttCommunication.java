package com.flimbis;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttCommunication implements MqttCallback {
    private MqttClient client;

    public MqttCommunication(String mqttHost, int mqttPort){
        String endpoint = String.format("tcp://%s:%d", mqttHost, mqttPort);
        String clientId = "client123";
        try {
            this.client = new MqttClient(endpoint, clientId);
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }
    }

    public void connect(){
        MqttConnectOptions options = new MqttConnectOptions(); /* exposes options to customize some aspects of the protocol */
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
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

    public void subTopic(String topic,int qos) throws MqttException{
        this.client.subscribe(topic, qos);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost because: " + throwable);
        System.exit(1);
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
    }

}