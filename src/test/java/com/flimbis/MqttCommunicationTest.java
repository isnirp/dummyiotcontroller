package com.flimbis;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.List;
import java.util.ArrayList;
import io.reactivex.rxjava3.core.Observable;
import org.eclipse.paho.client.mqttv3.*;
import com.flimbis.MqttCommunication;
import com.flimbis.MqttCommunication.Traffic;

public class MqttCommunicationTest{
    private static MqttCommunication client;

    @BeforeClass
    public static void setUp(){
        System.out.println("setUp");
        client = new MqttCommunication("127.0.0.1", 1883);
        client.connect();

        client.subTopic("/test/topic");
        client.sendMessage("/test/topic", "mqttTest");
    }

    @Test
    public void shouldEmitMqttTraffic() throws MqttException {
        MqttCommunication comm = new MqttCommunication("localhost", 1884);
        
        List<String> messages = new ArrayList<>();

        Observable<Traffic> observable = client.getTraffic();
        observable.subscribe(traffic -> messages.add(traffic.getMessage()));
     
        String expectedMessage = "mqttTest";
        assertNotNull("should return mqtt message", messages);
        assertEquals(expectedMessage, messages.get(0));

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("tearDown");
        client.disconnect();
    }
}