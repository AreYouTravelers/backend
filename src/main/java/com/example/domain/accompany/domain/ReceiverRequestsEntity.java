//package com.example.domain.accompany.domain;
//
//import com.example.domain.blackList.domain.Blacklist;
//import com.example.domain.users.domain.Users;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity // 클래스 위에 선언하여 이 클래스가 엔티티임을 알려준다. 이렇게 되면 JPA에서 정의된 필드들을 바탕으로 데이터베이스에 테이블을 만들어준다.
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor // 파라미터가 아예없는 기본생성자를 자동으로 만들어준다.
//@Table(name = "receiver_requests") // 생성되는 테이블의 이름을 "receiver_requests"로
//public class ReceiverRequestsEntity {
//    @Id // 해당 엔티티의 PK가 될 값을 지정해주는 어노테이션
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK가 자동으로 1씩 증가하는 형태로 생성될지 등을 결정해주는 어노테이션
//    private Long id; // 동행 모집 ID
//
//    @Column
//    private Boolean status;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "rejected_at")
//    private LocalDateTime rejectedAt;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    private Users sender;
//
//    @ManyToOne
//    @JoinColumn(name = "receiver_id")
//    private Users receiver;
//
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Blacklist.BoardsEntity board;
//}
