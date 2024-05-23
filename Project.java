import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

 class Message 
{
    private LocalDateTime timestamp;
    private String sender;
    private String message;

    public Message(LocalDateTime timestamp, String sender, String message)
    {
        this.timestamp = timestamp;
        this.sender = sender;
        this.message = message;
    }

    public LocalDateTime getTimestamp() 
    {
        return timestamp;
    }

    public String getSender() 
    {
        return sender;
    }

    public String getMessage() 
    {
        return message;
    }
}
 
public class Project 
{
    private static Map<String, List<Message>> conversationsByUser = new HashMap<>();
    private static Map<String, String> userAccounts = new HashMap<>();
    private static Set<String> onlineUsers = new HashSet<>();
    private static Scanner obj = new Scanner(System.in);

    public static void main(String[] args)
    {
    	System.out.println("**************************************************************************************");
        System.out.println("*-Welcome to the Enhanced Chat Application using lambda and functional programming-*");
        System.out.println("**************************************************************************************");

        while (true)
        {
        	System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("\n1. Login\n2. Register\n3. Exit");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Choose an option: ");
            int choice = obj.nextInt();
            obj.nextLine(); 

            switch (choice) 
            {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice! Please try again.");
            }
        }
    }

    private static void login()
    {
        System.out.print("Enter username: ");
        String username = obj.nextLine();

        if (!userAccounts.containsKey(username)) 
        {
            System.out.println("User does not exist! Please register first.");
            return;
        }

        System.out.print("Enter password: ");
        String password = obj.nextLine();

        if (!userAccounts.get(username).equals(password)) 
        {
            System.out.println("Incorrect password! Please try again.");
            return;
        }

        System.out.println("Login successful!");
        startChat(username);
    }

    private static void register() 
    {
        System.out.print("Enter new username: ");
        String username = obj.nextLine();

        if (userAccounts.containsKey(username)) 
        {
            System.out.println("Username already exists! Please choose a different one.");
            return;
        }

        System.out.print("Set password: ");
        String password = obj.nextLine();

        userAccounts.put(username, password);
        System.out.println("Registration successful! You can now login.");
    }

    private static void startChat(String username) 
    {
        onlineUsers.add(username);
        while (true) 
        {
            System.out.println("\n1. Send Message\n2. Private Message\n3. Online Users\n4. Logout");
            System.out.print("Choose an option: ");
            int option = obj.nextInt();
            obj.nextLine(); // Consume newline

            switch (option) 
            {
                case 1:
                    sendMessage(username);
                    break;
                case 2:
                    sendPrivateMessage(username);
                    break;
                case 3:
                    displayOnlineUsers();
                    break;
                case 4:
                    logout(username);
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void sendMessage(String sender) 
    {
        System.out.print("Enter your message: ");
        String message = obj.nextLine();
        LocalDateTime timestamp = LocalDateTime.now();
        Message msg = new Message(timestamp, sender, message);
        conversationsByUser.computeIfAbsent(sender, k -> new ArrayList<>()).add(msg);
        printMessage(msg);
    }

    private static void sendPrivateMessage(String sender) 
    {
        System.out.print("Enter recipient's username: ");
        String recipient = obj.nextLine();

        if (!userAccounts.containsKey(recipient))
        {
            System.out.println("Recipient does not exist!");
            return;
        }

        System.out.print("Enter your message: ");
        String message = obj.nextLine();
        LocalDateTime timestamp = LocalDateTime.now();
        Message msg = new Message(timestamp, sender, message);
        conversationsByUser.computeIfAbsent(recipient, k -> new ArrayList<>()).add(msg);
        printMessage(msg);
    }

    private static void displayOnlineUsers()
    {
        System.out.println("Online Users:");
        for (String user : onlineUsers)
        {
            System.out.println(user);
        }
    }

    private static void logout(String username) 
    {
        onlineUsers.remove(username);
        System.out.println("Logout successful!");
    }

    private static void printMessage(Message message)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        ZoneId userTimeZone = ZoneId.systemDefault();

        String formattedTimestamp = message.getTimestamp().atZone(userTimeZone).format(formatter);
        System.out.println("[" + formattedTimestamp + "] " + message.getSender() + ": " + message.getMessage());
    }

    
}
