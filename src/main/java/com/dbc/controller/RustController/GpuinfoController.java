package com.dbc.controller.RustController;

import com.dbc.dao.GpuinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class GpuinfoController {

                @Autowired
                GpuinfoDao gpuinfoDao;


                @ResponseBody
                @RequestMapping("/getGPU")
                public Object getinformation(){
                    return gpuinfoDao.getGpu();
                }
}
