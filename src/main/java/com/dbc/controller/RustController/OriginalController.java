package com.dbc.controller.RustController;

import com.dbc.dao.OriginalDao;
import com.dbc.entity.Original;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class OriginalController {

                    @Autowired
                    OriginalDao originalDao;


                    @ResponseBody
                    @RequestMapping("/getOriginal")
                    public Original getOneOriginal(String machine_id) {

                        return originalDao.getOriginal(machine_id);
                    }
}
