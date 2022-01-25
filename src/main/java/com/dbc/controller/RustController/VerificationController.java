package com.dbc.controller.RustController;

import com.dbc.dao.MailboxDao;
import com.dbc.dao.VerificationDao;
import com.dbc.service.DBService.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Controller
@CrossOrigin
public class VerificationController {

                @Autowired
                VerificationService verificationService;

                @Autowired
                VerificationDao verificationDao;

                @Autowired
                MailboxDao mailboxDao;

                @ResponseBody
                @RequestMapping("/onlineProfile_getMachineList")
                public Object verif() throws URISyntaxException {
                    return verificationService.Verification();
                }

                @ResponseBody
                @RequestMapping("/getMachineList")
                public Object getMachine(int id){
                    return verificationDao.getVer(id);
                }

                @ResponseBody
                @RequestMapping("/emil")
                public String getMachine(String wallet){
                    return mailboxDao.SelectEmil(wallet);
                }



}
