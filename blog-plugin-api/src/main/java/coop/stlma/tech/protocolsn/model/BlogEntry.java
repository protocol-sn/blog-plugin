package coop.stlma.tech.protocolsn.model;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@Serdeable
public class BlogEntry {
    private UUID id;
    private UUID author;
    private String blogTitle;
    private String blogText;
    private String tags;
    private Instant createdAt;
    private String updatedAt;
}
