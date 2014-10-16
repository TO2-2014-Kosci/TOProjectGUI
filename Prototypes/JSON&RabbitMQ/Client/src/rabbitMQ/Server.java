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
	private Channel channel;
	private QueueingConsumer consumer;
	private Channel mainChannel;
	private Channel gameChannel;
	private QueueingConsumer mainConsumer;
	private QueueingConsumer gameConsumer;
	private final static String QUEUE_NAME = "testNormal";
	private final static String EXCHANGE_NAME = "testFanout";
	
	public void connectToServerCreateChannelAndConsume() {
		connect();
		createMainChannel();
		mainConsumer = createConsumerForChannel(mainChannel);
	}
	
	private void connect() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			System.out.println(channel+"dd");
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

	private void createMainChannel() {
		try {
			mainChannel = connection.createChannel();
			mainChannel.queueDeclare(QUEUE_NAME, false, false, false, null);
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	private QueueingConsumer createConsumerForChannel(Channel channel) {
		try {
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(QUEUE_NAME, true, consumer);
			return consumer;
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		return new QueueingConsumer(channel); //to jest �le
	}
	
	public void createChannelForGameAndConsume() {
		createGameChannel();
		gameConsumer = createConsumerForChannel(gameChannel);
	}
	
	private void createGameChannel() {
		try {
			gameChannel = connection.createChannel();
			gameChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = gameChannel.queueDeclare().getQueue();
			gameChannel.queueBind(queueName, EXCHANGE_NAME, "");
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			channel.close();
			mainChannel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void disconnectFromGame() {
		try {
			gameChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendStringToServer(String message) {
		try {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			mainChannel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String receiveStringFromServer() {
		return receiveStringFromConsumer(mainConsumer);
	}
	
	public String receiveStringFromGame() {
		return receiveStringFromConsumer(gameConsumer);
	}
	
	private String receiveStringFromConsumer(QueueingConsumer consumer) {
		try {
			QueueingConsumer.Delivery delivery = mainConsumer.nextDelivery();
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