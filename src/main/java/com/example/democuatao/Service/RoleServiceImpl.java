package com.example.democuatao.Service;

import com.example.democuatao.model.Role;
import com.example.democuatao.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService{
    private final RoleRepo roleRepo;
    @Override
    public List<Role> getAll() {
        return roleRepo.findAll();
    }
}
