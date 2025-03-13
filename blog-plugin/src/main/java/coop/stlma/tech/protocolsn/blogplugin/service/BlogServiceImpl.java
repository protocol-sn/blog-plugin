package coop.stlma.tech.protocolsn.blogplugin.service;

import coop.stlma.tech.protocolsn.blogplugin.data.BlogEntryRepository;
import coop.stlma.tech.protocolsn.blogplugin.util.Util;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Singleton
public class BlogServiceImpl implements BlogService {

    private final BlogEntryRepository blogEntryRepository;

    public BlogServiceImpl(BlogEntryRepository blogEntryRepository) {
        this.blogEntryRepository = blogEntryRepository;
    }

    @Override
    public Mono<BlogEntry> getBlogEntry(UUID id) {
        return blogEntryRepository.findById(id)
                .map(Util::toModel);
    }
}
