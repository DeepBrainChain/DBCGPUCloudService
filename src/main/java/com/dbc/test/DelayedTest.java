package com.dbc.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.dbc.entity.OrderEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;

public class DelayedTest  {

                static boolean flag = true;     // 自动取消线程开关

                static DelayQueue<OrderEntity> queue = new DelayQueue<>(); // 存放过期订单

                public static void main(String[] args) {

                        DelayedTest delayedTest = new DelayedTest();

                        // 新建线程
                        new Thread(() -> {
                           while (flag){
                               try {
                                   OrderEntity orderEntity = delayedTest.queue.take();
                                   orderEntity.setOrderStatus("超时取消");
                                   System.out.println("订单：" + orderEntity.getOrderNO() + "超时取消" + DateUtil.format(LocalDateTime.now(),DatePattern.NORM_DATETIME_PATTERN));

                               }catch (InterruptedException e){
                                   e.printStackTrace();
                               }
                           }
                        }).start();

                        new Thread(() -> {
                            // 最早的订单创建时间
                            long now = System.currentTimeMillis();
                            System.out.println("下单开始时间" + DateUtil.format(new Date(now),DatePattern.NORM_DATETIME_PATTERN));
                            OrderEntity orderEntity = new OrderEntity();
                            orderEntity.setOrderStatus("超时取消");
                            orderEntity.setOrderNO("001");
                            orderEntity.setTime(new Date(now + 3000));
                            queue.add(orderEntity);
                        }).start();

                }

}
//                              List<String> params = new ArrayList<String>();
//                           params.add("node");
//                            params.add("src\\main\\resources\\DeepBrainChain\\rentMachine.js");
//                            params.add("--key");
//                            params.add("0x92cc40e7e7f6f2807b07b29389a11aafde956fc1d40273c0307b7cbeb45b11b2");
//                            params.add("--id");
//                            params.add("ada545sd5w14ad5a5d4a545a4dadadads545545a");
//                            params.add("--data");
//                            params.add("1");       // 天数
//
//                            ProcessBuilder processBuilder = new ProcessBuilder(params);
//
//                            processBuilder.redirectErrorStream(true);
//                            String line = "";
//                            String Message = "";
//                            try {
//                            Process process = processBuilder.start();
//                            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                            while ((line = br.readLine()) != null) {
//                            Message += line;
//                            System.out.println(line);
//                            }
////                            System.out.println(Message);
//                            } catch (IOException e) {
//                            e.printStackTrace();
//                            } catch (JSONException e) {
//                            e.printStackTrace();
//                            }
//                            System.out.println(Message);
//
//                            if (Message.equals("ExtrinsicSuccess")){
//                            System.out.println("成功");
//                            }else {
//                            System.out.println("失败");
//                            }