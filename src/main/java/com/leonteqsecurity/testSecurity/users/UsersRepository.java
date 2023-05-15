package com.leonteqsecurity.testSecurity.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    Optional<Users> findByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"authorities"})
    List<Users> findAll();
}