package com.yc.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerService implements Runnable {
	private Socket socket;
	private InputStream iis;
	private OutputStream oos;
	
	public ServerService(  Socket socket ){
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
			this.iis=  this.socket.getInputStream();
			this.oos=this.socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			//创建请求对象: 它的作用是就是解析http请求协议
			HttpServletRequest request=new HttpServletRequest(  this.iis   );
			request.parse(); //完成解析 ->  在request对象中就会很多的信息( http请求头信息) 
			
			//创建响应对象
			HttpServletResponse response=new HttpServletResponse( request, this.oos);
			response.sendRedirect();  //重定向:  从request中取出   uri的地址,     判断uri是否存在( 在webapps中是否存在)  ，不存在，读取404.html页面，以输出流的形式返回客户机
			                                             															//以输入流的方式读取这个资源,再以输出流的形式返回给客户机
			
			//http协议是一个无状态. 基于请求/响应
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
