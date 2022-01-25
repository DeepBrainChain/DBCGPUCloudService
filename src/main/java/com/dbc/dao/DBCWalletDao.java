package com.dbc.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.entity.DBCWalletEntity;

import java.util.List;

/***
 * @Author feng
 * @Date 2019年8月13日 下午6:18:16
 * @Email:feng@deepbrain.ai
 * @Description:
 */
public interface DBCWalletDao {
	// 保存钱包
	public void save(String only_key,String machine_id,String wallet,String wif,String random_number);

	public String getWif(String only_key);

	JSONObject getInfo (String only_key);

	public String getWallet(String only_key);

	// 查询机器ID和钱包地址
	public JSONArray Wallets ();

	// 返回指定地址
	DBCWalletEntity QueryInfo (String only_key);





}
