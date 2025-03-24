package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.util.BlogUtil;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
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

    /**
     * Get a blog by ID
     * @param blogId    Id of the blog
     * @return          The requested blog
     */
    @Override
    public Mono<BlogEntry> getBlog(UUID blogId) {
        return blogEntryRepository.findById(blogId)
                .map(BlogUtil::mapToModel);
    }

    /**
     * Submit a blog entry
     * @param blogEntry The blog entry
     * @return          The saved blog
     */
    @Override
    public Mono<BlogEntry> saveBlog(BlogEntry blogEntry) {
        return blogEntryRepository.save(BlogUtil.mapToEntity(blogEntry))
                .map(BlogUtil::mapToModel);
    }

    @Override
    public Flux<BlogEntryMetadata> queryBlogMetadata(UUID userId) {
        return blogEntryRepository.findByAuthorOrderByCreatedAtDesc(userId)
                .map(BlogUtil::mapToMetaModel);
    }

    @Override
    public Mono<BlogEntry> mostRecentBlogByUser(UUID userId) {
        return blogEntryRepository.findFirstByAuthorOrderByCreatedAtDesc(userId)
                .map(BlogUtil::mapToModel);
    }
}
