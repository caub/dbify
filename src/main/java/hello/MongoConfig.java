package hello;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

@Configuration
public class MongoConfig {
	 @Bean
     public DB getDb() throws UnknownHostException, MongoException {
         String uri="mongodb://cyril:cccc1111@ds053978.mongolab.com:53978/jfx";
         MongoClientURI mongoClientURI=new MongoClientURI(uri);
         MongoClient mongoClient=new MongoClient(mongoClientURI);
         DB db=mongoClient.getDB(mongoClientURI.getDatabase());
         return db;
     }
}
