package com.bookmystay.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;

public class CommonException extends RuntimeException {

    private final Response response;
    private final Kind kind;
    private int statusCode;
    private String message;

    private CommonException(String message, Response response, Kind kind, Throwable exception) {
        super(message, exception);
        this.response = response;
        this.message = message;
        this.kind = kind;
        if (response != null) {
            statusCode = response.code();
        }
    }

    public static CommonException checkException(Throwable exception) {
        if (exception == null) {
            return null;
        }
        if (exception instanceof HttpException) {
            return httpError((HttpException) exception);
        } else if (exception instanceof IOException) {
            return networkError((IOException) exception);
        }
        return unexpectedError(exception);
    }

    private static CommonException httpError(HttpException httpException) {
        String message = httpException.code() + " " + httpException.message();
        return new CommonException(message, httpException.response(), Kind.HTTP, null);
    }

    private static CommonException networkError(IOException exception) {
        return new CommonException(exception.getMessage(), null, Kind.NETWORK, exception);
    }

    private static CommonException unexpectedError(Throwable exception) {
        return new CommonException(exception.getMessage(), null, Kind.UNEXPECTED, exception);
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * HTTP response body converted to specified {@code type}. {@code null} if there is no response.
     */
    public <T> T getErrorBodyAs(Class<T> type) {
        if (response.errorBody() == null) {
            return null;
        }
        Gson gson =
                new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
        try {
            String json = response.errorBody().string();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isNetworkError() {
        return kind == Kind.NETWORK;
    }

    public boolean isUnauthenticated() {
        return statusCode == HTTP_FORBIDDEN;
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500 && statusCode != HTTP_FORBIDDEN;
    }

    public boolean isServerError() {
        return statusCode >= 500 && statusCode < 600;
    }

    /**
     * Identifies the event kind which triggered a {@link CommonException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}
