package bankBackend.bank.service;
import bankBackend.bank.model.Transaction;
import bankBackend.bank.model.User;
import bankBackend.bank.repository.TransactionRepository;
import bankBackend.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setSavings(userDetails.getSavings());
            user.setStatus(userDetails.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Transaction createTransaction(Long userId, Transaction transactionDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Deduct savings
        if (user.getSavings().compareTo(transactionDetails.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        user.setSavings(user.getSavings().subtract(transactionDetails.getAmount()));

        transactionDetails.setUser(user);
        transactionDetails.setTransactionDate(LocalDateTime.now());

        userRepository.save(user);
        return transactionRepository.save(transactionDetails);
    }

    // Transfer money to another user
    public Transaction transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getSavings().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct from sender and add to receiver
        sender.setSavings(sender.getSavings().subtract(amount));
        receiver.setSavings(receiver.getSavings().add(amount));

        // Create transaction record
        Transaction senderTransaction = new Transaction(amount.negate(), "Transfer to " + receiver.getUsername(), LocalDateTime.now(), sender);
        Transaction receiverTransaction = new Transaction(amount, "Received from " + sender.getUsername(), LocalDateTime.now(), receiver);

        userRepository.save(sender);
        userRepository.save(receiver);
        transactionRepository.save(senderTransaction);
        transactionRepository.save(receiverTransaction);

        return senderTransaction;
    }
}