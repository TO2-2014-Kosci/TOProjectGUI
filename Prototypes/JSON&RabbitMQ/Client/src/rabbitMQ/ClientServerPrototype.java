package rabbitMQ;

import java.io.IOException;

import message.HandshakingMessage;
import message.Message;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ClientServerPrototype {
	private Connection connection;
	private Channel directChannel;
	private Channel gameChannel;
	private QueueingConsumer directConsumer;
	private QueueingConsumer gameConsumer;
	private String directQueueName;
	private String gameQueueName;
	
	public void connectToServerCreateChannelsAndConsume() {
		connect();
		createDirectChannelAndConsumeAndQueue();
		createGameChannel();
		handshake();
	}

	private void connect() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}

	private void createDirectChannelAndConsumeAndQueue() {
		try {
			directChannel = connection.createChannel();
			directQueueName = directChannel.queueDeclare().getQueue();
			directConsumer = new QueueingConsumer(directChannel);
			directChannel.basicConsume(directQueueName, true, directConsumer);
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	private void createGameChannel() {
		try {
			gameChannel = connection.createChannel();
			gameConsumer = new QueueingConsumer(gameChannel);
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	private void handshake() {
		try {
			Channel handShakeChannel = connection.createChannel();
			//handShakeChannel.queueDeclare("handshakeChannel", false, false, false, null);
			System.out.println("Send by handshake " + new HandshakingMessage(directQueueName));
			handShakeChannel.basicPublish("", "handshakeChannel", null, new HandshakingMessage(directQueueName).getBytes());
			handShakeChannel.close();
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	public void connectToGameChannelByNameAndConsume(String name) {
		try {
			gameChannel.exchangeDeclare(name, "fanout");
			gameQueueName = gameChannel.queueDeclare().getQueue();
			gameChannel.basicConsume(gameQueueName, true, gameConsumer);
			gameChannel.queueBind(gameQueueName, name, "");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void disconnectFromGameChannel() {
		try {
			gameChannel.queueDelete(gameQueueName);
			gameChannel.exchangeDelete(gameQueueName);
			gameQueueName = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message) {
		try {
			System.out.println("Send by direct " + message);
			directChannel.basicPublish("", directQueueName, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Temporary
	public String receiveStringFromDirect() {
		try {
			QueueingConsumer.Delivery delivery = directConsumer.nextDelivery();// <= tu nie dzia³a
			return new String(delivery.getBody());
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new String();
	}
	
	public String receiveStringFromGame() {
		try {
			QueueingConsumer.Delivery delivery = gameConsumer.nextDelivery();// <= tu nie dzia³a
			return new String(delivery.getBody());
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new String();
	}
}