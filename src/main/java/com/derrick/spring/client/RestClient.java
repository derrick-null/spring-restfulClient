package com.derrick.spring.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;


/**
 *
 * Created by Dre on 2016/11/30.
 */
@Component
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    private final static Charset UTF8 = Charset.forName("UTF-8");

    private static final Logger LOG = LoggerFactory.getLogger(RestClient.class.getName());

    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    /**
     * 发送请求,可以设置HttpHeaders
     *
     * @param url   请求地址
     * @param method    请求方式
     * @param httpHeaders   请求头
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public <T> T exchange(String url, HttpMethod method, HttpHeaders httpHeaders, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        try {
            HttpEntity<?> requestEntity = new HttpEntity(requestBody, httpHeaders);
            if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
                Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
                return restTemplate.exchange(url, method, requestEntity, responseType, _uriVariables).getBody();
            }
            return restTemplate.exchange(url, method, requestEntity, responseType, uriVariables).getBody();
        } catch (Exception e) {
            throw new RestClientException(e);
        }
    }

    /**
     * GET JSON，请求和返回都为JSON
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public <T> T getJSON(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.GET, setJSONHeaders(), requestBody, responseType, uriVariables);
    }

    /**
     * POST JSON，请求和返回都为JSON
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public <T> T postJSON(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.POST, setJSONHeaders(), requestBody, responseType, uriVariables);
    }

    /**
     * GET XML，请求和返回都为XML
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public <T> T getXML(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.GET, setXMLHeaders(), requestBody, responseType, uriVariables);
    }

    /**
     * POST XML，请求和返回都为XML
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public <T> T postXML(String url, Object requestBody, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.POST, setXMLHeaders(), requestBody, responseType, uriVariables);
    }

    /**
     * GET String，请求和返回都为String
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public String getString(String url, String requestBody, Class<String> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.GET, setStringHeaders(), requestBody, responseType, uriVariables);
    }

    /**
     * POST String，请求和返回都为String
     *
     * @param url   请求地址
     * @param requestBody   提交的数据
     * @param responseType 返回数据类型
     * @param uriVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],则解析的url为api/1/2，使用Map参数时，遵循按key匹配
     * @return 结果对象
     * @throws RestClientException RestClient异常，包含状态码和非200的返回内容
     * */
    public String postString(String url, String requestBody, Class<String> responseType, Object... uriVariables) throws RestClientException {
        return exchange(url, HttpMethod.POST, setStringHeaders(), requestBody, responseType, uriVariables);
    }
    /**
     * application/x-www-form-urlencoded
     *
     * @return HttpHeaders
     */
    public static HttpHeaders setBasicFormHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "x-www-form-urlencoded", UTF8));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        return headers;
    }

    /**
     * application/json
     *
     * @return HttpHeaders
     */
    public static HttpHeaders setJSONHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", UTF8));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * application/xml
     *
     * @return HttpHeaders
     */
    public static HttpHeaders setXMLHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "xml", UTF8));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        return headers;
    }

    /**
     * text/html
     *
     * @return HttpHeaders
     */
    public static HttpHeaders setHTMLHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "html", UTF8));
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        return headers;
    }

    /**
     * text/plain
     *
     * @return HttpHeaders
     */
    public static HttpHeaders setStringHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", UTF8));
        headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
        return headers;
    }
}
