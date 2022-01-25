package com.dbc.dao;

import com.dbc.entity.TaskIdEntity;

public interface TaskIdDao {

                void Save_TaskId (TaskIdEntity taskIdEntity);


                String Query_id (String machine_id);
}
