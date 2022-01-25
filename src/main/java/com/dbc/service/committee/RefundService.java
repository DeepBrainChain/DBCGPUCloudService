package com.dbc.service.committee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DBCWalletDao;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.DBCWalletEntity;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.impl.MyVirtualImpl;
import com.dbc.service.SUBDBC.SUBDBCWallet;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *  退币 17点03分
 */

@Service
@Slf4j
public class RefundService {

        @Autowired
        DBCWalletDao dbcWalletDao;

        @Autowired
        SUBDBCWallet subdbcWallet;

        @Autowired
        MyVirtualDao myVirtualDao;
        @Value("${chainUrl}")
    	private   String chainUrl;
        public String ReFund() throws URISyntaxException {

            WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
//                private final List<String>  comm = new ArrayList<>();

                private int count;

                final JSONArray jsonArray = dbcWalletDao.Wallets();

                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                    for (int i=0; i<jsonArray.size(); i++) {
                        DBCWalletEntity dbcWalletEntity = dbcWalletDao.QueryInfo(jsonArray.getString(i));

                        JSONObject dates = JSONObject.parseObject(JSONObject.toJSONString(dbcWalletEntity));
                        String wallet = dates.getString("wallet");
                        String machine_id= dates.getString("machine_id");


                        List<String> f = new ArrayList<>();
                        f.add(wallet);
                        f.add(machine_id);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("jsonrpc", "2.0");
                        jsonObject.put("id", 1);
                        jsonObject.put("method", "rentMachine_getRentOrder");
                        jsonObject.put("params",f);

                        send(jsonObject.toString());
                    }

                }


                @Override
                public void onMessage(String s) {
                    log.info("====收到来自服务端的消息===" + s);


                    JSONObject data = new JSONObject(JSON.parseObject(s));
                    JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));

                    // 判断是否扣过rentStart不为0
                    if (result.get("rentEnd").equals(0)){

                        log.info("临时钱包未有过转账记录");
                    }else {

                      

                        DBCWalletEntity dbcWalletEntity = dbcWalletDao.QueryInfo(jsonArray.getString(count));
                        JSONObject dates = JSONObject.parseObject(JSONObject.toJSONString(dbcWalletEntity));
                        double dbc_count = 0;
                        try {
                            dbc_count = subdbcWallet.getBanlance(chainUrl,dates.getString("wallet"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String number = dates.getString("wif");
                        String fromAddressKey =number.replaceAll("^0[x|X]", ""); // 正则去掉0x
                        try {
                       //  subdbcWallet.transferDBCByPrivateKey(chainUrl,fromAddressKey,myVirtualDao.getWallet(jsonArray.getString(count)),
                        //            dbc_count*1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    this.count+=1;
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("关闭连接:::" + "i = " + i + ":::s = " + s +":::b = " + b);
                }

                @Override
                public void onError(Exception e) {
                    log.error("====出现错误====" + e.getMessage());
                }

            };
            client.connect();
            return null;
        }

//    {
//        "jsonrpc":"2.0",
//            "id":1,
//            "method":"rentMachine_getRentOrder",
//            "params": ["8eaf04151687736326c9fea17e25fc5287613693c912909cb226aa4794f26a48", "5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY"]
//    }

//    返回结果
//    {
//        "jsonrpc": "2.0",
//            "result": {
//        "confirmRent": "0",
//                "rentEnd": "0",
//                "rentStart": "0",
//                "renter": "5C4hrfjw9DjXZTzV3MwzrrAr9P1MJhSrvWGWqi1eSuyUpnhM",
//                "stakeAmount": "0"
//    },
//        "id": 1
//    }

}
