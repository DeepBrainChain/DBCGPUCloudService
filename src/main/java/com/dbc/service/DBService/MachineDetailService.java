package com.dbc.service.DBService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.MachineDetailsDao;
import com.dbc.dao.VerificationDao;
import com.dbc.entity.ChillListEntity;
import com.dbc.entity.LCMachineEntity;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.Verification;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;


@Service
@Slf4j
public class MachineDetailService {

            @Autowired
            VerificationDao verificationDao;

            @Autowired
            MachineDetailsDao machineDetailsDao;

            //S3b9buXfNthnsH1GOF2gI2HIOufDTWhs
            public final static String BAIDU_MAP_AK = "jQc7i76SLm2k5j54z5y6ppjWjhb0nlhC";

            @Value("${chainUrl}")
        	private   String chainUrl;
            List<String> stringList = new ArrayList<>();
            public String M_Details() throws URISyntaxException {

                WebSocketClient client = new WebSocketClient(new URI(chainUrl),new Draft_6455()) {
//                    private List<String> machine_id = new ArrayList<>();
                    //private int count;
                    /**
                     * 建立连接调用
                     * @param serverHandshake
                     */
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {

                        Verification verification = verificationDao.getVer(1);
                        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(verification));
                        JSONArray b = JSON.parseArray(data.getString("online_machine"));
                        JSONArray bo = JSON.parseArray(b.toJSONString());

                        JSONArray f = JSON.parseArray(data.getString("rented_machine"));
                        JSONArray fa = JSON.parseArray(f.toJSONString());

                        if(bo != null && bo.size() > 0) {
                            for (int i = 0; i < bo.size(); i++) {
                                String id = bo.get(i).toString();
                                List<String> s = new ArrayList<>();
                                s.add(id);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("jsonrpc","2.0");
                                jsonObject.put("id",1);
                                jsonObject.put("method","onlineProfile_getMachineInfo");
                                jsonObject.put("params",s);
//                                machine_id.add(id);

                                send(jsonObject.toString());
                            }
                        }

                        if(fa != null && fa.size() > 0) {
                            for (int i = 0; i < fa.size(); i++) {
                                String id = fa.get(i).toString();
                                List<String> s = new ArrayList<>();
                                s.add(id);

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("jsonrpc","2.0");
                                jsonObject.put("id",1);
                                jsonObject.put("method","onlineProfile_getMachineInfo");
                                jsonObject.put("params",s);
//                                machine_id.add(id);

                                send(jsonObject.toString());
                            }
                        }

                    }
                    /**
                     * 收到服务端消息调用
                     *                 private List<String> machine_info_detail;  //机器信息详情
                     *                 private JSONArray reward_committee;     // 奖励委员会
                     *                 gpu_type
                     *                 machine_id
                     *                 images
                     *                 telecom_operators
                     * @param s
                     */
                    @SneakyThrows
                    @Override
                    public void onMessage(String s) {

                        log.info("====收到来自服务端的消息===" + s);
                        JSONObject data = new JSONObject(JSON.parseObject(s));
                        JSONObject result= new JSONObject(JSON.parseObject(data.getString("result")));


//                        JSONObject status = new JSONObject(JSON.parseObject(result.getString("machine_status")));
                        JSONObject jsonArray = new JSONObject(JSON.parseObject(result.getString("machineInfoDetail")));
                        JSONObject upload = new JSONObject(JSON.parseObject(jsonArray.getString("committee_upload_info")));
                        JSONObject customize = new JSONObject(JSON.parseObject(jsonArray.getString("staker_customize_info")));
                        JSONObject south = new JSONObject(JSON.parseObject(customize.getString("longitude")));
                        JSONObject la = new JSONObject(JSON.parseObject(customize.getString("latitude")));

                        Integer longit = south.getInteger("East");
                        Integer latitu = la.getInteger("North");
                        Integer Wlongit = south.getInteger("West");
                        Integer Slatitu = la.getInteger("South");
                        JSONObject address = null;
                        // 经纬度转换为城市


                        if(longit == south.getInteger("East") && Slatitu == la.getInteger("South") && longit != null && Slatitu!=null){
                            address = new JSONObject(getAddressInfoByLngAndLat(longit/10000d,Slatitu/10000d));
                        }else if ( longit == south.getInteger("East") && latitu == la.getInteger("North") && longit != null && latitu!=null){
                            address = new JSONObject(getAddressInfoByLngAndLat(longit/10000d,latitu/10000d));
                        }else if (Wlongit == south.getInteger("West") && latitu == la.getInteger("North") && Wlongit != null && latitu!=null){
                            address = new JSONObject(getAddressInfoByLngAndLat(Wlongit/10000d,latitu/10000d));
                        }else if (Wlongit == south.getInteger("West") && Slatitu == la.getInteger("South") && Wlongit != null && Slatitu!=null){
                            address = new JSONObject(getAddressInfoByLngAndLat(Wlongit/10000d,Slatitu/10000d));
                        }
                        else {
                            return;
                        }

                        JSONObject longs = new JSONObject(JSON.parseObject(address.getString("result")));
                        JSONObject lats = new JSONObject(JSON.parseObject(longs.getString("addressComponent")));


                        byte[] type= JSONArray.parseObject(upload.getString("gpu_type"),byte[].class);
                        String t =new String(type);
                        byte[] c_type= JSONArray.parseObject(upload.getString("cpu_type"),byte[].class);
                        String cpu =new String(c_type);
                        byte[] str= JSONArray.parseObject(upload.getString("rand_str"),byte[].class);
                        String rand =new String(str);

                        JSONArray reward = JSON.parseArray(result.getString("rewardCommittee"));
                        JSONArray committee = JSON.parseArray(reward.toJSONString());
                        if(committee != null && committee.size() > 0) {
                            for (int i = 0; i < committee.size(); i++) {
                                committee.get(i).toString();
                            }
                        }

                        JSONArray telecom = JSON.parseArray(customize.getString("telecom_operators"));
                        JSONArray operator = JSON.parseArray(telecom.toJSONString());
                        JSONArray js = new JSONArray();
                        if(operator != null && operator.size() > 0) {
                            for (int i = 0; i < operator.size(); i++) {
                                byte[] fuse= JSONArray.parseObject(operator.get(i).toString(),byte[].class);
                                String on =new String(fuse,"UTF-8");
                                js.add(on);
                            }
                        }

//                        JSONArray image = JSON.parseArray(customize.getString("images"));
//                        JSONArray images = JSON.parseArray(image.toJSONString());
//                        JSONArray jsonArray1 = new JSONArray();
//                        if(images != null && images.size() > 0) {
//                            for (int i = 0; i < images.size(); i++) {
//                                byte[] bytes= JSONArray.parseObject(images.get(i).toString(),byte[].class);
//                                String s1 =new String(bytes);
//                                jsonArray1.add(s1);
//                            }
//                        }


                        byte[] by= JSONArray.parseObject(upload.getString("machine_id"),byte[].class);
                        String s1 =new String(by);
                        stringList.add(s1);


/**
 *     private String gpu_type;
 *                 private String gpu_num;         // Gpu 个数节奏正在街道
 *                 private String cuda_core;
 *                 private String gpu_mem;         // 内存
 *                 private String calc_point;      // 算例
 *                 private String sys_disk;        // 系统磁盘
 *                 private String data_disk;       // 数据盘           upload
 *                 private String cpu_type;     // cpu 类型
 *                 private String cpu_core_num;        // cpu 核数
 *                 private String cpu_rate;        // cpu 使用率
 *                 private String mem_num;
 *                 private String rand_str;
 *                 private String is_support;
 *
 *                 private String upload_net;      // 上行带宽
 *                 private String download_net;       // 下行带宽
 *                 private String longitude;       // 经度  customize
 *                 private String latitude;        // 维度
 *                 private JSONArray telecom_operators;        // 电信运营商
 *                 private JSONArray images;       // 镜像
 *
 */

//                        getAddressInfoByLngAndLat("113.24668","22.96261");

                        //log.info(String.valueOf(count));
//                        detailsEntity.setMachine_id(machine_id.get(count));


                        MachineDetailsEntity detailsEntity = new MachineDetailsEntity();
                        detailsEntity.setMachine_stash(result.getString("machineStash"));
                        detailsEntity.setMachine_renter(result.getString("machineRenter"));
                        detailsEntity.setBonding_height(result.getString("bondingHeight"));
                        detailsEntity.setStake_amount(result.getString("stakeAmount"));
                        detailsEntity.setMachine_status(result.getString("machineStatus"));
                        detailsEntity.setTotal_rented_duration(result.getString("totalRentedDuration"));
                        detailsEntity.setTotal_rented_times(result.getString("totalRentedTimes"));
                        detailsEntity.setTotal_rent_fee(result.getString("totalRentFee"));
                        detailsEntity.setTotal_burn_fee(result.getString("totalBurnFee"));

                        detailsEntity.setGpu_type(t.replaceAll("s","S"));
                        detailsEntity.setGpu_num(upload.getInteger("gpu_num"));
                        detailsEntity.setCuda_core(upload.getString("cuda_core"));
                        detailsEntity.setGpu_mem(upload.getString("gpu_mem"));
                        detailsEntity.setCalc_point(upload.getDouble("calc_point"));
                        detailsEntity.setSys_disk(upload.getString("sys_disk"));
                        detailsEntity.setData_disk(upload.getString("data_disk"));
                        detailsEntity.setMachine_id(s1);
                        detailsEntity.setCpu_type(cpu);
                        detailsEntity.setCpu_core_num(upload.getString("cpu_core_num"));
                        detailsEntity.setCpu_rate(upload.getString("cpu_rate"));
                        detailsEntity.setMem_num(upload.getString("mem_num"));
                        detailsEntity.setRand_str(rand);
                        detailsEntity.setIs_support(upload.getString("is_support"));

                        detailsEntity.setUpload_net(customize.getString("upload_net"));
                        detailsEntity.setDownload_net(customize.getString("download_net"));
                        detailsEntity.setServer_room(customize.getString("server_room"));

//                        detailsEntity.setLongitude(customize.getInteger("longitude"));
//                        detailsEntity.setLatitude(customize.getInteger("latitude"));

                        detailsEntity.setCountry(lats.getString("country"));
                        detailsEntity.setCity(lats.getString("city"));
                        detailsEntity.setTelecom_operators(js);
//                        detailsEntity.setImages(jsonArray1);

                        detailsEntity.setReward_deadline(result.getString("rewardDeadline"));
                        detailsEntity.setReward_committee(committee);
                        machineDetailsDao.SaveDetails(detailsEntity);
                       // this.count+=1;
                        stringList.add(result.getString("machineStash"));

                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        log.info("关闭连接:::" + "i = " + i + ":::s = " + s +":::b = " + b);
                    }




                    /**
                     * 连接报错调用
                     * @param e
                     */
                    @Override
                    public void onError(Exception e) {
                        log.error("====出现错误====" + e.getMessage());
                    }

                };
                client.connect();


                return null;
    }


             static  Map<String,JSONObject> location_cash = new HashMap<>();

                public static JSONObject getAddressInfoByLngAndLat(double longitude, double latitude){
                    JSONObject obj = new JSONObject();
                    String location=latitude+","+longitude;
                   if(location_cash.containsKey(location)){
                       return location_cash.get(location);
                   }

                   System.out.println("*********************************************************************************************************");

                    String url ="http://api.map.baidu.com/reverse_geocoding/v3/?ak="+BAIDU_MAP_AK+"&output=json&coordtype=wgs84ll&location="+location;
                    System.out.println(url);
                    try {
                        String json = loadJSON(url);
                        obj = JSONObject.parseObject(json);
                        System.out.println(obj.toString());
                        // status:0 成功
                        String success="0";
                        String status = String.valueOf(obj.get("status"));
                        if(success.equals(status)){
                            String result = String.valueOf(obj.get("result"));
                            JSONObject resultObj = JSONObject.parseObject(result);
                            String addressComponent = String.valueOf(resultObj.get("addressComponent"));
                            //JSON字符串转换成Java对象
                            // AddressComponent addressComponentInfo = JSONObject.parseObject(addressComponent, AddressComponent.class);
                            System.out.println("addressComponentInfo:"+addressComponent);
                        }
                    } catch (Exception e) {
                        System.out.println("未找到相匹配的经纬度，请检查地址！");
                    }
                    location_cash.put(location,obj);
                    return obj;
                }

                public static String loadJSON(String url) {
                    StringBuilder json = new StringBuilder();
                    try {
                        URL oracle = new URL(url);
                        URLConnection yc = oracle.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
                        String inputLine = null;
                        while ((inputLine = in.readLine()) != null) {
                            json.append(inputLine);
                        }
                        in.close();
                    } catch (MalformedURLException e) {} catch (IOException e) {}
                    return json.toString();
                }



}
