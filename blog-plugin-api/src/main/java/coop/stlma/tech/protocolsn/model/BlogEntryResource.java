package coop.stlma.tech.protocolsn.model;

import io.micronaut.http.hateoas.AbstractResource;
import io.micronaut.serde.annotation.Serdeable;

/**
 * Representation of a HATEOAS resource
 *
 * @author John Meyerin
 */
@Serdeable
public class BlogEntryResource extends AbstractResource<BlogEntryResource> {
}
