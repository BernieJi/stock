package com.boin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*
方法命名規則：https://blog.csdn.net/sbin456/article/details/53304148
1. 查詢方法以 find | read | get 開頭
2. 涉及條件查詢時，條件的屬性(首字母大寫)用條件關鍵字連結
3. 若要使用集聯屬性，則屬性之間使用 _ 進行連結
*/

//@Repository
//public interface UsersRepository extends JpaRepository<Users, Integer> {
//
//	Users getByUsername(String username);
//
//
//
//}
