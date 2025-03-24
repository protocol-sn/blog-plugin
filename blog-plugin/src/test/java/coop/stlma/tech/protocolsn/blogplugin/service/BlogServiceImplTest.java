package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.blogplugin.util.AuthProviderCreds;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.context.annotation.Primary;
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
import java.util.UUID;
import coop.stlma.tech.protocolsn.blogplugin.util.TestUtil;

@MicronautTest
class BlogServiceImplTest {
    private static final UUID BLOG_ID = UUID.nameUUIDFromBytes("blogId".getBytes());

    @MockBean
    @Primary
    BlogEntryRepository blogEntryRepositoryMock = Mockito.mock(BlogEntryRepository.class);

    @Inject
    BlogServiceImpl blogService;

    ArgumentCaptor<BlogEntryEntity> blogEntryCaptor = ArgumentCaptor.forClass(BlogEntryEntity.class);

    @Test
    void testMostRecentBlogByUser_happyPath() {
        Mockito.when(blogEntryRepositoryMock.findFirstByAuthorOrderByCreatedAtDesc(AuthProviderCreds.TEST_USER_ID))
                .thenReturn(Mono.just(TestUtil.makeEntity("my blog")));

        BlogEntry result = blogService.mostRecentBlogByUser(AuthProviderCreds.TEST_USER_ID).block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("my blog", result.getBlogTitle());
    }

    @Test
    void testQueryBlogMetadata_emptyResult() {
        Mockito.when(blogEntryRepositoryMock.findByAuthorOrderByCreatedAtDesc(AuthProviderCreds.TEST_USER_ID))
                .thenReturn(Flux.empty());

        List<BlogEntryMetadata> result = blogService.queryBlogMetadata(AuthProviderCreds.TEST_USER_ID).collectList().block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testQueryBlogMetadata_happyPath() {
        Mockito.when(blogEntryRepositoryMock.findByAuthorOrderByCreatedAtDesc(AuthProviderCreds.TEST_USER_ID))
                .thenReturn(Flux.just(TestUtil.makeEntity("my blog"),
                        TestUtil.makeEntity("my blog 2")));

        List<BlogEntryMetadata> result = blogService.queryBlogMetadata(AuthProviderCreds.TEST_USER_ID).collectList().block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        result = result.stream().sorted(Comparator.comparing(BlogEntryMetadata::getBlogTitle)).toList();
        Assertions.assertEquals("my blog", result.get(0).getBlogTitle());
        Assertions.assertEquals("my blog 2", result.get(1).getBlogTitle());
    }

    @Test
    void testSaveBlog_happyPath() {
        BlogEntry blogEntry = TestUtil.makeModel("my blog");
        Mockito.when(blogEntryRepositoryMock.save(blogEntryCaptor.capture()))
                .thenReturn(Mono.just(TestUtil.makeEntity("my blog")));

        blogService.saveBlog(blogEntry).block();

        Assertions.assertTrue(TestUtil.modelEntityCompare(blogEntry, blogEntryCaptor.getValue()));
    }

    @Test
    void testGetBlog_happyPath() {
        BlogEntryEntity expected = TestUtil.makeEntity("my blog");
        Mockito.when(blogEntryRepositoryMock.findById(BLOG_ID))
                .thenReturn(Mono.just(expected));

        BlogEntry result = blogService.getBlog(BLOG_ID).block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getBlogTitle(), result.getBlogTitle());
        Assertions.assertEquals(expected.getBlogText(), result.getBlogText());
        Assertions.assertEquals(expected.getTags(), result.getTags());
        Assertions.assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        Assertions.assertEquals(expected.getUpdatedAt(), result.getUpdatedAt());
    }
}
