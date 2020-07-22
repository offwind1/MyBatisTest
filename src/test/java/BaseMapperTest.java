import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import tk.mybatis.simple.domain.SysUser;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.test.MyMapperProxy;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Proxy;
import java.util.List;

public class BaseMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }


    @Test
    public void test() {
        SqlSession sqlSession = getSqlSession();
        MyMapperProxy userMapperProxy = new MyMapperProxy(tk.mybatis.simple.test.UserMapper.class, sqlSession);

        tk.mybatis.simple.test.UserMapper userMapper = (tk.mybatis.simple.test.UserMapper) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{tk.mybatis.simple.test.UserMapper.class},
                userMapperProxy
        );

        List<SysUser> users = userMapper.selectAll();
    }

    public interface ITest<T> {
        void test(T mapper);
    }

    public <T> void testSession(ITest test, Class<T> cls) {
        SqlSession sqlSession = getSqlSession();
        try {
            T userMapper = sqlSession.getMapper(cls);
            test.test(userMapper);
        } finally {
            sqlSession.close();
        }
    }

    public <T> void testRollbackSession(ITest test, Class<T> cls) {
        SqlSession sqlSession = getSqlSession();
        try {
            T userMapper = sqlSession.getMapper(cls);
            test.test(userMapper);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    public <T> void testCommitSession(ITest test, Class<T> cls) {
        SqlSession sqlSession = getSqlSession();
        try {
            T userMapper = sqlSession.getMapper(cls);
            test.test(userMapper);
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }


}
