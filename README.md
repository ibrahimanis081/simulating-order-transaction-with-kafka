<a name="readme-top"></a>

<!-- PROJECT TITLE AND LOGO -->
<br />
<div align="center">
  
   <h3 align="center">Simulating an Order Transaction With Apache Kafka</h3>

   [![weather-pipeline-Page-1-drawio-2.png](https://i.postimg.cc/hjgX7PNY/weather-pipeline-Page-1-drawio-2.png)](https://postimg.cc/VSGfx1f9)
 
</div>





<!-- ABOUT THE PROJECT -->
## About The Project
* Simulate an application that sends the records of an order transaction with the following fields; userID, transactionID, email, total_cost and items_ordered to a kafka topic ```order``` 
* A Kafka Streams Application to process the order, in this case, read input records from the ```order``` topic, map the email field from each record and write output records to ```email``` topic.
* A Kafka consumer application to read from the ```email``` topic and simulate sending an email to each record
* Kafka Connect Sink Connector to sink the records in ```order``` topic to an Amazon S3 bucket.


### Built With
<br/>

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

* ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000000?style=for-the-badge&logo=Apache%20Kafka&logoColor=white)

<!-- CONTACT -->
## Contact

[@ابراهيم انيس](https://twitter.com/ibrahim__Anees)


<p align="right">(<a href="#readme-top">back to top</a>)</p>


