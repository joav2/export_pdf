package br.com.example.export_data.service;

import javax.transaction.Transactional;

import br.com.example.export_data.domain.User;
import br.com.example.export_data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return repo.findAll(Sort.by("email").ascending());
    }

    public void save(User user) {
        user.setEnabled(true);
        repo.save(user);
    }
}