package coop.stlma.tech.protocolsn.blogplugin.util;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.model.BlogEntry;

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
}
