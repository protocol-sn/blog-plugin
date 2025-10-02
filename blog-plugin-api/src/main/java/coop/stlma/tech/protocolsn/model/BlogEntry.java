package coop.stlma.tech.protocolsn.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A blog entry
 *
 * @author John Meyerin
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Serdeable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogEntry {

    private BlogEntryMetadata metadata;

    /**
     * Text of the blog
     */
    private String blogText;

    /**
     * Format of the blog
     */
    private String blogFormat;
}
