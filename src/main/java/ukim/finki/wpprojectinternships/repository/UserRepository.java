package ukim.finki.wpprojectinternships.repository;


import ukim.finki.wpprojectinternships.model.User;

import java.util.Optional;

public interface UserRepository extends JpaSpecificationRepository<User,String> {
//  User findByName(String username);
 //Optional<User> findById(String username);
    //ne ni rabotese deka sme pisale <user,long> a klucot ni e string i nemase kako da se sporedi vo facultyuserdetails
}
