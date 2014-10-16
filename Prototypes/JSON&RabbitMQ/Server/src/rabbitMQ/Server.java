package rabbitMQ;

import java.io.IOException;

import message.Message;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Server {
	Connection connection;
	Channel channel;
	QueueingConsumer consumer;
	private final static String QUEUE_NAME = "queue";
	
	public void connect() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
		} catch (IOException exception){
			
		}
	}
	
	public void disconnect() {
		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message) {
		try {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Message receiveMessage() {
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		String message = new String(delivery.getBody());
	}
}
