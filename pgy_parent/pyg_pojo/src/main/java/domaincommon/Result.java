package domaincommon;

import java.io.Serializable;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/8 22:02
 */
public class Result implements Serializable {

    // 返回成功的信息
    private Boolean success;

    // 返回结果的信息
    private String message;

    public Result() {
    }

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
