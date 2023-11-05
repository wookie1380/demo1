import jakarta.persistence.*;

import com.example.entity.TodoItemsEntity;
import java.util.List;
import java.util.Scanner;

public class TodoApp {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public TodoApp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("TodoListPersistenceManager");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addTodoItem(String task) {
        TodoItemsEntity todoItem = new TodoItemsEntity();
        todoItem.setTask(task);

        entityManager.getTransaction().begin();
        entityManager.persist(todoItem);
        entityManager.getTransaction().commit();
    }

    public void deleteTodoItem(int number) {
        int id = number;

        entityManager.getTransaction().begin();
        TodoItemsEntity todoItem = entityManager.find(TodoItemsEntity.class, id);
        if (todoItem != null) {
            entityManager.remove(todoItem);
            entityManager.getTransaction().commit();
        } else {
            System.out.println("Invalid number. Item not found.");
            entityManager.getTransaction().rollback();
        }
    }

    public List<TodoItemsEntity> viewTodoItems() {
        entityManager.getTransaction().begin();
        TypedQuery<TodoItemsEntity> query = entityManager.createQuery("SELECT t FROM TodoItemsEntity t", TodoItemsEntity.class);
        List<TodoItemsEntity> items = query.getResultList();
        entityManager.getTransaction().commit();

        return items; // Return the list of items
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TodoApp todoApp = new TodoApp();

        System.out.println("         ,..........   ..........,\n" +
                        "     ,..,'          '.'          ',..,\n" +
                        "    ,' ,'            :            ', ',\n" +
                        "   ,' ,'             :             ', ',\n" +
                        "  ,' ,'              :              ', ',\n" +
                        " ,' ,'............., : ,.............', ',\n" +
                        ",'  '............   '.'   ............'  ',\n" +
                        " '''''''''''''''''';''';''''''''''''''''''\n" +
                        "                    '''");
        System.out.println("Todo List Application");
        System.out.println("1. Add a to-do item");
        System.out.println("2. Delete a to-do item");
        System.out.println("3. View to-do items");
        System.out.println("4. Quit");
        while (true) {

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the task: ");
                    String task = scanner.nextLine();
                    todoApp.addTodoItem(task);
                    System.out.println("Task added successfully!");
                    break;

                case 2:
                    System.out.print("Enter the number of the task to delete: ");
                    int number = scanner.nextInt();
                    scanner.nextLine();
                    todoApp.deleteTodoItem(number);
                    break;

                case 3:
                    todoApp.viewTodoItems();
                    break;

                case 4:
                    todoApp.close();
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}