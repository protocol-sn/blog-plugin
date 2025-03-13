package coop.stlma.tech.protocolsn.blogplugin.data;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;

import java.util.UUID;

/**
 * CrUD repository for {@link BlogEntryEntity}
 *
 * @author John Meyerin
 */
@Repository
public interface BlogEntryRepository  extends ReactorCrudRepository<BlogEntryEntity, UUID> {
}
