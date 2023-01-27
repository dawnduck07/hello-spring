package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    /*
        - Optional
            - "존재할 수도 있지만 안 할 수도 있는 객체" -> "null이 될 수도 있는 객체"
            - 직접 다루기에 위험하고 까다로운 null을 담을 수 있는 특수한 그릇
        - Optional.ofNullable(value)
            - null이 넘어올 경우, NPE를 던지지 않고, Optional.empty()와 동일하게
              비어있는 Optional 객체를 얻어온다.
     */
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /*
        - stream().filter()
            - stream() : 컬렉션 데이터를 선언형으로 쉽게 처리
            - filter() : stream()에서 뽑아져 나오는 데이터에서 특정 데이터들만 골라내는 역할

        - Lambda Expression
            - (매개 변수, ...) -> { 실행문 }
                - 화살표(->)를 기준으로 왼쪽에는 람다식을 실행하기 위한 배개변수가 위치하고,
                  오른쪽에는 매개변수를 이용한 실행코드 혹은 실행 코드 블럭이 온다.
            - Example
                public int sum(int a, int b) {
                    return a + b;
                }
            - Lambda
                (a, b) -> a + b;
        - findAny()
            - 조건에 일치하는 요소 1개를 리턴한다.
     */
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();;
    }
}
