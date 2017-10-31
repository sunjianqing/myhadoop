package com.jianqing.netflix;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * Created by jianqingsun on 10/31/17.
 *
 * Supposedly we have a Kafka Queue to host movie URL on Wikipedia
 * This is task is used to scrape web page content, build movie metadata and insert movies to database.
 */
public class WebScrapingTask implements TaskInterface {

    public Consumer<Long, String> consumer;

    @Override
    public void init() {
        consumer = createConsumer();
    }

    @Override
    public int run() {
        ConsumerRecords<Long, String> consumerRecords =
                consumer.poll(100);

        if (consumerRecords.count()==0) {
            System.out.println("There is no message in queue.");
            return 0;
        }

        for(ConsumerRecord<Long, String> record : consumerRecords) {
            System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                    record.key(), record.value(),
                    record.partition(), record.offset());

            String url = record.value();
            MovieMetadata movieMetadata = generateMovieMetaDataFromURL(url);
            saveDB(movieMetadata);
        }

        consumer.commitAsync();

        return 0;
    }

    /**
     * Save movie metadata to NoSQL database
     * @param movieMetadata
     */
    private void saveDB(MovieMetadata movieMetadata) {

    }

    /**
     * Make http call to get content of page and build movie meta data
     * @param url
     * @return movie metadata
     */
    private MovieMetadata generateMovieMetaDataFromURL(String url) {
        return null;
    }

    @Override
    public int stop() {
        consumer.close();
        return 0;
    }

    @Override
    public void clean() {

    }

    private Consumer<Long, String> createConsumer() {
        return null;
    }
}
