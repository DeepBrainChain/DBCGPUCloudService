package com.dbc.dao;

import com.alibaba.fastjson.JSONArray;
import com.dbc.entity.CommitteeListEntity;

public interface CommitteeListDao {

                void SaveCommit(CommitteeListEntity machineCommitteeList);

                JSONArray getComm(String machineId);

                //查询confirm_start_time
                CommitteeListEntity getTime (String machine_id);
}
