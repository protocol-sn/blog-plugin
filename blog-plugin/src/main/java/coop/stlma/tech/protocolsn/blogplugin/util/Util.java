package coop.stlma.tech.protocolsn.blogplugin.util;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.model.BlogEntry;

/**
 * Blog-related utilities
 *
 * @author John Meyerin
 */
public class Util {
    private Util() {}

    /**
     * Converts a blog entry entity to a blog entry model
     *
     * @param blogEntryEntity entity to convert
     * @return blog entry model
     */
    public static BlogEntry toModel(BlogEntryEntity blogEntryEntity) {
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
}
