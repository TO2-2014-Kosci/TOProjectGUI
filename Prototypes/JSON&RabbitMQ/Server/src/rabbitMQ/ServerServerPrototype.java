package rabbitMQ;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ServerServerPrototype {
	private Connection connection;
	private Channel channelNormal;
	private Channel channelFanout;
	private QueueingConsumer consumer;
	private ConnectionFactory factory;
	private final static String QUEUE_NAME = "handShakeChannel";
	private String EXCHANGE_NAME_FANOUT= "testFanout";
	
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
			//consumer = new QueueingConsumer(channelNormal);
			//channelNormal.basicConsume(QUEUE_NAME, true, consumer);
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	public void connectFanoutbyName(String name){
		if(factory == null){
			connectInit();
		}
		try{ 
			EXCHANGE_NAME_FANOUT = name;
			channelFanout = connection.createChannel();
			channelFanout.exchangeDeclare(name, "fanout");
		}
		catch(IOException e){
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
	
	public String receiveStringFromHangshake() {
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
