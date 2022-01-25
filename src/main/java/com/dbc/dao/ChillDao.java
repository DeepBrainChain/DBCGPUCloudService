package com.dbc.dao;

import com.dbc.entity.ChillListEntity;

public interface ChillDao {

            void insertChill (ChillListEntity chillListEntity);

            ChillListEntity getChilInfo(int id);
}
