package com.dbc.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.entity.LCOpsEntity;

import java.util.List;

public interface LCOpsDao {

            public void AddLCO (LCOpsEntity lcOps);

            LCOpsEntity getOPsInfo (String mid,String booked_committee);

//            void DeleteLCO (LCOpsEntity lcOpsEntity);

            JSONArray FindAll ();

//            JSONObject mid();

             JSONArray booked_committee();

             LCOpsEntity getIdInfo (String mid);

             // 修改发送邮件状态
            public void updateInfo(LCOpsEntity lcOpsEntity);
}
