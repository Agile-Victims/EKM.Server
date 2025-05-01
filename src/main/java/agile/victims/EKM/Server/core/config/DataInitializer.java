package agile.victims.EKM.Server.core.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import agile.victims.EKM.Server.entity.Admin;
import agile.victims.EKM.Server.entity.User;
import agile.victims.EKM.Server.repository.AdminRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(AdminRepository adminRepository) {
        return args -> {
            String defaultAdminEmail = "admin@gmail.com";
            String defaultAdminPassword = "123Admin-456";

            boolean adminExists = adminRepository.findByEmail(defaultAdminEmail).isPresent();

            if (!adminExists) {
                Admin admin = new Admin();
                admin.setName("Default");
                admin.setSurname("Admin");
                admin.setEmail(defaultAdminEmail);
                admin.setPassword(defaultAdminPassword);
                
                // Rolü ADMIN olarak setleyelim:
                admin.setRole(User.Role.ADMIN);

                adminRepository.save(admin);
                System.out.println("Default admin created!");
            } else {
                System.out.println("Admin already exists, skipping creation.");
            }
        };
    }
}
