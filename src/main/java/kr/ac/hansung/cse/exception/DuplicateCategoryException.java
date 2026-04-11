package kr.ac.hansung.cse.exception;

public class DuplicateCategoryException extends RuntimeException {

    // 같은 이름의 카테고리가 이미 있을 때 사용한다.
    public DuplicateCategoryException(String name) {
        super("이미 존재하는 카테고리입니다: " + name);
    }
}
