package rabbitMQ;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ClientServer {
	private Connection connection;
	private Channel normalChannel;
	private Channel fanoutChannel;
	private Channel topicChannel;
	private QueueingConsumer normalConsumer;
	private QueueingConsumer fanoutConsumer;
	private QueueingConsumer topicConsumer;
	private String fanoutQueueName;
	private String topicQueueName;
	private final static String NORMAL_NAME = "testNormal";
	private final static String FANOUT_NAME = "testFanout";
	private final static String TOPIC_NAME= "testTopic";
	
	public void connectToServerCreateChannelAndConsume() {
		connect();
		createNormalChannel();
		normalConsumer = createConsumerForNormalChannel();
	}
	
	private void connect() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

	private void createNormalChannel() {
		try {
			normalChannel = connection.createChannel();
			normalChannel.queueDeclare(NORMAL_NAME, false, false, false, null);
		} catch (IOException exception){
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	private QueueingConsumer createConsumerForNormalChannel() {
		try {
			QueueingConsumer consumer = new QueueingConsumer(normalChannel);
			normalChannel.basicConsume(NORMAL_NAME, true, consumer);
			return consumer;
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		return new QueueingConsumer(normalChannel); //to jest �le
	}
	
	public void createFanoutChannelAndConsume() {
		createFanoutChannel();
		fanoutConsumer = createConsumerForChannelForQueueWithName(fanoutChannel, fanoutQueueName);
	}
	
	public void createTopicChannelAndConsume(String topic) {
		createTopicChannel(topic);
		topicConsumer = createConsumerForChannelForQueueWithName(topicChannel, topicQueueName);
	}
	
	
	private void createFanoutChannel() {
		try {
			fanoutChannel = connection.createChannel();
			fanoutChannel.exchangeDeclare(FANOUT_NAME, "fanout");
			fanoutQueueName = fanoutChannel.queueDeclare().getQueue();
			fanoutChannel.queueBind(fanoutQueueName, FANOUT_NAME, "");
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	private void createTopicChannel(String topic) {
		try {
			topicChannel = connection.createChannel();
			topicChannel.exchangeDeclare(TOPIC_NAME, "topic");
			topicQueueName = topicChannel.queueDeclare().getQueue();
			topicChannel.queueBind(topicQueueName, TOPIC_NAME, topic);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private QueueingConsumer createConsumerForChannelForQueueWithName(Channel channel, String queueName) {
		try {
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
			return consumer;
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		return new QueueingConsumer(channel); //to jest �le
	}
	
	public void disconnect() {
		try {
			normalChannel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void disconnectFromFanout() {
		try {
			fanoutChannel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendStringToNormal(String message) {
		try {
			normalChannel.basicPublish("", NORMAL_NAME, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String receiveStringFromNormal() {
		return receiveStringFromConsumer(normalConsumer);
	}
	
	public String receiveStringFromFanout() {
		return receiveStringFromConsumer(fanoutConsumer);
	}
	
	public String receiveStringFromTopic() {
		return receiveStringFromConsumer(topicConsumer);
	}
	
	private String receiveStringFromConsumer(QueueingConsumer consumer) {
		try {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();// <= tu nie działa
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