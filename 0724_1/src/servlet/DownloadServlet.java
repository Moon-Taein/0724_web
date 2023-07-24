package servlet;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get 방식으로 url 이 입력되어서 request 되었을때
		// ? 쿼리파라미터 존재하면~ 없으면~
		String filename = req.getParameter("filename");

		if (filename == null) {
			// 쿼리파라미터가 비워져 있을 경우
			// 파일리스트 받아서
			req.setAttribute("filelist", getFileNameList());

			// 리스트폼으로 포워드 시킨다
			req.getRequestDispatcher("WEB-INF/filelist.jsp").forward(req, resp);

		} else {
			// file download 기능

			// file 이름으로 파일 byte[] 가져오고
			byte[] bytes = readFile(filename);

			// header에다가 해당 형식을 넣어주는데
			resp.setHeader("Content-Type", "application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			// 아웃풋스트림 response 에서 가져오는데 servletoutputstream??
			ServletOutputStream output = resp.getOutputStream();

			// output.write 는 파일을 써서 만들어주는 메소드라서
			// 웹에서 지정한 경로에 다운로드 바로 시켜준다
			output.write(bytes, 0, bytes.length);

			// 버퍼 비워주는 메소드
			output.flush();
		}
	}

	private byte[] readFile(String filename) throws IOException {
		Path file = Paths.get("d:\\myfolder", filename);
		return Files.readAllBytes(file);

	}

	private List<String> getFileNameList() {
		List<String> files = new ArrayList<>();
		File folder = new File("d:\\myfolder\\");

		for (File f : folder.listFiles()) {
			if (!f.isDirectory()) {
				files.add(f.getName());
			}
		}
		return files;
	}
}