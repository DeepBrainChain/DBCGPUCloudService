package com.dbc.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


//import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;

public class dbcweall {

   // public static void main(String[] args) throws Exception{


//        PipedReader prd = new PipedReader();

//        ProcessBuilder pb = new ProcessBuilder("node","src/main/resources/DeepBrainChain/query_storage.js");
//
//        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
//
//        Process p = pb.start();
//
//
//        System.out.println("#######");
//        System.out.println(p);
//        System.out.println("#######");


//            ScriptEngineManager manager = new ScriptEngineManager();
//    //获取JavaScript执行引擎
//            ScriptEngine engine = manager.getEngineByName("js");
//    //方式一
//            String script = "function hello(name){" +
//                    " return 'Hello '+name; " +
//                    "}";
//            engine.eval(script);
//    // 注意：JavaScript engine 实现了 Invo    cable 接口
//            Invocable inv = (Invocable)engine;
//    // 执行这个名字为 "hello"的全局的函数
//            Object eval = inv.invokeFunction("hello", "张三枫");
//            System.out.println(eval);
//
//    //方式二
//            script = "'Hello '+name";//JS可执行的表达式
//    //传入参数
//            engine.put("name","张三枫");
//
//            eval = engine.eval(script);
//            System.out.println(eval);



//        String cmds[] = new String[9];
//        cmds[0] = "src/main/resources/DeepBrainChain/query_storage.js";
//        cmds[1] = "1"; // 任务省Id
//        cmds[2] = "2";// 插入任务人所属的纳税主体标识
//        cmds[3] = "3";// 任务期间
//        cmds[4] = "4";// 统计表单类型 (1：计算表,2：申报表)
//        cmds[5] = "5";// 插入任务人的二级公司Id
//        cmds[6] = "6";// 表名
//        cmds[7] = "7";
//        cmds[8] = "8";// 库名
//
//        // 执行shell脚本
//        Process pcs = Runtime.getRuntime().exec(cmds);
//
//        // 定义shell返回值
//        String result = null;
//
//        // 获取shell返回流
//        BufferedInputStream in = new BufferedInputStream(pcs.getInputStream());
//        // 字符流转换字节流
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        // 这里也可以输出文本日志
//
//        String lineStr;
//        while ((lineStr = br.readLine()) != null) {
//            result = lineStr;
//        }
//        // 关闭输入流
//        br.close();
//        in.close();
//
//        System.out.println("==============================" + result);
//


  //  }


//        private static void callScript(String script, String args, String... workspace){
//                try {
//                        String cmd = "sh " + script + " " + args;
////        	String[] cmd = {"sh", script, "4"};
//                        File dir = null;
//                        if(workspace[0] != null){
//                                dir = new File(workspace[0]);
//                                System.out.println(workspace[0]);
//                        }
//                        String[] evnp = {"val=2", "call=Bash Shell"};
//                        Process process = Runtime.getRuntime().exec(cmd, evnp, dir);
////            process = Runtime.getRuntime().exec(cmd);
//                        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                        String line = "";
//                        while ((line = input.readLine()) != null) {
//                                System.out.println(line);
//                        }
//                        input.close();
//                }
//                catch (Exception e){
//                        e.printStackTrace();
//                }
//        }
//
//        public static void main(String[] args) {
//                // TODO Auto-generated method stub
//                callScript("query_storage.js", "4", "src/main/resources/DeepBrainChain");
//        }

//        node sign_txs.js --port="wss://infotest.dbcwallet.io" --module
//        onlineProfile --func bondMachine --key "sample split bamboo west visual approve brain fox arch impact relief smile"
//                5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty 2gfpp3MAB4Aq2ZPEU72neZTVcZkbzDzX96op9d3fvi3
        public static void main(String[] args) {
                List<String> params = new ArrayList<String>();
                //params.add("src/main/resources/DeepBrainChain/sign_txs.js");
                params.add("node");
                params.add("src\\main\\resources\\DeepBrainChain\\sign_txs.js");
                //params.add("sign_txs.js");
                params.add("--port");
                params.add("wss://infotest.dbcwallet.io");
                params.add("--module");
                params.add("onlineProfile");
                params.add("--func");
                params.add("bondMachine");
                params.add("--js");
                params.add("src\\main\\resources\\DeepBrainChain\\types.json");
                params.add("--key");
                params.add("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a");
                params.add("5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty");
                params.add("2gfpp3MAB4AqZPEU72neZTVcZkbzDzX96op9d3fv");
//                params.add(">");
//                params.add("a.txt");
//                                params.add("ipconfig");
//                params.add("--help");1



                ProcessBuilder processBuilder = new ProcessBuilder(params);

                processBuilder.redirectErrorStream(true);
                try {
                        Process process = processBuilder.start();
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        String Message = "";
                        List<String> list = new ArrayList<String>();
                        String s = null;
                        String e = null;
                        while ((line = br.readLine()) != null) {
                                System.out.println(line);
                                Message += line;
                              //  System.out.println(line.startsWith("{") );
//                                if(line.startsWith("{")){
//                                        JSONObject object = new JSONObject(line);
//                                        System.out.println(object);
////                                        System.out.println(object.has("Tx_status"));
////                                        System.out.println(line);
//                                }


                                if(line.startsWith("{")){
                                        JSONObject object = new JSONObject(line);
//                                        System.out.println(object);
                                        if(object.has("Tx_status")){
                                                 s = object.getString("Tx_status");
                                                 System.out.println(s);
                                                 list.add(s);
                                        }
                                        if(object.has("Event")){
                                                 e = object.getString("func");
                                                System.out.println(e);
                                                list.add(e);

                                        }
                                }

                        }
                        int exitCode = process.waitFor();
                        System.out.println(Message);

//                        if(Message.startsWith("{")){
//                                JSONObject object = new JSONObject(Message);
////                                        System.out.println(object);
//                                if(object.has("Tx_status")){
//                                        s = object.getString("Tx_status");
//                                        //System.out.println(s);
//                                }
//                                if(object.has("Event")){
//                                        e = object.getString("func");
//                                        System.out.println(e);
//
//                                }
//                        }



                        System.out.println(list);


//                        if (line.startsWith("{")) {
//                                JSONObject object = new JSONObject(line);
////                                        System.out.println(object);
//                                if (object.has("Tx_status")) {
//                                        String s = object.getString("Tx_status");
//                                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
//                                        System.out.println(s);
//                                }
//                                if (object.has("Event")) {
//                                        String e = object.getString("func");
//                                        System.out.println(e);
//
//                                }
//                        }





//                        Message.trim();
//                        JSONObject jsonObject = JSONObject.parseObject(Message);
//                        String r = jsonObject.getString("Tx_status");
//                        System.out.println(r);


//                        JSONObject object = JSONObject.parseObject(s);
//                        String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
//                                SerializerFeature.WriteDateUseDateFormat);
//                        System.out.println(pretty);


                        boolean status = Message.contains("ExtrinsicFailed");
                        boolean Final = Message.contains("Finalized");

                        if(status && Final){
                                System.out.println(false);
                        }else{
                                System.out.println(true);
                        }

                        System.out.println("exitCode = "+exitCode);

                } catch (IOException e) {
                        e.printStackTrace();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } catch (JSONException e) {
                        e.printStackTrace();
                }
        }



}
