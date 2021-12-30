package org.mhtocs.es.repository.zip;

import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.RepositoryPlugin;
import org.elasticsearch.repositories.Repository;

import java.util.Collections;
import java.util.Map;

public class ZipRepositoryPlugin extends Plugin implements RepositoryPlugin {



    @Override
    public Map<String, Repository.Factory> getRepositories(Environment env, NamedXContentRegistry namedXContentRegistry) {
        return Collections.singletonMap(ZipRepository.TYPE, meta -> new ZipRepository(meta, env, namedXContentRegistry));
    }
}
