package coop.stlma.tech.protocolsn.blogplugin.controller;

import coop.stlma.tech.protocolsn.api.BlogMetadataOperations;
import coop.stlma.tech.protocolsn.blogplugin.service.BlogService;
import coop.stlma.tech.protocolsn.blogplugin.util.AuthProviderCreds;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.type.Argument;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@MicronautTest
class BlogMetadataControllerTest {

    public static final UUID BLOG_ID = UUID.nameUUIDFromBytes("blogId".getBytes());

    @MockBean
    @Primary
    BlogService blogServiceMock = Mockito.mock(BlogService.class);

    @Inject
    @Client("/")
    HttpClient httpClient;

    ArgumentCaptor<BlogEntry> blogEntryCaptor = ArgumentCaptor.forClass(BlogEntry.class);

    @Test
    void testRecentBlogMetaForUser_happyPath() {
        HttpRequest<BlogEntryMetadata> request = HttpRequest.GET(
                BlogMetadataOperations.GET_USER_BLOG_METADATA.replace("{userId}", AuthProviderCreds.TEST_USER_ID.toString()));

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
    void testGetDefaultBlogMetadataStream_happyPath() {
        HttpRequest<BlogEntryMetadata> request = HttpRequest.GET(
                BlogMetadataOperations.GET_DEFAULT_STREAM_METADATA);

        Mockito.when(blogServiceMock.getDefaultBlogStreamMetadata(25, 0))
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
    void testGetDefaultBlogMetadataStream_pagedRequest() {
        HttpRequest<BlogEntryMetadata> request = HttpRequest.GET(
                BlogMetadataOperations.GET_DEFAULT_STREAM_METADATA + "?limit=10&offset=1");

        Mockito.when(blogServiceMock.getDefaultBlogStreamMetadata(10, 1))
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
}
