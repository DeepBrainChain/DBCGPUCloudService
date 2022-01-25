package com.dbc.controller.RustController;

import com.dbc.dao.InformationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class InformationController {

            @Autowired
            InformationDao informationDao;

            @ResponseBody
            @RequestMapping("Information_Insert")
            public void SaveInformation (){
               informationDao.InsertInformation();
            }
}
