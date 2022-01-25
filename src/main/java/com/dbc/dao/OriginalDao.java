package com.dbc.dao;

import com.dbc.entity.Original;

import java.util.List;

public interface OriginalDao {

                Original getOriginal (String machine_id);

                void SaveOrigin (Original original);
}
