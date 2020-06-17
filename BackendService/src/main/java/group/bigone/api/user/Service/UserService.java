package group.bigone.api.user.Service;

import group.bigone.api.user.domain.User;
import group.bigone.api.common.domain.SingleResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final private SqlSession sqlSession;

    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SingleResult<User> selectUser(String userId) {

        SingleResult<User> singleResult = new SingleResult<>();

        User user = (User) sqlSession.selectOne("user.selectUser", userId);

        singleResult.setData(user);

        return singleResult;
    }

    public SingleResult<User> selectProviderUser(String userId, String provider) {

        SingleResult<User> singleResult = new SingleResult<>();

        User user = (User) sqlSession.selectOne("user.selectProviderUser", userId);

        singleResult.setData(user);

        return singleResult;
    }

    public SingleResult<List<User>> selectUsers() {

        SingleResult<List<User>> singleResult = new SingleResult<>();

        List<User> userList = (List) sqlSession.selectList("user.selectUsers");

        singleResult.setData(userList);

        return singleResult;
    }

    public void deleteUser(String userId) {
        sqlSession.delete("user.deleteUser", userId);
    }

    public void insertUser(User user) {
        sqlSession.insert("user.insertUser", user);
    }

    public void updateUser(User user) {
        sqlSession.update("user.updateUser", user);
    }
}
