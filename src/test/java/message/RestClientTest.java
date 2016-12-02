package message;

import com.derrick.spring.client.RestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Dre on 2016/12/2.
 */
@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exchangeTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.exchange("",HttpMethod.GET, RestClient.setStringHeaders(),mockVO,HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), eq(HelloVO.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void exchangeExceptionTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.BAD_REQUEST);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.exchange("",HttpMethod.GET, RestClient.setStringHeaders(),mockVO,HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), eq(HelloVO.class));

    }

    @Test
    public void getJSONTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.getJSON("",mockVO,HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.GET), Matchers.<HttpEntity<HelloVO>>any(), eq(HelloVO.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void postJSONTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.postJSON("", mockVO, HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.POST), Matchers.<HttpEntity<HelloVO>>any(), eq(HelloVO.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void getXMLTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.getXML("", mockVO, HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.GET), Matchers.<HttpEntity<HelloVO>>any(), eq(HelloVO.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void postXMLTest(){
        HelloVO mockVO = new HelloVO();
        ResponseEntity<HelloVO> response = new ResponseEntity(mockVO, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        HelloVO result = restClient.postXML("", mockVO, HelloVO.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.POST), Matchers.<HttpEntity<HelloVO>>any(), eq(HelloVO.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void getStringTest(){
        String mock = "mock";
        ResponseEntity<String> response = new ResponseEntity(mock, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        String result = restClient.getString("",mock,String.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.GET), Matchers.<HttpEntity<String>>any(), eq(String.class));
        assertEquals(response.getBody(), result);
    }

    @Test
    public void postStringTest(){
        String mock = "mock";
        ResponseEntity<String> response = new ResponseEntity(mock, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), Matchers.<HttpMethod>any(), Matchers.<HttpEntity<?>>any(), Matchers.any(Class.class))).thenReturn(response);
        String result = restClient.postString("", mock, String.class);
        verify(restTemplate, times(1)).exchange(eq(""),eq(HttpMethod.POST), Matchers.<HttpEntity<String>>any(), eq(String.class));
        assertEquals(response.getBody(), result);
    }

}