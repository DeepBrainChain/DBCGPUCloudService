package com.dbc.dao;

import com.alibaba.fastjson.JSONArray;
import com.dbc.entity.MyVirtualEntity;
import com.utils.HReply;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MyVirtualDao {

                public void addInfo (MyVirtualEntity myVirtualEntity);

                ResponseEntity<HReply> getInfo (String wallet,String language);

                // 修改订单状态
                public void update (MyVirtualEntity myVirtualEntity);

                // 查询未支付的订单
                List<MyVirtualEntity>  getOrder ();

                // 续费 修改使用天数和剩余DBC
                public void renew (MyVirtualEntity virtualEntity);

                // 查询订单天数和DBC
                public MyVirtualEntity Query (String id);

                // 查询所有机器ID
                public JSONArray MachineIdS ();

                // 查询临时浅薄啊地址
                List<MyVirtualEntity> onlyKey ();

                public String getWallet (String only_key);

}
