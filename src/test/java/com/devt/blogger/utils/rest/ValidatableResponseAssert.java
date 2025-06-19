package com.devt.blogger.utils.rest;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.equalTo;

public class ValidatableResponseAssert extends AbstractAssert<ValidatableResponseAssert, ValidatableResponse> {

    private Map<String, Object> responseJson;
    private List<Object> responseList;

    public ValidatableResponseAssert(ValidatableResponse actual) {
        super(actual, ValidatableResponseAssert.class);
    }

    public static ValidatableResponseAssert assertThat(ValidatableResponse actual) {
        return new ValidatableResponseAssert(actual);
    }

    public ValidatableResponseAssert hasStatusCode(int expectedStatusCode) {
        actual.statusCode(expectedStatusCode);
        return this;
    }

    public ValidatableResponseAssert hasBodyText(String expectedBody) {
        actual.contentType(ContentType.TEXT).body(equalTo(expectedBody));
        return this;
    }

    public ValidatableResponseAssert hasNotFoundErrorResponse(String message) {
        hasError("NOT_FOUND", message);
        return this;
    }

    public ValidatableResponseAssert hasBadRequestErrorResponse(String message) {
        hasError("BAD_REQUEST", message);
        return this;
    }

    public ValidatableResponseAssert hasError(String code, String message) {
        containsBodyProperty("errorCode", code);
        containsBodyProperty("errorMessage", message);
        return this;
    }

    public ValidatableResponseAssert containsBodyProperty(String key, String value) {
        setJsonResponse();
        if (key.contains(".")) {
            String[] keys = key.split("\\.");
            Map<String, Object> currentMap = responseJson;
            for (int i = 0; i < keys.length - 1; i++) {
                currentMap = (Map<String, Object>) currentMap.get(keys[i]);
                if (currentMap == null) {
                    throw new AssertionError("Key path not found: " + key);
                }
            }
            Assertions.assertThat(currentMap).containsEntry(keys[keys.length - 1], value);
        } else {
            Assertions.assertThat(responseJson).containsEntry(key, value);
        }
        return this;
    }

    public ValidatableResponseAssert hasArraySize(int expectedSize) {
        setListResponse();
        Assertions.assertThat(responseList).hasSize(expectedSize);
        return this;
    }

    public <T> ValidatableResponseAssert satisfiesInArray(int rowIndex,
                                                          Class<T> clazz,
                                                          Consumer<T> satisfyer) {
        setListResponse();
        Assertions.assertThat(rowIndex).isLessThan(responseList.size());
        T element = Json.objectify(responseList.get(rowIndex), clazz);
        satisfyer.accept(element);
        return this;
    }

    public ValidatableResponseAssert containsBodyNotNullKeys(String... keys) {
        setJsonResponse();
        for (String key : keys) {
            Assertions.assertThat(responseJson.get(key))
                    .as("Expected value for key '%s' to be not null", key)
                    .isNotNull();
        }
        return this;
    }

    public Object extractBodyPropertyValue(String key) {
        containsBodyNotNullKeys(key);
        return responseJson.get(key);
    }

    private void setJsonResponse() {
        if (responseJson != null) {
            return;
        }
        responseJson = actual.extract().as(Map.class);
    }

    private void setListResponse() {
        if (responseList != null) {
            return;
        }
        responseList = actual.extract().as(List.class);
    }
}