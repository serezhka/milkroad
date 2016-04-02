package com.tsystems.javaschool.milkroad.util;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 05.03.2016.
 */
public class AuthUtilTest {
    private HttpSession httpSession;
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;
    private UserDTO user4;

    @Before
    public void setUp() throws Exception {
        httpSession = new HttpSessionAdapter() {
            private final Map<String, Object> attributes = new HashMap<>();

            @Override
            public void setAttribute(final String name, final Object value) {
                this.attributes.put(name, value);
            }

            @Override
            public Object getAttribute(final String name) {
                return this.attributes.get(name);
            }
        };
        user1 = new UserDTO();
        user1.setId(1L);
        user1.setUserType(UserTypeEnum.ADMIN);
        user2 = new UserDTO();
        user2.setId(2L);
        user2.setUserType(UserTypeEnum.SELLER);
        user3 = new UserDTO();
        user3.setId(3L);
        user3.setUserType(UserTypeEnum.SELLER); // UserDTOs with same fields are equals
        user4 = new UserDTO();
        user4.setId(4L);
        user4.setUserType(UserTypeEnum.CUSTOMER);
    }

    @Test
    public void testAuthUser() throws Exception {
        AuthUtil.authUser(httpSession, user1);
        Assert.assertTrue(AuthUtil.isUserAuthed(httpSession));
        AuthUtil.authUser(httpSession, null);
        Assert.assertFalse(AuthUtil.isUserAuthed(httpSession));
    }

    @Test
    public void testGetAuthedUser() throws Exception {
        AuthUtil.authUser(httpSession, user2);
        Assert.assertNotNull(AuthUtil.getAuthedUser(httpSession));
        Assert.assertEquals(user2, AuthUtil.getAuthedUser(httpSession));
        Assert.assertNotEquals(user4, AuthUtil.getAuthedUser(httpSession));
    }

    @Test
    public void testGetAuthedUserType() throws Exception {
        AuthUtil.authUser(httpSession, user3);
        Assert.assertNotNull(AuthUtil.getAuthedUserType(httpSession));
        Assert.assertEquals(user3.getUserType(), AuthUtil.getAuthedUserType(httpSession));
        Assert.assertNotEquals(user4.getUserType(), AuthUtil.getAuthedUserType(httpSession));
    }

    @Test
    public void testDeauthUser() throws Exception {
        AuthUtil.authUser(httpSession, user4);
        AuthUtil.deauthUser(httpSession);
        Assert.assertFalse(AuthUtil.isUserAuthed(httpSession));
        Assert.assertNull(AuthUtil.getAuthedUser(httpSession));
    }

    @Test
    public void testDeauthedUserType() throws Exception {
        AuthUtil.authUser(httpSession, user1);
        AuthUtil.deauthUser(httpSession);
        Assert.assertNull(AuthUtil.getAuthedUserType(httpSession));
    }

    private class HttpSessionAdapter implements HttpSession {

        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(final int interval) {
        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(final String name) {
            return null;
        }

        @Override
        public Object getValue(final String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return new String[0];
        }

        @Override
        public void setAttribute(final String name, final Object value) {
        }

        @Override
        public void putValue(final String name, final Object value) {
        }

        @Override
        public void removeAttribute(final String name) {
        }

        @Override
        public void removeValue(final String name) {
        }

        @Override
        public void invalidate() {
        }

        @Override
        public boolean isNew() {
            return false;
        }
    }
}
