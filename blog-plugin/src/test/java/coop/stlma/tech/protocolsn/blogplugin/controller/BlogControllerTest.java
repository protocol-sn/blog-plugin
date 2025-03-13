package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.blogplugin.util.TestUtil;
import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
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

    public static final UUID BLOG_ID = UUID.nameUUIDFromBytes("blogId".getBytes());

    @MockBean
    @Primary
    BlogService blogServiceMock = Mockito.mock(BlogService.class);

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void testGetBlog_happyPath() {
        Mockito.when(blogServiceMock.getBlog(BLOG_ID))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .id(BLOG_ID)
                        .blogTitle("Cool Blog")
                        .blogText("Some text")
                        .build()));

        HttpRequest<BlogEntry> request = HttpRequest.GET(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString()));
        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getId());
        Assertions.assertEquals("Cool Blog", responseBody.getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }

    @Test
    void testGetBlog_withAuth() {
        Mockito.when(blogServiceMock.getBlog(BLOG_ID))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .id(BLOG_ID)
                        .blogTitle("Cool Blog")
                        .blogText("Some text")
                        .build()));

        HttpRequest<?> request = HttpRequest.GET(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString()))
                .bearerAuth(TestUtil.getTestUserAccessToken(httpClient));
        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getId());
        Assertions.assertEquals("Cool Blog", responseBody.getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }
}
