import java.text.SimpleDateFormat;
import java.util.Date;

class Message {
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

abstract class MessageDecorator extends Message {
    protected Message message;

    public MessageDecorator(Message message) {
        super(message.getText());
        this.message = message;
    }

    public abstract String getText();
}

// Декоратор для шифрування повідомлення
class EncryptDecorator extends MessageDecorator {
    public EncryptDecorator(Message message) {
        super(message);
    }

    @Override
    public String getText() {
        // Просте шифрування: зсув кожної букви на 3 позиції
        String encryptedText = "";
        for (char c : message.getText().toCharArray()) {
            if (Character.isAlphabetic(c)) {
                encryptedText += (char) (c + 3);
            } else {
                encryptedText += c;
            }
        }
        return encryptedText;
    }
}

// Декоратор для стискання повідомлення (видалення зайвих пробілів)
class CompressDecorator extends MessageDecorator {
    public CompressDecorator(Message message) {
        super(message);
    }

    @Override
    public String getText() {
        // Стискаємо текст: видаляємо зайві пробіли
        return message.getText().replaceAll("\\s+", " ").trim();
    }
}

class DateTimeDecorator extends MessageDecorator {
    public DateTimeDecorator(Message message) {
        super(message);
    }

    @Override
    public String getText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        return message.getText() + " (Sent at: " + timestamp + ")";
    }
}

class AuthorDecorator extends MessageDecorator {
    private String author;

    public AuthorDecorator(Message message, String author) {
        super(message);
        this.author = author;
    }

    @Override
    public String getText() {
        return message.getText() + " (Author: " + author + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Message message = new Message("  Hello there, how are you doing today?  ");

        message = new EncryptDecorator(message);
        System.out.println("Encrypted message: " + message.getText());

        message = new CompressDecorator(message);
        System.out.println("Compressed message: " + message.getText());

        message = new DateTimeDecorator(message);
        System.out.println("Message with timestamp: " + message.getText());

        message = new AuthorDecorator(message, "Stanislav Dobranovskyi");
        System.out.println("Final message with author: " + message.getText());
    }
}
