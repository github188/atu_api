package com.atu.api.web.controller.utils;

import java.util.concurrent.Semaphore;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendMessageCallback;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class MetaqUtil {

	public static MessageProducer producer;
	public static MessageSessionFactory sessionFactory;

	static{
		MetaClientConfig metaClientConfig = new MetaClientConfig();
		ZKConfig zkConfig = new ZKConfig();
		zkConfig.zkConnect = "124.202.157.2:2181";
		metaClientConfig.setZkConfig(zkConfig);
		try {
			sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
		} catch (MetaClientException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 异步发送消息
	 * @param topic
	 * @param message
	 */
	public static void sendMsg(String topic, String message) {
		producer = sessionFactory.createProducer();
		producer.publish(topic);
		final Semaphore permits= new Semaphore(10000);
        while (true) {
        	// 每次发送前都请求许可
        	if (permits.tryAcquire()) {
        		// 获取许可成功，发送消息
        		try {
        			//异步发送
        			producer.sendMessage(new Message(topic, message.getBytes()), new SendMessageCallback() {
        	        	 @Override
        	             public void onMessageSent(final SendResult result) {
        	        		 try{
	        	                 if (result.isSuccess()) {
	        	                     System.out.println("发送成功:" + result.getPartition());
	        	                 }
	        	                 else {
	        	                     System.err.println("发送失败:" + result.getErrorMessage());
	        	                 }
        	        		 }
        	        		 finally {
         						// 切记释放许可
         						permits.release();
         					}
        	             }
        	             @Override
        	             public void onException(final Throwable e) {
        	                 e.printStackTrace();
        	             	// 切记释放许可
         					permits.release();
        	             }
        	        });
        		}
        		catch (Exception e) {
        			e.printStackTrace();
        		}
        		//已发送，跳出循环。
        		break;
        	}
        	else {
        		// 让出执行权，等待再次获取许可
        		Thread.yield();
        	}
        }
        //关闭
        try {
   			producer.shutdown();
   		} catch (MetaClientException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
	}
	
	public static void sendMsg(String topic) {
		try {
			producer.publish(topic);
			SendResult sendResult = null;
				sendResult = producer.sendMessage(new Message(topic,null));
			if (sendResult.isSuccess()) {
				System.out.println("发送成功！");
			} else if (!sendResult.isSuccess()) {
				System.out.println("发送失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//关闭
	public static void closeProducer(){
   
	}
	public static void main(String[] args) {
		sendMsg("risk","123456");
		sendMsg("risk","123456");
	}
}
