package com.springBlog.BlogCreater.Repository;

import com.springBlog.BlogCreater.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,String> {

}