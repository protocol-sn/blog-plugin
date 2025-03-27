package coop.stlma.tech.protocolsn.blogplugin.data;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.blogplugin.util.RepositoryTest;
import coop.stlma.tech.protocolsn.blogplugin.util.TestUtil;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Sort;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.awt.*;
import java.util.List;

@MicronautTest(startApplication = false)
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogEntryRepositoryTest extends RepositoryTest {

    @Inject
    BlogEntryRepository blogEntryRepository;

    @Test
    void testFindAllOrderByCreatedAtDesc_happyPath() throws Exception {
        blogEntryRepository.deleteAll().block();
        long count = blogEntryRepository.count().block();
        Assertions.assertEquals(0, count);

        BlogEntryEntity blog1 = TestUtil.makeEntity("my blog");
        BlogEntryEntity blog2 = TestUtil.makeEntity("my blog 2");
        BlogEntryEntity blog3 = TestUtil.makeEntity("my blog 3");
        BlogEntryEntity blog4 = TestUtil.makeEntity("my blog 4");
        blogEntryRepository.save(blog1).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog2).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog3).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog4).block();

        List<BlogEntryEntity> result = blogEntryRepository.findAllOrderByCreatedAtDesc(Pageable.from(1, 2)).collectList().block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals("my blog 2", result.get(0).getBlogTitle());
        Assertions.assertEquals("my blog", result.get(1).getBlogTitle());
    }

    @Test
    void testFindFirstOrderByCreatedAtDesc_happyPath() throws Exception {
        BlogEntryEntity blog1 = TestUtil.makeEntity("my blog");
        BlogEntryEntity blog2 = TestUtil.makeEntity("my blog 2");
        blog2.setAuthor(blog1.getAuthor());
        BlogEntryEntity blog3 = TestUtil.makeEntity("my blog 3");
        blogEntryRepository.save(blog1).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog2).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog3).block();

        BlogEntryEntity result = blogEntryRepository.findFirstOrderByCreatedAtDesc().block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(blog3.getId(), result.getId());
    }

    @Test
    void testFindFirstByAuthorOrderByCreatedAtDesc_happyPath() throws Exception {
        BlogEntryEntity blog1 = TestUtil.makeEntity("my blog");
        BlogEntryEntity blog2 = TestUtil.makeEntity("my blog 2");
        blog2.setAuthor(blog1.getAuthor());
        BlogEntryEntity blog3 = TestUtil.makeEntity("my blog 3");
        blogEntryRepository.save(blog1).block();
        Thread.sleep(10);
        blogEntryRepository.save(blog2).block();
        blogEntryRepository.save(blog3).block();

        BlogEntryEntity result = blogEntryRepository.findFirstByAuthorOrderByCreatedAtDesc(blog1.getAuthor()).block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(blog2.getId(), result.getId());

    }

    @Test
    void testFindByAuthorOrderByCreatedAtDesc_happyPath() throws Exception {
        BlogEntryEntity blog1 = TestUtil.makeEntity("my blog");
        BlogEntryEntity blog2 = TestUtil.makeEntity("my blog 2");
        blog2.setAuthor(blog1.getAuthor());
        BlogEntryEntity blog3 = TestUtil.makeEntity("my blog 3");
        blogEntryRepository.save(blog1).block();
        Thread.sleep(10); //Add a few millis to ensure there is a different date to order by
        blogEntryRepository.save(blog2).block();
        blogEntryRepository.save(blog3).block();

        List<BlogEntryEntity> result = blogEntryRepository.findByAuthorOrderByCreatedAtDesc(blog1.getAuthor()).collectList().block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(blog2.getId(), result.get(0).getId());
        Assertions.assertEquals(blog1.getId(), result.get(1).getId());
    }

    @Test
    void testGetById_happyPath() {
        BlogEntryEntity expected = TestUtil.makeEntity("my blog");

        blogEntryRepository.save(expected).block();
        blogEntryRepository.save(TestUtil.makeEntity("my blog 2")).block();

        BlogEntryEntity result = blogEntryRepository.findById(expected.getId()).block();
        
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected.getId(), result.getId());
        Assertions.assertEquals(expected.getBlogTitle(), result.getBlogTitle());
        Assertions.assertEquals(expected.getBlogText(), result.getBlogText());
        Assertions.assertEquals(expected.getTags(), result.getTags());
        Assertions.assertEquals(expected.getCreatedAt(), result.getCreatedAt());
        Assertions.assertEquals(expected.getUpdatedAt(), result.getUpdatedAt());
    }
}
