package cn.xiaoxige.firstssm.aop;

import com.apple.eawt.AppEvent;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class TestRdis {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());

        Set<String> keys = jedis.keys("*");
        for(Iterator itertor=keys.iterator();itertor.hasNext();){

            String key = (String)itertor.next();
            System.out.println(key);
        }
        jedis.set("k6","v6");

        jedis.expire("k6",2);
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.get("k6"));
        System.out.println(jedis.ttl("k6"));


    }
}
