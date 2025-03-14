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
    Mono<BlogEntry> getBlog(UUID blogId);

    Mono<BlogEntry> saveBlog(BlogEntry blogEntry);
}
