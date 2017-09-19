package com.vain.manager.shiro.authenticator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class ShiroSubject implements Subject {

    protected transient Subject delegate = null;

    @Override
    public void logout() {
        getDelegate().logout();
    }

    @Override
    public Object getPrincipal() {
        return this.getDelegate().getPrincipal();
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return this.getDelegate().getPrincipals();
    }

    @Override
    public boolean isPermitted(String permission) {
        return this.getDelegate().isPermitted(permission);
    }

    @Override
    public boolean isPermitted(Permission permission) {
        return this.getDelegate().isPermitted(permission);
    }

    @Override
    public boolean[] isPermitted(String... permissions) {
        return this.getDelegate().isPermitted(permissions);
    }

    @Override
    public boolean[] isPermitted(List<Permission> permissions) {
        return this.getDelegate().isPermitted(permissions);
    }

    @Override
    public boolean isPermittedAll(String... permissions) {
        return this.getDelegate().isPermittedAll(permissions);
    }

    @Override
    public boolean isPermittedAll(Collection<Permission> permissions) {
        return this.getDelegate().isPermittedAll(permissions);
    }

    @Override
    public void checkPermission(String permission)
            throws AuthorizationException {
        this.getDelegate().checkPermission(permission);
    }

    @Override
    public void checkPermission(Permission permission)
            throws AuthorizationException {
        this.getDelegate().checkPermission(permission);
    }

    @Override
    public void checkPermissions(String... permissions)
            throws AuthorizationException {
        this.getDelegate().checkPermissions(permissions);
    }

    @Override
    public void checkPermissions(Collection<Permission> permissions)
            throws AuthorizationException {
        this.getDelegate().checkPermissions(permissions);
    }

    @Override
    public boolean hasRole(String roleIdentifier) {
        return this.getDelegate().hasRole(roleIdentifier);
    }

    @Override
    public boolean[] hasRoles(List<String> roleIdentifiers) {
        return this.getDelegate().hasRoles(roleIdentifiers);
    }

    @Override
    public boolean hasAllRoles(Collection<String> roleIdentifiers) {
        return this.getDelegate().hasAllRoles(roleIdentifiers);
    }

    @Override
    public void checkRole(String roleIdentifier) throws AuthorizationException {
        this.getDelegate().checkRole(roleIdentifier);
    }

    @Override
    public void checkRoles(Collection<String> roleIdentifiers)
            throws AuthorizationException {
        this.getDelegate().checkRoles(roleIdentifiers);
    }

    @Override
    public void checkRoles(String... roleIdentifiers)
            throws AuthorizationException {
        this.getDelegate().checkRoles(roleIdentifiers);
    }

    @Override
    public void login(AuthenticationToken token)
            throws org.apache.shiro.authc.AuthenticationException {
        getDelegate().login(token);

    }

    @Override
    public boolean isAuthenticated() {
        return this.getDelegate().isAuthenticated();
    }

    @Override
    public boolean isRemembered() {
        return this.getDelegate().isRemembered();
    }

    @Override
    public Session getSession() {
        return this.getDelegate().getSession();
    }

    @Override
    public Session getSession(boolean create) {

        return getDelegate().getSession(create);
    }

    @Override
    public <V> V execute(Callable<V> callable) throws ExecutionException {
        return getDelegate().execute(callable);
    }

    @Override
    public void execute(Runnable runnable) {
        this.getDelegate().execute(runnable);
    }

    @Override
    public <V> Callable<V> associateWith(Callable<V> callable) {
        return this.getDelegate().associateWith(callable);
    }

    @Override
    public Runnable associateWith(Runnable runnable) {
        return this.getDelegate().associateWith(runnable);
    }

    @Override
    public void runAs(PrincipalCollection principals)
            throws NullPointerException, IllegalStateException {
        this.getDelegate().runAs(principals);
    }

    @Override
    public boolean isRunAs() {
        return this.getDelegate().isRunAs();
    }

    @Override
    public PrincipalCollection getPreviousPrincipals() {
        return this.getDelegate().getPreviousPrincipals();
    }

    @Override
    public PrincipalCollection releaseRunAs() {
        return this.getDelegate().releaseRunAs();
    }

    private Subject getDelegate() {
        if (this.delegate == null) {
            delegate = SecurityUtils.getSubject();
        }
        return delegate;
    }
}
