package com.dbc.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbc.dao.MachineDetailsDao;
import com.dbc.entity.LCOpsEntity;
import com.dbc.entity.MachineDetailsEntity;
import com.dbc.entity.PageHelp;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MachineDetailsImpl implements MachineDetailsDao {

                    @Autowired
                    MongoTemplate mongoTemplate;

                    @Override
                    public void SaveDetails(MachineDetailsEntity machineDetailsEntity) { mongoTemplate.save(machineDetailsEntity); }

                    @Override
                    public void UpdateDetails(MachineDetailsEntity machineDetailsEntity) {
                        Query query =new Query();
                        query.addCriteria(Criteria.where("_id").is(machineDetailsEntity.getMachine_id()));
                        Update update = new Update();
                        update.set("operator",machineDetailsEntity.getOperator());
//                        update.set("_id",machineDetailsEntity.getMachine_id());
                        mongoTemplate.updateMulti(query,update,MachineDetailsEntity.class) ;

                    }

                    @Override
                    public PageHelp<MachineDetailsEntity> getDetail(String machine_status, String gpu_type, Integer gpu_num, Integer pageNum, Integer pageSize) {

//                        Pageable pageable =PageRequest.of(pageNum,pageSize);
                        if (pageNum == null & pageSize ==null ){
                            pageNum = 0;
                            pageSize = 5;
                        }
                        Criteria c1 = null;
                        Criteria c2 = null;
                        Criteria c3 = null;
                        ArrayList<Criteria> list =new ArrayList<Criteria>();

                        if(machine_status != null){
                            c1 = Criteria.where("machine_status").is(machine_status);
                            list.add(c1);
                        }
                        if(gpu_type != null){
                            c2 = Criteria.where("gpu_type").is(gpu_type);
                            list.add(c2);
                        }
                        if(gpu_num != null){
                            c3 = Criteria.where("gpu_num").is(gpu_num);
                            list.add(c3);
                        }
                        Criteria[] arr =new Criteria[list.size()];
                        list.toArray(arr);
                        Criteria criteria =new Criteria().andOperator(arr);
                        Query query =new Query(criteria);

                        // 总个数
                        long total =this.mongoTemplate.count(query,MachineDetailsEntity.class);
                        Integer pages =(int)Math.ceil((double)total / (double)pageSize);
                        if (pageNum <= 0 || pageNum > pages){
                            pageNum = 1;
                        }

                        int skip =pageSize * (pageNum - 1);
                        query.skip(skip).limit(pageSize);

                        List<MachineDetailsEntity> machineDetailsEntity = mongoTemplate.find(query,MachineDetailsEntity.class);
                        PageHelp pageHelp = new PageHelp();
                        pageHelp.setTotal(total);
                        pageHelp.setPages(pages);
                        pageHelp.setPageSize(pageSize);
                        pageHelp.setPageNum(pageNum);
                        pageHelp.setList(machineDetailsEntity);

                        return pageHelp;

                    }

                    @Override
                    public List<String> GPUType() {

                        List<String> result = new ArrayList<>();

                        List<Document> aggregateCondList = new ArrayList<Document>();
                        aggregateCondList.add(new Document("$group", new Document("_id", "$gpu_type")));
                        aggregateCondList.add(new Document("$sort", new Document("calc_point",1)));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").aggregate(aggregateCondList).iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("_id");

                            result.add(treatVal);
                        }

                        return result;
                    }


                    /**
                     *  查询gpu_type 对应的机器总数和空闲状态
                     *
                     *  db.getCollection('MachineDetailsInfo').aggregate([
                     *     {"$match": {"machine_status": "online","gpu_type":"GeForceRTX3090"}},
                     *     {"$group": {"_id": "$gpu_type", "count": {"$sum": 1}}}
                     *  ])
                     * @return
                     */
                    @Override
                    public JSONObject countGPUCPU(String gpu_type){

                        JSONObject result = new JSONObject();

                        result.put("sum",countTreatment(gpu_type)); // 空闲机器
                        result.put("count",countOnline(gpu_type));// 机器总数
                        result.put("cpu_num",Cpu_num(gpu_type));// cpu 总数
                        result.put("cpu_total",Cpu_total(gpu_type));// cpu 空闲数

                        return result;
                    }

                    @Override
                    public MachineDetailsEntity selectOwer(String machine_stash) {

                        Query query = Query.query(Criteria.where("machine_stash").is(machine_stash));
                        MachineDetailsEntity machineDetailsEntity = mongoTemplate.findOne(query,MachineDetailsEntity.class);

                        return machineDetailsEntity;
                    }

                    /**
                     *      db.getCollection('MachineDetailsInfo').find({}, {"machine_stash":1,"_id":0})  查询语句
                     *
                     *
                     *      db.getCollection('MachineDetailsInfo').find({"_id":"029cac37fb61744a0ace988ba37c4094f0803f54d0ef125866a8926932ce9e64"}, {"machine_stash":1,"_id":0})    查询某一列语句
                     * @return
                     */
                    @Override
                    public JSONArray findInfo() {


                        JSONArray result = new JSONArray();

                        List<Document> aggregateCondList = new ArrayList<Document>();
                        aggregateCondList.add(new Document());
                        aggregateCondList.add(new Document("machine_stash",1).append("_id",1));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").find().iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("machine_stash");
//                            String id = doc.getString("_id");

                            result.add(treatVal);
//                            result.add(id);
                        }

                        return result;
                    }
                    @Override
                    public JSONArray findid() {


                        JSONArray result = new JSONArray();

                        List<Document> aggregateCondList = new ArrayList<Document>();
                        aggregateCondList.add(new Document());
                        aggregateCondList.add(new Document("machine_stash",1).append("_id",1));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").find().iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

//                            String treatVal = doc.getString("machine_stash");
                                            String id = doc.getString("_id");

//                            result.add(treatVal);
                                            result.add(id);
                        }

                        return result;
                    }

                    @Override
                    public MachineDetailsEntity FindOne(String machine_id) {

                        Query query = Query.query(Criteria.where("_id").is(machine_id));
                        MachineDetailsEntity machineDetailsEntity = mongoTemplate.findOne(query,MachineDetailsEntity.class);
                        return machineDetailsEntity;
                    }

                    @Override
                    public JSONObject countTreatment(String gpu_type) {  // 根据机器型号查询对应的空闲数量

                        JSONObject result = new JSONObject();

                        List<Document> aggregateCondList = new ArrayList<Document>();

                        //unwind
                        //                        aggregateCondList.add(new Document("$match", new Document("gpu_type", gpu_type)));
                        aggregateCondList.add(new Document("$match", new Document("machine_status", "online").append("gpu_type",gpu_type)));
                        aggregateCondList.add(new Document("$group", new Document("_id", "$gpu_type").append("count",new Document("$sum",1))));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").aggregate(aggregateCondList).iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("_id");

                            Integer count =  doc.getInteger("count");

                            result.put(treatVal, count);
                        }

                        return result;

                    }


                    public JSONObject countOnline(String gpu_type) {  // 根据机器型号查询对应的空闲数量

                        JSONObject result = new JSONObject();

                        List<Document> aggregateCondList = new ArrayList<Document>();

                        //unwind
//                        aggregateCondList.add(new Document("$skip",pageNum));
//                        aggregateCondList.add(new Document("$limit",pageSize));
                        aggregateCondList.add(new Document("$match", new Document("gpu_type", gpu_type)));
                        aggregateCondList.add(new Document("$group", new Document("_id", "$gpu_type").append("count",new Document("$sum",1))));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").aggregate(aggregateCondList).iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("_id");

                            Integer count =  doc.getInteger("count");

                            result.put(treatVal, count);
                        }

                        return result;

                    }

                        /**
                         *
                         * db.getCollection('MachineDetailsInfo').aggregate([
                         *                         {"$match": {"gpu_type":"GeForceRTX3080"}},
                         *                        {"$group": {"_id": "$gpu_type","totalFormalNumber": {"$sum": {"$toDouble": "$gpu_num"}},"count": { "$sum": 1}}}
                         *                     ])
                         *                           分页参数
                         *                          { "$skip" : 0},
                         *                          {"$limit" : 5},
                         * @param gpu_type
                         * @return
                         */
                    public JSONObject Cpu_num(String gpu_type) {

                        JSONObject result = new JSONObject();

                        List<Document> aggregateCondList = new ArrayList<Document>();

                        //unwind
                        //分页

                        aggregateCondList.add(new Document("$match", new Document("gpu_type", gpu_type)));
                        aggregateCondList.add(new Document("$group", new Document("_id", "$gpu_type").append("total",new Document("$sum","$gpu_num"))));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").aggregate(aggregateCondList).iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("_id");

                            Integer total =  doc.getInteger("total");

                            result.put(treatVal, total);
                        }

                        return result;

                    }

                    public JSONObject Cpu_total(String gpu_type) {  // 根据机器型号查询对应的空闲数量

                        JSONObject result = new JSONObject();

                        List<Document> aggregateCondList = new ArrayList<Document>();

                        //unwind
                        aggregateCondList.add(new Document("$match", new Document("machine_status", "online").append("gpu_type",gpu_type)));
                        aggregateCondList.add(new Document("$group", new Document("_id", "$gpu_type").append("total",new Document("$sum","$gpu_num"))));

                        //遍历结果集
                        MongoCursor<Document> cursor = mongoTemplate.getCollection("MachineDetailsInfo").aggregate(aggregateCondList).iterator();
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();

                            String treatVal = doc.getString("_id");

                            Integer total =  doc.getInteger("total");

                            result.put(treatVal, total);
                        }

                        return result;

                    }

                    @Override
                    public void deleteMachine(String Machine_id) {

                        Query query  =  Query.query(Criteria.where("Machine_id").is(Machine_id));
                        mongoTemplate.remove(query);
                    }


                    @Override
                    public void updateOrder(MachineDetailsEntity ma) {     // 修改订单状态

                        Query query = Query.query(Criteria.where("_id").is(ma.getMachine_id()));
                        Update update = Update.update("orderStatus",ma.getOrderStatus());

                        mongoTemplate.updateFirst(query,update,MachineDetailsEntity.class);
                    }


}
