package WhereWear.server.wherewear.util;

import org.springframework.http.HttpStatus;

public class ApiUtils {

    public static <T> ApiResultSuccess<T> success(T response) {
        return new ApiResultSuccess<>(response);
    }

    public static ApiResultError error(String message, HttpStatus status) {
        return new ApiResultError(new ApiError(message, status));
    }

    public static class ApiError {
        private final String message;
        private final int status;

        public ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }
    }

    public static class ApiResultError {
        private final boolean success;
        private final ApiError error;

        public ApiResultError(ApiError error) {
            this.success = false;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public ApiError getError() {
            return error;
        }
    }

    public static class ApiResultSuccess<T> {
        private final boolean success;
        private final T response;

        public ApiResultSuccess(T response) {
            this.success = true;
            this.response = response;
        }

        public boolean isSuccess() {
            return success;
        }

        public T getResponse() {
            return response;
        }
    }
}

