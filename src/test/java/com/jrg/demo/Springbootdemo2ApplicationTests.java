package com.jrg.demo;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springbootdemo2ApplicationTests {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	/**
	 * 发送消息测试
	 */
	@Test
	public void contextLoads() {
		//rabbitTemplate.send(exchange, routingKey, message);
		HashMap<String,Object> map = new HashMap<>();
		map.put("msg", "fruit信息");
		map.put("data", Arrays.asList("hello,fruit",123,true));
		/**
		 * 将map数据序列化后再发送，可以自己定义默认的序列化方式
		 */
		rabbitTemplate.convertAndSend("fruit", "fruit", map);
	}
	/**
	 * 测试从队列里取消息
	 */
	@Test
	public void receive() {
		//从队列中获取数据，并将数据反序列化返回
		Object o =rabbitTemplate.receiveAndConvert("fruit");
		System.out.println(o.getClass());
		System.out.println(o);
	}

}
