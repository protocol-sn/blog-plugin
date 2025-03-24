package coop.stlma.tech.protocolsn.blogplugin.util;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.blogplugin.error.PsnAuthException;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.security.utils.SecurityService;

import java.util.UUID;

/**
 * Common utilities for the blog plugin
 *
 * @author John Meyerin
 */
public class BlogUtil {
    private BlogUtil() { }

    /**
     * Translate an entity to the model
     * @param blogEntryEntity   the entity
     * @return                  the model
     */
    public static BlogEntry mapToModel(BlogEntryEntity blogEntryEntity) {
        return BlogEntry.builder()
                .id(blogEntryEntity.getId())
                .author(blogEntryEntity.getAuthor())
                .blogTitle(blogEntryEntity.getBlogTitle())
                .blogText(blogEntryEntity.getBlogText())
                .tags(blogEntryEntity.getTags())
                .createdAt(blogEntryEntity.getCreatedAt())
                .updatedAt(blogEntryEntity.getUpdatedAt())
                .build();
    }

    /**
     * Translate a model to an entity
     * @param blogEntry the model
     * @return          the entity
     */
    public static BlogEntryEntity mapToEntity(BlogEntry blogEntry) {
        return new BlogEntryEntity(
                blogEntry.getId(),
                blogEntry.getAuthor(),
                blogEntry.getBlogTitle(),
                blogEntry.getBlogText(),
                blogEntry.getTags(),
                blogEntry.getCreatedAt(),
                blogEntry.getUpdatedAt());
    }

    /**
     * Map the entity to a metadata model, losing the blog text
     * @param blogEntryEntity   the entity
     * @return                  the metadata model
     */
    public static BlogEntryMetadata mapToMetaModel(BlogEntryEntity blogEntryEntity) {
        return BlogEntryMetadata.builder()
                .id(blogEntryEntity.getId())
                .author(blogEntryEntity.getAuthor())
                .blogTitle(blogEntryEntity.getBlogTitle())
                .tags(blogEntryEntity.getTags())
                .createdAt(blogEntryEntity.getCreatedAt())
                .updatedAt(blogEntryEntity.getUpdatedAt())
                .build();
    }

    /**
     * Parse the user's id using the MN security service
     * @return          the user's id as UUID
     */
    public static UUID parseUserId(SecurityService securityService) {
        return securityService.getAuthentication().map(authentication -> {
            try {
                return UUID.fromString(String.valueOf(authentication.getAttributes().get("sub")));
            } catch (Exception e) {
                throw new PsnAuthException("Malformatted authentication");
            }
        }).orElseThrow(() -> new PsnAuthException("User not authenticated"));
    }
}
