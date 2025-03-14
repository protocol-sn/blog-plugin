package coop.stlma.tech.protocolsn.api;

import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.hateoas.Link;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Operations for registration of plugins
 *
 * @author John Meyerin
 */
public interface BlogOperations {

    /**
     * Endpoint for accessing a specific blog
     *
     * @param blogId    Id of the blog
     * @return          The requested blog
     */
    String GET_BLOG_ENDPOINT = "/blog/{blogId}";

    Mono<HttpResponse<BlogEntry>> getBlog(@PathVariable("blogId") UUID blogId);

    String SUBMIT_BLOG_ENDPOINT = "/blog";
    /**
     * Endpoint for submitting a blog entry
     * @param blogEntry The blog entry
     * @return          An empty response
     */
    Mono<HttpResponse<BlogEntryResource>> submitBlog(BlogEntry blogEntry);
}
