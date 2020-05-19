package com.axxes.traineeship.testing.mocking;

import com.axxes.traineeship.testing.mocking.CreateOrderRequest;
import com.axxes.traineeship.testing.mocking.OoonaRestGateway;
import com.pgssoft.httpclient.HttpClientMock;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OoonaRestGatewayTest {

    private final HttpClientMock httpClientMock = initHttpClientMock();

    private final OoonaRestGateway ooonaRestGateway = new OoonaRestGateway(httpClientMock, "https://vrt.ooona.net/api");

    @Test
    void createOrder_full() {
        httpClientMock.onPost("https://vrt.ooona.net/api/CreateOrder")
                .doReturn("{ \"orderNumber\": 214935.0 }");

        CreateOrderRequest order = new CreateOrderRequest();
        order.setTemplateId(15);
        order.setPackageId("WP00001");
        order.setMovieTitle("MainTitle");
        order.setEpisodeName("AlternativeTitle");
        order.setEpisodeNumber(13);
        order.setSeasonNumber(5);
        order.setCustomer("Canvas");
        order.setDuration("00:25:00");
        order.setDueOn(ZonedDateTime.parse("2020-03-03T20:50:10.100+01:00"));

        int orderNumber = ooonaRestGateway.createOrder(order);

        assertThat(orderNumber).isEqualTo(214935);

        httpClientMock.verify()
                .post("https://vrt.ooona.net/api/CreateOrder")
                .withBody(sameJsonAs("{\n" +
                                     "  \"templateId\": 15,\n" +
                                     "  \"packageId\": \"WP00001\",\n" +
                                     "  \"customer\": \"Canvas\",\n" +
                                     "  \"movieTitle\": \"MainTitle\",\n" +
                                     "  \"episodeName\": \"AlternativeTitle\",\n" +
                                     "  \"seasonNumber\": 5,\n" +
                                     "  \"episodeNumber\": 13,\n" +
                                     "  \"sourceLanguage\" : \"Hebrew\",\n" +
                                     "  \"duration\": \"00:25:00\",\n" +
                                     "  \"dueOn\": \"2020-03-03T20:50:10.1+01:00\"\n" +
                                     "}"))
                .called();
    }

    private HttpClientMock initHttpClientMock() {
        final HttpClientMock httpClientMock = new HttpClientMock();

        httpClientMock.debugOff();

        return httpClientMock;
    }

    private TypeSafeMatcher<String> sameJsonAs(final String expected) {
        return new TypeSafeMatcher<>() {

            private JSONCompareResult jsonCompareResult;

            @Override
            protected boolean matchesSafely(String item) {
                try {
                    jsonCompareResult = JSONCompare.compareJSON(expected, item, JSONCompareMode.STRICT);
                    boolean passed = jsonCompareResult.passed();
                    if (!passed) {
                        System.out.println("Json compare failed: " + jsonCompareResult.getMessage());
                    }
                    return passed;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(jsonCompareResult.getMessage());
            }
        };
    }

}
