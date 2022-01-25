package com.dbc.example;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public class innertest {

            public static void main(String[] args) throws Exception {

//                ScriptEngineManager manager = new ScriptEngineManager();
//                ScriptEngine engine = manager.getEngineByName("javascript");
//
//                String jsFileName = "src/main/resources/DeepBrainChain/query_storage.js";   // 读取js文件
//
//                FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
//                engine.eval(reader);
//
////                if(engine instanceof Invocable) {
////                    Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数
////
////// c = merge(2, 3);
////
////                    Double c = (Double)invoke.invokeFunction("merge", 2 , 3);
////
////                    System.out.println("c = " + c);
////                }
//
//                reader.close();



                test();


            }

            public static void test() throws IOException {

                ProcessBuilder pb = new ProcessBuilder("node","src/main/resources/DeepBrainChain/sign_txs.js");

                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                Process p = pb.start();

                // 定义返回值
                String result = null;

                // 获取返回流
                BufferedInputStream in = new BufferedInputStream(p.getInputStream());
                // 字符流转换字节流
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String lineStr;
                while ((lineStr = br.readLine()) != null) {
                    result = lineStr;
                }
                // 关闭输入流
                br.close();
                in.close();

                System.out.println("================================");
                System.out.println(result);
                System.out.println("================================");

            }


    public static String processDataViaJs(String code,String JsURL,String functionName ) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        String newCode = "";
        InputStreamReader inputStreamReader = null;
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            URL url = new URL(JsURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "text/html");
            inputStreamReader = getInputContent("GET", null, conn);//获取js内容
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        engine.eval(inputStreamReader);
        if (engine instanceof Invocable) {
            Invocable invoke = (Invocable) engine;
            newCode = (String) invoke.invokeFunction(functionName, code);// 调用对应方法，并传入

        }
        inputStreamReader.close();
        return newCode;
    }

    private static InputStreamReader getInputContent(String requestMethod,
                                                     String outputStr, HttpURLConnection conn) throws ProtocolException,
            IOException, UnsupportedEncodingException {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        conn.setRequestMethod(requestMethod);

        if (null != outputStr) {
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(outputStr.getBytes("UTF-8"));
            outputStream.close();
        }

        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream, "UTF-8");
        return inputStreamReader;
    }


}
