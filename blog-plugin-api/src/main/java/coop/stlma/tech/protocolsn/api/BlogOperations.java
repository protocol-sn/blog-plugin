package coop.stlma.tech.protocolsn.api;

import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Operations for handling blog entries
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

    String GET_USER_BLOG_MOST_RECENT_ENDPOINT = "/blog/by-user/{userId}/most-recent";
    /**
     * Get the most recent blog entry for this user
     *
     * @param userId    Id of the user
     * @return          The blog
     */
    Mono<HttpResponse<BlogEntry>> mostRecentBlogByUser(@PathVariable("userId") UUID userId);

    String GET_DEFAULT_BLOG_ENDPOINT = "/blog/default";
    /**
     * Load a default blog based on some criteria
     * @return          The default blog
     */
    Mono<HttpResponse<BlogEntry>> getDefaultBlog();

    String DEFAULT_BLOG_STREAM_ENDPOINT = "/blog/default-stream";
    /**
     * Load a default blog stream
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Stream of blogs
     */
    Mono<HttpResponse<List<BlogEntry>>> getDefaultBlogStream(
            @QueryValue(value = "limit", defaultValue = "25") int limit,
            @QueryValue(value = "offset", defaultValue = "0") int offset);
}
