package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.util.BlogUtil;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * default implementation of @link BlogService
 *
 * @author John Meyerin
 */
@Singleton
public class BlogServiceImpl implements BlogService {

    private final BlogEntryRepository blogEntryRepository;

    public BlogServiceImpl(BlogEntryRepository blogEntryRepository) {
        this.blogEntryRepository = blogEntryRepository;
    }

    @Override
    public Mono<BlogEntry> getBlog(UUID blogId) {
        return blogEntryRepository.findById(blogId)
                .map(BlogUtil::mapToModel);
    }
}
