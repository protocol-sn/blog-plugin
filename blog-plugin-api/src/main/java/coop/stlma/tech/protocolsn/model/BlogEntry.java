package coop.stlma.tech.protocolsn.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * A blog entry
 *
 * @author John Meyerin
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Serdeable
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntry extends BlogEntryMetadata {

    /**
     * Text of the blog
     */
    private String blogText;
}
