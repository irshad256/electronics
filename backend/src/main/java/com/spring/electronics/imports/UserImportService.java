package com.spring.electronics.imports;

import com.spring.electronics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImportService {

    private final UserRepository userRepository;

    public void importOrUpdateUsers(String filePath) throws Exception {
        List<String[]> csvData = CsvUtils.parseCsv(filePath);
        for (String[] row : csvData) {
            if (!row[0].equalsIgnoreCase("username")) { // Skip header row
//                User user = new User();
//                user.setUsername(row[0]);
//                user.setFirstName(row[1]);
//                user.setLastName(row[2]);
//                user.setEmail(row[3]);
//                user.setPassword(row[4]); // You should hash the password

//                userRepository.save(user);
            }
        }
    }
}
