package com.aryabarzan.phonebook.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class RestErrorException extends RuntimeException {
    private int status;
    private int code;
    private String message;
    private String developerMessage;
    private String moreInfoUrl;
    private Throwable throwable;
    private Map<String, Object> customData;

    public RestErrorException(int status, int code, String message, String developerMessage, String moreInfoUrl, Throwable throwable) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
        this.moreInfoUrl = moreInfoUrl;
        this.throwable = throwable;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            RestErrorException restError = (RestErrorException) o;
            return this.code == restError.code && this.status == restError.status && Objects.equals(this.message, restError.message) && Objects.equals(this.developerMessage, restError.developerMessage) && Objects.equals(this.moreInfoUrl, restError.moreInfoUrl) && Objects.equals(this.throwable, restError.throwable);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.status, this.code, this.message, this.developerMessage, this.moreInfoUrl, this.throwable});
    }

    @Override
    public String toString() {
        return this.append(new StringBuilder(), this.getStatus()).append(", message: ").append(this.getMessage()).append(", code: ").append(this.getCode()).append(", developerMessage: ").append(this.getDeveloperMessage()).append(", moreInfoUrl: ").append(this.getMoreInfoUrl()).toString();
    }

    private StringBuilder append(StringBuilder buf, int status) {
        buf.append(status);
        return buf;
    }

    private String toString(int status) {
        return this.append(new StringBuilder(), status).toString();
    }

    public int getStatus() {
        return this.status;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getDeveloperMessage() {
        return this.developerMessage;
    }

    public String getMoreInfoUrl() {
        return this.moreInfoUrl;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }

    public static class Builder {
        private int status;
        private int code;
        private String message;
        private String developerMessage;
        private String moreInfoUrl;
        private Throwable throwable;
        private Map<String, Object> customData = new LinkedHashMap();

        public Builder setStatus(int statusCode) {
            this.status = statusCode;
            return this;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder setMoreInfoUrl(String moreInfoUrl) {
            this.moreInfoUrl = moreInfoUrl;
            return this;
        }

        public Builder setThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder addCustomData(String key, Object value) {
            this.customData.put(key, value);
            return this;
        }

        public RestErrorException build() {
            if (this.status == 0) {
                this.status = 500;
            }

            if (this.moreInfoUrl == null) {
                this.moreInfoUrl = "mailto:support@bgleam.com";
            }

            RestErrorException restError = new RestErrorException(this.status, this.code, this.message, this.developerMessage, this.moreInfoUrl, this.throwable);
            restError.setCustomData(this.customData);
            return restError;
        }
    }

    private static final long serialVersionUID = -2936261088813110682L;

    public RestErrorException() {
    }

    public RestErrorException(String message) {
        super(message);
    }

    public RestErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestErrorException(Throwable cause) {
        super(cause);
    }

    public RestErrorException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
