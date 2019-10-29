package com.jrg.demo.rabbitmq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 此处可以利用AmdqAdmin创建队列、交换器、binding信息等等
 * @author huyj
 *
 */
@Configuration
public class RabbitMqConfig {
	/**
	 * MessageConverter是将消息体序列化后发送给队列，可以改变默认的序列化方式
	 * 查询MessageConverter所有实现类，可以选中右键ctrl+T
	 * @return
	 */
	@Bean
	public MessageConverter messageConverter(){
		
		return new Jackson2JsonMessageConverter();
	}
}
