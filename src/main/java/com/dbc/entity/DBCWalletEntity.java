package com.dbc.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/***
 * @Author feng
 * @Date 2019年8月13日 下午6:10:22
 * @Email:feng@deepbrain.ai
 * @Description:
 */

@SuppressWarnings("serial")
@Document(collection="DBCWalletInfo") 
@Getter
@Setter
public class DBCWalletEntity implements Serializable{

		@Id
		String only_key;  // 主键 钱包地址加机器ID
		String machine_id; // 机器ID
		String wallet;	// 钱包地址
		String wif;	 	// 私钥
		String random_number;  // 随机数

	
}
