
package com.example.student.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class GridFsStorageService {

    private final GridFsTemplate gridFsTemplate;

    public GridFsStorageService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    // Upload file
    public String uploadFile(
            InputStream inputStream,
            String fileName,
            String contentType
    ) {
        ObjectId fileId = gridFsTemplate.store(
                inputStream,
                fileName,
                contentType
        );

        return fileId.toHexString();
    }

    // Download file
    public GridFsResource downloadFile(String fileId) {

        GridFSFile file = gridFsTemplate.findOne(
                new org.springframework.data.mongodb.core.query.Query(
                        org.springframework.data.mongodb.core.query.Criteria
                                .where("_id").is(new ObjectId(fileId))
                )
        );

        if (file == null) {
            throw new RuntimeException("File not found with ID: " + fileId);
        }

        return gridFsTemplate.getResource(file);
    }
}
