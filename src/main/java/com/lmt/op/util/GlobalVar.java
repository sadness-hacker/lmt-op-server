package com.lmt.op.util;

import com.lmt.common.util.ConfigUtil;

/**
 * 
 * @author ducx
 * @date 2016-11-27
 *
 */
public class GlobalVar {
	/**
	 * 上传文件保存路径
	 */
	public static final String UPLOAD_PATH = ConfigUtil.get("system", "upload.path");
	/**
	 * 站点域名路径
	 */
	public static final String SITE_URL = ConfigUtil.get("system", "site.url");
	/**
	 * 
	 */
	public static final String CDN_URL = ConfigUtil.get("system", "cdn.url");
}
