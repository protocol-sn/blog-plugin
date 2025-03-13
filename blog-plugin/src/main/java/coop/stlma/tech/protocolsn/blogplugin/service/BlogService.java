package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.model.BlogEntry;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BlogService {

    Mono<BlogEntry> getBlogEntry(UUID id);
}
