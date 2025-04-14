import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.util.List;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Comment c = new ObjectMapper().readValue(req.getReader(), Comment.class);
        DatabaseService.getInstance().addComment(c.postId, c.content);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int postId = Integer.parseInt(req.getParameter("postId"));
        List<Comment> comments = DatabaseService.getInstance().getCommentsByPostId(postId);
        resp.setContentType("application/json");
        new ObjectMapper().writeValue(resp.getWriter(), comments);
    }
}
