package org.edu.main.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    String code;
    boolean success;
    T data;
    Map<String, String> errors;


    @Getter
    public enum ApiCode {
        CODE_SUCCESS("0000"),
        CODE_ERROR("9999");

        private final String value;

        ApiCode(String value) {
            this.value = value;
        }

    }

    public static <T> ApiResponse<T> SUCCESS(T data) {
        return new ApiResponse<>(ApiCode.CODE_SUCCESS.getValue(), true, data, null);
    }

    public static <T> ApiResponse<T> ERROR() {
        return new ApiResponse<>(ApiCode.CODE_ERROR.getValue(), false, null, Map.of("System","Procedure execution error"));
    }

    public static <T> ApiResponse<T> ERROR(Map<String, String> errors) {
        return new ApiResponse<>(ApiCode.CODE_ERROR.getValue(), false, null, errors);
    }

}
