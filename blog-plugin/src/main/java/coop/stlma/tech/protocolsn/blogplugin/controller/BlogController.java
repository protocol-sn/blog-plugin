package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.hateoas.AbstractResource;
import io.micronaut.http.hateoas.GenericResource;
import io.micronaut.http.hateoas.Link;
import io.micronaut.http.hateoas.Resource;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Controller for blog operations
 *
 * @author John Meyerin
 */
@Controller
public class BlogController implements BlogOperations {

    private final BlogService blogService;
    private final EmbeddedServer embeddedServer;

    public BlogController(BlogService blogService, EmbeddedServer embeddedServer) {
        this.blogService = blogService;
        this.embeddedServer = embeddedServer;
    }

    /**
     * Get a specified blog
     * @param blogId    Id of the blog
     * @return          The requested blog
     */
    @Get(BlogOperations.GET_BLOG_ENDPOINT)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<BlogEntry>> getBlog(@PathVariable("blogId") UUID blogId) {
        return blogService.getBlog(blogId)
                .map(HttpResponse::ok);
    }

    /**
     * Submit a blog entry
     * @param blogEntry The blog entry
     * @return          An empty response
     */
    @Post(BlogOperations.SUBMIT_BLOG_ENDPOINT)
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Override
    public Mono<HttpResponse<BlogEntryResource>> submitBlog(@Body BlogEntry blogEntry) {
        return blogService.saveBlog(blogEntry)
                .map(blogEntry1 -> {
                    BlogEntryResource resource = new BlogEntryResource();
                    resource = resource.link("self", Link.of(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", blogEntry1.getId().toString())));
                    return resource;
                })
                .map(HttpResponse::created);
    }
}
