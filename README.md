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
    

Gotchas
-------
1. Create kafka topics beforehand. We cannot rely for topics to be created on the fly as the time delay causes the producers to timeout during first time sourcing

TODO
----
1. Dockerize this project
