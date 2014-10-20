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
	private String directChannelName;
	
	public void connectToServerCreateDirectChannelAndConsume() {
		connect();
		createDirectChannel();
		directConsumer = getConsumerForNormalChannel();
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

	private void createDirectChannel() {
		try {
			directChannel = connection.createChannel();
			directChannel.queueDeclare(directChannelName, false, false, false, null);
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	private QueueingConsumer getConsumerForNormalChannel() {
		try {
			QueueingConsumer consumer = new QueueingConsumer(directChannel);
			directChannel.basicConsume(directChannelName, true, consumer);
			return consumer;
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return new QueueingConsumer(directChannel); //to jest �le
	}
	
	private void handshake() {
		try {
			Channel handShakeChannel = connection.createChannel();
			handShakeChannel.queueDeclare("handShakeChannel", false, false, false, null);
			handShakeChannel.basicPublish("", "handShakeChannel", null, new HandshakingMessage(directChannelName).getBytes());
			handShakeChannel.close();
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}

}