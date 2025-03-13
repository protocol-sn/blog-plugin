package coop.stlma.tech.protocolsn;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import org.junit.jupiter.api.Assertions;

public class TestUtil {

    public static String getTestUserAccessToken(HttpClient httpClient, String userName, String password) {

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, password);
        HttpRequest<?> request = HttpRequest.POST("/login", creds);

        HttpResponse<BearerAccessRefreshToken> rsp = httpClient.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        Assertions.assertEquals(HttpStatus.OK, rsp.getStatus());
        return rsp.body().getAccessToken();
    }

    public static String getTestUserAccessToken(HttpClient httpClient) {
        return getTestUserAccessToken(httpClient, "TestUser", "TestPass");
    }
}
