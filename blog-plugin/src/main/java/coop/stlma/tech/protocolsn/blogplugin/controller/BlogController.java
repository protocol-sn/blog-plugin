package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class BlogController implements BlogOperations {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @Get(BlogOperations.GET_BLOG_ENDPOINT)
    @Override
    public Mono<HttpResponse<BlogEntry>> getBlog(@PathVariable("blogId") UUID blogId) {
        return blogService.getBlogEntry(blogId)
                .map(HttpResponse::ok);
    }
}
