package br.com.alexei.websiteSpringBoot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String uri = request.getRequestURI();
    	
		if(uri.endsWith("cadastroUsuarios") ||
			uri.endsWith("carregarUsuariosAjax") ||
			uri.contains("carregarUsuarioAjax") ||
			uri.endsWith("adicionarUsuarioAjax") ||
			uri.endsWith("alterarUsuarioAjax") ||
			uri.contains("removerUsuarioAjax") ||
			uri.endsWith("login") ||
			uri.endsWith("logar") || 
			uri.contains("images") ||
			uri.contains("resources") ||
			uri.contains("webjars")) {
			return true;
		}
		
		if(request.getSession().getAttribute("usuarioLogado")!=null) return true;
		
		response.sendRedirect("login");
		return false;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
 
}