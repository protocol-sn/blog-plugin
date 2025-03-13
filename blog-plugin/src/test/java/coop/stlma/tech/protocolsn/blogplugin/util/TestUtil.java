package coop.stlma.tech.protocolsn.blogplugin.util;

import coop.stlma.tech.protocolsn.blogplugin.data.entity.BlogEntryEntity;
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

    public static BlogEntryEntity makeEntity(String myBlog) {
        return new BlogEntryEntity(
                null,
                UUID.nameUUIDFromBytes(("auth"+myBlog).getBytes()),
                myBlog, myBlog + "TEXT",
                "One,Two",
                Instant.ofEpochMilli(1741898462967L),
                Instant.ofEpochMilli(1741898462967L));
    }
}
