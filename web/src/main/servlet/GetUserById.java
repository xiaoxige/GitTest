package main.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.api.TestUserApi;
import main.bean.TestUserEntity;
import org.apache.ibatis.io.Resources;
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

@WebServlet("/getUserById")
public class GetUserById extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String resource = "mybatis_main_conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory
                = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession =
                sqlSessionFactory.openSession(true);

        String id = req.getParameter("id");

        TestUserApi mapper = sqlSession.getMapper(TestUserApi.class);
        try {

            TestUserEntity user = mapper.getTestUserById(id);

            String json = new Gson().toJson(user);

            PrintWriter writer = resp.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        sqlSession.close();
    }
}
