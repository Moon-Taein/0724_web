package servertime;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerTimeHandler implements mvc.command.CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("servertime", LocalDateTime.now());
		return "/WEB-INF/view/servertime.jsp";
	}

}
