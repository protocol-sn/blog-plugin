package coop.stlma.tech.protocolsn.blogplugin.util;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
import coop.stlma.tech.protocolsn.model.BlogEntry;
import coop.stlma.tech.protocolsn.model.BlogEntryMetadata;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;
import java.util.UUID;

public class TestUtil {

    public static String getTestUserAccessToken(HttpClient httpClient, String userName, String password) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
        HttpRequest<?> request = HttpRequest.POST("/login", creds);

        HttpResponse<BearerAccessRefreshToken> rsp = httpClient.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        Assertions.assertEquals(HttpStatus.OK, rsp.getStatus());
        return rsp.body().getAccessToken();
    }

    public static String getAdminUserAccessToken(HttpClient httpClient) {
        return getTestUserAccessToken(httpClient, "AdminUser", "AdminPass");
    }

    public static String getTestUserAccessToken(HttpClient httpClient) {
        return getTestUserAccessToken(httpClient, "TestUser", "TestPass");
    }

    public static BlogEntryEntity makeEntity(String seed) {
        return new BlogEntryEntity(
                null,
                UUID.nameUUIDFromBytes(("auth"+seed).getBytes()),
                seed, seed + "TEXT",
                "One,Two",
                "plaintext",
                Instant.ofEpochMilli(1741898462967L),
                Instant.ofEpochMilli(1741898462967L));
    }

    public static BlogEntry makeModel(String seed) {
        return BlogEntry.builder()
                .metadata(BlogEntryMetadata.builder()
                        .id(UUID.nameUUIDFromBytes(("auth"+seed).getBytes()))
                        .blogTitle(seed)
                        .tags("One,Two")
                        .createdAt(Instant.ofEpochMilli(1741898462967L))
                        .updatedAt(Instant.ofEpochMilli(1741898462967L))
                        .build())
                .blogText(seed + "TEXT")
                .build();
    }

    public static boolean modelEntityCompare(BlogEntry model, BlogEntryEntity entity) {
        return model.getMetadata().getId().equals(entity.getId()) &&
                model.getMetadata().getBlogTitle().equals(entity.getBlogTitle()) &&
                model.getBlogText().equals(entity.getBlogText()) &&
                model.getMetadata().getTags().equals(entity.getTags()) &&
                model.getMetadata().getCreatedAt().equals(entity.getCreatedAt()) &&
                model.getMetadata().getUpdatedAt().equals(entity.getUpdatedAt());
    }
}
