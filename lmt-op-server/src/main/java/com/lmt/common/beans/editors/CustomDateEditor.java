package com.lmt.common.beans.editors;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;
/**
 * 
 * @author ducx
 * @date 2017-07-04
 * spring mvc支持date绑定
 *
 */
public class CustomDateEditor extends PropertyEditorSupport {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text == null || !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		text = text.replace("T", " ");
		text = text.replace("/", "-").trim();
		if(text.length() == 10){
			try {
				setValue(sdf1.parseObject(text));
				return;
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
		try {
			setValue(sdf.parseObject(text));
			return;
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
		}
	}
	
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? sdf.format(value) : "");
	}
	
}
