//package com.example.domain.reviews.repository;
//
//import com.example.domain.users.domain.Users;
//import com.example.domain.reviews.domain.Reviews;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
//    @Query(value = "SELECT r FROM Reviews r JOIN r.board BoardsEntity WHERE r.board.id = BoardsEntity.id")
//    List<Reviews> findAllByBoardId(Long id);
//    List<Reviews> findAllBySenderId(Long senderId);
//
//    List<Reviews> findAllByReceiverId(Long id);
//
//    Page<Reviews> findAllBySender(Optional<Users> usersEntity, Pageable pageable);
//
//    Page<Reviews> findAllByReceiver(Optional<Users> user, Pageable pageable);
//}