package com.dbc.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.PageHelp;

import java.util.List;

public interface MachineDetailsDao {
            // 详情保存
            public void SaveDetails (MachineDetailsEntity machineDetailsEntity);

            void UpdateDetails(MachineDetailsEntity machineDetailsEntity);
            // 查询分页 统计
           PageHelp<MachineDetailsEntity> getDetail (String machine_status, String gpu_type, Integer gpu_num, Integer pageNum, Integer pageSize);

           JSONObject countTreatment (String gpu_type);

           List<String> GPUType();

           public JSONObject countGPUCPU(String gpu_type);

           // 返回机器所有者
            MachineDetailsEntity selectOwer (String machine_stash);

            // 查询所有owner
            JSONArray findInfo ();

            JSONArray findid ();

            MachineDetailsEntity FindOne (String machine_id);

            // 删除下线机器
            void deleteMachine (String Machine_id);

            // 订单状态
            void updateOrder(MachineDetailsEntity ma);

}
