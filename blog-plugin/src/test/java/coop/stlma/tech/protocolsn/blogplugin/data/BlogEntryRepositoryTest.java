package coop.stlma.tech.protocolsn.blogplugin.data;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.blogplugin.util.RepositoryTest;
import coop.stlma.tech.protocolsn.blogplugin.util.TestUtil;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;

@MicronautTest(startApplication = false)
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogEntryRepositoryTest extends RepositoryTest {

    @Inject
    BlogEntryRepository blogEntryRepository;

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
