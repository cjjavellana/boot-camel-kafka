# boot-camel-kafka
A spring boot application which uses camel for routing inbound messages and for streaming to kafka

Requirements
------------
1. ActiveMQ 5.9.1
2. Kafka 0.9.0.1
3. JDK 1.8
4. Mysql 5.7.11
5. Maven 3.x

Quick Start
-----------
1. Clone the project  
    ```
    git clone git@github.com:cjjavellana/boot-camel-kafka.git
    ```
2. Download project dependency

    * Navigate to the project folder and execute
      ```
      $ mvn clean package
      ```
      
How does this work
-------------------
When this application is started (from the command line), it will start listening from the following queue / topics:

* *entityReady* queue from ActiveMQ
* *inbound* topic from Kafka

The expected message format is as follows:
    
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <availableEntity>
        <bookCode>11</bookCode>
        <countryCode>SG</countryCode>
        <positionDate>30-Mar-2016</positionDate>
        <isRerun>false</isRerun>
        <table>Employees</table>
    </availableEntity>
    
Flume Configuration
-------------------
```
a1.sources = kafka-source-1
a1.channels = hdfs-channel-1
a1.sinks = hdfs-sink-1

# For each one of the sources, the type is defined
a1.sources.kafka-source-1.type = org.apache.flume.source.kafka.KafkaSource
a1.sources.kafka-source-1.zookeeperConnect = 192.168.1.101:2181
a1.sources.kafka-source-1.topic = test
a1.sources.kafka-source-1.groupId = flume
a1.sources.kafka-source-1.batchSize = 100
a1.sources.kafka-source-1.channels = hdfs-channel-1
a1.sources.source1.kafka.consumer.timeout.ms = 90000

a1.channels.hdfs-channel-1.type = memory

a1.sinks.hdfs-sink-1.channel = hdfs-channel-1
a1.sinks.hdfs-sink-1.type = hdfs
a1.sinks.hdfs-sink-1.hdfs.writeFormat = Text
a1.sinks.hdfs-sink-1.hdfs.fileType = DataStream
a1.sinks.hdfs-sink-1.hdfs.filePrefix = test-events
a1.sinks.hdfs-sink-1.hdfs.useLocalTimeStamp = true
a1.sinks.hdfs-sink-1.hdfs.path = /tmp/kafka/%{topic}/%y-%m-%d
a1.sinks.hdfs-sink-1.hdfs.rollCount = 10
a1.sinks.hdfs-sink-1.hdfs.rollSize = 0

a1.channels.hdfs-channel-1.capacity = 10000
a1.channels.hdfs-channel-1.transactionCapacity = 1000
```

Start flume agent as follows:
```
$ nohup bin/flume-ng agent --name a1 --conf-file <filename containing config>
```

Gotchas
-------
1. Create kafka topics beforehand. We cannot rely for topics to be created on the fly as the time delay causes the producers to timeout during first time sourcing

TODO
----
1. Dockerize this project
