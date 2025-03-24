package coop.stlma.tech.protocolsn.blogplugin.data;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import io.micronaut.data.annotation.Repository;
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

    Flux<BlogEntryEntity> findByAuthorOrderByCreatedAtDesc(UUID userId);

    Mono<BlogEntryEntity> findFirstByAuthorOrderByCreatedAtDesc(UUID userId);
}
