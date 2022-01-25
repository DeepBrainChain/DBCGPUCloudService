package com.dbc.controller.RustController;

import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.MachineDetailsDao;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.PageHelp;
import com.dbc.service.DBService.MachineDetailService;
import com.dbc.service.DBService.OpperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@CrossOrigin
public class MachineDetailsController {

                @Autowired
                MachineDetailService detailService;

                @Autowired
                MachineDetailsDao machineDetailsDao;

                @Autowired
                OpperService opperService;

                @ResponseBody
                @RequestMapping("/onlineProfile_getMachineInfo")
                public Object Detail() throws URISyntaxException { return detailService.M_Details(); }


                @ResponseBody
                @RequestMapping("/onlineProfile_getStakerIdentity")
                public Object oper() throws URISyntaxException { return opperService.L_Details(); }

                @ResponseBody
                @RequestMapping("/GetMachine_Details")
                public PageHelp<MachineDetailsEntity> getDetail (String machine_status, String gpu_type, Integer gpu_num, Integer pageNum, Integer pageSize){
//                     machine_status = "";
//                     gpu_type="";
//                     gpu_num="";
                    return machineDetailsDao.getDetail(machine_status,gpu_type,gpu_num,pageNum,pageSize);
                }

                @ResponseBody
                @RequestMapping("/Count_Details")
                public JSONObject CountDetail (String gpu_type){
                    return machineDetailsDao.countGPUCPU(gpu_type);
                }

                @ResponseBody
                @RequestMapping("/GetGpu_Info")
                public List<String> gpus (){
                    return machineDetailsDao.GPUType();
                }

                @ResponseBody
                @RequestMapping("/Select_Owner")
                public MachineDetailsEntity ower(String machine_stash){
                    return  machineDetailsDao.selectOwer(machine_stash);
                }

                @ResponseBody
                @RequestMapping("/find_infos")
                public Object find(){
                    return  machineDetailsDao.findInfo();
                }

                @ResponseBody
                @RequestMapping("/find_one")
                public Object select(String machine_id){return machineDetailsDao.FindOne(machine_id);}







}
