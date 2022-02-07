package hello.core.order;

public interface OrderService {

    // 회원 id, 아이템 이름, 아이템 가격으로 주문 생성
    Order createOrder(Long memberId, String itemName, int itemPrice);


}
