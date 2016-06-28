package ${groupId}.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import ${groupId}.common.utils.RequestUtil;

/**
 * 做一些请求的拦截，比如登陆过滤，安全检查等
 * @author liujinfeng
 *
 */
public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (!RequestUtil.isUserSessionExist()) {
			response.sendRedirect("frontUser/registerIndex");
			return;
		}
		filterChain.doFilter(request, response);
	}


}
