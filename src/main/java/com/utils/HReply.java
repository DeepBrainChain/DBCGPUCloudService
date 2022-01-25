package com.utils;


import lombok.Data;

import java.io.Serializable;

import com.utils.HReplyStatusEnum;
import com.utils.HReplyStatusEnumEnglish;


/**
 * @author shijie 2018/5/18
 */
@Data
public class HReply implements Serializable {
    /**1:表示成功，-1：表示失败**/
    private Integer status;
    /** 0001~9999： 表示错误，其他值保留用于将来扩展 **/
    private String code;
    /**错误信息**/
    private String msg;
    private Object content;

    public HReply(HReplyStatusEnum replyEnum) {
        this.status =replyEnum.getStatus();
        this.code = replyEnum.getCode();
        this.msg = replyEnum.getMsg();
    }

    public HReply(HReplyStatusEnumEnglish  replyEnum) {
        this.status =  replyEnum.getStatus();
        this.code =  replyEnum.getCode();
        this.msg =  replyEnum.getMsg();
    }
    
    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public HReply setContentHReply(Object content) {
        this.content = content;
        return this;
    }

    public static HReply createHReply(HReplyStatusEnum hReplyStatusEnum,String language) {
    	
    	if (language==null||language.equals("")||language.equals("CN")||language.equals("cn")||language.equals("SimplifiedChinese")) {
    		   		   		 		
    		return new HReply(hReplyStatusEnum);
    		 
		}else if (language.equals("EN")) {
			
			String code =hReplyStatusEnum.getCode();    		
    		String msg="";    		
    		for (HReplyStatusEnumEnglish replyEnum:HReplyStatusEnumEnglish.values()) {
    			if (code.equals(replyEnum.getCode())) {
    				msg=replyEnum.getMsg();
    				break;
				}
    			
    	    }
    		
    		hReplyStatusEnum.setMsg(msg);    		
    		return new HReply(hReplyStatusEnum);
		}
    	
    	return new HReply(hReplyStatusEnum);
       
    }
}
