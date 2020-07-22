import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import tk.mybatis.simple.domain.SysRole;
import tk.mybatis.simple.domain.SysUser;
import tk.mybatis.simple.mapper.UserMapper;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1l);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRoleByUserId() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                List<SysRole> roleList = mapper.selectRolesByUserId(1L);
            }
        }, UserMapper.class);
    }

    @Test
    public void testInsert() {
        testRollbackSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = new SysUser();
                user.setUserName("test1");
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                user.setUserInfo("test info");
                user.setHeadImg(new byte[]{1, 2, 3});
                user.setCreateTime(new Date());
                int result = mapper.insert(user);
                Assert.assertEquals(1, result);
                Assert.assertNull(user.getId());
            }
        }, UserMapper.class);
    }

    @Test
    public void testInsert2() {
        testRollbackSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = new SysUser();
                user.setUserName("test1");
                user.setUserPassword("123456");
                user.setUserInfo("test info");
                user.setCreateTime(new Date());

                int result = mapper.insert2(user);
                Assert.assertEquals(1, result);

                user = mapper.selectById(user.getId());
                Assert.assertEquals("test@mybatis.tk", user.getUserEmail());

            }
        }, UserMapper.class);
    }

    @Test
    public void testInsert3() {
        testCommitSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = new SysUser();
                user.setUserName("test1");
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                user.setUserInfo("test info");
                user.setHeadImg(new byte[]{1, 2, 3});
                user.setCreateTime(new Date());

                int result = mapper.insert3(user);
                Assert.assertEquals(1, result);
                Assert.assertNotNull(user.getId());
            }
        }, UserMapper.class);
    }

    @Test
    public void testUpdateById() {
        testCommitSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = mapper.selectById(1009L);
                Assert.assertEquals("test1", user.getUserName());

                user.setUserName("test_test");
                user.setUserEmail("123123@bbbb.tk");
                int result = mapper.updateById(user);
                Assert.assertEquals(1, result);

                user = mapper.selectById(1009L);
                Assert.assertEquals("test_test", user.getUserName());
            }
        }, UserMapper.class);
    }


    @Test
    public void testDeleteById() {
        testRollbackSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = mapper.selectById(1009L);
                Assert.assertNotNull(user);

                Assert.assertEquals(1, mapper.deleteById(1009L));
                Assert.assertNull(mapper.selectById(1009L));

                SysUser user2 = mapper.selectById(1008L);
                Assert.assertNotNull(user2);
                Assert.assertEquals(1, mapper.deleteByUser(user2));
                Assert.assertNull(mapper.selectById(1008L));
            }
        }, UserMapper.class);
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                List<SysRole> userList = mapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
                Assert.assertNotNull(userList);
                Assert.assertTrue(userList.size() > 0);
            }
        }, UserMapper.class);
    }

    @Test
    public void testselectRolesByUserAndRole() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = mapper.selectById(1L);
                SysRole role = new SysRole();
                role.setEnabled(1);
                List<SysRole> userList = mapper.selectRolesByUserAndRole(user, role);
                Assert.assertNotNull(userList);
                Assert.assertTrue(userList.size() > 0);
            }
        }, UserMapper.class);
    }


    @Test
    public void testSelectByUser() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser query = new SysUser();
                query.setUserName("ad");
                List<SysUser> userList = mapper.selectByUser(query);
                Assert.assertTrue(userList.size() > 0);

                query = new SysUser();
                query.setUserEmail("test@mybatis.tk");
                userList = mapper.selectByUser(query);
                Assert.assertTrue(userList.size() > 0);

                query = new SysUser();
                query.setUserName("ad");
                query.setUserEmail("test@mybatis.tk");
                userList = mapper.selectByUser(query);
                Assert.assertTrue(userList.size() == 0);
            }
        }, UserMapper.class);
    }

    @Test
    public void testUpdateByIdSelective() {
        testRollbackSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = new SysUser();
                user.setId(1l);
                user.setUserEmail("test@mybatis.tk");
                int result = mapper.updateByIdSelective(user);
                Assert.assertEquals(1, result);
                user = mapper.selectById(1l);
                Assert.assertEquals("admin", user.getUserName());
                Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
            }
        }, UserMapper.class);
    }

    @Test
    public void testSelectByIdOrUserName() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser query = new SysUser();
                query.setId(1l);
                query.setUserName("admin");

                SysUser user = mapper.selectByIdOrUserName(query);
                Assert.assertNotNull(user);

                query.setId(null);
                user = mapper.selectByIdOrUserName(query);
                Assert.assertNotNull(user);

                query.setUserName(null);
                user = mapper.selectByIdOrUserName(query);
                Assert.assertNull(
                        user
                );
            }
        }, UserMapper.class);
    }

    @Test
    public void testSelectByIdList() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                List<Long> idList = new ArrayList<Long>();
                idList.add(1l);
                idList.add(1001l);

                List<SysUser> userList = mapper.selectByIdList(idList);
                Assert.assertEquals(2, userList.size());
            }
        }, UserMapper.class);
    }


    @Test
    public void testInsertList() {
        testRollbackSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                List<SysUser> userList = new ArrayList<SysUser>();
                for (int i = 0; i < 2; i++) {
                    SysUser user = new SysUser();
                    user.setUserName("test" + i);
                    user.setUserPassword("123456");
                    user.setUserEmail("test@mybatis.tk");
                    userList.add(user);
                }

                int result = mapper.insertList(userList);
                Assert.assertEquals(2, result);

                for (SysUser user : userList) {
                    System.out.println(user.getId());
                }
            }
        }, UserMapper.class);
    }


    @Test
    public void testUpdateByMap() {
        testRollbackSession(new ITest<UserMapper>() {

            @Override
            public void test(UserMapper mapper) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", 1l);
                map.put("user_email", "test@mybatis.tk");
                map.put("user_password", "12345678");

                mapper.updateByMap(map);
                SysUser user = mapper.selectById(1l);
                Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
            }
        }, UserMapper.class);
    }


    @Test
    public void testSelectUserAndRoleById() {
        testSession(new ITest<UserMapper>() {

            @Override
            public void test(UserMapper mapper) {
                SysUser user = mapper.selectUserAndRoleById(1001L);
                Assert.assertNotNull(user);
                Assert.assertNotNull(user.getRole());
            }
        }, UserMapper.class);
    }


    @Test
    public void testSelectUserAndRoleByIdSelect() {
        testSession(new ITest<UserMapper>() {
            @Override
            public void test(UserMapper mapper) {
                SysUser user = mapper.selectUserAndRoleByIdSelect(1001l);
                Assert.assertNotNull(user);
                System.out.println("调用 user.getRole");
                Assert.assertNotNull(user.getRole());
            }
        }, UserMapper.class);
    }

}
