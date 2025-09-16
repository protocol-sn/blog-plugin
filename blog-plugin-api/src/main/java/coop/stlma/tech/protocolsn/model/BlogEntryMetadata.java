package coop.stlma.tech.protocolsn.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable
@Data
public class BlogEntryMetadata {
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
