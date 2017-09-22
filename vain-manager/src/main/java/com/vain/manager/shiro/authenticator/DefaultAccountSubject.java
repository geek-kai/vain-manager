package com.vain.manager.shiro.authenticator;

import com.vain.manager.shiro.exception.AuthenticationException;
import com.vain.manager.shiro.token.Token;

import java.io.Serializable;

public class DefaultAccountSubject extends ShiroSubject implements AccountSubject, Serializable {

    private static final long serialVersionUID = 1L;
    private SubjectInfo subjectInfo;

    @Override
    public void login(Token token) throws AuthenticationException {
        super.login(token);
    }

    @Override
    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

}
