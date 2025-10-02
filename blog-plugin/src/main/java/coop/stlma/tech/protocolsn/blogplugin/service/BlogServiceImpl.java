package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.util.BlogUtil;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.data.model.Pageable;
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

    /**
     * Query blogs
     * @param userId    Id of the user
     * @return          A list of blog metadata that meet the criteria
     */
    @Override
    public Flux<BlogEntryMetadata> queryBlogMetadata(UUID userId) {
        return blogEntryRepository.findByAuthorOrderByCreatedAtDesc(userId)
                .map(BlogUtil::mapToMetaModel);
    }

    /**
     * Get the most recent blog for given user, or most recent blog at all if user is null
     * @param userId    Id of the user
     * @return          Most recent blog that meets criteria
     */
    @Override
    public Mono<BlogEntry> mostRecentBlogByUser(UUID userId) {
        if (userId == null) {
            return blogEntryRepository.findFirstOrderByCreatedAtDesc()
                    .map(BlogUtil::mapToModel);
        }
        return blogEntryRepository.findFirstByAuthorOrderByCreatedAtDesc(userId)
                .map(BlogUtil::mapToModel);
    }

    /**
     * Get a default blog stream. Our substitute for "The Algorithm".
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Stream of blogs
     */
    @Override
    public Flux<BlogEntry> getDefaultBlogStream(int limit, int offset) {
        return blogEntryRepository.findAllOrderByCreatedAtDesc(Pageable.from(offset, limit))
                .map(BlogUtil::mapToModel);
    }

    /**
     * Get a default blog stream. Our substitute for "The Algorithm".
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Stream of blogs
     */
    @Override
    public Flux<BlogEntryMetadata> getDefaultBlogStreamMetadata(int limit, int offset) {
        return blogEntryRepository.findAllOrderByCreatedAtDesc(Pageable.from(offset, limit))
                .map(BlogUtil::mapToMetaModel);
    }
}
