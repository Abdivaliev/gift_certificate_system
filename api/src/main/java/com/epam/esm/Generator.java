package com.epam.esm;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.service.CRDService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class Generator implements CommandLineRunner {
    private final ResourceLoader resourceLoader;
    private final CRDService<TagDto> tagService;
    private final OrderService orderService;
    private final CRDService<GiftCertificateDto> giftCertificateCRDService;
    private final UserService userService;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws IOException {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            generateAdmin();
            save1000Users();
            save1000Tags();
            save10000Gifts();
            save100Orders();
        }

    }

    private void generateAdmin() {
        UserDto user = new UserDto();
        user.setName("administrator");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);
        userService.signUp(user);
    }

    private void save1000Tags() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:google-10000-english.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            int limit = 0;
            while ((line = reader.readLine()) != null && limit < 1000) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    TagDto tagDto = new TagDto();
                    tagDto.setName(word);
                    tagService.save(tagDto);
                    limit++;
                }
            }
        }
    }

    private void save1000Users() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:google-10000-english.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            int limit = 1;
            while ((line = reader.readLine()) != null && limit < 2000) {

                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (limit > 999) {
                        UserDto userDto = new UserDto();
                        userDto.setName(word);
                        userDto.setUsername(word);
                        userDto.setPassword(word);
                        userService.signUp(userDto);
                    }
                    limit++;
                }
            }
        }
    }

    private void save10000Gifts() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:google-10000-english.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] words = line.split("\\s+");
                for (String word : words) {
                    TagDto tagDto = null;
                    int random = new Random().nextInt(100) + 1;
                    int randomPrice = new Random().nextInt(1000) + 10;
                    int randomScale = new Random().nextInt(4) + 1;

                    GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
                    giftCertificateDto.setName(word);
                    giftCertificateDto.setDuration(random);
                    Set<TagDto> setTag = new HashSet<>();
                    for (int i = 0; i < randomScale; i++) {
                        int randomTag = new Random().nextInt(500) + 1;
                        tagDto = tagService.findById(randomTag);
                        setTag.add(tagDto);
                    }
                    giftCertificateDto.setTags(setTag);
                    giftCertificateDto.setPrice(BigDecimal.valueOf(randomPrice));
                    giftCertificateDto.setDescription(word + "  " + randomScale + "  " + tagDto.getName());
                    giftCertificateCRDService.save(giftCertificateDto);
                }
            }
        }
    }
    private void save100Orders() {

        for (int i = 0; i < 1000; i++) {
            int randomUser = new Random().nextInt(1000) + 1;
            int randomGift = new Random().nextInt(10000) + 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setUserId(userService.findById(randomUser).getId());
            GiftCertificateDto certificateDto = giftCertificateCRDService.findById(randomGift);
            orderDto.setGiftCertificateId(certificateDto.getId());
            orderDto.setPurchaseCost(certificateDto.getPrice());
            orderService.save(orderDto);
        }
    }
}