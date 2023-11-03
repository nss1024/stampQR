package com.stampqr.dbaccessService.repos;

import com.stampqr.dbaccessService.resources.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
}
