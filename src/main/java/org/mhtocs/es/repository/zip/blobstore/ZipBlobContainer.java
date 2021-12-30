package org.mhtocs.es.repository.zip.blobstore;

import org.elasticsearch.common.blobstore.BlobContainer;
import org.elasticsearch.common.blobstore.BlobMetaData;
import org.elasticsearch.common.blobstore.BlobPath;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ZipBlobContainer implements BlobContainer {
    @Override
    public BlobPath path() {
        return null;
    }

    @Override
    public boolean blobExists(String blobName) {
        return false;
    }

    @Override
    public InputStream readBlob(String blobName) throws IOException {
        return null;
    }

    @Override
    public void writeBlob(String blobName, InputStream inputStream, long blobSize) throws IOException {

    }

    @Override
    public void deleteBlob(String blobName) throws IOException {

    }

    @Override
    public Map<String, BlobMetaData> listBlobs() throws IOException {
        return null;
    }

    @Override
    public Map<String, BlobMetaData> listBlobsByPrefix(String blobNamePrefix) throws IOException {
        return null;
    }

    @Override
    public void move(String sourceBlobName, String targetBlobName) throws IOException {

    }
}
