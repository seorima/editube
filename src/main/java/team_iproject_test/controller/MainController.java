//package team_iproject_test.controller;
//
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.ChannelListResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import team_iproject_test.utils.*;
//
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URLDecoder;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Log4j2
//@RequiredArgsConstructor
//@CrossOrigin(origins="*")
//@Controller
//public class MainController {
//
//    @Value("${mygoogle.apiKey}")
//    private String apiKey;
//
//    @Value("${mygoogle.clientid}")
//    private String CLIENT_ID ;
//
//    @Value("${mygoogle.clientsecret}")
//    private String CLIENT_SECRETS;
//
//    @Value("${mygoogle.clientredirectyoutube}")
//    private String clientredirectyoutube;
//
//    private final GoogleUtils configUtils;
//
//    private final ApiExample apiExample;
//
//    @GetMapping(value = "/")
//    public String mainPage(){
//
//
//        return "main";
//    }
//
//    @GetMapping(value = "check-youtube")
//    public String checkYouTube() throws IOException, GeneralSecurityException {
//
//        YouTube youtubeService = apiExample.getService();
//        // Define and execute the API request
//        YouTube.Channels.List request = youtubeService.channels()
//                .list(Collections.singletonList("snippet,contentDetails,statistics"));
//        ChannelListResponse response = request.setMine(true).execute();
//        System.out.println(response);
//
//        return "main";
//    }
//
//
//    @GetMapping(value = "/login")
//    public ResponseEntity<Object> moveGoogleInitUrl() {
//        String authUrl = configUtils.googleInitUrl();
//        URI redirectUri = null;
//
//        System.out.println("요청 url : "+ authUrl);
//
//        try {
//            redirectUri = new URI(authUrl);
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//            log.info("check login");
//            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//
//    @GetMapping(value = "/road-api")
//    public ResponseEntity<Object> rodeApi() {
//        String authUrl = "https://business.juso.go.kr/addrlink/addrLinkUrl.do?confmKey=U01TX0FVVEgyMDIzMDUwMjE0MTI1MDExMzczNzM=&returnUrl=http://localhost:3030/road-return&resultType=4";
//        URI redirectUri = null;
//
//        System.out.println("요청 url : "+ authUrl);
//
//        try {
//            redirectUri = new URI(authUrl);
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//
//            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//
//    @PostMapping(value = "/road-return")
//    @CrossOrigin(origins="*")
//    public String postRodeReturn(HttpServletRequest request,
//                                 @RequestBody String body,
//                                 Model model) throws JsonProcessingException, UnsupportedEncodingException {
//        String authUrl = configUtils.googleInitUrl();
//        URI redirectUri = null;
//
//
//        System.out.println("포스트");
//        System.out.println("headers : "+ request.getHeaderNames());
//        System.out.println("body : "+ body);
//
//        // ObjectMapper를 통해 String to Object로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//
//        System.out.println("decode : "+ URLDecoder.decode(body, "UTF-8"));
//
//        // URL 디코딩
//        String decoded = URLDecoder.decode(body, "UTF-8");
//
//        // 파라미터 분리
//        String[] params = decoded.split("&");
//
//        // key-value 쌍으로 저장할 Map 생성
//        Map<String, String> paramMap = new HashMap<>();
//
//        // 각 파라미터의 값을 Map에 저장
//        for (String param : params) {
//            String[] keyValue = param.split("=");
//            if (keyValue.length == 2) {
//                paramMap.put(keyValue[0], keyValue[1]);
//            }
//        }
//
//        JusoDto jusoDto = new JusoDto();
//
//        jusoDto.setInputYn(paramMap.get("inputYn"));
//        jusoDto.setRoadFullAddr(paramMap.get("roadFullAddr"));
//
//        System.out.println("최종:");
//        System.out.println(jusoDto.getRoadFullAddr());
//        System.out.println(jusoDto.getInputYn());
//
//        System.out.println(paramMap.values());
//
//        model.addAttribute("address", jusoDto.getRoadFullAddr());
//
//        return "join";
//    }
//
//    @GetMapping(value = "/oauth/login/redirect")
//    public String redirectGoogleLogin(
//            @RequestParam(value = "code") String authCode,
//            Model model
//    ) {
//        // HTTP 통신을 위해 RestTemplate 활용
//        RestTemplate restTemplate = new RestTemplate();
//        GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
//                .clientId(configUtils.getClientid())
//                .clientSecret(configUtils.getClientsecret())
//                .code(authCode)
//                .redirectUri(configUtils.getClientredirect())
//                .grantType("authorization_code")
//                .build();
//
//        log.info("login try....");
//        log.info(requestParams.getCode());
//
//        try {
//            // Http Header 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
//
//            System.out.println(httpRequestEntity.getBody());
//            System.out.println(httpRequestEntity.getHeaders());
//            System.out.println(configUtils.getAuthurl() + "/token");
//            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getAuthurl() + "/token", httpRequestEntity, String.class);
//
//
//            System.out.println("크리덴셜 확인");
//            System.out.println(apiResponseJson.getBody());
//
//
//            // ObjectMapper를 통해 String to Object로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});
//
//            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
//            String jwtToken = googleLoginResponse.getIdToken();
//
//            String accessToken = googleLoginResponse.getAccessToken();
//
//            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
//            String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getAuthurl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();
//
//            String resultJson = restTemplate.getForObject(requestUrl, String.class);
//
//            if(resultJson != null) {
//                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
//
//                // 구글 로그인 DTO 반환
//                log.info(userInfoDto.toString());
//
//                model.addAttribute("result", userInfoDto);
//
//                return "ReturnPage";
//            }
//            else {
//                throw new Exception("Google OAuth failed!");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "main";
//    }
//
//
//    @GetMapping(value = "/youtube-check")
//    public ResponseEntity youtubeCheck(){
//        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
//                "scope=profile%20email%20openid%20https://www.googleapis.com/auth/youtube.readonly" +
//                "&client_id="+CLIENT_ID+
//                "&redirect_uri="+clientredirectyoutube+
//                "&response_type=code&mine=true";
//        URI redirectUri = null;
//
//        System.out.println("요청 url : "+ authUrl);
//
//        try {
//            redirectUri = new URI(authUrl);
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//            log.info("check login");
//            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().build();
//    }
//
//    @RequestMapping("youtube-search")
//    @GetMapping(value = "youtube-search")
//    public ResponseEntity youtubeSearch(HttpServletRequest request){
//        String channel = request.getParameter("channel");
//        String searchUrl = "https://www.googleapis.com/youtube/v3/search?" +
//                "part=snippet&type=channel&maxResults=5&q="+ channel +
//                "&key=" + apiKey;
//
//        URI redirectUri = null;
//
//        System.out.println("요청 url : "+ searchUrl);
//
//        try {
//            redirectUri = new URI(searchUrl);
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(redirectUri);
//            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    // 로그인 성공후 , 코드
//    @GetMapping(value = "/oauth/youtube/redirect")
//    public ResponseEntity<ChannelListResponse> redirectGoogleYoutube(
//            @RequestParam(value = "code") String authCode
//    ) {
//        // HTTP 통신을 위해 RestTemplate 활용
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 이 템플릿을 통해 전달할때 필요한 자료를 담아줄려고 만든 클래스
//        GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
//                .clientId(configUtils.getClientid())
//                .clientSecret(configUtils.getClientsecret())
//                .code(authCode)
//                .redirectUri(configUtils.getClientredirectyoutube())
//                .grantType("authorization_code")
//                .build();
//
//        log.info("login try....");
//        log.info(requestParams.getCode());
//
//        try {
//            // Http Header 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
//
//            // Post 요청
//            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getAuthurl() + "/token", httpRequestEntity, String.class);
//
//            System.out.println("크리덴셜 확인");
//            System.out.println(apiResponseJson.getBody());
//
//
//            // ObjectMapper를 통해 String to Object로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});
//
//            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
//            String jwtToken = googleLoginResponse.getIdToken();
//
//            String accessToken = googleLoginResponse.getAccessToken();
//
//            //YouTube youTube = apiExample.getService(accessToken);
//
//            YouTube youTube = apiExample.getYouTubeService(accessToken);
//
//            // 여기까지 세팅
//            YouTube.Channels.List request = youTube.channels().list(Collections.singletonList("snippet,contentDetails,statistics"));
//
//            // 요청
//            ChannelListResponse response = request.setMine(true).execute();
//
//
//            System.out.println("결과값");
//            System.out.println(response);
//            System.out.println(response.toString());
//
//
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
//            YoutubeChannelList channelList = mapper.readValue(response.toString(), YoutubeChannelList.class);
//
//            System.out.println("변환 결과값");
//            System.out.println(channelList);
//            System.out.println(channelList.toString());
//
//            if(channelList.getItems().size() >0){
//                System.out.println("원하는 값 id : "+ channelList.getItems().get(0).getId());
//            }
//
//            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
//            String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.googleInitYoutubeUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();
//
//            String resultJson = restTemplate.getForObject(requestUrl, String.class);
//
//            if(resultJson != null) {
//
//                return ResponseEntity.ok().body(response);
//            }
//            else {
//                throw new Exception("Google OAuth failed!");
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.badRequest().body(null);
//    }
//
//    @GetMapping("/myPagetest")
//    public String pagetest() {
//        return "/myPagetest";
//    }
//}