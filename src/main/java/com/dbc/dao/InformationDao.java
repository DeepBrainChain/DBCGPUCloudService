package com.dbc.dao;

import com.dbc.entity.InformationEntity;

public interface InformationDao {

            void InsertInformation ();

            InformationEntity getInfo (String machine_id);
}
