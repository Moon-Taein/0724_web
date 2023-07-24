package kr.co.greenart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import kr.co.greenart.coffee.Coffee;

// Gson, Jackson, etc...
public class TestJson {
	@Test
	public void testWriteJson() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("name", "kildong");
		node.put("age", 33);

		try {
			String json = mapper.writeValueAsString(node);
			assertNotNull(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			fail("JSON 포맷으로의 변환 실패");
		}
	}

	@Test
	public void testPersonToJson() {
		Person p = new Person("kildong", 33);
		ObjectMapper mapper = new ObjectMapper();

		try {
			String json = mapper.writeValueAsString(p);
			assertNotNull(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			fail("json 포맷으로의 변환 실패");
		}
	}

	@Test
	public void testJsonParsingToPerson() {
		String json1 = "{\"no\":15,\"name\":\"straight\",\"price\":3000}";
		String json = "{\"name\":\"kildong\",\"age\":33}";
		Person expect = new Person("kildong", 33);

		ObjectMapper mapper = new ObjectMapper();
		try {
			Person parsed = mapper.readValue(json, Person.class);

			assertEquals(expect, parsed);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			fail("JSON 문자열을 Person 객체로 파싱 실패");
		}
	}

	@Test
	public void testJsonParsingToPerson2() {
		String json1 = "{\"no\":15,\"name\":\"iceamericano\",\"price\":3050}";
		String json = "{\"name\":\"kildong\",\"age\":33}";
		Person expect = new Person("kildong", 33);
		Coffee expectCof = new Coffee(15, "iceamericano", 3050);

		ObjectMapper mapper = new ObjectMapper();
		try {
			Person parsed = mapper.readValue(json, Person.class);
			Coffee parsed2 = mapper.readValue(json1, Coffee.class);

			assertEquals(expectCof, parsed2);
			assertEquals(expect, parsed);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			fail("JSON 문자열을 Person 객체로 파싱 실패");
		}
	}
}
