package coop.stlma.tech.protocolsn.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * A blog entry
 *
 * @author John Meyerin
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Serdeable
public class BlogEntry {
    /**
     * Unique identifier for the blog
     */
    private UUID id;

    /**
     * Unique identifier referencing the author
     */
    private UUID author;

    /**
     * Title of the blog
     */
    private String blogTitle;

    /**
     * Text of the blog
     */
    private String blogText;

    /**
     * Comma-separated list of tags
     */
    private String tags;

    /**
     * Timestamp when the blog was created
     */
    private Instant createdAt;

    /**
     * Timestamp when the blog was last updated
     */
    private Instant updatedAt;
}
