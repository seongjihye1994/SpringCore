package hello.core.singleton;

public class SingletonService {

    // 자기 자신 객체 만듦
    private static final SingletonService instance = new SingletonService();
    // static 선언 -> static 영역에 단 한개의 객체가 만들어지고 공유되어 사용됨.

    // 생성된 싱글톤 객체 조회
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자로 다른 클래스가 싱글톤 서비스 객체를 생성하는 것을 막음
    // private은 자기자신 이외의 다른 클래스에서는 가져다 사용할 수 x
    private SingletonService() {

    }

    // 다른 클래스에서 아래처럼 싱글톤 객체를 여러개 생성 하는 것을 막으려면 -> private으로 생성자 만들기!
    /* public static void main(String[] args) {
        SingletonService singletonService1 = new SingletonService();
        SingletonService singletonService2 = new SingletonService();
    } */

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
