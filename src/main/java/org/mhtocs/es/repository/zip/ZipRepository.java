package org.mhtocs.es.repository.zip;

import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.blobstore.fs.FsBlobStore;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.Environment;
import org.elasticsearch.repositories.RepositoryException;
import org.elasticsearch.repositories.blobstore.BlobStoreRepository;
import org.mhtocs.es.repository.zip.blobstore.ZipBlobStore;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

public class ZipRepository extends BlobStoreRepository {

    public static final String TYPE = "zip";

    public static final Setting<String> LOCATION_SETTING =
            new Setting<>("location", "", Function.identity(), Setting.Property.NodeScope);
    public static final Setting<String> REPOSITORIES_LOCATION_SETTING =
            new Setting<>("repositories.zip.location", LOCATION_SETTING, Function.identity(), Setting.Property.NodeScope);
    public static final Setting<ByteSizeValue> CHUNK_SIZE_SETTING =
            Setting.byteSizeSetting("chunk_size", new ByteSizeValue(-1), Setting.Property.NodeScope);
    public static final Setting<ByteSizeValue> REPOSITORIES_CHUNK_SIZE_SETTING =
            Setting.byteSizeSetting("repositories.zip.chunk_size", new ByteSizeValue(-1), Setting.Property.NodeScope);
    public static final Setting<Boolean> COMPRESS_SETTING = Setting.boolSetting("compress", false, Setting.Property.NodeScope);
    public static final Setting<Boolean> REPOSITORIES_COMPRESS_SETTING =
            Setting.boolSetting("repositories.zip.compress", false, Setting.Property.NodeScope);

    private final ZipBlobStore blobStore;

    private ByteSizeValue chunkSize;

    private final BlobPath basePath;

    private boolean compress;

    /**
     * Constructs a shared file system repository.
     */
    public ZipRepository(RepositoryMetaData metadata, Environment environment,
                        NamedXContentRegistry namedXContentRegistry) throws IOException {
        super(metadata, environment.settings(), namedXContentRegistry);
        String location = REPOSITORIES_LOCATION_SETTING.get(metadata.settings());
        if (location.isEmpty()) {
            logger.warn("the repository location is missing, it should point to a shared file system location that is available on all master and data nodes");
            throw new RepositoryException(metadata.name(), "missing location");
        }
        Path locationFile = environment.resolveRepoFile(location);
        if (locationFile == null) {
            if (environment.repoFiles().length > 0) {
                logger.warn("The specified location [{}] doesn't start with any repository paths specified by the path.repo setting: [{}] ", location, environment.repoFiles());
                throw new RepositoryException(metadata.name(), "location [" + location + "] doesn't match any of the locations specified by path.repo");
            } else {
                logger.warn("The specified location [{}] should start with a repository path specified by the path.repo setting, but the path.repo setting was not set on this node", location);
                throw new RepositoryException(metadata.name(), "location [" + location + "] doesn't match any of the locations specified by path.repo because this setting is empty");
            }
        }

        blobStore = new ZipBlobStore(settings, locationFile);
        if (CHUNK_SIZE_SETTING.exists(metadata.settings())) {
            this.chunkSize = CHUNK_SIZE_SETTING.get(metadata.settings());
        } else if (REPOSITORIES_CHUNK_SIZE_SETTING.exists(settings)) {
            this.chunkSize = REPOSITORIES_CHUNK_SIZE_SETTING.get(settings);
        } else {
            this.chunkSize = null;
        }
        this.compress = COMPRESS_SETTING.exists(metadata.settings()) ? COMPRESS_SETTING.get(metadata.settings()) : REPOSITORIES_COMPRESS_SETTING.get(settings);
        this.basePath = BlobPath.cleanPath();
    }

    @Override
    protected BlobStore blobStore() {
        return blobStore;
    }

    @Override
    protected boolean isCompress() {
        return compress;
    }

    @Override
    protected ByteSizeValue chunkSize() {
        return chunkSize;
    }

    @Override
    protected BlobPath basePath() {
        return basePath;
    }
}
