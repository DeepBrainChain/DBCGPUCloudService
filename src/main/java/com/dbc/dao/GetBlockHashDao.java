package com.dbc.dao;

import com.dbc.entity.GetBlockHashEntity;

public interface GetBlockHashDao {

                public void insertBlockHash (GetBlockHashEntity getBlockHashEntity);

                // 查询当前快高
                Integer FindHash (int id);
}
