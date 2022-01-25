package com.dbc.controller.RustController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.LCOpsDao;
import com.dbc.entity.LCOpsEntity;
import com.dbc.service.DBService.LCOpsService;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@CrossOrigin
public class LCOpsController {

                    @Autowired
                    LCOpsService lcOpsService;

                    @Autowired
                    LCOpsDao lcOpsDao;

                    @ResponseBody
                    @RequestMapping("/leaseCommittee_committeeOps")
                    public Object getlcops() throws URISyntaxException {
                        return lcOpsService.LCO();
                    }

                    @ResponseBody
                    @RequestMapping("/getops")
                    public Object get(String mid, String booked_committee) {
                        return lcOpsDao.getOPsInfo(mid,booked_committee);
                    }

//                    @ResponseBody
//                    @RequestMapping("/DeleteCommittee")
//                    public void Del(){
//
//                        lcOpsService.Del();
//                    }


                    @ResponseBody
                    @RequestMapping("/book")
                    public JSONArray info(){
                       return lcOpsDao.booked_committee();
                    }
    
                    @ResponseBody
                    @RequestMapping("/getInfo")
                    public Object get(String mid) {
                        return lcOpsDao.getIdInfo(mid);
                    }


}
