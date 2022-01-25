package com.dbc.dao;

import com.dbc.entity.LCMachineEntity;

import java.util.List;

public interface LCMachineDao {

                    void InsertLCM (LCMachineEntity lcMachine);

                    LCMachineEntity getMachineInfo (String wallet);

                    List<LCMachineEntity> FindAllMachine ();
}
