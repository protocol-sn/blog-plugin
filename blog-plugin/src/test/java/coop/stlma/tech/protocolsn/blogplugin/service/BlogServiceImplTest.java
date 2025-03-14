package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.context.annotation.Primary;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

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
