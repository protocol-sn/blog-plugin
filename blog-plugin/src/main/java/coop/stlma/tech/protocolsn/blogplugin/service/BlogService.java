package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.model.BlogEntry;
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
}
