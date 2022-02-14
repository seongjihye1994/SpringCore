package hello.core.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient /*implements InitializingBean, DisposableBean*/ { // 임의의 네트워크 클라이언트 생성

    private String url; // 접속해야 할 서버 url

    // 디폴트 생성자
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    // 외부에서 url을 세터로 수정
    public void setUrl(String  url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() { // 서비스 시작 시 접속 할 서버 url에 붙도록 설정
        System.out.println("connect: " + url);
    }

    // 연결이 된 상태에서 메세지를 연결 된 서버에 던질 수 있도록 설정
    public void call(String message) {
        System.out.println("call: " + url + "message = " + message);
        // 어떤 url에 콜을 했는지, 메세지는 무엇인지 출력
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    // InitializingBean 인터페이스 구현 메소드 오버라이드
    // InitializingBean: 빈 초기화 하는 인터페이스, 빈이 생성되고 의존관계가 주입된 이후에 호출되어 빈을 초기화한다.
/*    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        // 빈 생성 > 의존관계 주입 이후
        // 서버와 연결
        connect();
        // 서버와 연결 된 상태에서 메세지 전달
        call("초기화 연결 메세지");
    }*/

    // DisposableBean 인터페이스 구현 메소드 오버라이드
    // DisposableBean: 빈이 소멸 하기 직전 호출되어 빈을 소멸한다.
/*    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect(); // 서버와 연결을 끊음
    }*/

    @PostConstruct // 빈 초기화 어노테이션
    public void init() {
        System.out.println("NetworkClient.init");
        // 빈 생성 > 의존관계 주입 이후
        // 서버와 연결
        connect();
        // 서버와 연결 된 상태에서 메세지 전달
        call("초기화 연결 메세지");
    }

    @PreDestroy // 빈 소멸 어노테이션
    public void close()  {
        System.out.println("NetworkClient.close");
        disconnect(); // 서버와 연결을 끊음
    }
}
