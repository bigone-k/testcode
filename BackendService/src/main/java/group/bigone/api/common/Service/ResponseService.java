package group.bigone.api.common.Service;

import group.bigone.api.common.domain.CommonResult;
import group.bigone.api.common.domain.ListResult;
import group.bigone.api.common.domain.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public enum CommonResponse {
        SUCCESS(0, "success"),
        FAIL(-1, "failure");

        int ResultCode;
        String ResultMessage;

        CommonResponse(int ResultCode, String ResultMessage) {
            this.ResultCode = ResultCode;
            this.ResultMessage = ResultMessage;
        }

        public int getResultCode() {
            return this.ResultCode;
        }

        public String getResultMessage() {
            return this.ResultMessage;
        }
    }


    public  <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<T>();
        result.setData(data);
        setSuccessResult(result);
        return  result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setResultCode(code);
        result.setResultMessage(msg);
        return result;
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setResultCode(CommonResponse.SUCCESS.getResultCode());
        result.setResultMessage(CommonResponse.SUCCESS.getResultMessage());
    }
}
