package hello.core.singleton;

public class StatefulService {

    // private int price; // 상태를 유지하는 필드 10000 -> 20000

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        // this.price = price; // 여기가 문제!
        return price; // 지역변수로 들어오는 파라미터 그 값 자체를 리턴하면 값이 공유되지 않고 무상태로 된다.
    }

    /* public int getPrice() {
        return price;
    }*/
}
