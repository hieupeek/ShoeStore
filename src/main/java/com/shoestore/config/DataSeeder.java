package com.shoestore.config;

import com.shoestore.domain.*;
import com.shoestore.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    // Tiêm các Repository vào để dùng
    public DataSeeder(RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            SizeRepository sizeRepository,
            ColorRepository colorRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.sizeRepository = sizeRepository;
        this.colorRepository = colorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Tạo Roles (Nếu chưa có)
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setName("ROLE_USER");

            Role staffRole = new Role();
            staffRole.setName("ROLE_STAFF");

            roleRepository.saveAll(Arrays.asList(adminRole, userRole, staffRole));
            System.out.println(">>> Đã khởi tạo Roles thành công!");
        }

        // 2. Tạo Tài khoản Admin (Nếu chưa có)
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setFullName("Administrator");
            admin.setEmail("admin@shoestore.com");
            // Mật khẩu là 123456 (đã mã hóa)
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setStatus(1); // Active

            // Gán quyền Admin
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
            admin.setRole(adminRole);

            userRepository.save(admin);
            System.out.println(">>> Đã tạo tài khoản Admin: admin / 123456");
        }

        // 3. Tạo Tài khoản Khách test (Nếu chưa có)
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setFullName("Khách Hàng Mẫu");
            user.setEmail("user@shoestore.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setStatus(1);

            Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
            user.setRole(userRole);

            userRepository.save(user);
            System.out.println(">>> Đã tạo tài khoản User: user / 123456");
        }

        // 4. Tạo Danh mục (Category) mẫu
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Giày Sneaker"));
            categoryRepository.save(new Category(null, "Giày Tây"));
            categoryRepository.save(new Category(null, "Giày Boot"));
            System.out.println(">>> Đã tạo Categories mẫu");
        }

        // 5. Tạo Thương hiệu (Brand) mẫu
        if (brandRepository.count() == 0) {
            brandRepository.save(new Brand(null, "Nike"));
            brandRepository.save(new Brand(null, "Adidas"));
            brandRepository.save(new Brand(null, "Puma"));
            brandRepository.save(new Brand(null, "Biti's Hunter"));
            System.out.println(">>> Đã tạo Brands mẫu");
        }

        // 6. Tạo Size & Màu mẫu
        if (sizeRepository.count() == 0) {
            sizeRepository.saveAll(Arrays.asList(
                    new Size(null, "39"), new Size(null, "40"),
                    new Size(null, "41"), new Size(null, "42")));
        }

        if (colorRepository.count() == 0) {
            colorRepository.save(new Color(null, "Đen", "#000000"));
            colorRepository.save(new Color(null, "Trắng", "#FFFFFF"));
            colorRepository.save(new Color(null, "Đỏ", "#FF0000"));
        }
    }
}