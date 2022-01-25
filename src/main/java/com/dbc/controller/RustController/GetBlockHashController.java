package com.dbc.controller.RustController;

import com.dbc.dao.GetBlockHashDao;
import com.dbc.service.DBService.GetBlockHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Controller
@CrossOrigin
public class GetBlockHashController {

                    @Autowired
                    GetBlockHashService getBlockHashService;

                    @Autowired
                    GetBlockHashDao getBlockHashDao;


                    @ResponseBody
                    @RequestMapping("/get_BlockHeader")
                    public void block_hash() throws URISyntaxException {

                        getBlockHashService.BlockHash();
                    }

                    @ResponseBody
                    @RequestMapping("/find_BlockHash")
                    public Object findInfo(int id){
                        return  getBlockHashDao.FindHash(id);
                    }
}
