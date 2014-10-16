package rabbitMQ;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Server {
	private Connection connection;
	private Channel channelNormal;
	private Channel channelFanout;
	private Channel channelTopic;
	private QueueingConsumer consumer;
	private ConnectionFactory factory;
	private final static String QUEUE_NAME = "testNormals";
	private final static String EXCHANGE_NAME_FANOUT= "testFanout";
	private final static String EXCHANGE_NAME_TOPIC= "testTopic";

	
	private void connectInit(){
		try {
			factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();			
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	public void connectNormal() {
		try {
			if(factory == null){
				connectInit();
			}
			
			channelNormal = connection.createChannel();
			channelNormal.queueDeclare(QUEUE_NAME, false, false, false, null);
			consumer = new QueueingConsumer(channelNormal);
			channelNormal.basicConsume(QUEUE_NAME, true, consumer);
			System.out.println(channelNormal+"dd");
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	public void connectFanout(){
			if(factory == null){
				connectInit();
			}
		try{	
			channelFanout = connection.createChannel();
	        channelFanout.exchangeDeclare(EXCHANGE_NAME_FANOUT, "fanout");
			
			//channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			//consumer = new QueueingConsumer(channel);
			//channel.basicConsume(QUEUE_NAME, true, consumer);
			System.out.println(channelFanout+"dd");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void connectTopic(){
		if(factory==null){
			connectInit();
		}
		try{	
			channelTopic = connection.createChannel();
			channelTopic.exchangeDeclare(EXCHANGE_NAME_TOPIC, "topic");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void disconnect() {
		try {
			channelNormal.close();
			channelFanout.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendString(String message) {
		try {
			channelNormal.basicPublish("", QUEUE_NAME, null, message.getBytes());
	        System.out.println(" [x] Sent normal message'" + message + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendStringFanout(String message){
		try {
	        channelFanout.basicPublish(EXCHANGE_NAME_FANOUT, "", null, message.getBytes());
	        System.out.println(" [x] Sent fanout message '" + message + "'");
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public void sendStringTopic(String message,String topic){
		try{
	        channelTopic.basicPublish(EXCHANGE_NAME_TOPIC, topic, null, message.getBytes());
	        System.out.println(" [x] Sent topic message '" + message + "'");

		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public String receiveString() {
		QueueingConsumer.Delivery delivery;
		try {
			delivery = consumer.nextDelivery();
			return new String(delivery.getBody());
		} catch (ShutdownSignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String();
	}
}
