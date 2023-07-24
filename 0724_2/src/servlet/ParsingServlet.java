package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import person.Person;

@WebServlet("/person/kildong")
public class ParsingServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Person person = new Person("길동", 33);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(person);

		resp.setHeader("Content-Type", "application/json;chartset=utf-8");
		resp.getWriter().println(json);

//		System.out.println(json);
//		req.setAttribute("json", json);
//		req.getRequestDispatcher("/WEB-INF/jsonView.jsp").forward(req, resp);
	}

}