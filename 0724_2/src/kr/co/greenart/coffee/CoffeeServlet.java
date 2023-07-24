package kr.co.greenart.coffee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/*
GET /coffee => 목록
GET /coffee?no=#
	=> #번호(no)의 커피 정보
	=> 1. 정수 형태가 아닌 경우 400 BAD REQUEST 응답
	=> 2. 없는 번호인 경우 404 NOT FOUND 응답 
*/

@WebServlet("/coffee")
public class CoffeeServlet extends HttpServlet {
	private final CoffeeDAO dao = new CoffeeDAO();
	private ObjectMapper mapper = new ObjectMapper();
	private String pattern = "^[0-9]*$";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reqParam = req.getParameter("no");

		// 쿼리파라미터가 존재하지 않는 경우
		if (reqParam == null) {
			try {
				List<Coffee> list = dao.getAll();
				String json = mapper.writeValueAsString(list);
				outputControl.makePassMessage(200, json, resp);
			} catch (SQLException | JsonProcessingException e) {
				e.printStackTrace();
				outputControl.makeErrorMessage(500, "서버 요청 처리 중 에러가 발생했습니다.", resp);
			}
			// 쿼리파라미터 존재할 경우 숫자로만 이루어져 있는지 확인
		} else if (Pattern.matches(pattern, reqParam)) {
			// 숫자이고 목록에 존재하는 경우
			if (dao.isExist(reqParam)) {
				try {
					Coffee coffee = dao.getRowByNumber(Integer.valueOf(reqParam));
					String json = mapper.writeValueAsString(coffee);
					outputControl.makePassMessage(200, json, resp);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// 숫자이지만 목록에 없는 경우
			} else {
				outputControl.makeErrorMessage(404, "목록에 존재하지 않습니다.", resp);
			}
			// 숫자가 아닐 경우
		} else {
			outputControl.makeErrorMessage(400, "숫자를 입력해주세요", resp);
		}

	}

//	POST /coffee => 올바른 Json 포맷의 coffee 데이터를 DB의 행으로 추가 (201 코드 검색해보기)
	// input form 만들기
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		String str = req.getParameter("json");
		System.out.println("원래 형태 : " + str);
		if (str == null) {
			outputControl.makeErrorMessage(400, "JSON 텍스트를 제대로 입력해주세요!", resp);
		} else {
			// string을 json 형태의 string 으로 바꿔준다
			// 이게 필요 없고 그냥 받아온 str 바로 mapper로 객체로 만들어주면 되네?
			String json = gson.toJson(req.getParameter("json"));
			String json2 = gson.toJson(str);
			System.out.println("json 변환 형태 : " + json);
			System.out.println("json 변환 형태2 : " + json2);
			try {
				// json 직렬화 한걸로는 왜 안되고 원본 형태 넣으면 되는기지?
				// gson으로 역직렬화
//				Coffee coffee1 = gson.fromJson(json, Coffee.class);
				Coffee coffee2 = gson.fromJson(str, Coffee.class);
				// jackson으로 역직렬화
//				Coffee coffee3 = mapper.readValue(json, Coffee.class);
//				Coffee coffee4 = mapper.readValue(str, Coffee.class);
//				dao.insertCoffee(coffee);
				outputControl.makePassMessage(201, "정확하게 입력되어 sql에 행이 추가되었습니다!", resp);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				outputControl.makeErrorMessage(400, "json 형식이 맞지 않습니다!", resp);
				resp.getWriter().println("json 변환 형태 : " + json);
			}

		}
	}

}