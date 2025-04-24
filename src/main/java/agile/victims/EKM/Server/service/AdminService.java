package agile.victims.EKM.Server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agile.victims.EKM.Server.repository.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    AdminRepository adminRepository;



    
}
