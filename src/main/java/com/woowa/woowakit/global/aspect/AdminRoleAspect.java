package com.woowa.woowakit.global.aspect;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.woowa.woowakit.domain.auth.domain.AuthPrincipal;
import com.woowa.woowakit.global.error.UnAuthorizationException;

@Aspect
@Component
public class AdminRoleAspect {

	private static final String MEMBER_KEY = "memberId";

	@Before("@annotation(com.woowa.woowakit.domain.auth.annotation.Admin)")
	public void checkAdminRole() {
		HttpServletRequest request =
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

		AuthPrincipal authPrincipal = (AuthPrincipal)request.getAttribute(MEMBER_KEY);
		if (Objects.isNull(authPrincipal) || !authPrincipal.isAdmin()) {
			throw new UnAuthorizationException();
		}
	}
}
