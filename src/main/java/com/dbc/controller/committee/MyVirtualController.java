package com.dbc.controller.committee;

import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.DelayOrder;
import com.dbc.dao.MyVirtualDao;
import com.dbc.entity.ItemDelayed;
import com.dbc.entity.MyVirtualEntity;
import com.dbc.entity.OrderEntity;
import com.dbc.service.committee.*;
import com.utils.HReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.DelayQueue;

@Controller
@CrossOrigin
public class MyVirtualController {

                    @Autowired
                    MyVirtualService myVirtualService;

                    @Autowired
                    MyVirtualDao myVirtualDao;

                    @Autowired
                    DelayOrder delayOrder;

                    @Autowired
                    rentMachineService rentMachineService;

                    @Autowired
                    CreateVirtualMachineService machineService;

                    @Autowired
                    ConFirmRentService conFirmRentService;  // 确认租用服务

                    @Autowired
                    RenewalService renewalService;      // 续租

                    static boolean flag = true;     // 自动取消线程开关

                    static DelayQueue<OrderEntity> queue = new DelayQueue<>(); // 存放过期订单


                    @ResponseBody
                    @RequestMapping("/My_Virtual")
                    public boolean addMv(String only_key, String machine_id, String dollar, int day, String count, double dbc, String wallet) {

                      return  myVirtualService.ADDMyVirtual(only_key,machine_id,dollar,day,count,dbc,wallet);
                    }
                    // http://localhost:8080/My_Virtual?machine_id=1e72e08c883fe01a32ee5caec3b01faf3ae77df71c7dd38e69151f916a407645&dollar=100&day=30&count=10000&dbc=23211211&wallet=5HjW7xKJJgq7XuHeum1pS6GgpNfPGaUgYi42tfzH8VvihuFF

                    // 临时钱包链上支付
                    @ResponseBody
                    @RequestMapping("/Chain")
                    public String chain (String only_key,String machine_id,String wallet, int day) throws Exception {
                       return myVirtualService.chain_lease(only_key,machine_id,wallet,day);
                    }
                    @ResponseBody
                    @RequestMapping("get_Virtual")
                    public ResponseEntity<HReply> query (String wallet,String language) {

                        return myVirtualDao.getInfo(wallet,language);
                    }

                    //if (language==null||language.equals("")||language.equals("CN")||language.equals("cn")||language.equals("SimplifiedChinese"))

                    @ResponseBody
                    @RequestMapping("Delay_Order")
                    public void DelayQueue (ItemDelayed<MyVirtualEntity> myVirtualEntity) {
                        delayOrder.addToOrder(myVirtualEntity);
                    }

                    @ResponseBody
                    @RequestMapping("query_info")
                    public MyVirtualEntity query (String id) {
                        return myVirtualDao.Query(id);
                    }

                    // 租用机器 机器ID 天数
                    @ResponseBody
                    @RequestMapping("/rent_Machine")
                    public String rent  (String only_key,String machine_id,String day) {
                      return rentMachineService.rentMachine(only_key,machine_id,day);
                    }

                    // 创建虚拟机
                    @ResponseBody
                    @RequestMapping("/Create_VMS")
                    public JSONObject create (String only_key, String machine_id, String ssh_port, String gpu_count,
                                              String cpu_cores, String mem_rate,String nonce, String sign , String wallet) throws IOException {
                        return machineService.Creat_VM(only_key,machine_id,ssh_port,gpu_count,cpu_cores,mem_rate,nonce,sign,wallet);
                    }

                    @ResponseBody
                    @RequestMapping("/VMS_details")        // 虚拟机详细信息
                    public String details (String machine_id, String only_key) throws IOException {

                        return machineService.VM_details(machine_id,only_key);
                    }

                    @ResponseBody
                    @RequestMapping("/VMS_restart")             // 重启虚拟机
                    public String restart (String machine_id, String only_key) {
                        return machineService.VM_restart(machine_id,only_key);
                    }

                    @ResponseBody
                    @RequestMapping("/ConFirm_Rent")         // 确认租用机器
                    public String  conFirm (String only_key,String machine_id){
                        return conFirmRentService.conFirmRent(only_key,machine_id);
                    }


                    @ResponseBody
                    @RequestMapping("/Renew_Rent")        //  续租机器
                    public String Renew (String only_key, String machine_id, int add_day , double dbc) {
                        return renewalService.Renew(only_key,machine_id,add_day,dbc);
                    }




                    // 订单确认，超时关闭订单
                    @ResponseBody
                    @RequestMapping("Confirm_payment")
                    public void Con (String machine_id) {
//
//                        DelayedTest delayedTest = new DelayedTest();
//
//                        // 新建线程
//                        new Thread(() -> {
//                            while (flag){
//                                try {
//                                    OrderEntity orderEntity = delayedTest.queue.take();
//                                    orderEntity.setOrderStatus("超时取消");
//                                    System.out.println("订单：" + orderEntity.getOrderNO() + "超时取消" + DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN));
//
//                                }catch (InterruptedException e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start();
//
//                        new Thread(() -> {
//                            // 最早的订单创建时间
//                            long now = System.currentTimeMillis();
//                            System.out.println("下单开始时间" + DateUtil.format(new Date(now),DatePattern.NORM_DATETIME_PATTERN));
//                            OrderEntity orderEntity = new OrderEntity();
//                            orderEntity.setOrderStatus("超时取消");
//                            orderEntity.setOrderNO("001");
//                            orderEntity.setTime(new Date(now + 3000));
//                            queue.add(orderEn000000000000000000000000000000tity);
//                        }).start();


//                        MyVirtualEntity myVirtualEntity = new MyVirtualEntity();
//                        myVirtualEntity.setMachine_id(machine_id);
//                        myVirtualEntity.setOrderStatus("正在使用中");
////                        redisUtil.ins("order_"+myVirtualEntity.getMachine_id(),machine_id,30, TimeUnit.MINUTES);
//                        myVirtualDao.update(myVirtualEntity);
                    }

//    http://localhost:8080/Confirm_payment?machine_id=1e72e08c883fe01a32ee5caec3b01faf3ae77df71c7dd38e69151f916a407645

}
