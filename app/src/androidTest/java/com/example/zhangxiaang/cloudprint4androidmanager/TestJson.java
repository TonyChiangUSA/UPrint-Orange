package com.example.zhangxiaang.cloudprint4androidmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

import java.lang.reflect.Type;

/**
 * Created by zhangxiaang on 15/10/28.
 */
public class TestJson {
    public void test() {
        TestObject obj = new TestObject("test1");

        String text = JSON.toJSONString(obj);
        System.out.println(text);

        JSONObject jo = JSON.parseObject(text);

        Type type = new TypeReference<TestObject>() {
        }.getType();

        TestObject onj2 = TypeUtils.cast(jo, type, ParserConfig.getGlobalInstance());
        System.out.println("onj2" + onj2.toString());
    }

    public static class TestObject {

        public String name;

        public TestObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
