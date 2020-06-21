package group.bigone.api.controller.v1;

import group.bigone.api.advice.exception.FailScrapIDCardException;
import group.bigone.api.advice.exception.NoDataCodeException;
import group.bigone.api.common.constants.ProcessStepCode;
import group.bigone.api.common.constants.StateCode;
import group.bigone.api.entity.IDCard;
import group.bigone.api.entity.ProcessStep;
import group.bigone.api.model.response.CommonResult;
import group.bigone.api.model.response.SingleResult;
import group.bigone.api.service.IDCardService;
import group.bigone.api.service.ProcessStepService;
import group.bigone.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"Bank"})
@RestController
@RequestMapping(value = "/v1")
public class IDCardController {
    private final ResponseService responseService;
    private final IDCardService idCardService;
    private final ProcessStepService processStepService;

    public IDCardController(ResponseService responseService, IDCardService idCardService, ProcessStepService processStepService) {
        this.responseService = responseService;
        this.idCardService = idCardService;
        this.processStepService = processStepService;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Get IDCard Information", notes = "Scan IDCard")
    @PostMapping(value = "/idcardscan")
    public SingleResult<IDCard> IDCardScanner(@RequestPart MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userNo = Long.valueOf(authentication.getName());

        /// Todo : Gateway API 에서 처리 할 것 ( Process Start, Finish )
        // Process Insert
        ProcessStep insertProcessStep = ProcessStep.InsertBuilder()
                .stateCode(StateCode.WAIT.getCode())
                .userNo(userNo)
                .stepType(ProcessStepCode.IDCARD.getCode())
                .build();

        processStepService.InsertProcessStep(insertProcessStep);

        // File Upload
        idCardService.FileUpload(file);

        // Scan IDCard
        IDCard reponseIDCard = idCardService.ScanIDCard(file).orElseThrow(FailScrapIDCardException::new);

        return responseService.getSingleResult(reponseIDCard);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "AccessToken", required = true, dataType = "String", paramType = "header")})
    @ApiOperation(value = "Check IDCard Information", notes = "check IDCard")
    @PostMapping(value = "/checkidcard")
    public CommonResult CheckIdCard(@RequestBody IDCard idCard) {

        // Check IDCard
        Boolean checkFlag = idCardService.CheckIDCard(idCard);

        // Get Process
        ProcessStep processStep = processStepService.FindProcessStep().orElseThrow(NoDataCodeException::new);

        if ( checkFlag ) {
            // Update Sucess ProcessStep
            ProcessStep updateprocessStep = ProcessStep.UpdateBuilder()
                    .seqNo(processStep.getSeqNo())
                    .stateCode(StateCode.SUCCESS.getCode())
                    .build();
            processStepService.UpdateProcessStep(updateprocessStep);

            return responseService.getSuccessResult();
        }
        else {
            // Update Fail ProcessStep
            ProcessStep updateprocessStep = ProcessStep.UpdateBuilder()
                    .seqNo(processStep.getSeqNo())
                    .stateCode(StateCode.FAILURE.getCode())
                    .build();
            processStepService.UpdateProcessStep(updateprocessStep);

            return responseService.getFailResult();
        }
    }
}
