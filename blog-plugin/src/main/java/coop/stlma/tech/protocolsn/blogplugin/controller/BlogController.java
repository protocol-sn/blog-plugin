package coop.stlma.tech.protocolsn.blogplugin.controller;


import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.blogplugin.util.BlogUtil;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import coop.stlma.tech.protocolsn.model.BlogEntryResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.hateoas.Link;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.utils.SecurityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Controller for blog operations
 *
 * @author John Meyerin
 */
@Controller
public class BlogController implements BlogOperations {

    private final BlogService blogService;
    private final SecurityService securityService;

    public BlogController(BlogService blogService,
                          SecurityService securityService) {
        this.blogService = blogService;
        this.securityService = securityService;
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
    @SecurityRequirement(name = "authenticatedUser")
    @Override
    public Mono<HttpResponse<BlogEntryResource>> submitBlog(@Body BlogEntry blogEntry) {
        UUID userId = BlogUtil.parseUserId(securityService);
        blogEntry.getMetadata().setAuthor(userId);
        return blogService.saveBlog(blogEntry)
                .map(blogEntry1 -> {
                    BlogEntryResource resource = new BlogEntryResource();
                    resource = resource.link("self", Link.of(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", blogEntry1.getMetadata().getId().toString())));
                    return resource;
                })
                .map(HttpResponse::created);
    }

    /**
     * Get the metadata for this user's most recent blogs
     * @param userId    Id of the user
     * @return          The metadata
     */
    @Get(BlogOperations.GET_USER_BLOGS_ENDPOINT)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<List<BlogEntryMetadata>>> recentBlogMetaForUser(@PathVariable("userId") UUID userId) {
        return blogService.queryBlogMetadata(userId)
                .collectList()
                .map(HttpResponse::ok);
    }

    /**
     * Get a user's most recent blog
     * @param userId    Id of the user
     * @return          The blog
     */
    @Get(BlogOperations.GET_USER_BLOG_MOST_RECENT_ENDPOINT)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<BlogEntry>> mostRecentBlogByUser(@PathVariable("userId") UUID userId) {
        return blogService.mostRecentBlogByUser(userId)
                .map(HttpResponse::ok);
    }

    /**
     * By some process get the default blog
     * @return          The default blog, specifically the most recent blog posted
     */
    @Get(BlogOperations.GET_DEFAULT_BLOG_ENDPOINT)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<BlogEntry>> getDefaultBlog() {
        return blogService.mostRecentBlogByUser(null)
                .map(HttpResponse::ok);
    }

    /**
     * Default blog stream. Our substitute for "The Algorithm".
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Stream of blogs
     */
    @Get(BlogOperations.DEFAULT_BLOG_STREAM_ENDPOINT)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<List<BlogEntry>>> getDefaultBlogStream(
            @QueryValue(value = "limit", defaultValue = "25") int limit,
            @QueryValue(value = "offset", defaultValue = "0") int offset) {
        return blogService.getDefaultBlogStream(limit, offset)
                .collectList()
                .map(HttpResponse::ok);
    }
}
