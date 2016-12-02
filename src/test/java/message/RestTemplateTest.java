package message;

import com.derrick.spring.client.RestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlRootElement;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dre on 2016/11/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring-restful-client.xml"})
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;

    @Before
    public void setUp(){
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void postTest() {
        String url = "http://localhost:8080/user/tom";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<hello>\n" +
                        "  <name>tom</name>\n" +
                        "  <greetings>hello</greetings>\n" +
                        "</hello>",MediaType.APPLICATION_XML));

        HelloVO helloVO = new HelloVO();
        helloVO.setGreetings("hello");
        HttpEntity<?> requestEntity = new HttpEntity(helloVO, RestClient.setXMLHeaders());
        ResponseEntity<HelloVO> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, HelloVO.class);
        assertEquals("tom",entity.getBody().getName());
        assertEquals("hello",entity.getBody().getGreetings());
    }

    @Test
    public void putTest() {

        String url = "http://localhost:8080/user/tom";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.PUT))
                .andRespond(MockRestResponseCreators.withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<hello>\n" +
                        "  <name>tom</name>\n" +
                        "  <greetings>hello</greetings>\n" +
                        "</hello>", MediaType.APPLICATION_XML));

        HelloVO helloVO = new HelloVO();
        helloVO.setGreetings("putTom");
        HttpEntity<?> requestEntity = new HttpEntity(helloVO, RestClient.setXMLHeaders());
        ResponseEntity<HelloVO> entity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, HelloVO.class);
        assertEquals("tom", entity.getBody().getName());
        assertEquals("hello",entity.getBody().getGreetings());
    }

    @Test
    public void deleteTest() {

        String url = "http://localhost:8080/user/tom";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE))
                .andRespond(MockRestResponseCreators.withSuccess("success", MediaType.TEXT_PLAIN));
        HttpEntity<?> requestEntity = new HttpEntity("", RestClient.setStringHeaders());
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        assertEquals("success", entity.getBody());
    }

    @Test
    public void getTest() {

        String url = "http://localhost:8080/user/tom";
        server.expect(MockRestRequestMatchers.requestTo(url))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<hello>\n" +
                        "  <name>tom</name>\n" +
                        "  <greetings>hello</greetings>\n" +
                        "</hello>", MediaType.APPLICATION_XML));

        HttpEntity<?> requestEntity = new HttpEntity("", RestClient.setStringHeaders());
        ResponseEntity<HelloVO> entity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, HelloVO.class);
        assertEquals("tom", entity.getBody().getName());
        assertEquals("hello",entity.getBody().getGreetings());
    }
}

@XmlRootElement(name = "hello")
class HelloVO {

    private String name;

    private String greetings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreetings() {
        return greetings;
    }

    public void setGreetings(String greetings) {
        this.greetings = greetings;
    }
}