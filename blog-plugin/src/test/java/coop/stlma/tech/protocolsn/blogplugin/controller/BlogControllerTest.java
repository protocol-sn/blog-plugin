package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.api.BlogOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.blogplugin.util.AuthProviderCreds;
import coop.stlma.tech.protocolsn.blogplugin.util.TestUtil;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.hateoas.Resource;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    ArgumentCaptor<BlogEntry> blogEntryCaptor = ArgumentCaptor.forClass(BlogEntry.class);

    @Test
    void testGetDefaultBlogStream_pagedRequest() {
        Mockito.when(blogServiceMock.getDefaultBlogStream(10, 1))
                .thenReturn(Flux.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .author(AuthProviderCreds.TEST_USER_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .build()));

        HttpRequest<?> request = HttpRequest.GET(BlogOperations.DEFAULT_BLOG_STREAM_ENDPOINT + "?limit=10&offset=1");

        HttpResponse<List<BlogEntry>> response = httpClient.toBlocking()
                .exchange(request, Argument.listOf(BlogEntry.class));

        Assertions.assertEquals(HttpStatus.OK, response.status());
        List<BlogEntry> responseBody = response.getBody(Argument.listOf(BlogEntry.class)).get();
        Assertions.assertEquals(1, responseBody.size());
        BlogEntry blogEntry = responseBody.get(0);
        Assertions.assertEquals(BLOG_ID, blogEntry.getMetadata().getId());
        Assertions.assertEquals(AuthProviderCreds.TEST_USER_ID, blogEntry.getMetadata().getAuthor());
        Assertions.assertEquals("Cool Blog", blogEntry.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", blogEntry.getBlogText());
    }

    @Test
    void testGetDefaultBlogStream_happyPath() {
        Mockito.when(blogServiceMock.getDefaultBlogStream(25, 0))
                .thenReturn(Flux.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .author(AuthProviderCreds.TEST_USER_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .blogFormat("markdown")
                        .build()));

        HttpRequest<?> request = HttpRequest.GET(BlogOperations.DEFAULT_BLOG_STREAM_ENDPOINT);

        HttpResponse<List<BlogEntry>> response = httpClient.toBlocking()
                .exchange(request, Argument.listOf(BlogEntry.class));

        Assertions.assertEquals(HttpStatus.OK, response.status());
        List<BlogEntry> responseBody = response.getBody(Argument.listOf(BlogEntry.class)).get();
        Assertions.assertEquals(1, responseBody.size());
        BlogEntry blogEntry = responseBody.get(0);
        Assertions.assertEquals(BLOG_ID, blogEntry.getMetadata().getId());
        Assertions.assertEquals(AuthProviderCreds.TEST_USER_ID, blogEntry.getMetadata().getAuthor());
        Assertions.assertEquals("Cool Blog", blogEntry.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", blogEntry.getBlogText());
        Assertions.assertEquals("markdown", blogEntry.getBlogFormat());
    }

    @Test
    void testDefaultBlog_happyPath() {
        Mockito.when(blogServiceMock.mostRecentBlogByUser(null))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .author(AuthProviderCreds.TEST_USER_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .build()));

        HttpRequest<BlogEntry> request = HttpRequest.GET(BlogOperations.GET_DEFAULT_BLOG_ENDPOINT);
        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request, BlogEntry.class);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getMetadata().getId());
        Assertions.assertEquals(AuthProviderCreds.TEST_USER_ID, responseBody.getMetadata().getAuthor());
        Assertions.assertEquals("Cool Blog", responseBody.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }

    @Test
    void testMostRecentBlogByUser_happyPath() {
        HttpRequest<BlogEntry> request = HttpRequest.GET(
                BlogOperations.GET_USER_BLOG_MOST_RECENT_ENDPOINT.replace("{userId}", AuthProviderCreds.TEST_USER_ID.toString()));

        Mockito.when(blogServiceMock.mostRecentBlogByUser(AuthProviderCreds.TEST_USER_ID))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .author(AuthProviderCreds.TEST_USER_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .build()));

        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request, BlogEntry.class);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getMetadata().getId());
        Assertions.assertEquals(AuthProviderCreds.TEST_USER_ID, responseBody.getMetadata().getAuthor());
        Assertions.assertEquals("Cool Blog", responseBody.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }

    @Test
    void testRecentBlogMetaForUser_happyPath() {
        HttpRequest<BlogEntryMetadata> request = HttpRequest.GET(
                BlogOperations.GET_USER_BLOGS_ENDPOINT.replace("{userId}", AuthProviderCreds.TEST_USER_ID.toString()));

        Mockito.when(blogServiceMock.queryBlogMetadata(AuthProviderCreds.TEST_USER_ID))
                .thenReturn(Flux.just(
                        BlogEntryMetadata.builder()
                                .blogTitle("blog1")
                                .build(),
                        BlogEntryMetadata.builder()
                                .blogTitle("blog2")
                                .build()));

        HttpResponse<List<BlogEntryMetadata>> response = httpClient.toBlocking()
                .exchange(request, Argument.listOf(BlogEntryMetadata.class));

        Assertions.assertEquals(HttpStatus.OK, response.status());
        List<BlogEntryMetadata> responseBody = response.body();
        Assertions.assertEquals(2, responseBody.size());
        responseBody = responseBody.stream().sorted(Comparator.comparing(BlogEntryMetadata::getBlogTitle)).toList();
        Assertions.assertEquals("blog1", responseBody.get(0).getBlogTitle());
        Assertions.assertEquals("blog2", responseBody.get(1).getBlogTitle());
    }

    @Test
    void testSaveBlog_noAuthFails() {
        HttpRequest<BlogEntry> request = HttpRequest.POST(BlogOperations.SUBMIT_BLOG_ENDPOINT, BlogEntry.builder().build());

        HttpClientResponseException response = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            httpClient.toBlocking()
                    .exchange(request);
        });

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
    }

    @Test
    void testSaveBlog_happyPath() {

        BlogEntry expected = BlogEntry.builder()
                .metadata(BlogEntryMetadata.builder()
                        .id(BLOG_ID)
                        .author(AuthProviderCreds.TEST_USER_ID)
                        .blogTitle("Cool Blog")
                        .build())
                .blogText("Some text")
                .build();

        Mockito.when(blogServiceMock.saveBlog(blogEntryCaptor.capture()))
                .thenReturn(Mono.just(expected));

        HttpRequest<BlogEntry> request = HttpRequest.POST(BlogOperations.SUBMIT_BLOG_ENDPOINT, expected)
                .bearerAuth(TestUtil.getTestUserAccessToken(httpClient));
        HttpResponse<?> response = httpClient.toBlocking()
                .exchange(request);

        Assertions.assertEquals(HttpStatus.CREATED, response.status());
        Map<String, Map<String, List<Map<String, String>>>> responseBody = response.getBody(Map.class).get();
        Assertions.assertEquals(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString()),
                responseBody.get(Resource.LINKS).get("self").get(0).get("href"));
        Assertions.assertEquals(expected, blogEntryCaptor.getValue());
    }

    @Test
    void testGetBlog_happyPath() {
        Mockito.when(blogServiceMock.getBlog(BLOG_ID))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .build()));

        HttpRequest<BlogEntry> request = HttpRequest.GET(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString()));
        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getMetadata().getId());
        Assertions.assertEquals("Cool Blog", responseBody.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }

    @Test
    void testGetBlog_withAuth() {
        Mockito.when(blogServiceMock.getBlog(BLOG_ID))
                .thenReturn(Mono.just(BlogEntry.builder()
                        .metadata(BlogEntryMetadata.builder()
                                .id(BLOG_ID)
                                .blogTitle("Cool Blog")
                                .build())
                        .blogText("Some text")
                        .build()));

        HttpRequest<?> request = HttpRequest.GET(BlogOperations.GET_BLOG_ENDPOINT.replace("{blogId}", BLOG_ID.toString()))
                .bearerAuth(TestUtil.getTestUserAccessToken(httpClient));
        HttpResponse<BlogEntry> response = httpClient.toBlocking()
                .exchange(request);

        Assertions.assertEquals(HttpStatus.OK, response.status());
        BlogEntry responseBody = response.getBody(BlogEntry.class).get();
        Assertions.assertEquals(BLOG_ID, responseBody.getMetadata().getId());
        Assertions.assertEquals("Cool Blog", responseBody.getMetadata().getBlogTitle());
        Assertions.assertEquals("Some text", responseBody.getBlogText());
    }
}
