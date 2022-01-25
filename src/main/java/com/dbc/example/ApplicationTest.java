package com.dbc.example;

import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.*;
import com.dbc.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

                @Autowired
                DemoDao demoDao;

                @Autowired
                OriginalDao originalDao;

                @Autowired
                VerificationDao verificationDao;

                @Autowired
                ResultHashDao resultHashDao;

                @Autowired
                GrabbingHashDao grabbingHashDao;

                @Autowired
                MailboxDao mailboxDao;

//                @Test
//                public void SaveGrabbing(){
//                    GrabbingHashEntity grabbing = new GrabbingHashEntity();
//                    grabbing.setReport_id("123");
//                    grabbing.setMachine_id("5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM69Fi9");
//                    grabbing.setCommittee_rand_str("SSS");
//                    grabbing.setErr_reason("XXXXXX");
//                    grabbing.setReporter_rand_str("1231121");
//                    grabbing.setSupport_report(1);
//                    grabbingHashDao.SaveGrabbing(grabbing);
//                }

//                @Test
//                public void save(){
//                    ResultHashEntity result = new ResultHashEntity();
//                    result.setMachine_id("XXXXXXXXXXXX");
//                    result.setGpu_type("GeForceRTX2080Ti");
//                    result.setGpu_num("3");
//                    result.setCuda_core("3453");
//                    result.setGpu_mem("23422223");
//                    result.setCalc_point("232");
//                    result.setSys_disk("2323424222");
//                    result.setData_disk("1231121");
//                    result.setCpu_type("Intel(R) Xeon(R) Silver 4110 CPU");
//                    result.setCpu_core_num("23");
//                    result.setCpu_rate("23");
//                    result.setMem_num("3254243223");
//                    result.setRand_str("Xjj2132kja");
//                    result.setIs_support(1);
//
//                    resultHashDao.Saveresult(result,"5HL92dTnQrZSJZy7ckDVYVt9mMX3NsjShWsYDquB3eB3yb5R","0xa48560c0ed02839448e40435b84d374c6292e9ba70ea8c799642b474ca97ba1e62ce8b33370d62e6d4dc4ca57b1007ec1c0e87fd805aaca9dfe61d9b961dc78f","test message");
//
//                }

                @Test
                public void save(){
                    MailboxEntity mailboxEntity = new MailboxEntity();
                    mailboxEntity.setWallet("5HgjMd2iuiyzPvurozVydmwCcSbDZRgdA6UHtvtXi9KDIME0");
                    mailboxEntity.setEmil("heaven@dbchain.ai");

                    mailboxDao.insert(mailboxEntity);

                }



//                @Test
//                public void queryDemoTest() {
//
//                    DemoEntity demoEntity = demoDao.findDemo(1L);
//                    System.out.println(JSONObject.toJSONString(demoEntity));
//                }
//                @Test
//                public void  saveDemoTest(){
//
//                    DemoEntity demoEntity =new DemoEntity();
//                    demoEntity.setId(3L);
//                    demoEntity.setTitle("Rest");
//                    demoEntity.setDescription("----------------");
//                    demoEntity.setBy("jack");
//                    demoEntity.setUrl("www.baidu.com");
//
//                    demoDao.saveDemo(demoEntity);
//                }
//    @Test
//    public void query(){
//        Verification verification = verificationDao.getVer(1);
//        System.out.println(verification);
//    }

//                @Test
//                public void queryDemoTest() {
//
//                    Original demoEntity = originalDao.getOriginal("5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty");
//                    System.out.println(JSONObject.toJSONString(demoEntity));
//                }

//                @Test
//                public void Save(){
//
//                        Original original = new Original();
//                        original.setMachine_id("5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM69Fi9");
//                        original.setGpu_type("GeForceRTX2080Ti");
//                        original.setGpu_num("3");
//                        original.setCuda_core("3453");
//                        original.setGpu_mem("23422223");
//                        original.setCalc_point("232");
//                        original.setSys_disk("2323424222");
//                        original.setData_disk("1231121");
//                        original.setCpu_type("Intel(R) Xeon(R) Silver 4110 CPU");
//                        original.setCpu_core_num("23");
//                        original.setCpu_rate("23");
//                        original.setMem_num("3254243223");
//
//                        originalDao.SaveOrigin(original);
//                }


}
