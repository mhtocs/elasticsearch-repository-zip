package org.mhtocs.es.repository.zip.blobstore;

import org.elasticsearch.common.blobstore.BlobContainer;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.nio.file.Path;

public class ZipFsBlobStore implements BlobStore {
    public ZipFsBlobStore(Settings settings, Path locationFile) {
    }

    @Override
    public BlobContainer blobContainer(BlobPath path) {
        return new ZipFsBlobContainer();
    }

    @Override
    public void delete(BlobPath path) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
