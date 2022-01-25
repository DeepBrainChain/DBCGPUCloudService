package com.dbc.dao;

import com.dbc.entity.ResultHashEntity;

public interface ResultHashDao {

                void Saveresult (ResultHashEntity resultHashEntity,String wallet,String signature,String machine_id);

                ResultHashEntity GetRHash (String machine_id);
}
