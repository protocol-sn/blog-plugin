package coop.stlma.tech.protocolsn.api;

import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Operations for handling metadata around plugins
 *
 * @author John Meyerin
 */
public interface BlogMetadataOperation {
    /**
     * Get the metadata of the most recent blogs for this user in reverse chronological order
     *
     * @param userId    Id of the user
     * @return          The metadata
     */
    Mono<HttpResponse<List<BlogEntryMetadata>>> recentBlogMetaForUser(@PathVariable("userId") UUID userId);

    /**
     * Load a default blog stream metadata
     * @param limit     page size of results. Default 25
     * @param offset    page offset. Default 0
     * @return          Stream of blogs
     */
    Mono<HttpResponse<List<BlogEntryMetadata>>> getDefaultBlogMetadataStream(
            @QueryValue(value = "limit", defaultValue = "25") int limit,
            @QueryValue(value = "offset", defaultValue = "0") int offset);
}
