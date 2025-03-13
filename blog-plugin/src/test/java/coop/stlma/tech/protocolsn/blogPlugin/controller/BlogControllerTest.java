package coop.stlma.tech.protocolsn.blogPlugin.controller;

import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.util.UUID;

@MicronautTest
class BlogControllerTest {

    private static final UUID BLOG_ID = UUID.nameUUIDFromBytes("blogId".getBytes());

    @MockBean
    @Primary
    BlogService blogService = Mockito.mock(BlogService.class);

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void testGetBlock_happyPath() {
        BlogEntry expected = BlogEntry.builder()
                .id(BLOG_ID)
                .blogText("This blog is so cool")
                .blogTitle("Is this blog cool?")
                .build();

        Mockito.when(blogService.getBlogEntry(BLOG_ID))
                .thenReturn(Mono.just(expected));

        BlogEntry actual = httpClient.toBlocking().retrieve(HttpRequest.GET(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString())), BlogEntry.class);
        Assertions.assertEquals(expected, actual);
    }
}
