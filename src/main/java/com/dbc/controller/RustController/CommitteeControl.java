package com.dbc.controller.RustController;

import com.alibaba.fastjson.JSONArray;
import com.dbc.dao.CommitteeListDao;
import com.dbc.entity.CommitteeListEntity;
import com.dbc.service.DBService.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Controller
@CrossOrigin
public class CommitteeControl {

                @Autowired
                CommitteeService committeeService;

                @Autowired
                CommitteeListDao committeeListDao;

                @ResponseBody
                @RequestMapping("/leaseCommittee_getMachineCommitteeList")
                public Object SaveCommittee() throws URISyntaxException {
                    return  committeeService.Commit();
                }

                @ResponseBody
                @RequestMapping("/getMachineCommittee")
                public JSONArray getId(String machineId){
                    return committeeListDao.getComm(machineId);
                }
}