package org.mhtocs.es.repository.zip.blobstore;

import org.elasticsearch.common.blobstore.BlobContainer;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.blobstore.fs.FsBlobStore;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.nio.file.Path;

public class ZipBlobStore implements BlobStore {
    public ZipBlobStore(Settings settings, Path locationFile) {
    }

    @Override
    public BlobContainer blobContainer(BlobPath path) {
        return new ZipBlobContainer();
    }

    @Override
    public void delete(BlobPath path) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
