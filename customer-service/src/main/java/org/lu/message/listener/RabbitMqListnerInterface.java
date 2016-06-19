package org.lu.message.listener;

public interface RabbitMqListnerInterface<T> {
	void processQueue(T t);
}