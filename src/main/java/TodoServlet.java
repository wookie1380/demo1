import com.example.entity.TodoItemsEntity;
import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TodoServlet", value = "/todo")
public class TodoServlet extends HttpServlet {
    private EntityManagerFactory emf;
    private EntityManager em;

    public void init() {
        emf = Persistence.createEntityManagerFactory("TodoListPersistenceManager");
        em = emf.createEntityManager();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            deleteTodoItem(id);
            // Redirect to view page after deletion
            response.sendRedirect("jsp/viewTodos.jsp");
        } else {
            // Default action is to view to-do items
            List<TodoItemsEntity> items = viewTodoItems();
            request.setAttribute("items", items);
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/viewTodos.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String task = request.getParameter("task");
            addTodoItem(task);
            // Redirect to view page after adding
            response.sendRedirect("jsp/viewTodos.jsp");
        }
    }

    private void addTodoItem(String task) {
        TodoItemsEntity todoItem = new TodoItemsEntity();
        todoItem.setTask(task);
        em.getTransaction().begin();
        em.persist(todoItem);
        em.getTransaction().commit();
    }

    private void deleteTodoItem(int id) {
        em.getTransaction().begin();
        TodoItemsEntity todoItem = em.find(TodoItemsEntity.class, id);
        if (todoItem != null) {
            em.remove(todoItem);
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
        }
    }

    private List<TodoItemsEntity> viewTodoItems() {
        TypedQuery<TodoItemsEntity> query = em.createQuery("SELECT t FROM TodoItemsEntity t", TodoItemsEntity.class);
        return query.getResultList();
    }

    public void destroy() {
        em.close();
        emf.close();
    }
}
