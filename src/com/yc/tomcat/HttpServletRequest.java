package com.yc.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

/**
 * 封装http请求: 1. 解析请求 2. 取出请求头中资源名
 */
public class HttpServletRequest {
	// 请求的资源地址 /index.html
	private String uri;
	// 输入流
	private InputStream input;
	private String method;
	private String protocalVersion;
	/**
	 * 构造方法
	 */
	public HttpServletRequest(InputStream iis) {
		this.input = iis;
	}
	/**
	 * 解析协议: 最重要的就是uri
	 */
	public void parse() {
		// 1. 从input中读出所有的内容( http请求协议 =》 protocal)
		String protocal = null;
		// TODO: 从流中取protocal
		StringBuffer sb = new StringBuffer(1024 * 10);
		int length = -1;
		byte[] bs = new byte[1024 * 10];
		try {
			length = this.input.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
			length = -1;
		}
		for (int j = 0; j < length; j++) {
			sb.append((char) bs[j]);
		}
		protocal = sb.toString();  
		
		parseUri(protocal);
		
		
	}

	private void parseUri(String protocal) {
//		// //校验操作
//		String[] ss = protocal.split(" ");
//		int code = validateProtocal(ss);
//		if (code != 200) {
//			return;
//		}
		StringTokenizer st = new StringTokenizer(protocal);
		this.method = st.nextToken();
		this.uri = st.nextToken();
		this.protocalVersion = st.nextToken();
	}
//
//	private int validateProtocal(String[] ss) {
//		return 200;
//	}

	public String getUri() {
		return this.uri;
	}
}
