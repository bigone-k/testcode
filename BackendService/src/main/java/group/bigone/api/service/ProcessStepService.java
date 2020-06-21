package group.bigone.api.service;

import group.bigone.api.config.security.JwtTokenProvider;
import group.bigone.api.entity.ProcessStep;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class ProcessStepService {

    private final JwtTokenProvider jwtTokenProvider;
    private final SqlSession sqlSession;

    public ProcessStepService(JwtTokenProvider jwtTokenProvider, SqlSession sqlSession) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.sqlSession = sqlSession;
    }

    //    # Find Process Step
    public Optional<ProcessStep> FindProcessStep() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = jwtTokenProvider.resolveToken(request);
        Long userNo = Long.valueOf(jwtTokenProvider.getUserPk(token));

        ProcessStep processStep = (ProcessStep) sqlSession.selectOne("processstep.selectProcessStep", userNo);

        return Optional.of(processStep);
    }

    //    # Update Process Step
    public void UpdateProcessStep(ProcessStep processStep) {
        sqlSession.update("processstep.updateProcessStep", processStep);
    }

    //    # Insert Process Step
    public void InsertProcessStep(ProcessStep processStep) {
        sqlSession.insert("processstep.insertProcessStep", processStep);
    }
}
