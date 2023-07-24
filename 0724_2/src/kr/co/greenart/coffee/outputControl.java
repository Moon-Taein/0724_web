package kr.co.greenart.coffee;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class outputControl {
	private static ObjectMapper mapper = new ObjectMapper();

	public static void makeErrorMessage(int errorCode, String errorMessage, HttpServletResponse resp)
			throws IOException {
		ObjectNode errorNode = mapper.createObjectNode();
		errorNode.put("message", errorMessage);
		errorNode.put("callNumber", "010-5039-5692");
		String errorMes = mapper.writeValueAsString(errorNode);

		resp.setStatus(errorCode);
		resp.setHeader("Content-Type", "application/json;charset=utf-8");
		resp.getWriter().println(errorMes);
	}

	public static void makePassMessage(int code, String json, HttpServletResponse resp) throws IOException {
		resp.setStatus(200);
		resp.setHeader("Content-Type", "application/json;charset=utf-8");
		resp.getWriter().println(json);
	}

}
