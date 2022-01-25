package com.dbc.dao;

import com.dbc.entity.Machine_list;

import java.util.List;

public interface MachineListDao {

                List<String> SaveMachine(Machine_list machine_list);

                List<Machine_list> getMachine();

}
