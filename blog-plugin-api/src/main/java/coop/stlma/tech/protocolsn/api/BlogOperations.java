package coop.stlma.tech.protocolsn.api;

import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import coop.stlma.tech.protocolsn.model.BlogEntryResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Operations for registration of plugins
 *
 * @author John Meyerin
 */
public interface BlogOperations {
    String GET_BLOG_ENDPOINT = "/blog/{blogId}";

    /**
     * Endpoint for accessing a specific blog
     *
     * @param blogId    Id of the blog
     * @return          The requested blog
     */
    Mono<HttpResponse<BlogEntry>> getBlog(@PathVariable("blogId") UUID blogId);

    String SUBMIT_BLOG_ENDPOINT = "/blog";
    /**
     * Endpoint for submitting a blog entry
     * @param blogEntry The blog entry
     * @return          An empty response
     */
    Mono<HttpResponse<BlogEntryResource>> submitBlog(BlogEntry blogEntry);

    /**
     * Get the metadata of the most recent blogs for this user in reverse chronological order
     *
     * @param userId    Id of the user
     * @return          The metadata
     */
    String GET_USER_BLOGS_ENDPOINT = "/blog/by-user/{userId}/metadata";
    Mono<HttpResponse<List<BlogEntryMetadata>>> recentBlogMetaForUser(@PathVariable("userId") UUID userId);

    /**
     * Get the most recent blog entry for this user
     *
     * @param userId    Id of the user
     * @return          The blog
     */
    String GET_USER_BLOG_MOST_RECENT_ENDPOINT = "/blog/by-user/{userId}/most-recent";
    Mono<HttpResponse<BlogEntry>> mostRecentBlogByUser(@PathVariable("userId") UUID userId);
}
