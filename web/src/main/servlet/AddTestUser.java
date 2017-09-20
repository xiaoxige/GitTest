package main.servlet;

import com.google.gson.JsonObject;
import main.api.TestUserApi;
import main.bean.TestUserEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


@WebServlet("/addTestUser")
public class AddTestUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_name = req.getParameter("user_name");
        String user_sex = req.getParameter("user_sex");
        String user_age = req.getParameter("user_age");
        String user_desc = req.getParameter("user_desc");

        JsonObject jsonObject = new JsonObject();

        TestUserEntity entity = new TestUserEntity();
        try {

            entity.setUser_name(user_name == null ? "" : user_name);
            entity.setUser_sex(user_sex == null ? true : (user_sex.equals(0)));
            int age = 0;
            try {
                age = Integer.parseInt(user_age);
            } catch (Exception e) {
            }
            entity.setUser_age(age);
            entity.setUser_desc(user_desc == null ? "" : user_desc);


            String resource = "mybatis_main_conf.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory
                    = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession =
                    sqlSessionFactory.openSession(true);
            TestUserApi mapper = sqlSession.getMapper(TestUserApi.class);
            int n = mapper.insetTestUser(entity);
            jsonObject.addProperty("status", "1");
            jsonObject.addProperty("状态", "成功");
            jsonObject.addProperty("id", entity.getUser_id());
        } catch (Exception e) {
            jsonObject.addProperty("status", "0");
            jsonObject.addProperty("状态", "失败");
        }

        PrintWriter writer = resp.getWriter();

        writer.print(jsonObject.toString());
        writer.flush();

        writer.close();

    }
}
