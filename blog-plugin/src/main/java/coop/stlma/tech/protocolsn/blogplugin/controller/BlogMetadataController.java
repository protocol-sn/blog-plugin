package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.api.BlogMetadataOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
/**
 * Controller for blog metadata operations
 *
 * @author John Meyerin
 */
@Controller
public class BlogMetadataController implements BlogMetadataOperations {

    private final BlogService blogService;

    public BlogMetadataController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * Get the metadata for this user's most recent blogs
     * @param userId    Id of the user
     * @return          The metadata
     */
    @Get(BlogMetadataOperations.GET_USER_BLOG_METADATA)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<List<BlogEntryMetadata>>> recentBlogMetaForUser(@PathVariable("userId") UUID userId) {
        return blogService.queryBlogMetadata(userId)
                .collectList()
                .map(HttpResponse::ok);
    }

    /**
     * Metadata for the default blog stream. Our substitute for "The Algorithm".
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Metadata
     */
    @Get(BlogMetadataOperations.GET_DEFAULT_STREAM_METADATA)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Override
    public Mono<HttpResponse<List<BlogEntryMetadata>>> getDefaultBlogMetadataStream(
            @QueryValue(value = "limit", defaultValue = "25") int limit,
            @QueryValue(value = "offset", defaultValue = "0") int offset) {
        return blogService.getDefaultBlogStreamMetadata(limit, offset)
                .collectList()
                .map(HttpResponse::ok);
    }
}
