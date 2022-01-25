package com.dbc.controller.RustController;

import com.dbc.dao.ChillDao;
import com.dbc.service.DBService.ChillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Controller
@CrossOrigin
public class ChillController {

            @Autowired
            ChillService chillService;

            @Autowired
            ChillDao chilDao;

            @ResponseBody
            @RequestMapping("/committee_committee")
            public Object SaveChil () throws URISyntaxException { return chillService.Chill(); }

            @ResponseBody
            @RequestMapping("/findCommit")
            public Object findChil (int id) { return chilDao.getChilInfo(id); }
}
