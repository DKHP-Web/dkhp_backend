package dkhpweb.dkhp_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {
    private Integer statusCode;

    private Boolean succeeded;

    private T result;

    private List<String> errors;

    public static ApiResult succeedBodiless(){
        return new ApiResult(200, true, null, null);
    }

    public static <T> ApiResult succeed(T result){
        return new ApiResult(200, true, result, null);
    }

    public static ApiResult failure(List<String> errors, Integer statusCode){
        return new ApiResult(statusCode, false, null, errors);
    }
}
