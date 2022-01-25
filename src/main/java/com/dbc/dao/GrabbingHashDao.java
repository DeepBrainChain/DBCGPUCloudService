package com.dbc.dao;

import com.dbc.entity.GrabbingHashEntity;

public interface GrabbingHashDao {

                void SaveGrabbing(GrabbingHashEntity grabbingHashEntity);

                GrabbingHashEntity GetGraHash (String report_id);
}
