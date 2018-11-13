package com.cupd.app.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring前端请求处理器代理类 ,可显示日志输出
 * @author yaoym
 *
 */
@SuppressWarnings("serial")
public class SpringDispatcherServlet extends DispatcherServlet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) {
		if(logger.isDebugEnabled()){
			logger.debug("----------SpringDispatcherServlet .start--------------");
		}
		try {
			
//			request.setCharacterEncoding("GBK");//linux需要注释  
			doPrintRequest(request, response);
			super.doService(request, response);
			doPrintResponse(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(logger.isDebugEnabled()){
			logger.debug("---------SpringDispatcherServlet.end---------------");
		}
	}
    
    public void doPrintRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

    	logger.debug("begin doPrintRequest!");
		Enumeration rnames = request.getParameterNames();
		for (Enumeration e = rnames; e.hasMoreElements(); ) {
		    String thisName = e.nextElement().toString();
		    String thisValue = request.getParameter(thisName);
		    logger.debug(thisName + "请求数据：-------" + thisValue);
		}
		logger.debug("end doPrintRequest!");
    	}
    
    public void doPrintResponse(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

    	logger.debug("begin doPrintResponse!");
    	WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse)response);


    	byte[] data = wrapResponse.getResponseData();
    	logger.debug("返回数据： " + new String(data));


    	logger.debug("end doPrintResponse!");
    	}
}
