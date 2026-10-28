
package com.example.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class GridFsConfig {

    @Bean
    public GridFsTemplate gridFsTemplate(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoConverter mongoConverter) {

        // Print the connected MongoDB database name
        String databaseName = mongoDatabaseFactory
                .getMongoDatabase()
                .getName();

        System.out.println("=================================");
        System.out.println("MONGODB DATABASE: " + databaseName);
        System.out.println("GRIDFS BUCKET: documents");
        System.out.println("=================================");

        return new GridFsTemplate(
                mongoDatabaseFactory,
                mongoConverter,
                "documents"
        );
    }
}
