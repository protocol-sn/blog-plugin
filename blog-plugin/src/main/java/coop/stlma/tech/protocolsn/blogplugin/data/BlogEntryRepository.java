package coop.stlma.tech.protocolsn.blogplugin.data;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * CrUD repository for {@link BlogEntryEntity}
 *
 * @author John Meyerin
 */
@Repository
public interface BlogEntryRepository extends ReactorCrudRepository<BlogEntryEntity, UUID> {

    /**
     * Get a blogger's most recent entries in reverse chronological order.
     * @param userId    Id of the blogger
     * @return          the blogs
     */
    Flux<BlogEntryEntity> findByAuthorOrderByCreatedAtDesc(UUID userId);

    /**
     * Get a blogger's most recent entry
     * @param userId    Id of the blogger
     * @return          the blog
     */
    Mono<BlogEntryEntity> findFirstByAuthorOrderByCreatedAtDesc(UUID userId);

    /**
     * Get the most recent blog entry
     * @return          the blog
     */
    Mono<BlogEntryEntity> findFirstOrderByCreatedAtDesc();

    /**
     * Reverse chronological order of blogs
     * @param pageable  pagination info
     * @return          the blogs
     */
    Flux<BlogEntryEntity> findAllOrderByCreatedAtDesc(Pageable pageable);
}
