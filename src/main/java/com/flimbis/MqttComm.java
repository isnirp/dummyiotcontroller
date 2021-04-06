public class MqttComm {
    private MqttClient publisher;
    private String endpoint; /* endpoint address */
    private String clientId; /* identifies client */

    public MqttComm(String mqttHost, int mqttPort){
        endpoint = mqttHost+":"+mqttPort;
        publisher = new MqttClient(endpoint, clientId);
    }

    public void connect(){
        MqttConnectOptions options = new MqttConnectOptions(); /* exposes options to customize some aspects of the protocol */
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        publisher.connect(options);
        
    }

    public void sendMessage(String topic, MqttMessage mqttMsg){
        msg.setQos(0);
        msg.setRetained(true);
        publisher.publish(topic, mqttMsg);
    }
}