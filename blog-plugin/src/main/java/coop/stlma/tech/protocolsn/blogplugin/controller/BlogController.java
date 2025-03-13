package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
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

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
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
}
