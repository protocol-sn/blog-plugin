package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service for interacting with blogs
 *
 * @author John Meyerin
 */
public interface BlogService {
    /**
     * Get a blog by ID
     * @param blogId    Id of the blog
     * @return          The requested blog
     */
    Mono<BlogEntry> getBlog(UUID blogId);

    /**
     * Submit a blog entry
     * @param blogEntry The blog entry
     * @return          The saved blog
     */
    Mono<BlogEntry> saveBlog(BlogEntry blogEntry);

    /**
     * query blog metadata.
     * @param userId    Id of the user
     * @return          A list of blog metadata
     */
    Flux<BlogEntryMetadata> queryBlogMetadata(UUID userId);

    /**
     * Get the most recent blog for a given user
     * @param userId    Id of the user
     * @return          The most recent blog
     */
    Mono<BlogEntry> mostRecentBlogByUser(UUID userId);
}
