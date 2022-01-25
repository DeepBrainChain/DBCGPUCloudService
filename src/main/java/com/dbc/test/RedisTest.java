package com.dbc.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedisTest {

            public static void main(String[] args) {

                List<String> list = new ArrayList<>();
                JSONObject jsonArray =new JSONObject();
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("peer_nodes_list",list);
                jsonObject.put("additional",jsonArray);
                jsonObject.put("sign","1e500ac6352889453e972ba59ba0f78318531b30c5d607c6ea585e0b8c36a14959bd82ee7005666208bb39ab08884418133600476e91c2d29d383c3441711087");
                jsonObject.put("nonce","3hjGT9XYyh7Auf85nMHcE7zUUTTDkjzaGoeAojC2vdUnE8DXXuUdZ7g");
                jsonObject.put("wallet","5G6Bb5Lo9em2wxm2NcSGiV4APxp5Fr9LtvMSLEspRVSq1yUF");

                System.out.println(jsonObject);

//                Random random = new Random();
//                String fourRandom = random.nextInt(10000) + "";
//                int randLength = fourRandom.length();
//                if(randLength<4){
//                    for(int i=1; i<=4-randLength; i++)
//                        fourRandom = "0" + fourRandom ;
//                }
//                System.out.println(fourRandom);

                String val = "";
                Random random = new Random();
                for ( int i = 0; i < 55; i++ )
                {
                    String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
                    if ( "char".equalsIgnoreCase( str ) )
                    { // 产生字母
                        int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                        // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                        val += (char) ( nextInt + random.nextInt( 6 ) );
                    }
                    else if ( "num".equalsIgnoreCase( str ) )
                    { // 产生数字
                        val += String.valueOf( random.nextInt( 10 ) );
                    }
                }

                System.out.println(val);
            }
}
